/******************************************************************************
 * Name : BaseView.java Author : pranav_jha Date : 2010/02/21 Description : Base class for all JSF managed beans
 *****************************************************************************/
package com.worldvisa.business.infra.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.infra.constants.ReportType;
import com.worldvisa.business.infra.report.ReportGenerator;
import com.worldvisa.business.service.stub.CommonService;
import com.worldvisa.business.service.stub.FileHandlerService;

/**
 * Base class for all JSF managed beans
 */
@Component
public abstract class BaseView
implements Serializable {
    protected static final Logger log              = Logger.getLogger(BaseView.class);
    private static final long     serialVersionUID = 968997671197844763L;
    @Autowired
    private CommonService         commonService;
    @Autowired
    private FileHandlerService    fileHandlerService;

    /**
     * Generates a file from a template
     * @param extensionLessFileName
     * @param contentType
     * @param reportType
     * @param templateName
     * @param paramMap
     * @param reportGenerator
     * @throws Exception
     */
    public File generateReport(final ReportType reportType, final String templateName, final Object parameters, final Collection<?> beanCollection,
    final ReportGenerator reportGenerator)
    throws Exception {
        final File file = this.fileHandlerService.createTempFile();
        final FileOutputStream fos = new FileOutputStream(file);
        // set up the param map
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("param", parameters);
        paramMap.put("lookup", this.commonService.readLookupMap());
        reportGenerator.generateReport(reportType, paramMap, beanCollection, fos, templateName);
        fos.flush();
        fos.close();
        return file;
    }

    /**
     * Gets the user details form the session
     * @return logged in user details
     */
    public LoginDetails user() {
        final Object user = WebContextFactory.get().getSession().getAttribute("user");
        if (null != user) {
            return (LoginDetails) user;
        } else {
            return null;
        }
    }
}
