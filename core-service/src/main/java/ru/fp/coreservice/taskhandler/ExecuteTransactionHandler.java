package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecuteTransactionHandler implements JavaDelegate {

    private final TransactionService transactionService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: ExecuteTransaction");
        Pacs008Dto pacs008 = (Pacs008Dto) execution.getVariable("pacs008");
        Transaction transaction = (Transaction) execution.getVariable("transaction");
        String accountCd = (String) execution.getVariable("accountCdCode");
        String accountDb = (String) execution.getVariable("accountDbCode");
        IncomingMessage incomingMessage = (IncomingMessage) execution.getVariable("incomingMessage");

        synchronized (transactionService) {
            transactionService.handleTransactionExecution(pacs008, transaction,
                    accountCd, accountDb, incomingMessage);
        }

    }

}
