package ru.fp.receiptservice.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.fp.receipt.proto.ReceiptRequest;
import ru.fp.receipt.proto.ReceiptResponse;
import ru.fp.receipt.proto.ReceiptServiceGrpc;
import ru.fp.receiptservice.entity.Participant;
import ru.fp.receiptservice.entity.Receipt;
import ru.fp.receiptservice.entity.ReceiptStatus;
import ru.fp.receiptservice.repository.ReceiptRepository;
import ru.fp.receiptservice.service.ParticipantService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@GrpcService
@AllArgsConstructor
public class ReceiptListenerGrpc extends ReceiptServiceGrpc.ReceiptServiceImplBase {

    private final ReceiptRepository receiptRepository;
    private final ParticipantService participantService;

    @Override
    public void transferReceipt(ReceiptRequest request,
                                StreamObserver<ReceiptResponse> responseObserver) {
        log.info("Received transfers: {}", request.getTransfersCount());
        val transfers = request.getTransfersList();
        ArrayList<Receipt> receipts = new ArrayList<>(request.getTransfersCount());

        for (var transfer : transfers) {
            Receipt receipt = new Receipt();
            receipt.setAmount(BigDecimal.valueOf(transfer.getAmount()));

            Participant sender = participantService
                    .findByBic(transfer.getBicCd()).orElseThrow();
            Participant receiver = participantService
                    .findByBic(transfer.getBicDb()).orElseThrow();

            receipt.setReceiver(receiver);
            receipt.setSender(sender);
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
