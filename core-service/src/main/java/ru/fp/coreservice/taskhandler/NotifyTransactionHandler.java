package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyTransactionHandler implements JavaDelegate {

    private final TransactionService transactionService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: NotifyTransaction");

        val transaction = (Transaction) execution.getVariable("transaction");
        String accountCdCode = (String) execution.getVariable("accountCdCode");
        String accountDbCode = (String) execution.getVariable("accountDbCode");

        transactionService.handleTransactionNotification(
                transaction, accountCdCode, accountDbCode
        );
    }

}
