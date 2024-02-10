package ru.fp.billingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.billingservice.dto.DescriptorDto;
import ru.fp.billingservice.entity.CalculationStatus;
import ru.fp.billingservice.entity.Results;
import ru.fp.billingservice.entity.decriptor.Descriptor;
import ru.fp.billingservice.entity.inbox.TransferInbox;
import ru.fp.billingservice.entity.transaction.Transaction;
import ru.fp.billingservice.entity.transaction.TransactionStatus;
import ru.fp.billingservice.repository.CurrencyRepository;
import ru.fp.billingservice.repository.DescriptorRepository;
import ru.fp.billingservice.repository.ParticipantRepository;
import ru.fp.billingservice.repository.TransactionRepository;
import ru.fp.billingservice.repository.ResultsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommissionService {

    private final ParticipantRepository participantRepository;
    private final DescriptorRepository descriptorRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final ResultsRepository resultsRepository;

    private static final String DEFAULT_DESCRIPTION = "Сервисный сбор";

    public List<DescriptorDto> getDescriptors() {
        return descriptorRepository.findAll().stream()
                .map(this::createDescriptor)
                .toList();
    }

    private DescriptorDto createDescriptor(final Descriptor entity) {
        return DescriptorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .rate(entity.getRate())
                .maxPercent(entity.getMaxPercent())
                .minPercent(entity.getMinPercent())
                .build();
    }

    @Transactional
    public void commissionCalculation(TransferInbox transfer) {
        val descriptor = descriptorRepository.findByName(DEFAULT_DESCRIPTION);
        val senderParticipant = participantRepository.findByBic(transfer.getBicDb());
        val receiverParticipant = participantRepository.findByBic(transfer.getBicCd());
        val currency = currencyRepository.findByName(transfer.getCurrencyCode());

        Transaction transaction = new Transaction();
        transaction.setUuid(transfer.getUuid());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(transfer.getAmount());
        transaction.setTransactionDate(transfer.getValueDate());
        senderParticipant.ifPresent(transaction::setSenderParticipant);
        receiverParticipant.ifPresent(transaction::setReceiverParticipant);
        transaction.setCurrency(currency);

        transaction = transactionRepository.save(transaction);
        log.info("Transaction was saved under id {}", transaction.getId());

        val commissionAmount = transfer.getAmount()
                .multiply(descriptor.getRate().add(BigDecimal.valueOf(100)))
                .divide(BigDecimal.valueOf(100), RoundingMode.DOWN);

        Results results = new Results();
        results.setTransaction(transaction);
        results.setDescriptor(descriptor);
        results.setCommissionAmount(commissionAmount);
        results.setStatus(CalculationStatus.PROCESSED);
        results.setDate(Timestamp.valueOf(LocalDateTime.now()));
        senderParticipant.ifPresent(results::setSenderParticipant);
        receiverParticipant.ifPresent(results::setReceiverParticipant);

        results = resultsRepository.save(results);
        log.info("Result of calculating the commission ont transaction with id {} was saved under id {}",
                results.getId(), transaction.getId());
    }

}
