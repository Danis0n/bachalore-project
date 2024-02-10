package ru.fp.billingservice.jasper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JasperReportFactory {

    private final Map<String, JasperReportService> jasperReportServices;

    public Optional<JasperReportService> getReportByFormat(String format) {
        return Optional.of(jasperReportServices.get(format));
    }

}
