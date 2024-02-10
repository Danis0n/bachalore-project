package ru.fp.billingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.billingservice.service.ReportService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/result")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Resource> findResultsWithFilters(
            @RequestParam(value = "format", required = true) String format,
            @RequestParam(value = "bic", required = false) String bic,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate
    ) {
        InputStreamResource report = reportService
                .findResultsWithFilters(bic, startDate, endDate, format);

        String fileName = "Report file at " + LocalDate.now();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "." + format + "\"")
                .body(report);
    }

}
