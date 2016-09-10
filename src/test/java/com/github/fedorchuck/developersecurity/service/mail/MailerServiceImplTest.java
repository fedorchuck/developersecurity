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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Collections;

/**
 * @author fedorchuck.
 */
public class MailerServiceImplTest {

    public static void main(String[] args) throws MessagingException {

        //Create the application context
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "/src/main/webapp/WEB-INF/application-context.xml",
                "/src/main/webapp/WEB-INF/spring-database.xml",
                "/src/main/webapp/WEB-INF/spring-security.xml",
                "/src/main/webapp/WEB-INF/spring-web-config.xml",
                "/src/main/webapp/WEB-INF/mail-service.xml"
        );

        //Get the mailer instance
        MailerServiceImpl mailer = (MailerServiceImpl) context.getBean("mailService");

        String to = "randomholy@gmail.com";//"skype.v.v@gmail.com";
        String subject = "no reply";

        String body = "Just test. Method *sendMail*";
        //Send a composed mail
//        mailer.sendMail(to, subject, body);
        System.out.println("DONE!");
        File file = new File("/home/fedorchuck/Dropbox/Documents/manlove.pdf");
        body = "Just test. Method *sendMailWithAttachments*";
        mailer.sendMailWithAttachments(to,subject,body, Collections.singletonList(file));
        System.out.println("DONE!");
    }
}