package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.service.PayDocsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePayDocHandler implements JavaDelegate {

    private final PayDocsService payDocsService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: CreatePayDoc");
        Pacs008Dto pacs008 = (Pacs008Dto) execution.getVariable("pacs008");
        IncomingMessage incomingMessage = (IncomingMessage) execution.getVariable("incomingMessage");

        PayDoc payDoc = payDocsService.createPaydocHandler(pacs008, incomingMessage);

        execution.setVariable("payDoc", payDoc);
    }

}
