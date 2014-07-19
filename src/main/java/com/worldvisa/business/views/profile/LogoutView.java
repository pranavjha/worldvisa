/**
 * 
 */
package com.worldvisa.business.views.profile;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.profile.LoginDetails;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;

/**
 * @author Administrator
 */
@RemoteProxy
public class LogoutView
extends BaseView {
    private static final long serialVersionUID = 3632245791032034001L;

    @RemoteMethod
    public DWRResponse<LoginDetails> logout() {
        final DWRResponse<LoginDetails> response = new DWRResponse<LoginDetails>();
        WebContextFactory.get().getSession().removeAttribute("user");
        WebContextFactory.get().getSession().invalidate();
        response.addMessage(new WebMessage("You have been successfully logged out from the system.", MessageSeverity.INFO));
        response.setNextScreen("profile/Login");
        return response;
    }
}
