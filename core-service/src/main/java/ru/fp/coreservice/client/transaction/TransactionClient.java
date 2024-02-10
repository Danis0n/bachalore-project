package ru.fp.coreservice.client.transaction;

import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

public interface TransactionClient {

    void proceedTransaction(Pacs008Dto pacs008, IncomingMessage incomingMessage);

}
