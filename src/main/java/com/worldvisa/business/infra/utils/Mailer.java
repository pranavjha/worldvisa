/******************************************************************************
 * Name : Mailer.java Author : Administrator Date : 09-May-2010,2:27:56 AM Description : Implementation for Mailer
 *****************************************************************************/
package com.worldvisa.business.infra.utils;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.worldvisa.business.domain.common.FileBean;
import com.worldvisa.business.infra.base.BaseBean;

@Component
public class Mailer {
    protected static final Logger log = Logger.getLogger(Mailer.class);
    @Autowired
    private MailPreparator        mailPreparator;
    @Autowired
    private JavaMailSender        mailSender;

    public void sendEmail(final BaseBean baseBean, final String subject, final String templateName, final String toEmailId, final FileBean... details)
    throws Exception {
        final JavaMailSender mailSender = this.mailSender;
        final MailPreparator mailPreparator = this.mailPreparator;
        BasicUtils.executeAsThread(new Runnable() {
            public void run() {
                try {
                    mailSender.send(mailPreparator.setup(toEmailId.split(";"), baseBean, "mailTemplates/" + templateName, subject, details));
                } catch (final Exception e) {
                    Mailer.log.error(e);
                }
            }
        });
    }

    public void sendEmail(final String subject, final String body, final String toEmailId, final List<FileBean> attatchments, final boolean addBcc)
    throws Exception {
        final JavaMailSender mailSender = this.mailSender;
        final MailPreparator mailPreparator = this.mailPreparator;
        BasicUtils.executeAsThread(new Runnable() {
            public void run() {
                try {
                    mailSender.send(mailPreparator.setupSimpleMail(subject, body, toEmailId.split(";"), attatchments, addBcc));
                } catch (final Exception e) {
                    Mailer.log.error(e);
                }
            }
        });
    }
}
