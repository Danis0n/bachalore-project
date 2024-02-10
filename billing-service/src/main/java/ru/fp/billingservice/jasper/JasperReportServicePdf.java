package ru.fp.billingservice.jasper;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.stereotype.Service;
import ru.fp.billingservice.exception.JasperExportException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("pdf")
public class JasperReportServicePdf extends JasperReportService {

    @Override
    public byte[] readyReport(List<?> resultSetList) {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultSetList);
        Map<String, Object> params = new HashMap<>();
        params.put(JASPER_SET, dataSource);

        try {
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport, params, new JREmptyDataSource());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exportPdfReport(jasperPrint, outputStream);

            return outputStream.toByteArray();
        } catch (JRException e) {
            throw new JasperExportException(e.getMessage());
        }

    }

    public void exportPdfReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRPdfExporter pdfExporter = new JRPdfExporter();

        pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);

        pdfExporter.setConfiguration(configuration);
        pdfExporter.exportReport();
    }

}
