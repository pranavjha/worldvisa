/******************************************************************************
 * Name : MailPreparator.java Author : Administrator Date : 09-May-2010,5:27:39 AM Description : Implementation for MailPreparator
 *****************************************************************************/
package com.worldvisa.business.infra.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.infra.base.BaseBean;
import com.worldvisa.business.service.stub.CommonService;
import com.worldvisa.business.service.stub.FileHandlerService;

/**
 * 
 */
@Component
public class MailPreparator
implements MimeMessagePreparator {
    private static final Logger     log = Logger.getLogger(MailPreparator.class);
    private Boolean                 addBcc;
    private Map<String, DataSource> attatchmentsMap;
    private String                  body;
    @Autowired
    private CommonService           commonService;
    private BaseBean                dataSource;
    private String                  defaultCCMailIds;
    @Autowired
    private FileHandlerService      fileHandlerService;
    private String                  fromMailId;
    private boolean                 isSimple;
    private String                  subject;
    private String                  templatePath;
    private String[]                toEmailId;
    @Autowired
    private VelocityEngine          velocityEngine;

    /**
     * @return the defaultCCMailIds
     */
    public String getDefaultCCMailIds() {
        return this.defaultCCMailIds;
    }

    /**
     * @return the fromMailId
     */
    public String getFromMailId() {
        return this.fromMailId;
    }

    /**
     * @see org.springframework.mail.javamail.MimeMessagePreparator#prepare(javax.mail.internet.MimeMessage)
     */
    public void prepare(final MimeMessage mime)
    throws Exception {
        MailPreparator.log.debug("-------------------------------------------------------------------------------------------");
        mime.setFrom(new InternetAddress(this.fromMailId));
        MailPreparator.log.debug("From: " + this.fromMailId);
        for (final String toEmails : this.toEmailId) {
            MailPreparator.log.debug("To:   " + toEmails);
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails));
        }
        if (this.defaultCCMailIds != null && (this.addBcc == null || this.addBcc == true)) {
            final String[] cc = this.defaultCCMailIds.split(";");
            for (final String ccIds : cc) {
                MailPreparator.log.debug("CC:   " + ccIds);
                mime.addRecipient(Message.RecipientType.CC, new InternetAddress(ccIds));
            }
        }
        MailPreparator.log.debug("Subject: " + this.subject);
        mime.setSubject(this.subject);
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("obj", this.dataSource);
        model.put("lookup", this.commonService.readLookupMap());
        String text = this.body;
        if (!this.isSimple) {
            text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, this.templatePath, model);
        }
        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        final MimeMultipart multipart = new MimeMultipart("related");
        // first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        final String htmlText = text.replaceAll("download/MARKET_FILE_DETAILS/[0-9]+/", "cid:");
        messageBodyPart.setContent(htmlText, "text/html");
        MailPreparator.log.debug("Body: " + htmlText);
        // add it
        multipart.addBodyPart(messageBodyPart);
        // second part (the image)
        // attach the file to the message
        for (final String fileName : this.attatchmentsMap.keySet()) {
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(this.attatchmentsMap.get(fileName)));
            if (htmlText.indexOf("cid:" + fileName) != -1) {
                messageBodyPart.setHeader("Content-ID", "<" + fileName + ">");
            } else {
                messageBodyPart.setFileName(fileName);
            }
            // add it
            multipart.addBodyPart(messageBodyPart);
        }
        // put everything together
        mime.setContent(multipart);
    }

    /**
     * @param defaultCCMailIds the defaultCCMailIds to set
     */
    public void setDefaultCCMailIds(final String defaultCCMailIds) {
        this.defaultCCMailIds = defaultCCMailIds;
    }

    /**
     * @param fromMailId the fromMailId to set
     */
    public void setFromMailId(final String fromMailId) {
        this.fromMailId = fromMailId;
    }

    /**
     * @param toEmailIds
     * @param dataSource
     * @param templatePath
     * @throws Exception
     */
    public MimeMessagePreparator setup(final String[] toEmailId, final BaseBean dataSource, final String templatePath, final String subject,
    final FileBean... attachments)
    throws Exception {
        this.toEmailId = toEmailId;
        this.dataSource = dataSource;
        this.templatePath = templatePath;
        this.subject = subject;
        this.attatchmentsMap = new HashMap<String, DataSource>();
        for (final FileBean stream : attachments) {
            this.attatchmentsMap.put(stream.getFileName(), new FileDataSource(stream.getRelativePath()));
        }
        this.isSimple = false;
        return this;
    }

    /**
     * @param subject2
     * @param body
     * @param toEmailId2
     * @param attatchments
     * @param addBcc
     * @return
     * @throws Exception
     */
    public MimeMessagePreparator setupSimpleMail(final String subject, final String body, final String[] toEmailId,
    final List<FileBean> attatchments, final boolean addBcc)
    throws Exception {
        this.isSimple = true;
        this.toEmailId = toEmailId;
        this.subject = subject;
        this.attatchmentsMap = new HashMap<String, DataSource>();
        this.body = body;
        this.addBcc = addBcc;
        for (final FileBean stream : attatchments) {
            final File file = this.fileHandlerService.readFile(stream);
            this.attatchmentsMap.put(file.getName(), new FileDataSource(file));
        }
        return this;
    }
}
