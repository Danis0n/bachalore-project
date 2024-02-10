package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionsService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyTransactionHandler implements JavaDelegate {

    private final TransactionsService transactionsService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: NotifyTransaction");

        val transaction = (Transaction) execution.getVariable("transaction");
        String accountCdCode = (String) execution.getVariable("accountCdCode");
        String accountDbCode = (String) execution.getVariable("accountDbCode");

        transactionsService.handleTransactionNotification(
                transaction, accountCdCode, accountDbCode
        );
    }

}
