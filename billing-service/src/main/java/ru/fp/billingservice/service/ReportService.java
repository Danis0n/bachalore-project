package ru.fp.billingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import ru.fp.billingservice.dto.Report;
import ru.fp.billingservice.exception.UnsupportedFormat;
import ru.fp.billingservice.jasper.JasperReportFactory;
import ru.fp.billingservice.jasper.JasperReportService;
import ru.fp.billingservice.repository.ReportDao;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportDao reportDao;
    private final JasperReportFactory jasperReportFactory;

    @Transactional
    public InputStreamResource findResultsWithFilters(String bic, String startDate,
                                                      String endDate, String format) {

        Timestamp endDateTime = getTimeOrNull(endDate);
        Timestamp startDateTime = getTimeOrNull(startDate);

        List<Report> results = reportDao
                .findResultsWithFilters(bic, startDateTime, endDateTime);

        if (results.isEmpty()) {
            return null;
        }

        Optional<JasperReportService> reportByFormat = jasperReportFactory.getReportByFormat(format);

        if (reportByFormat.isEmpty()) {
            throw new UnsupportedFormat("Format " + format + " is not supported!");
        }

        byte[] file = reportByFormat.get().readyReport(results);

        log.info("Getting ready for report with query params {}, {}, {}", bic, startDate, endDate);
        return new InputStreamResource(new ByteArrayInputStream(file));
    }

    private Timestamp getTimeOrNull(String date) {
        return date == null ? null : Timestamp.valueOf(LocalDateTime.parse(date));
    }

}
