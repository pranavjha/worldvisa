/**
 * 
 */
package com.worldvisa.business.views.market;

import java.util.List;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import com.worldvisa.business.domain.common.DWRResponse;
import com.worldvisa.business.domain.common.WebMessage;
import com.worldvisa.business.domain.market.Communication;
import com.worldvisa.business.domain.market.CommunicationsFilter;
import com.worldvisa.business.infra.base.BaseView;
import com.worldvisa.business.infra.constants.MessageSeverity;
import com.worldvisa.business.service.stub.MarketService;

/**
 * @author Administrator
 */
@RemoteProxy
public class ListTemplatesView
extends BaseView {
    private static final long serialVersionUID = 5882116114765277848L;
    @Autowired
    private MarketService     marketService;

    @RemoteMethod
    public DWRResponse<Communication> deleteCommunication(final Communication communication)
    throws Exception {
        final DWRResponse<Communication> response = new DWRResponse<Communication>();
        this.marketService.deleteCommunication(communication);
        response.setMainObject(communication);
        response.addMessage(new WebMessage("The selected communication template is now deleted successfully.", MessageSeverity.INFO));
        return response;
    }

    @RemoteMethod
    public List<Communication> getCommunicationList(final CommunicationsFilter filter)
    throws Exception {
        return this.marketService.readCommunicationList(filter);
    }

    @RemoteMethod
    public Integer getCommunicationListCount(final CommunicationsFilter filter)
    throws Exception {
        return this.marketService.readCommunicationListCount(filter);
    }
}
