package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTransactionHandler implements JavaDelegate {

    private final TransactionsService transactionsService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: CreateTransaction");
        Pacs008Dto pacs008 = (Pacs008Dto) execution.getVariable("pacs008");
        PayDoc payDoc = (PayDoc) execution.getVariable("payDoc");

        Transaction transaction = transactionsService
                .handleTransactionCreation(pacs008, payDoc);

        execution.setVariable("transaction", transaction);
    }

}
