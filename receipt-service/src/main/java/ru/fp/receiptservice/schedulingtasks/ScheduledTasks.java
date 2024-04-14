package ru.fp.receiptservice.schedulingtasks;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fp.receiptservice.dto.Email;
import ru.fp.receiptservice.util.EmailUtil;
import ru.fp.receiptservice.entity.Participant;
import ru.fp.receiptservice.entity.Receipt;
import ru.fp.receiptservice.entity.ReceiptStatus;
import ru.fp.receiptservice.service.EmailSenderService;
import ru.fp.receiptservice.service.ParticipantService;
import ru.fp.receiptservice.service.ReceiptService;

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
//    @Scheduled(cron="${scheduled-tasks.transfer-email}")
    public void scheduleTransferOutbox() {
        log.info("Schedule TransferOutbox");
        val receipts = receiptService.findFirst10OrderByValueDateAsc();

        if (receipts.isEmpty()) {
            return;
        }

        for (Receipt receipt : receipts) {
            Participant sender = receipt.getSender();
            Participant receiver = receipt.getReceiver();

            String messageBody = emailUtil.prepareMail(receipt, receiver.getName());

            Email email = new Email();
            email.setRecipient(sender.getEmail());
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
