package ru.fp.billingservice.schedulingtasks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.billingservice.entity.inbox.TransferInboxStatus;
import ru.fp.billingservice.repository.TransferInboxRepository;
import ru.fp.billingservice.service.CommissionService;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTasks {
    private final TransferInboxRepository transferInboxRepository;
    private final CommissionService commissionService;

    @Scheduled(cron="${scheduled-tasks.transfer-inbox}")
    @Transactional
    public void scheduleTransferInbox() {
        log.info("Schedule TransferInbox");
        val transfers = transferInboxRepository.find10NewTransfers();
        if (transfers.isEmpty()) {
            return;
        }

        for (var transfer: transfers) {
            try {
                commissionService.commissionCalculation(transfer);
                transfer.setStatus(TransferInboxStatus.EFFECTED);
            } catch (RuntimeException ex) {
                transfer.setStatus(TransferInboxStatus.REJECTED);
                log.info(ex.getMessage());
            }
        }

        transferInboxRepository.saveAll(transfers);
    }

    @Scheduled(cron="${scheduled-tasks.transfer-clean}")
    @Transactional
    public void scheduleCleanTransferInbox() {
        log.info("Schedule Clean TransferInbox");
        transferInboxRepository.deleteByStatus(TransferInboxStatus.EFFECTED);
    }


}
