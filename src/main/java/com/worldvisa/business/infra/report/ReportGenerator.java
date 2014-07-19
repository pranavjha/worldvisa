/******************************************************************************
 * Name : ReportGenerator.java Author : administrator Date : 2010/03/19 Description : Utility class to generate reports
 *****************************************************************************/
package com.worldvisa.business.infra.report;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jxls.transformer.XLSTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.worldvisa.business.infra.constants.ReportType;

/**
 * Utility class to generate reports
 */
@Component
public class ReportGenerator {
    @Autowired
    private ReportPathResolver resolver;

    /**
     * This method is used to generate PDF, CSV, HTML, XLS and JRXLS (XLS using JasperReports) files. According to the ReportType attribute sent
     * appropriate JRExporter are called for PDF, CSV, HTML or JRXLS creation. XLS are created using renderExcel method which in turn uses the JXLS
     * API with better features for EXCEL creation.
     * @param reportType The attribute used to distinguish between the various report types.
     * @param templateName The name of the template file to be used.
     * @param parameters Map attribute with the parameters for report generation.
     * @param dataList The collection used for creation of the JRDataSource which in turn is used for the data population in reports.
     * @param stream OutputStream to which the generated report is written to
     */
    public void generateReport(final ReportType reportType, final Map<String, Object> parameters, final Collection<?> dataList,
    final OutputStream stream, final String templateName)
    throws Exception {
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        JRDataSource jrDataSource = null;
        JRExporter exporter = null;
        // populate the data list by default if it contains nothing
        if (ReportType.XLS.equals(reportType)) {
            this.renderExcel(templateName, parameters, stream);
        } else {
            // Getting the params passed to the report
            jasperReport = this.resolver.loadReport(templateName);
            final String filePath = this.resolver.getReportsAbsolutePath(templateName);
            final String folderPath = filePath.substring(0, filePath.length() - templateName.length());
            parameters.put("SUBREPORT_DIR", folderPath);
            // Creating JR data source from the given data source
            if (null != dataList && dataList.size() != 0) {
                jrDataSource = new JRBeanCollectionDataSource(dataList);
            } else {
                final List<String> dummyDataList = new ArrayList<String>();
                dummyDataList.add("");
                jrDataSource = new JRBeanCollectionDataSource(dummyDataList);
            }
            // Filling the report template with data
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
            if (ReportType.PDF.equals(reportType)) {
                exporter = new JRPdfExporter();
            } else if (ReportType.CSV.equals(reportType)) {
                exporter = new JRCsvExporter();
            } else if (ReportType.HTML.equals(reportType)) {
                exporter = new JRHtmlExporter();
            } else if (ReportType.JRXLS.equals(reportType)) {
                exporter = new JRXlsExporter();
            }
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
            exporter.exportReport();
        }
    }

    /**
     * This method takes the template file name for the Excel, a map with a collection of objects to be iterated on for generation of the xls and
     * writes the generated Excel file to the given output stream
     * @param templateFileName Template to be used for Excel generation
     * @param dataToFill Map with the data to be populated in the Excel
     * @param stream Output stream to which the Excel data will be written
     */
    private void renderExcel(String templateFileName, final Map<String, ?> dataToFill, final OutputStream stream)
    throws Exception {
        templateFileName = this.resolver.getPath(templateFileName);
        final XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(new FileInputStream(templateFileName), dataToFill).write(stream);
    }
}
