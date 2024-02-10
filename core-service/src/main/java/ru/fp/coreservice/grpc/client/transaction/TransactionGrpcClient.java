package ru.fp.coreservice.grpc.client.transaction;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.fp.transaction.proto.TransactionRequest;
import ru.fp.transaction.proto.TransactionResponse;
import ru.fp.transaction.proto.TransactionServiceGrpc;

@Slf4j
@Service
public class TransactionGrpcClient {

    @GrpcClient("billing-service")
    TransactionServiceGrpc.TransactionServiceBlockingStub stub;

    public TransactionResponse sendTransaction(TransactionRequest request) {

        try {
            return stub.transferTransaction(request);
        } catch (RuntimeException e) {
            log.error("Grpc sendTransaction error: ", e);
            return null;
        }
    }

}
