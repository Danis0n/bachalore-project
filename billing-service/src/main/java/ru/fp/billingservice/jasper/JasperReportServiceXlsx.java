package ru.fp.billingservice.jasper;

import lombok.Getter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import ru.fp.billingservice.exception.JasperExportException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Service("xlsx")
public class JasperReportServiceXlsx extends JasperReportService {

    public JasperReportServiceXlsx() {
        super();
    }

    public byte[] readyReport(List<?> resultSetList) {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultSetList);
        Map<String, Object> params = new HashMap<>();
        params.put(JASPER_SET, dataSource);

        try {
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(jasperReport, params, new JREmptyDataSource());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exportXlsxReport(jasperPrint, outputStream);

            return outputStream.toByteArray();
        } catch (JRException e) {
            throw new JasperExportException(e.getMessage());
        }
    }

    public void exportXlsxReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration xlsxReportConfiguration = new SimpleXlsxReportConfiguration();
        xlsxReportConfiguration.setOnePagePerSheet(false);
        xlsxReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsxReportConfiguration.setDetectCellType(false);
        xlsxReportConfiguration.setWhitePageBackground(false);

        xlsxExporter.setConfiguration(xlsxReportConfiguration);
        xlsxExporter.exportReport();
    }

}
