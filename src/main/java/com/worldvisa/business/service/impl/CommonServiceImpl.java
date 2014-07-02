package com.worldvisa.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.worldvisa.business.dao.common.LookupDAO;
import com.worldvisa.business.dao.request.AttributeMasterDAO;
import com.worldvisa.business.dao.workflow.PaymentOptionDAO;
import com.worldvisa.business.domain.common.Lookup;
import com.worldvisa.business.domain.request.AttributeMaster;
import com.worldvisa.business.domain.request.ReportDetails;
import com.worldvisa.business.domain.workflow.PaymentOption;
import com.worldvisa.business.infra.base.BaseServiceImpl;
import com.worldvisa.business.service.stub.CommonService;

@Service
@Scope("singleton")
public class CommonServiceImpl
extends BaseServiceImpl
implements CommonService {
    @Autowired
    private AttributeMasterDAO                                     attributeMasterDAO;
    @Autowired
    private LookupDAO                                              lookupDAO;
    @Autowired
    private PaymentOptionDAO                                       paymentOptionDAO;
    private Map<Long, Map<Long, AttributeMaster>>                  reportAttributesMap;
    private Map<Long, Map<String, Map<Long, List<PaymentOption>>>> requestTypeCountryPaymentModeMap;

    private String evaluate(final String args) {
        final Context cx = Context.enter();
        try {
            final Scriptable scope = cx.initStandardObjects();
            final Object result = cx.evaluateString(scope, args, "<cmd>", 1, null);
            return Context.toString(result);
        } finally {
            // Exit from the context.
            Context.exit();
        }
    }

    @Autowired
    public void init()
    throws Exception {
        final List<AttributeMaster> attributeList = this.attributeMasterDAO.readList(new AttributeMaster());
        this.reportAttributesMap = new HashMap<Long, Map<Long, AttributeMaster>>();
        for (final AttributeMaster attribute : attributeList) {
            if (!this.reportAttributesMap.containsKey(attribute.getReportType())) {
                this.reportAttributesMap.put(attribute.getReportType(), new HashMap<Long, AttributeMaster>());
            }
            this.reportAttributesMap.get(attribute.getReportType()).put(attribute.getAttributeId(), attribute);
        }
        final List<PaymentOption> paymentOptions = this.paymentOptionDAO.readList(new PaymentOption());
        this.requestTypeCountryPaymentModeMap = new HashMap<Long, Map<String, Map<Long, List<PaymentOption>>>>();
        for (final PaymentOption option : paymentOptions) {
            option.setCountry(option.getCountry().toLowerCase().replaceAll(" ", "").trim());
            if (!this.requestTypeCountryPaymentModeMap.containsKey(option.getRequestType())) {
                this.requestTypeCountryPaymentModeMap.put(option.getRequestType(), new HashMap<String, Map<Long, List<PaymentOption>>>());
            }
            if (!this.requestTypeCountryPaymentModeMap.get(option.getRequestType()).containsKey(option.getCountry())) {
                this.requestTypeCountryPaymentModeMap.get(option.getRequestType()).put(option.getCountry(), new HashMap<Long, List<PaymentOption>>());
            }
            if (!this.requestTypeCountryPaymentModeMap.get(option.getRequestType()).get(option.getCountry()).containsKey(option.getInstalmentPlan())) {
                this.requestTypeCountryPaymentModeMap.get(option.getRequestType()).get(option.getCountry())
                .put(option.getInstalmentPlan(), new ArrayList<PaymentOption>());
            }
            this.requestTypeCountryPaymentModeMap.get(option.getRequestType()).get(option.getCountry()).get(option.getInstalmentPlan()).add(option);
        }
    }

    public List<Lookup> readLookupList(final Long parentLookupId)
    throws Exception {
        final Lookup lkp = new Lookup();
        lkp.setParentLookupId(parentLookupId);
        return this.lookupDAO.readList(lkp);
    }

    public Map<Long, Lookup> readLookupMap()
    throws Exception {
        final List<Lookup> lookupList = this.lookupDAO.readList(new Lookup());
        final Map<Long, Lookup> lookupVal = new HashMap<Long, Lookup>();
        for (final Lookup lookup : lookupList) {
            lookupVal.put(lookup.getLookupId(), lookup);
        }
        return lookupVal;
    }

    /**
     * @return the paymentOptionsMap
     */
    @SuppressWarnings("unchecked")
    public List<PaymentOption> readPaymentOptions(final ReportDetails reportDetails)
    throws Exception {
        String country = reportDetails.getCountryPreferencesList().get(0).getCountry();
        final Long serviceType = reportDetails.getRequestType();
        final Long paymentMode = reportDetails.getPaymentOption();
        country = country.toLowerCase().replaceAll(" ", "").trim();
        List<PaymentOption> list = null;
        if (this.requestTypeCountryPaymentModeMap.containsKey(serviceType)) {
            try {
                list = this.requestTypeCountryPaymentModeMap.get(serviceType).get(country).get(paymentMode);
            } catch (final Exception exception) {
                try {
                    list = this.requestTypeCountryPaymentModeMap.get(serviceType).get("default").get(paymentMode);
                } catch (final Exception e) {
                    throw new Exception(
                    "No Payment Option configured for the entered payment mode and service type. You may enter the amount manually.");
                }
            }
            if (list == null) {
                throw new Exception("No Payment Option configured for the entered payment mode and service type. You may enter the amount manually.");
            }
            for (final PaymentOption option : list) {
                String evaluationFunction = option.getAmount();
                final Map<String, Object> beanMap = BeanUtils.describe(reportDetails);
                for (final String keys : beanMap.keySet()) {
                    String value = "0";
                    if (beanMap.get(keys) != null) {
                        value = beanMap.get(keys).toString();
                    }
                    evaluationFunction = evaluationFunction.replaceAll(keys, value);
                }
                BaseServiceImpl.log.debug("Payment evaluation string is:: " + evaluationFunction);
                option.setAmountValue(Double.parseDouble(this.evaluate(evaluationFunction)));
            }
        } else {
            throw new Exception("No Payment Option configured for the entered payment mode. You may enter the amount manually.");
        }
        return list;
    }

    /**
     * @return the reportAttributesMap
     */
    public AttributeMaster readReportAttribute(final Long reportType, final Long attributeId) {
        try {
            return this.reportAttributesMap.get(reportType).get(attributeId);
        } catch (final Exception exception) {
            return null;
        }
    }
}