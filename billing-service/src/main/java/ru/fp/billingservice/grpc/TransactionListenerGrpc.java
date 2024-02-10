package ru.fp.billingservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.fp.billingservice.entity.inbox.TransferInbox;
import ru.fp.billingservice.entity.inbox.TransferInboxStatus;
import ru.fp.billingservice.repository.TransferInboxRepository;
import ru.fp.transaction.proto.TransactionRequest;
import ru.fp.transaction.proto.TransactionResponse;
import ru.fp.transaction.proto.TransactionServiceGrpc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@GrpcService
@AllArgsConstructor
public class TransactionListenerGrpc extends TransactionServiceGrpc.TransactionServiceImplBase {
    private final TransferInboxRepository transferInboxRepository;

    @Override
    public void transferTransaction(TransactionRequest request,
                                   StreamObserver<TransactionResponse> responseObserver) {
        log.info("Received transfers: {}", request.getTransfersCount());
        val transfers = request.getTransfersList();
        val transferSequence = new ArrayList<TransferInbox>(request.getTransfersCount());
        for (var transfer: transfers) {
            val tr = new TransferInbox();
            tr.setAmount(BigDecimal.valueOf(transfer.getAmount()));
            tr.setBicCd(transfer.getBicCd());
            tr.setBicDb(transfer.getBicDb());
            tr.setCurrencyCode(transfer.getCurrency());
            tr.setValueDate(Timestamp.valueOf(transfer.getValueDate()));
            tr.setUuid(UUID.fromString(transfer.getGuid()));
            tr.setStatus(TransferInboxStatus.NEW);
            transferSequence.add(tr);
        }
        transferInboxRepository.saveAll(transferSequence);

        TransactionResponse response = TransactionResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
