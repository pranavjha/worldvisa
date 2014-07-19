/**
 * 
 */
package com.worldvisa.business.views.profile;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.infra.annotations.AuthenticationBypass;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.infra.utils.Mailer;
import com.worldvisa.business.service.stub.ProfileService;
import com.worldvisa.business.service.stub.RequestService;

/**
 * @author Administrator
 */
@RemoteProxy
public class LoginView
extends BaseView {
    private static final long serialVersionUID = 3632245791032034001L;
    @Autowired
    private Mailer            mailer;
    @Autowired
    private ProfileService    profileService;
    @Autowired
    private RequestService    requestService;

    @RemoteMethod
    @AuthenticationBypass
    public DWRResponse<LoginDetails> activeSessionDetails(final LoginDetails cachedLoginDetails)
    throws Exception {
        final DWRResponse<LoginDetails> response = new DWRResponse<LoginDetails>();
        final LoginDetails loginDetails = this.user();
        if (loginDetails == null) {
            if (cachedLoginDetails != null && cachedLoginDetails.getEmailId() != null && cachedLoginDetails.getPassword() != null) {
                try {
                    return this.login(cachedLoginDetails);
                } catch (final Exception e) {
                    return null;
                }
            } else {
                return null;
            }
        }
        response.setNextScreen(this.calculateNextScreen(loginDetails));
        return response;
    }

    private String calculateNextScreen(final LoginDetails loginDetails)
    throws Exception {
        if (loginDetails.getPasswordExpFlag().equals(LookupConstants.INDICES.TRUE)) {
            return "profile/ChangePassword";
        } else {
            if (LookupConstants.USER_ROLE.Customer.equals(loginDetails.getUserRole())) {
                final ReportDetails report = new ReportDetails();
                report.setRaisedFor(loginDetails.getEmailId());
                return "request/ReportDetails/" + this.requestService.readRequest(report).getReportId();
            } else {
                return "workflow/Notification";
            }
        }
    }

    /**
     * @return
     * @throws Exception
     */
    @RemoteMethod
    @AuthenticationBypass
    public DWRResponse<LoginDetails> forgotPassword(final LoginDetails loginDetails)
    throws Exception {
        final DWRResponse<LoginDetails> response = new DWRResponse<LoginDetails>();
        final LoginDetails newLoginDetails = this.profileService.readLoginDetails(loginDetails);
        if (loginDetails.getEmailId() == null || loginDetails.getEmailId().trim().length() == 0) {
            throw new Exception("Please enter a valid email ID so that we can send you your password.");
        }
        if (null == newLoginDetails) {
            response.addMessage(new WebMessage("The specified mail ID is not registerd to our database", MessageSeverity.ERROR));
        } else {
            this.mailer.sendEmail(newLoginDetails, "Password Recovery Mail", "CustomerPasswordRecovery.html", newLoginDetails.getEmailId());
            response.addMessage(new WebMessage("We have mailed your password to your e-mail account.", MessageSeverity.INFO));
        }
        response.setMainObject(loginDetails);
        return response;
    }

    @RemoteMethod
    @AuthenticationBypass
    public DWRResponse<LoginDetails> inlineLogin(final LoginDetails loginDetails)
    throws Exception {
        final DWRResponse<LoginDetails> response = new DWRResponse<LoginDetails>();
        final LoginDetails newLoginDetails = this.profileService.readLoginDetails(loginDetails);
        if (null == newLoginDetails || !newLoginDetails.getPassword().equals(loginDetails.getPassword())
        || LookupConstants.INDICES.FALSE.equals(newLoginDetails.getIsActive())
        || LookupConstants.CLIENT_REG_STATUS.Unregisterd.equals(newLoginDetails.getRegistrationStatus())) {
            throw new Exception("The user id / password is not valid. Please try again.");
        }
        newLoginDetails.setPassword("");
        WebContextFactory.get().getSession().setAttribute("user", newLoginDetails);
        response.addMessage(new WebMessage("Hi, " + loginDetails.getEmailId() + ". Welcome to worldvisa infra.", MessageSeverity.INFO));
        response.setMainObject(newLoginDetails);
        return response;
    }

    /**
     * @return
     * @throws Exception
     */
    @RemoteMethod
    @AuthenticationBypass
    public DWRResponse<LoginDetails> login(final LoginDetails loginDetails)
    throws Exception {
        final DWRResponse<LoginDetails> response = this.inlineLogin(loginDetails);
        response.setNextScreen(this.calculateNextScreen(response.getMainObject()));
        return response;
    }
}
