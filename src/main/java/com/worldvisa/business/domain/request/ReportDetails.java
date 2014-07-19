/******************************************************************************
 * Name : ReportDetails.java Author : Administrator Date : 2010/05/08 Description : POJO implementation for ReportDetails
 *****************************************************************************/
package com.worldvisa.business.domain.request;

import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;
import org.directwebremoting.convert.BeanConverter;
import com.worldvisa.business.domain.profile.EmployeeDetails;
import com.worldvisa.business.domain.profile.UserDetails;
import com.worldvisa.business.domain.workflow.Payment;
import com.worldvisa.business.infra.base.BaseBean;

/**
 * POJO implementation for ReportDetails.
 */
@DataTransferObject(converter = BeanConverter.class, params={@Param(name="exclude", value="paymentDataSource")})
public class ReportDetails
extends BaseBean {
    private static final long           serialVersionUID = 1L;
    private String                      assignedTo;
    private EmployeeDetails             assignmentDetails;
    private Long                        caseStatus;
    private List<CountryPreferences>    countryPreferencesList;
    private List<CoursePreference>      coursePreferenceList;
    private Double                      discountAmount;
    private Long                        editableInd;
    private List<EduExperience>         eduExperienceList;
    private List<FamilyDetails>         familyDetailsList;
    private List<LangProfeciency>       langProfeciencieList;
    private Date                        lastStatusUpdate;
    private Long                        numColl;
    private Long                        numPass;
    private Long                        numUniv;
    private Long                        otherServices;
    private Double                      payableAmount;
    private List<Payment>               paymentList;
    private Long                        paymentOption;
    private UserDetails                 personalDetails;
    private String                      raisedBy;
    private java.util.Date              raisedDt;
    private String                      raisedFor;
    private List<RelativesAbroad>       relativesAbroadList;
    private Map<Long, ReportAttributes> reportAttributesMap;
    private String                      reportId;
    private Long                        requestMode;
    private Long                        requestType;
    private Integer                     rescheduledInd;
    // vaiable for storing the sla status
    private Long                        slaStatus;
    private List<TestScore>             testScoreList;
    private List<WorkExperience>        workExperienceList;

    /**
     * Instantiates ReportDetails with defaults
     */
    public ReportDetails() {
        super();
        this.personalDetails = new UserDetails();
        this.assignmentDetails = new EmployeeDetails();
    }

    /**
     * Instantiates ReportDetails with primary key combination
     */
    public ReportDetails(final String reportId) {
        super();
        this.personalDetails = new UserDetails();
        this.assignmentDetails = new EmployeeDetails();
        this.reportId = reportId;
    }

    /**
     * Getter method for assignedTo
     * @return assignedTo
     */
    public String getAssignedTo() {
        return this.assignedTo;
    }

    /**
     * @return the assignmentDetails
     */
    public EmployeeDetails getAssignmentDetails() {
        return this.assignmentDetails;
    }

    /**
     * @return the caseStatus
     */
    public Long getCaseStatus() {
        return this.caseStatus;
    }

    /**
     * @return the countryPreferencesList
     */
    public List<CountryPreferences> getCountryPreferencesList() {
        return this.countryPreferencesList;
    }

    /**
     * @return the coursePreferenceList
     */
    public List<CoursePreference> getCoursePreferenceList() {
        return this.coursePreferenceList;
    }

    /**
     * returns the total amount that is pending for payment for the customer
     * @return
     */
    public Double getCurrentPayableAmount() {
        double amount = 0;
        if (this.getNetPayableAmount() == null) {
            return amount;
        } else {
            amount = this.getNetPayableAmount();
        }
        if (this.getPaymentList() != null) {
            for (final Payment payment : this.getPaymentList()) {
                amount -= payment.getAmount();
            }
        }
        return amount;
    }

    /**
     * Getter method for discountAmount
     * @return discountAmount
     */
    public Double getDiscountAmount() {
        return this.discountAmount;
    }

    /**
     * @return the editableInd
     */
    public Long getEditableInd() {
        return this.editableInd;
    }

    /**
     * @return the eduExperienceList
     */
    public List<EduExperience> getEduExperienceList() {
        return this.eduExperienceList;
    }

    /**
     * @return the familyDetailsList
     */
    public List<FamilyDetails> getFamilyDetailsList() {
        return this.familyDetailsList;
    }

    /**
     * @return the langProfeciencieList
     */
    public List<LangProfeciency> getLangProfeciencieList() {
        return this.langProfeciencieList;
    }

    /**
     * @return the lastStatusUpdate
     */
    public Date getLastStatusUpdate() {
        if (null == this.lastStatusUpdate) {
            this.lastStatusUpdate = this.raisedDt;
        }
        return this.lastStatusUpdate;
    }

    /**
     * Getter method for net payableAmount
     * @return netPayableAmount
     */
    public Double getNetPayableAmount() {
        if (this.discountAmount == null || this.payableAmount == null) {
            return this.payableAmount;
        }
        return (this.payableAmount - this.discountAmount);
    }

    /**
     * @return the numColl
     */
    public Long getNumColl() {
        return this.numColl;
    }

    /**
     * @return the numPass
     */
    public Long getNumPass() {
        return this.numPass;
    }

    /**
     * @return the numUniv
     */
    public Long getNumUniv() {
        return this.numUniv;
    }

    /**
     * @return the otherServices
     */
    public Long getOtherServices() {
        return this.otherServices;
    }

    /**
     * Getter method for payableAmount
     * @return payableAmount
     */
    public Double getPayableAmount() {
        return this.payableAmount;
    }

    public JRDataSource getPaymentDataSource() {
        return new JRBeanCollectionDataSource(this.paymentList);
    }

    /**
     * @return the paymentList
     */
    public List<Payment> getPaymentList() {
        return this.paymentList;
    }

    /**
     * Getter method for paymentOption
     * @return paymentOption
     */
    public Long getPaymentOption() {
        return this.paymentOption;
    }

    /**
     * @return the personalDetails
     */
    public UserDetails getPersonalDetails() {
        return this.personalDetails;
    }

    /**
     * Getter method for raisedBy
     * @return raisedBy
     */
    public String getRaisedBy() {
        return this.raisedBy;
    }

    /**
     * Getter method for raisedDt
     * @return raisedDt
     */
    public java.util.Date getRaisedDt() {
        return this.raisedDt;
    }

    /**
     * Getter method for raisedFor
     * @return raisedFor
     */
    public String getRaisedFor() {
        return this.raisedFor;
    }

    /**
     * @return the relativesAbroadList
     */
    public List<RelativesAbroad> getRelativesAbroadList() {
        return this.relativesAbroadList;
    }

    /**
     * @return the reportAttributesMap
     */
    public Map<Long, ReportAttributes> getReportAttributesMap() {
        return this.reportAttributesMap;
    }

    /**
     * Getter method for reportId
     * @return reportId
     */
    public String getReportId() {
        return this.reportId;
    }

    /**
     * Getter method for requestMode
     * @return requestMode
     */
    public Long getRequestMode() {
        return this.requestMode;
    }

    /**
     * Getter method for requestType
     * @return requestType
     */
    public Long getRequestType() {
        return this.requestType;
    }

    /**
     * @return the rescheduledInd
     */
    public Integer getRescheduledInd() {
        return this.rescheduledInd;
    }

    /**
     * @return the slaStatus
     */
    public Long getSlaStatus() {
        return this.slaStatus;
    }

    /**
     * @return the testScoreList
     */
    public List<TestScore> getTestScoreList() {
        return this.testScoreList;
    }

    /**
     * @return the workExperienceList
     */
    public List<WorkExperience> getWorkExperienceList() {
        return this.workExperienceList;
    }

    /**
     * Setter method for assignedTo
     * @param assignedTo Value for assignedTo
     */
    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * @param assignmentDetails the assignmentDetails to set
     */
    public void setAssignmentDetails(final EmployeeDetails assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    /**
     * @param caseStatus the caseStatus to set
     */
    public void setCaseStatus(final Long caseStatus) {
        this.caseStatus = caseStatus;
    }

    /**
     * @param countryPreferencesList the countryPreferencesList to set
     */
    public void setCountryPreferencesList(final List<CountryPreferences> countryPreferencesList) {
        this.countryPreferencesList = countryPreferencesList;
    }

    /**
     * @param coursePreferenceList the coursePreferenceList to set
     */
    public void setCoursePreferenceList(final List<CoursePreference> coursePreferenceList) {
        this.coursePreferenceList = coursePreferenceList;
    }

    /**
     * Setter method for discountAmount
     * @param discountAmount Value for discountAmount
     */
    public void setDiscountAmount(final Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @param editableInd the editableInd to set
     */
    public void setEditableInd(final Long editableInd) {
        this.editableInd = editableInd;
    }

    /**
     * @param eduExperienceList the eduExperienceList to set
     */
    public void setEduExperienceList(final List<EduExperience> eduExperienceList) {
        this.eduExperienceList = eduExperienceList;
    }

    /**
     * @param familyDetailsList the familyDetailsList to set
     */
    public void setFamilyDetailsList(final List<FamilyDetails> familyDetailsList) {
        this.familyDetailsList = familyDetailsList;
    }

    /**
     * @param langProfeciencieList the langProfeciencieList to set
     */
    public void setLangProfeciencieList(final List<LangProfeciency> langProfeciencieList) {
        this.langProfeciencieList = langProfeciencieList;
    }

    /**
     * @param lastStatusUpdate the lastStatusUpdate to set
     */
    public void setLastStatusUpdate(final Date lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }

    /**
     * @param numColl the numColl to set
     */
    public void setNumColl(final Long numColl) {
        this.numColl = numColl;
    }

    /**
     * @param numPass the numPass to set
     */
    public void setNumPass(final Long numPass) {
        this.numPass = numPass;
    }

    /**
     * @param numUniv the numUniv to set
     */
    public void setNumUniv(final Long numUniv) {
        this.numUniv = numUniv;
    }

    /**
     * @param otherServices the otherServices to set
     */
    public void setOtherServices(final Long otherServices) {
        this.otherServices = otherServices;
    }

    /**
     * Setter method for payableAmount
     * @param payableAmount Value for payableAmount
     */
    public void setPayableAmount(final Double payableAmount) {
        this.payableAmount = payableAmount;
    }

    /**
     * @param paymentList the paymentList to set
     */
    public void setPaymentList(final List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    /**
     * Setter method for paymentOption
     * @param paymentOption Value for paymentOption
     */
    public void setPaymentOption(final Long paymentOption) {
        this.paymentOption = paymentOption;
    }

    /**
     * @param personalDetails the personalDetails to set
     */
    public void setPersonalDetails(final UserDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    /**
     * Setter method for raisedBy
     * @param raisedBy Value for raisedBy
     */
    public void setRaisedBy(final String raisedBy) {
        this.raisedBy = raisedBy;
    }

    /**
     * Setter method for raisedDt
     * @param raisedDt Value for raisedDt
     */
    public void setRaisedDt(final java.util.Date raisedDt) {
        this.raisedDt = raisedDt;
    }

    /**
     * Setter method for raisedFor
     * @param raisedFor Value for raisedFor
     */
    public void setRaisedFor(final String raisedFor) {
        this.raisedFor = raisedFor;
    }

    /**
     * @param relativesAbroadList the relativesAbroadList to set
     */
    public void setRelativesAbroadList(final List<RelativesAbroad> relativesAbroadList) {
        this.relativesAbroadList = relativesAbroadList;
    }

    /**
     * @param reportAttributesMap the reportAttributesMap to set
     */
    public void setReportAttributesMap(final Map<Long, ReportAttributes> reportAttributesMap) {
        this.reportAttributesMap = reportAttributesMap;
    }

    /**
     * Setter method for reportId
     * @param reportId Value for reportId
     */
    public void setReportId(final String reportId) {
        this.reportId = reportId;
    }

    /**
     * Setter method for requestMode
     * @param requestMode Value for requestMode
     */
    public void setRequestMode(final Long requestMode) {
        this.requestMode = requestMode;
    }

    /**
     * Setter method for requestType
     * @param requestType Value for requestType
     */
    public void setRequestType(final Long requestType) {
        this.requestType = requestType;
    }

    /**
     * @param rescheduledInd the rescheduledInd to set
     */
    public void setRescheduledInd(final Integer rescheduledInd) {
        this.rescheduledInd = rescheduledInd;
    }

    /**
     * @param slaStatus the slaStatus to set
     */
    public void setSlaStatus(final Long slaStatus) {
        this.slaStatus = slaStatus;
    }

    /**
     * @param testScoreList the testScoreList to set
     */
    public void setTestScoreList(final List<TestScore> testScoreList) {
        this.testScoreList = testScoreList;
    }

    /**
     * @param workExperienceList the workExperienceList to set
     */
    public void setWorkExperienceList(final List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }
}
