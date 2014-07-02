/**
 * 
 */
package com.worldvisa.business.views.market;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.LookupConstants;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.service.stub.MarketService;
import com.worldvisa.business.service.stub.ProfileService;

/**
 * @author Administrator
 */
@RemoteProxy
public class AddTemplateView
extends BaseView {
    private static final long serialVersionUID = 5882116114765277848L;
    @Autowired
    private MarketService     marketService;
    @Autowired
    private ProfileService    profileService;

    @RemoteMethod
    public Communication readCommunication(final Communication communication)
    throws Exception {
        return this.marketService.readCommunication(communication);
    }

    @RemoteMethod
    public DWRResponse<Communication> saveCommunication(final Communication communication)
    throws Exception {
        final DWRResponse<Communication> response = new DWRResponse<Communication>();
        response.setMainObject(this.marketService.createCommunication(communication));
        response.addMessage(new WebMessage("Communication Details saved successfully", MessageSeverity.INFO));
        this.profileService.createPublicDart(this.user(), "Updated mailer with id : " + communication.getSeqNum(),
        LookupConstants.CASE_SUBSTATUS.Miscellaneous, null);
        return response;
    }
}
