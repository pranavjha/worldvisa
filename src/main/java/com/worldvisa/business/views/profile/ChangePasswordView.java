package com.worldvisa.business.views.profile;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.service.stub.ProfileService;

@RemoteProxy
public class ChangePasswordView
extends BaseView {
    private static final long serialVersionUID = 5882116114765277848L;
    @Autowired
    private ProfileService    profileService;

    @RemoteMethod
    public DWRResponse<Boolean> changePassword(final String newPassword)
    throws Exception {
        final DWRResponse<Boolean> result = new DWRResponse<Boolean>();
        this.profileService.updatePassword(this.user(), newPassword);
        result.setMainObject(true);
        result.addMessage(new WebMessage("Password changed successfully. Please login with your new password from now.", MessageSeverity.INFO));
        result.setNextScreen("profile/Login");
        return result;
    }
}
