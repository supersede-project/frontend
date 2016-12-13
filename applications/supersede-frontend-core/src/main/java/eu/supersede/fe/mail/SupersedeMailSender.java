/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.supersede.fe.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Mail sender.
 */
@Component
public class SupersedeMailSender
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    /**
     * Send an email with the given subject and the given text to the given recipients.
     * @param subject
     * @param text
     * @param to
     */
    public void sendEmail(String subject, String text, String... recipients)
    {
        if (javaMailSender != null)
        {
            try
            {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper;
                // SSL Certificate.
                helper = new MimeMessageHelper(message, true);
                helper.setSubject(subject);
                helper.setTo(recipients);
                helper.setText(text, true);
                javaMailSender.send(message);
            }
            catch (MailException ex)
            {
                log.error("Exception while send an email: " + ex.getMessage());
                ex.printStackTrace();
            }
            catch (MessagingException ex)
            {
                log.error("Exception while send an email: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        else
        {
            log.error("Java mail not configured");
        }
    }
}