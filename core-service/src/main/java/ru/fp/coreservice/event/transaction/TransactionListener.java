package ru.fp.coreservice.event.transaction;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.dto.VerifiedAccountsDto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.entity.transaction.Transaction;
import ru.fp.coreservice.service.TransactionsService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionListener {

    private final TransactionsService transactionsService;
    private final Tracer tracer;

    @EventListener
    public TransactionActivationEvent TransactionCreateHandler(TransactionCreateEvent event) {
        Pacs008Dto pacs008;
        PayDoc payDoc;
        Transaction transaction;

        Span span = tracer.buildSpan("transaction-create-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: TransactionCreate");

            pacs008 = (Pacs008Dto) event.getSource();
            payDoc = event.getPayDoc();

            transaction = transactionsService
                    .handleTransactionCreation(pacs008, payDoc);

            log.info("Transaction step: A, Paydoc Step: P");
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
        return new TransactionActivationEvent(pacs008, transaction, event.getIncomingMessage());
    }

    @EventListener
    public TransactionExecutionEvent TransactionActivationHandler(TransactionActivationEvent event) {
        VerifiedAccountsDto verifiedAccounts;
        Span span = tracer.buildSpan("transaction-activation-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: TransactionActivation");

            Pacs008Dto pacs008 = (Pacs008Dto) event.getSource();
            Transaction transaction = event.getTransaction();

            verifiedAccounts = transactionsService
                    .handleTransactionActivation(pacs008, transaction, event.getIncomingMessage());

            log.info("Transaction step: E, Paydoc Step: P");
        }
            catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
        return new TransactionExecutionEvent(
                (Pacs008Dto) event.getSource(),
                event.getTransaction(),
                verifiedAccounts.getAccountCdCode(),
                verifiedAccounts.getAccountDbCode(),
                event.getIncomingMessage());
    }

    @EventListener
    public TransactionNotificationEvent TransactionExecutionHandler(TransactionExecutionEvent event) {
        Pacs008Dto pacs008;
        Transaction transaction;
        String accountCd;
        String accountDb;
        Span span = tracer.buildSpan("transaction-execution-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: TransactionExecution");

            pacs008 = (Pacs008Dto) event.getSource();
            transaction = event.getTransaction();
            accountCd = event.getAccountCdCode();
            accountDb = event.getAccountDbCode();
            IncomingMessage incomingMessage = event.getIncomingMessage();

            log.info("Transaction step: F, Paydoc Step: D");

            synchronized (transactionsService) {

                transactionsService.handleTransactionExecution(
                        pacs008, transaction,
                        accountCd, accountDb,
                        incomingMessage
                );

            }
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
        return new TransactionNotificationEvent(
                pacs008, transaction, accountCd, accountDb
        );
    }

    @EventListener
    public void TransactionNotificationHandler(TransactionNotificationEvent event) {
        Span span = tracer.buildSpan("transaction-notification-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: TransactionNotification");

            transactionsService.handleTransactionNotification(
                    event.getTransaction(), event.getAccountCdCode(), event.getAccountDbCode()
            );
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
    }

}

