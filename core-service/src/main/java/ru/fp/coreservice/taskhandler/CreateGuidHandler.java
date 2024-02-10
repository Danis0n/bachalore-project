package ru.fp.coreservice.taskhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.service.PayDocsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateGuidHandler implements JavaDelegate {

    private final PayDocsService payDocsService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("JavaDelegate: CreateGuid");
        PayDoc payDoc = (PayDoc) execution.getVariable("payDoc");

        payDocsService.GuidCreateHandler(payDoc);

        execution.setVariable("payDoc", payDoc);
    }

}
