package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.dto.TransactionDto;
import ru.fp.coreservice.dto.VerifiedAccountsDto;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Balances;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.entity.TransferOutbox;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessageStatus;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.entity.transaction.TransactionStatus;
import ru.fp.coreservice.exception.BadRequestException;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.TransactionRepository;
import ru.fp.coreservice.repository.TransferOutboxRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BalancesService balancesService;
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final ParticipantService participantService;
    private final PayDocsService payDocsService;
    private final TransferOutboxRepository transferOutboxRepository;

    public List<TransactionDto> findLatestTransactions(String bic, Integer limit) {

        Participant participant = participantService.findParticipantByBicOrThrow(bic);

        List<Account> accounts = accountService.findAccountsByParticipant(participant);

        List<String> accountCodes = accounts
                .stream()
                .map(Account::getCode)
                .toList();

        List<PayDoc> payDocs = payDocsService.findPaydocsByAccountsAndLimit(accountCodes, limit);

        return payDocs
                .stream()
                .map(payDoc -> TransactionDto.builder()
                        .creditAccount(payDoc.getCreditAcc())
                        .debitAccount(payDoc.getDebitAcc())
                        .amount(payDoc.getAmount().toString())
                        .currency(payDoc.getCurrency().getCode())
                        .time(payDoc.getValueDate())
                        .build())
                .toList();
    }

    @Transactional
    public Transaction handleTransactionCreation(Pacs008Dto pacs008, PayDoc payDoc) {
        Transaction transaction = create(pacs008);
        transaction.setPayDoc(payDoc);
        payDocsService.moveNextStep(payDoc);
        return transaction;
    }

    private Transaction create(Pacs008Dto pacs008Dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(pacs008Dto.getValue());
        transaction.setValueDate(Timestamp.valueOf(pacs008Dto.getDateTime()));
        transaction.setStatus(TransactionStatus.ACTIVATED);
        return transactionRepository.save(transaction);
    }

    @Transactional(noRollbackFor = BadRequestException.class)
    public VerifiedAccountsDto handleTransactionActivation(Pacs008Dto pacs008, Transaction transaction,
                                                           IncomingMessage incomingMessage) {
        VerifiedAccountsDto verifiedAccounts = verifyAccounts(pacs008);

        if (verifiedAccounts.getError() != null) {

            String error = verifiedAccounts.getError();
            transaction.setError(error);
            moveNextStep(transaction, true);
            incomingMessage.setStatus(IncomingMessageStatus.REJECT);
            throw new BadRequestException(error);
        }

        PayDoc payDoc = transaction.getPayDoc();
        payDoc.setCreditAcc(verifiedAccounts.getAccountCdCode());
        payDoc.setDebitAcc(verifiedAccounts.getAccountDbCode());

        moveNextStep(transaction, false);
        return verifiedAccounts;
    }

    private VerifiedAccountsDto verifyAccounts(Pacs008Dto pacs008) {
        Currency currency = currencyService.findCurrencyByCodeOrThrow(pacs008.getCurrency());
        Account accountCd = findAccount(pacs008.getCodeCd(), pacs008.getBicCd(), currency);
        Account accountDb = findAccount(pacs008.getCodeDb(), pacs008.getBicDb(), currency);

        return (accountService.isVerified(accountCd) && accountService.isVerified(accountDb)) ?
                VerifiedAccountsDto.builder().
                        accountCdCode(accountCd.getCode()).
                        accountDbCode(accountDb.getCode()).
                        build() :
                VerifiedAccountsDto.builder()
                        .error("Account verification went wrong!")
                        .build();
    }

    private Account findAccount(String code, String bic, Currency currency) {
        if (code != null) {
            return accountService.findAccountByCodeAndCurrencyOrNull(code, currency);
        } else {
            Participant participant = participantService.findParticipantByBicOrThrow(bic);
            return accountService.findAccountByBicAndCurrency(participant, currency);
        }
    }

    @Transactional(noRollbackFor = BadRequestException.class)
    public void handleTransactionExecution(Pacs008Dto pacs008, Transaction transaction,
                                                                   String accountCdCode, String accountDbCode,
                                                                   IncomingMessage incomingMessage) {

        if (!transaction.getStatus().equals(TransactionStatus.ACTIVATED)) {
            log.info("Transaction with id {} step: X guaranteed", transaction.getId());
            incomingMessage.setStatus(IncomingMessageStatus.REJECT);
            throw new BadRequestException("Transaction status mismatch!");
        }

        Currency currency = currencyService.findCurrencyByCodeOrThrow(pacs008.getCurrency());
        Account accountCd = accountService.findAccountByCodeAndCurrencyOrThrow(accountCdCode, currency);
        Balances balancesCd = verifyBalancesForCredit(
                accountCd, pacs008.getValue()
        );

        if (balancesCd == null) {
            String error = "Balance from credit bic is less than required amount (" + pacs008.getValue() + ")";
            transaction.setError(error);
            moveNextStep(transaction, true);

            incomingMessage.setStatus(IncomingMessageStatus.REJECT);
            throw new BadRequestException(error);
        }

        Account accountDb = accountService.findAccountByCodeAndCurrencyOrThrow(accountDbCode, currency);
        Balances balancesDb = findBalancesByAccount(accountDb);

        if (balancesDb == null) {
            String error = "Balance from debit bic is not exists";
            transaction.setError(error);
            moveNextStep(transaction, true);

            incomingMessage.setStatus(IncomingMessageStatus.REJECT);
            throw new NotFoundException(error);
        }

        proceedTransaction(pacs008.getValue(), balancesCd, balancesDb);

        PayDoc payDoc = transaction.getPayDoc();
        payDocsService.finishPayDoc(payDoc);
        incomingMessage.setStatus(IncomingMessageStatus.RECEIVE);
        moveNextStep(transaction, null);
    }

    @Transactional
    public void handleTransactionNotification(Transaction transaction, String accountCdCode,
                                              String accountDbCode) {
        val payDoc = transaction.getPayDoc();
        val accountCd = accountService.findAccountByCodeAndCurrencyOrThrow(accountCdCode, payDoc.getCurrency());
        val accountDb = accountService.findAccountByCodeAndCurrencyOrThrow(accountDbCode, payDoc.getCurrency());
        
        TransferOutbox transferOutbox = new TransferOutbox();
        transferOutbox.setBicCd(accountCd.getParticipant().getBic());
        transferOutbox.setBicDb(accountDb.getParticipant().getBic());
        transferOutbox.setValueDate(payDoc.getValueDate());
        transferOutbox.setAmount(payDoc.getAmount());
        transferOutbox.setCurrencyCode(payDoc.getCurrency().getCode());
        transferOutbox.setUuid(payDoc.getGuid());

        transferOutbox = transferOutboxRepository.save(transferOutbox);
        log.info("TransferOutbox was saved under id {}", transferOutbox.getId());
    }

    public void proceedTransaction(BigDecimal value, Balances balancesCd, Balances balancesDb) {
        balancesService.proceedBalanceUpdate(balancesCd, value.negate());
        balancesService.proceedBalanceUpdate(balancesDb, value);
    }

    public Balances verifyBalancesForCredit(Account accountCd, BigDecimal value) {
        Balances balancesCd = balancesService.findBalancesByAccountOrNull(accountCd);

        return balancesCd.getAmount().compareTo(value) >= 0 ? balancesCd : null;
    }

    public Balances findBalancesByAccount(Account accountDb) {
        return balancesService.findBalancesByAccountOrNull(accountDb);
    }

    private void moveNextStep(Transaction transaction, Boolean isRejected) {
        if (isRejected != null) {
            if (isRejected) {
                transaction.setStatus(TransactionStatus.REJECTED);
            } else {
                transaction.setStatus(TransactionStatus.ACTIVATED);
            }
        } else {
            transaction.setStatus(transaction.getStatus().nextStep());
        }

        log.info("Transaction with id {} step: {}", transaction.getId(), transaction.getStatus());
    }

}
