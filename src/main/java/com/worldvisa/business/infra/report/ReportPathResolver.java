/******************************************************************************
 * Name : ReportPathResolver.java Author : arun_menon02 Date : 2010/03/24 Description : Utility class to resolve report templates path and load the
 * same
 *****************************************************************************/
package com.worldvisa.business.infra.report;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Utility class to resolve report templates path and load the same
 */
@Component
public class ReportPathResolver
implements ApplicationContextAware {
    private static Map<String, JasperReport> compiledReportsMap  = Collections.synchronizedMap(new HashMap<String, JasperReport>());
    private static Map<String, Long>         lastAccessedTimeMap = Collections.synchronizedMap(new HashMap<String, Long>());
    private ApplicationContext               ctx;

    /**
     * Gets the path for the given report template
     * @param templateName Template name
     * @return File path
     */
    public String getPath(final String templateName)
    throws Exception {
        String path = null;
        final Resource resource = this.ctx.getResource("classpath:reports/" + templateName);
        path = resource.getFile().getPath();
        return path;
    }

    public String getReportsAbsolutePath(final String reportName)
    throws Exception {
        final Resource resource = this.ctx.getResource("classpath:reports/" + reportName);
        return resource.getFile().getAbsolutePath();
    }

    /**
     * Load the report template for the given name
     * @param reportName Template name
     * @return Report object
     */
    public JasperReport loadReport(final String reportName)
    throws Exception {
        JasperReport report = null;
        final Resource resource = this.ctx.getResource("classpath:reports/" + reportName);
        if (ReportPathResolver.lastAccessedTimeMap.containsKey(reportName) && ReportPathResolver.compiledReportsMap.containsKey(reportName)) {
            if (ReportPathResolver.lastAccessedTimeMap.get(reportName) > resource.getFile().lastModified()) {
                report = ReportPathResolver.compiledReportsMap.get(reportName);
            }
        }
        if (report == null) {
            report = JasperCompileManager.compileReport(resource.getInputStream());
            ReportPathResolver.compiledReportsMap.put(reportName, report);
            ReportPathResolver.lastAccessedTimeMap.put(reportName, System.currentTimeMillis());
        }
        return report;
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(ApplicationContext)
     */
    public void setApplicationContext(final ApplicationContext ctx)
    throws BeansException {
        this.ctx = ctx;
    }
}
