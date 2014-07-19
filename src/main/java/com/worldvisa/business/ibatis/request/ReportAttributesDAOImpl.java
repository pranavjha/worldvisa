/******************************************************************************
 * Name : ReportAttributesDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the ReportAttributesDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.request;

import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.request.ReportAttributesDAO;
import com.worldvisa.business.domain.request.ReportAttributes;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.REQUEST_REPORT_ATTRIBUTES table<br/>
 */
@Repository("requestReportAttributesDAOImpl")
public class ReportAttributesDAOImpl
extends BaseIbatisDAOImpl<ReportAttributes>
implements ReportAttributesDAO {
}
