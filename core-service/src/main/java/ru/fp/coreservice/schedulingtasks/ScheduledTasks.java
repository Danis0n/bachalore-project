package ru.fp.coreservice.schedulingtasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.entity.TransferOutbox;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.grpc.client.receipt.ReceiptGrpcClient;
import ru.fp.coreservice.grpc.client.transaction.TransactionGrpcClient;
import ru.fp.coreservice.repository.TransferOutboxRepository;
import ru.fp.coreservice.service.PayDocsService;
import ru.fp.transaction.proto.TransactionRequest;
import ru.fp.transaction.proto.Transfer;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final TransferOutboxRepository transferOutboxRepository;

    private final TransactionGrpcClient transactionGrpcClient;
    private final ReceiptGrpcClient receiptGrpcClient;
    private final PayDocsService payDocsService;

    @Scheduled(cron = "${scheduled-tasks.transfer-outbox}")
    public void scheduleTransferOutbox() {
        log.info("Schedule TransferOutbox");
        val transfers = transferOutboxRepository.findFirst10OrderByValueDateAsc();
        if (transfers.isEmpty()) {
            return;
        }

        val requestBuilder = TransactionRequest.newBuilder();
        for (TransferOutbox transfer : transfers) {
            Transfer tr = Transfer.newBuilder()
                    .setAmount(transfer.getAmount().doubleValue())
                    .setBicCd(transfer.getBicCd())
                    .setBicDb(transfer.getBicDb())
                    .setCurrency(transfer.getCurrencyCode())
                    .setValueDate(transfer.getValueDate().toString())
                    .setGuid(transfer.getUuid().toString())
                    .build();
            requestBuilder.addTransfers(tr);
        }
        val request = requestBuilder.build();

        val transactionResponse = transactionGrpcClient.sendTransaction(request);
        val receiptResponse = receiptGrpcClient.sendReceipt(request);

        if (transactionResponse != null && transactionResponse.getSuccess() &&
                receiptResponse != null && receiptResponse.getSuccess()) {
            for (var transfer : transfers) {
                PayDoc paydoc = payDocsService.findPaydocByGuid(transfer.getUuid());
                payDocsService.moveNextStep(paydoc);
            }
            transferOutboxRepository.deleteAllInBatch(transfers);
        }
    }
}
