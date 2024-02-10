package ru.di.receiptservice.schedulingtasks;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.di.receiptservice.dto.Email;
import ru.di.receiptservice.entity.Participant;
import ru.di.receiptservice.entity.Receipt;
import ru.di.receiptservice.entity.ReceiptStatus;
import ru.di.receiptservice.util.EmailUtil;
import ru.di.receiptservice.service.EmailSenderService;
import ru.di.receiptservice.service.ParticipantService;
import ru.di.receiptservice.service.ReceiptService;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {

    private final ReceiptService receiptService;
    private final EmailSenderService emailSenderService;
    private final ParticipantService participantService;
    private final EmailUtil emailUtil;

    @Transactional
    @Scheduled(cron="${scheduled-tasks.transfer-email}")
    public void scheduleTransferOutbox() {
        log.info("Schedule TransferOutbox");
        val receipts = receiptService.findFirst10OrderByValueDateAsc();

        if (receipts.isEmpty()) {
            return;
        }

        for (Receipt receipt : receipts) {
            Optional<Participant> sender = participantService.findByBic(receipt.getBicCd());
            Optional<Participant> receiver = participantService.findByBic(receipt.getBicDb());

            if (sender.isEmpty() || receiver.isEmpty()) {
                receipt.setStatus(ReceiptStatus.REJECTED);
                return;
            }

            String messageBody = emailUtil.prepareMail(receipt, receiver.get().getName());

            Email email = new Email();
            email.setRecipient(sender.get().getEmail());
            email.setMsgBody(messageBody);
            email.setSubject("ПЕРЕВОД СРЕДСТВ");

            boolean isSent = emailSenderService.sendSimpleMail(email);

            if (!isSent) {
                receipt.setStatus(ReceiptStatus.REJECTED);
            } else {
                receipt.setStatus(ReceiptStatus.EFFECTED);
            }

            receiptService.save(receipt);
        }

    }
}
