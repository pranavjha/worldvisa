package com.worldvisa.business.service.stub;

import java.util.List;
import java.util.Map;
import com.worldvisa.business.domain.common.Lookup;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.workflow.PaymentOption;

public interface CommonService {
    public List<Lookup> readLookupList(Long parentLookupId)
    throws Exception;

    public Map<Long, Lookup> readLookupMap()
    throws Exception;

    public List<PaymentOption> readPaymentOptions(ReportDetails reportDetails)
    throws Exception;

    public AttributeMaster readReportAttribute(final Long reportType, final Long attributeId);
}
