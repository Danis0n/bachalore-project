package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.dto.VerifiedAccountsDto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivateTransactionHandler implements JavaDelegate {

    private final TransactionService transactionService;
    Map<String, Object> variables;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: ActivateTransaction");
        Pacs008Dto pacs008 = (Pacs008Dto) execution.getVariable("pacs008");
        Transaction transaction = (Transaction) execution.getVariable("transaction");
        IncomingMessage incomingMessage = (IncomingMessage) execution.getVariable("incomingMessage");

        VerifiedAccountsDto verifiedAccounts = transactionService
                .handleTransactionActivation(pacs008, transaction, incomingMessage);

        variables = new HashMap<>();
        variables.put("accountCdCode", verifiedAccounts.getAccountCdCode());
        variables.put("accountDbCode", verifiedAccounts.getAccountDbCode());
        execution.setVariables(variables);
    }

}
