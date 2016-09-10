/*
 * Copyright 2016 Volodymyr Fedorchuk <vl.fedorchuck@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fedorchuck.developersecurity.service.mail;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * @author fedorchuck.
 */
@Service("mailService")
@Transactional
public class MailerServiceImpl implements MailerService {
    private final Logger log = Logger.getLogger(MailerServiceImpl.class);
    private final InternetAddress FROM;
    private final MailSender mailSender;

    public MailerServiceImpl(JavaMailSenderImpl mailSender) throws AddressException {
        this.mailSender = mailSender;
        this.FROM = new InternetAddress(mailSender.getUsername());
    }

    /**
     * This method will send compose and send the message
     * */
    @Override
    public void sendMail(String to, String subject, String body) throws AddressException {
        log.log(Level.DEBUG, "try to send. to: "+to);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(FROM));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.log(Level.DEBUG, "send successful.");
    }

    @Override
    public void sendMailWithAttachments(String to, String subject, String body, List<File> attachments) throws MessagingException {
        JavaMailSender mailSender = (JavaMailSender) this.mailSender;

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);/*simpleMailMessage.getTo()*/
            helper.setSubject(subject);/*simpleMailMessage.getSubject()*/
            helper.setText(body);/*simpleMailMessage.getText()*/

            for (File attachment : attachments){
                helper.addAttachment(attachment.getName(), attachment);
            }
        } catch (MessagingException e) {
            log.log(Level.ERROR, e);
            throw new MailParseException(e);
        }

        message.setFrom(FROM);
        mailSender.send(message);
    }
}
