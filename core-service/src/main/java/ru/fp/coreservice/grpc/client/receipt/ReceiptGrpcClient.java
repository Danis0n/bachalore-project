package ru.fp.coreservice.grpc.client.receipt;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.fp.transaction.proto.TransactionRequest;
import ru.fp.transaction.proto.TransactionResponse;
import ru.fp.transaction.proto.TransactionServiceGrpc;

@Slf4j
@Service
public class ReceiptGrpcClient {

    @GrpcClient("receipt-service")
    TransactionServiceGrpc.TransactionServiceBlockingStub stub;

    public TransactionResponse sendReceipt(TransactionRequest request) {

        try {
            return stub.transferTransaction(request);
        } catch (RuntimeException e) {
            log.error("Grpc sendTransaction error: ", e);
            return null;
        }
    }

}
