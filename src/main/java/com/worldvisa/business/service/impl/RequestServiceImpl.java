/******************************************************************************
 * Name : RequestServiceImpl.java Author : Administrator Date : 09-May-2010,2:28:06 AM Description : Implementation for RequestServiceImpl
 *****************************************************************************/
package com.worldvisa.business.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldvisa.business.dao.profile.LoginDetailsDAO;
import com.worldvisa.business.dao.profile.UserDetailsDAO;
import com.worldvisa.business.dao.request.AttributeMasterDAO;
import com.worldvisa.business.dao.request.CaseStatusDAO;
import com.worldvisa.business.dao.request.CountryPreferencesDAO;
import com.worldvisa.business.dao.request.CoursePreferenceDAO;
import com.worldvisa.business.dao.request.EduExperienceDAO;
import com.worldvisa.business.dao.request.FamilyDetailsDAO;
import com.worldvisa.business.dao.request.LangProfeciencyDAO;
import com.worldvisa.business.dao.request.RelativesAbroadDAO;
import com.worldvisa.business.dao.request.ReportAttributesDAO;
import com.worldvisa.business.dao.request.ReportDetailsDAO;
import com.worldvisa.business.dao.request.TestScoreDAO;
import com.worldvisa.business.dao.request.WorkExperienceDAO;
import com.worldvisa.business.domain.common.DataTableRequest;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.profile.UserDetails;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.domain.request.CaseStatus;
import com.worldvisa.business.domain.request.CountryPreferences;
import com.worldvisa.business.domain.request.CoursePreference;
import com.worldvisa.business.domain.request.EduExperience;
import com.worldvisa.business.domain.request.FamilyDetails;
import com.worldvisa.business.domain.request.LangProfeciency;
import com.worldvisa.business.domain.request.RelativesAbroad;
import com.worldvisa.business.domain.request.ReportAttributes;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.request.RequestManagementFilter;
import com.worldvisa.business.domain.request.TestScore;
import com.worldvisa.business.domain.request.WorkExperience;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.utils.BasicUtils;
import com.worldvisa.business.infra.utils.BusinessParams;
import com.worldvisa.business.service.stub.CommonService;
import com.worldvisa.business.service.stub.RequestService;

/**
 * 
 */
