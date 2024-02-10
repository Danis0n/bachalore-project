package ru.fp.coreservice.client.transaction;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = "transaction", name = "profile", havingValue = "camunda")
@RequiredArgsConstructor
public class CamundaTransactionClient implements TransactionClient {

    private final RuntimeService runtimeService;
    final String PROCESS_KEY = "camunda_transfer_process";
    Map<String, Object> variables;

    @Override
    public void proceedTransaction(Pacs008Dto pacs008, IncomingMessage incomingMessage) {
        variables = new HashMap<>();
        variables.put("pacs008", pacs008);
        variables.put("incomingMessage", incomingMessage);
        runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables);
    }
}
