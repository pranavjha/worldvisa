/******************************************************************************
 * Name : EmployeeDetailsDAOImpl.java Author : Administrator Date : 2010/05/08 Description : Implementation for the EmployeeDetailsDAOImpl class
 *****************************************************************************/
package com.worldvisa.business.ibatis.profile;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.stereotype.Repository;
import com.worldvisa.business.dao.profile.EmployeeDetailsDAO;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.EmployeeManagementFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceFilter;
import com.worldvisa.business.domain.profile.EmployeePerformanceReport;
import com.worldvisa.business.infra.base.BaseIbatisDAOImpl;

/**
 * iBatis DAO implementation for interfacing with the ADMIN.PROFILE_EMPLOYEE_DETAILS table<br/>
 */
@Repository("profileEmployeeDetailsDAOImpl")
public class EmployeeDetailsDAOImpl
extends BaseIbatisDAOImpl<EmployeeDetails>
implements EmployeeDetailsDAO {
    /**
     * @see com.worldvisa.business.dao.profile.EmployeeDetailsDAO#getPerformanceReport(com.worldvisa.business.domain.profile.EmployeePerformanceFilter)
     */
    @SuppressWarnings("unchecked")
    public List<EmployeePerformanceReport> getPerformanceReport(final EmployeePerformanceFilter filter)
    throws Exception {
        final List<EmployeePerformanceReport> allReports = this.getSqlMapClientTemplate().queryForList("profileEmployeeDetails.getTotalReport",
        filter);
        allReports.addAll(this.getSqlMapClientTemplate().queryForList("profileEmployeeDetails.getConvertedReport", filter));
        allReports.addAll(this.getSqlMapClientTemplate().queryForList("profileEmployeeDetails.getRevenueReport", filter));
        final Map<Date, EmployeePerformanceReport> dailyPerformanceMap = new HashMap<Date, EmployeePerformanceReport>();
        final SortedSet<Date> set = new TreeSet<Date>();
        final Calendar thisCal = new GregorianCalendar();
        for (final EmployeePerformanceReport report : allReports) {
            thisCal.setTime(report.getPdate());
            thisCal.set(Calendar.HOUR_OF_DAY, 0);
            thisCal.set(Calendar.MINUTE, 0);
            thisCal.set(Calendar.SECOND, 0);
            thisCal.set(Calendar.MILLISECOND, 0);
            if (!dailyPerformanceMap.containsKey(thisCal.getTime())) {
                dailyPerformanceMap.put(thisCal.getTime(), new EmployeePerformanceReport());
                set.add(thisCal.getTime());
                dailyPerformanceMap.get(thisCal.getTime()).setPdate(thisCal.getTime());
            }
            dailyPerformanceMap.get(thisCal.getTime()).setTotalRequest(
            dailyPerformanceMap.get(thisCal.getTime()).getTotalRequest() + report.getTotalRequest());
            dailyPerformanceMap.get(thisCal.getTime()).setConvertedRequests(
            dailyPerformanceMap.get(thisCal.getTime()).getConvertedRequests() + report.getConvertedRequests());
            dailyPerformanceMap.get(thisCal.getTime()).setTotalRevenue(
            dailyPerformanceMap.get(thisCal.getTime()).getTotalRevenue() + report.getTotalRevenue());
        }
        allReports.clear();
        for (final Date thisDate : set) {
            allReports.add(dailyPerformanceMap.get(thisDate));
        }
        return allReports;
    }

    /**
     * @see com.worldvisa.business.dao.profile.EmployeeDetailsDAO#readAssignmentOptions(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeDetails> readAssignmentOptions(final String emailId)
    throws Exception {
        return this.getSqlMapClient().queryForList("profileEmployeeDetails.readAssignmentOptions", emailId);
    }

    /**
     * @see com.worldvisa.business.dao.profile.EmployeeDetailsDAO#readListByFilter(com.worldvisa.business.domain.profile.EmployeeManagementFilter)
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeDetails> readListByFilter(final EmployeeManagementFilter filter)
    throws Exception {
        return this.getSqlMapClient().queryForList("profileEmployeeDetails.readFromFilter", filter);
    }

    /**
     * @see com.worldvisa.business.dao.profile.EmployeeDetailsDAO#readListLengthByFilter(com.worldvisa.business.domain.profile.EmployeeManagementFilter)
     */
    public Integer readListLengthByFilter(final EmployeeManagementFilter filter)
    throws Exception {
        return (Integer) this.getSqlMapClient().queryForObject("profileEmployeeDetails.readLengthFromFilter", filter);
    }
}
