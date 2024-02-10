package ru.fp.billingservice.jasper;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import ru.fp.billingservice.exception.JasperInitException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
public abstract class JasperReportService {

    protected final JasperReport jasperReport;
    private final static String FILENAME = "/jasper/result.jrxml";
    protected final static String JASPER_SET = "resultset";

    public JasperReportService() {
        try(InputStream inputStream = getClass().getResourceAsStream(FILENAME)) {
            jasperReport = JasperCompileManager.compileReport(inputStream);
        } catch (JRException e) {
            throw new JasperInitException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract byte[] readyReport(List<?> resultSetList);

}
