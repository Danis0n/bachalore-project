package ru.di.receiptservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.di.receiptservice.entity.Receipt;
import ru.di.receiptservice.entity.ReceiptStatus;
import ru.di.receiptservice.repository.ReceiptRepository;
import ru.fp.receipt.proto.ReceiptRequest;
import ru.fp.receipt.proto.ReceiptResponse;
import ru.fp.receipt.proto.ReceiptServiceGrpc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@GrpcService
@AllArgsConstructor
public class ReceiptListenerGrpc extends ReceiptServiceGrpc.ReceiptServiceImplBase {

    private final ReceiptRepository receiptRepository;

    @Override
    public void transferReceipt(ReceiptRequest request,
                                StreamObserver<ReceiptResponse> responseObserver) {
        log.info("Received transfers: {}", request.getTransfersCount());
        val transfers = request.getTransfersList();
        ArrayList<Receipt> receipts = new ArrayList<>(request.getTransfersCount());

        for (var transfer : transfers) {
            Receipt receipt = new Receipt();
            receipt.setAmount(BigDecimal.valueOf(transfer.getAmount()));
            receipt.setBicCd(transfer.getBicCd());
            receipt.setBicDb(transfer.getBicDb());
            receipt.setCurrencyCode(transfer.getCurrency());
            receipt.setDate(Timestamp.valueOf(transfer.getValueDate()));
            receipt.setUuid(UUID.fromString(transfer.getGuid()));
            receipt.setStatus(ReceiptStatus.NEW);
            receipts.add(receipt);
        }
        receiptRepository.saveAll(receipts);

        ReceiptResponse response = ReceiptResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