@Service
public class RequestServiceImpl
extends BaseServiceImpl
implements RequestService {
    @Autowired
    private AttributeMasterDAO    attributeMasterDAO;
    @Autowired
    private CaseStatusDAO         caseStatusDAO;
    @Autowired
    private CommonService         commonService;
    @Autowired
    private CountryPreferencesDAO countryPreferencesDAO;
    @Autowired
    private CoursePreferenceDAO   coursePreferenceDAO;
    @Autowired
    private EduExperienceDAO      eduExperienceDAO;
    @Autowired
    private FamilyDetailsDAO      familyDetailsDAO;
    @Autowired
    private LangProfeciencyDAO    langProfeciencyDAO;
    @Autowired
    private LoginDetailsDAO       loginDetailsDAO;
    @Autowired
    private RelativesAbroadDAO    relativesAbroadDAO;
    @Autowired
    private ReportAttributesDAO   reportAttributesDAO;
    @Autowired
    private ReportDetailsDAO      reportDetailsDAO;
    @Autowired
    private TestScoreDAO          testScoreDAO;
    @Autowired
    private UserDetailsDAO        userDetailsDAO;
    @Autowired
    private WorkExperienceDAO     workExperienceDAO;

    /**
     * @see com.worldvisa.business.service.stub.RequestService#addCaseStatus(com.worldvisa.business.domain.request.CaseStatus)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public CaseStatus createCaseStatus(final LoginDetails user, final CaseStatus newCaseStatus)
    throws Exception {
        try {
            final Long caseStatusId = (Long) this.caseStatusDAO.create(newCaseStatus);
            newCaseStatus.setCaseStatusId(caseStatusId);
            return newCaseStatus;
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not save the case status. Try adding the case status again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    public List<CaseStatus> readCaseStatus(final String reportId)
    throws Exception {
        return this.caseStatusDAO.readList(new CaseStatus(null, reportId));
    }

    public List<AttributeMaster> readReportMasterAttributes(final Long reportType)
    throws Exception {
        return this.attributeMasterDAO.readList(new AttributeMaster(null, reportType));
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#readRequest(com.worldvisa.business.domain.request.ReportDetails)
     */
    public ReportDetails readRequest(final ReportDetails reportDetails)
    throws Exception {
        return this.reportDetailsDAO.read(reportDetails);
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#readRequestDetails(java.lang.String)
     */
    public ReportDetails readRequestDetails(final String reportId)
    throws Exception {
        final ReportDetails result = this.reportDetailsDAO.read(new ReportDetails(reportId));
        if (result == null) {
            throw new Exception("The entered report ID appears invalid. Please check.");
        }
        result.setPersonalDetails(this.userDetailsDAO.read(new UserDetails(null, result.getRaisedFor())));
        result.setCountryPreferencesList(this.countryPreferencesDAO.readList(new CountryPreferences(reportId, null)));
        result.setCoursePreferenceList(this.coursePreferenceDAO.readList(new CoursePreference(reportId, null)));
        result.setEduExperienceList(this.eduExperienceDAO.readList(new EduExperience(reportId, null)));
        result.setLangProfeciencieList(this.langProfeciencyDAO.readList(new LangProfeciency(reportId, null)));
        result.setRelativesAbroadList(this.relativesAbroadDAO.readList(new RelativesAbroad(reportId, null)));
        result.setTestScoreList(this.testScoreDAO.readList(new TestScore(reportId, null)));
        result.setWorkExperienceList(this.workExperienceDAO.readList(new WorkExperience(reportId, null)));
        final List<ReportAttributes> reportAttributesList = this.reportAttributesDAO.readList(new ReportAttributes(null, reportId));
        result.setReportAttributesMap(new HashMap<Long, ReportAttributes>());
        for (final ReportAttributes attribute : reportAttributesList) {
            result.getReportAttributesMap().put(attribute.getAttributeId(), attribute);
        }
        result.setFamilyDetailsList(this.familyDetailsDAO.readList(new FamilyDetails(reportId, null)));
        return result;
    }

    /**
     * @see com.worldvisa.business.service.stub.ProfileService#saveRequest(com.worldvisa.business.domain.request.ReportDetails)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public ReportDetails saveRequest(final LoginDetails user, final ReportDetails details)
    throws Exception {
        try {
            String createdBy = null;
            if (user == null || user.getEmailId() == null || LookupConstants.REQUEST_MODE.Online.equals(details.getRequestMode())) {
                createdBy = details.getPersonalDetails().getEmailId();
            } else {
                createdBy = user.getEmailId();
            }
            // creating login details
            BaseServiceImpl.log.debug("creating login details...");
            details.getPersonalDetails().setCreatedBy(createdBy);
            details.getPersonalDetails().setIsActive(LookupConstants.INDICES.FALSE);
            details.getPersonalDetails().setCreatedDate(BasicUtils.getCurrentDateTime());
            details.getPersonalDetails().setPassword(LoginDetails.autoGeneratePassword());
            details.getPersonalDetails().setPasswordExpFlag(LookupConstants.INDICES.TRUE);
            details.getPersonalDetails().setRegistrationStatus(LookupConstants.CLIENT_REG_STATUS.Unregisterd);
            details.getPersonalDetails().setUserRole(LookupConstants.USER_ROLE.Customer);
            details.setNumPass(1L);
            if (LookupConstants.REQUEST_TYPE.OverseasEducation_AdmissionandVisa.equals(details.getRequestMode())
            || LookupConstants.REQUEST_TYPE.OverseasEducation_VisaOnly.equals(details.getRequestMode())) {
                details.setNumUniv(1L);
            }
            try {
                details.setPaymentOption(LookupConstants.PAYMENT_OPTION.OneTimePaymentPlan);
                details.setPayableAmount(this.commonService.readPaymentOptions(details).get(0).getAmountValue());
                details.setPaymentOption(null);
            } catch (final Exception e) {
                details.setPayableAmount(0.00);
            }
            this.loginDetailsDAO.create(new LoginDetails(details.getPersonalDetails()));
            // creating user details
            BaseServiceImpl.log.debug("login details created! creating user details...");
            this.userDetailsDAO.create(details.getPersonalDetails());
            // creating report details
            BaseServiceImpl.log.debug("user details created! creating report details...");
            details.setRaisedBy(createdBy);
            details.setRaisedDt(BasicUtils.getCurrentDateTime());
            details.setRaisedFor(details.getPersonalDetails().getEmailId());
            // <one character representing service type><1 character country
            // code><DOB in YYYYMMDD format>
            String prefix = "";
            final String prefixName = details.getPersonalDetails().getName().trim().replaceAll(" ", "");
            if (prefixName.length() < 4) {
                prefix = prefixName.toUpperCase() + "0000".substring(prefixName.length(), 4);
            } else {
                prefix = prefixName.substring(0, 4).toUpperCase();
            }
            BaseServiceImpl.log.debug("prefix::" + prefix);
            details.setReportId(this.reportDetailsDAO.getNextReportId(prefix));
            this.reportDetailsDAO.create(details);
            // creating country preferences
            BaseServiceImpl.log.debug("creating country preferences...");
            if (null != details.getCountryPreferencesList()) {
                for (final CountryPreferences countryPreferences : details.getCountryPreferencesList()) {
                    countryPreferences.setReportId(details.getReportId());
                    this.countryPreferencesDAO.create(countryPreferences);
                }
            }
            // creating course preferences
            if (LookupConstants.REQUEST_TYPE.OverseasEducation_AdmissionandVisa.equals(details.getRequestType())
            || LookupConstants.REQUEST_TYPE.OverseasEducation_VisaOnly.equals(details.getRequestType()) && null != details.getCoursePreferenceList()) {
                BaseServiceImpl.log.debug("creating course preferences...");
                for (final CoursePreference coursePreference : details.getCoursePreferenceList()) {
                    coursePreference.setReportId(details.getReportId());
                    this.coursePreferenceDAO.create(coursePreference);
                }
            }
            // creating family details
            if (!LookupConstants.MARITAL_STATUS.Single.equals(details.getPersonalDetails().getMaritalStatus())
            && null != details.getFamilyDetailsList()) {
                BaseServiceImpl.log.debug("creating family details...");
                for (final FamilyDetails familyDetails : details.getFamilyDetailsList()) {
                    familyDetails.setReportId(details.getReportId());
                    this.familyDetailsDAO.create(familyDetails);
                }
            }
            // creating test scores
            if (null != details.getTestScoreList()) {
                BaseServiceImpl.log.debug("creating test scores...");
                for (final TestScore testScore : details.getTestScoreList()) {
                    testScore.setReportId(details.getReportId());
                    this.testScoreDAO.create(testScore);
                }
            }
            // creating educational experience
            if (null != details.getEduExperienceList()) {
                BaseServiceImpl.log.debug("creating educational experience...");
                for (final EduExperience experience : details.getEduExperienceList()) {
                    experience.setReportId(details.getReportId());
                    this.eduExperienceDAO.create(experience);
                }
            }
            // creating language profeciency
            if (null != details.getLangProfeciencieList()) {
                BaseServiceImpl.log.debug("creating language profeciency...");
                for (final LangProfeciency langProfeciency : details.getLangProfeciencieList()) {
                    langProfeciency.setReportId(details.getReportId());
                    this.langProfeciencyDAO.create(langProfeciency);
                }
            }
            // creating relatives abroad
            if (null != details.getRelativesAbroadList()) {
                BaseServiceImpl.log.debug("creating relatives abroad...");
                for (final RelativesAbroad relativesAbroad : details.getRelativesAbroadList()) {
                    relativesAbroad.setReportId(details.getReportId());
                    this.relativesAbroadDAO.create(relativesAbroad);
                }
            }
            // creating work experience.
            if (null != details.getWorkExperienceList()) {
                BaseServiceImpl.log.debug("creating work experience...");
                for (final WorkExperience workExperience : details.getWorkExperienceList()) {
                    workExperience.setReportId(details.getReportId());
                    this.workExperienceDAO.create(workExperience);
                }
            }
            // creating request Attributes
            BaseServiceImpl.log.debug("creating request attributes...");
            for (final ReportAttributes reportAttributes : details.getReportAttributesMap().values()) {
                reportAttributes.setReportId(details.getReportId());
                this.reportAttributesDAO.create(reportAttributes);
            }
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "Oops!. Your request could not be updated into the system. If you are having trouble filling up the online assessment form, please call up our office.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return details;
    }

    public int searchRequestCount(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        return this.reportDetailsDAO.readRequestsCount(requestManagementFilter);
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#searchRequests(com.worldvisa.business.domain.request.RequestManagementFilter)
     */
    public List<ReportDetails> searchRequests(final RequestManagementFilter requestManagementFilter)
    throws Exception {
        final List<ReportDetails> reportsList = this.reportDetailsDAO.readRequests(requestManagementFilter);
        for (final ReportDetails details : reportsList) {
            final long timeDiff = BasicUtils.getCurrentDateTime().getTime() - details.getRaisedDt().getTime();
            details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.Normal);
            if (details.getAssignedTo() != null) {
                if (timeDiff > new Long(BusinessParams.get("AssignedCaseStatus.slaAtRisk")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAAtRisk);
                }
                if (timeDiff > new Long(BusinessParams.get("AssignedCaseStatus.slaExpired")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAExpired);
                }
            } else {
                if (timeDiff > new Long(BusinessParams.get("UnassignedCaseStatus.slaAtRisk")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAAtRisk);
                }
                if (timeDiff > new Long(BusinessParams.get("UnassignedCaseStatus.slaExpired")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAExpired);
                }
            }
        }
        return reportsList;
    }

    public int searchUnassignedRequestCount(final DataTableRequest request)
    throws Exception {
        return this.reportDetailsDAO.readUnassignedRequestCount(request);
    }

    public List<ReportDetails> searchUnassignedRequests(final DataTableRequest request)
    throws Exception {
        final List<ReportDetails> reportsList = this.reportDetailsDAO.readUnassignedRequest(request);
        for (final ReportDetails details : reportsList) {
            final long timeDiff = BasicUtils.getCurrentDateTime().getTime() - details.getRaisedDt().getTime();
            details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.Normal);
            if (details.getAssignedTo() != null) {
                if (timeDiff > new Long(BusinessParams.get("AssignedCaseStatus.slaAtRisk")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAAtRisk);
                }
                if (timeDiff > new Long(BusinessParams.get("AssignedCaseStatus.slaExpired")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAExpired);
                }
            } else {
                if (timeDiff > new Long(BusinessParams.get("UnassignedCaseStatus.slaAtRisk")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAAtRisk);
                }
                if (timeDiff > new Long(BusinessParams.get("UnassignedCaseStatus.slaExpired")).longValue()) {
                    details.setSlaStatus(LookupConstants.REQUEST_SLA_SATUS.SLAExpired);
                }
            }
        }
        return reportsList;
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#updateAssignment(java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public void updateAssignment(final String requestId, final String emailId)
    throws Exception {
        final ReportDetails reportId = new ReportDetails(requestId);
        final ReportDetails data = this.reportDetailsDAO.read(reportId);
        if (null == data.getRescheduledInd() || data.getRescheduledInd().equals(new Integer(0))) {
            data.setRescheduledInd(1);
        }
        try {
            data.setAssignedTo(emailId);
            BaseServiceImpl.log.debug("updating now");
            this.reportDetailsDAO.update(data, reportId);
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not change the assignment. Please try again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#updateRequest(com.worldvisa.business.domain.request.ReportDetails)
     */
    @Transactional(rollbackFor = java.lang.Exception.class)
    public ReportDetails updateRequest(final ReportDetails details, final String oldEmail, final LoginDetails user)
    throws Exception {
        try {
            if (!oldEmail.equals(details.getPersonalDetails().getEmailId())) {
                if (oldEmail.equals(details.getRaisedBy())) {
                    details.setRaisedBy(details.getPersonalDetails().getEmailId());
                }
                if (oldEmail.equals(details.getRaisedFor())) {
                    details.setRaisedFor(details.getPersonalDetails().getEmailId());
                }
            }
            // creating login details
            BaseServiceImpl.log.debug("updating login details...");
            this.loginDetailsDAO.update(new LoginDetails(details.getPersonalDetails()), new LoginDetails(oldEmail));
            // creating user details
            BaseServiceImpl.log.debug("login details updated! updating user details...");
            this.userDetailsDAO.update(details.getPersonalDetails(), new UserDetails(details.getPersonalDetails().getSeqNum(), null));
            // creating report details
            BaseServiceImpl.log.debug("user details updated! updating report details...");
            this.reportDetailsDAO.update(details, new ReportDetails(details.getReportId()));
            // creating country preferences
            BaseServiceImpl.log.debug("updating country preferences...");
            this.countryPreferencesDAO.delete(new CountryPreferences(details.getReportId(), null));
            for (final CountryPreferences countryPreferences : details.getCountryPreferencesList()) {
                countryPreferences.setReportId(details.getReportId());
                this.countryPreferencesDAO.create(countryPreferences);
            }
            // creating course preferences
            BaseServiceImpl.log.debug("updating course preferences...");
            this.coursePreferenceDAO.delete(new CoursePreference(details.getReportId(), null));
            if (LookupConstants.REQUEST_TYPE.OverseasEducation_AdmissionandVisa.equals(details.getRequestType())
            || LookupConstants.REQUEST_TYPE.OverseasEducation_VisaOnly.equals(details.getRequestType())) {
                for (final CoursePreference coursePreference : details.getCoursePreferenceList()) {
                    coursePreference.setReportId(details.getReportId());
                    this.coursePreferenceDAO.create(coursePreference);
                }
            }
            // creating family details
            BaseServiceImpl.log.debug("updating family details...");
            this.familyDetailsDAO.delete(new FamilyDetails(details.getReportId(), null));
            if (!LookupConstants.MARITAL_STATUS.Single.equals(details.getRequestType())) {
                for (final FamilyDetails familyDetails : details.getFamilyDetailsList()) {
                    familyDetails.setReportId(details.getReportId());
                    this.familyDetailsDAO.create(familyDetails);
                }
            }
            // creating course preferences
            BaseServiceImpl.log.debug("updating test scores...");
            this.testScoreDAO.delete(new TestScore(details.getReportId(), null));
            for (final TestScore testScore : details.getTestScoreList()) {
                testScore.setReportId(details.getReportId());
                this.testScoreDAO.create(testScore);
            }
            // creating educational experience
            BaseServiceImpl.log.debug("updating educational experience...");
            this.eduExperienceDAO.delete(new EduExperience(details.getReportId(), null));
            for (final EduExperience experience : details.getEduExperienceList()) {
                experience.setReportId(details.getReportId());
                this.eduExperienceDAO.create(experience);
            }
            // creating language profeciency
            BaseServiceImpl.log.debug("updating language profeciency...");
            this.langProfeciencyDAO.delete(new LangProfeciency(details.getReportId(), null));
            for (final LangProfeciency langProfeciency : details.getLangProfeciencieList()) {
                langProfeciency.setReportId(details.getReportId());
                this.langProfeciencyDAO.create(langProfeciency);
            }
            // creating relatives abroad
            BaseServiceImpl.log.debug("updating relatives abroad...");
            this.relativesAbroadDAO.delete(new RelativesAbroad(details.getReportId(), null));
            for (final RelativesAbroad relativesAbroad : details.getRelativesAbroadList()) {
                relativesAbroad.setReportId(details.getReportId());
                this.relativesAbroadDAO.create(relativesAbroad);
            }
            // creating relatives abroad
            BaseServiceImpl.log.debug("updating relatives abroad...");
            this.workExperienceDAO.delete(new WorkExperience(details.getReportId(), null));
            for (final WorkExperience workExperience : details.getWorkExperienceList()) {
                workExperience.setReportId(details.getReportId());
                this.workExperienceDAO.create(workExperience);
            }
            // creating request Attributes
            BaseServiceImpl.log.debug("updating request attributes...");
            this.reportAttributesDAO.delete(new ReportAttributes(null, details.getReportId()));
            for (final ReportAttributes reportAttributes : details.getReportAttributesMap().values()) {
                reportAttributes.setReportId(details.getReportId());
                this.reportAttributesDAO.create(reportAttributes);
            }
        } catch (final Exception e) {
            final Exception ex = new Exception(
            "The system could not update the report details. Please try again. If the problem persists, contact administrator for assistance.");
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return details;
    }

    /**
     * @see com.worldvisa.business.service.stub.RequestService#validateEmail(java.lang.String)
     */
    public boolean validateEmail(final String emailId)
    throws Exception {
        final LoginDetails login = this.loginDetailsDAO.read(new LoginDetails(emailId));
        return (login == null);
    }
}