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

import com.github.fedorchuck.developersecurity.dao.impl.Internal;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;

/**
 * @author fedorchuck.
 */
@Service("accountEmailService")
@Transactional
public class AccountEmailServiceImpl implements AccountEmailService {
    private final Logger log = Logger.getLogger(AccountEmailServiceImpl.class);
    private final MailerService mailerService;
    private final Internal internal;

    public AccountEmailServiceImpl(MailerService mailerService, Internal internal) {
        this.mailerService = mailerService;
        this.internal = internal;
    }

    @Override
    public void sendFeedback(Developer developer, String feedbackMessage) throws AddressException {
        String message = "feedback: " + feedbackMessage + "\n\nfrom: " + developer.toString();
        mailerService.sendMail(internal.getWhereSendFeedback(), "developersecurity Feedback", message);
        log.log(Level.DEBUG, "send feedback: "+developer.toString()+"; message: "+message);
    }

    @Override
    public void confirmEmail(String email, String token) throws AddressException {
        String ACCOUNT_VERIFICATION_MESSAGE =
            "Hi there!\n" +
            "\n" +
            "Someone (hopefully you) just created an account at developersecurity.herokuapp.com.\n" +
            "\n" +
            "Before we do anything else, we need to confirm that this request came from you, " +
            "so if it did then please click the link below to confirm your account:\n" +
            "\n" +
            "https://developersecurity.herokuapp.com/confirm?confirm_email="+email+"&confirm_string="+token+"\n" +
            "\n" +
            "After you confirm your account, we'll provide you with some additional information to get you started.";
        mailerService.sendMail(email,"Verify Your Account", ACCOUNT_VERIFICATION_MESSAGE);
    }

    //    https://developersecurity.herokuapp.com/confirm?confirm_email="+email+"&confirm_string="+token+"&email="{old/new}"
    @Override
    public void confirmChangeEmail(String email, String token, String emailStatus) throws AddressException {
        String EMAIL_CHANGE_MESSAGE =
            "Hi there!\n" +
            "\n" +
            "Someone (hopefully you) tried to change email to account at developersecurity.herokuapp.com.\n" +
            "\n" +
            "Before we do anything else, we need to confirm that this request came from you, " +
            "so if it did then please click the link below to confirm your account:\n" +
            "\n" +
            "https://developersecurity.herokuapp.com/confirm?confirm_email="+email+"&confirm_string="+token+"&email="+emailStatus+"\n" +
            "\n" +
            "If did not - just ignoring this letter. Also you should confirm another email address.";
        mailerService.sendMail(email,"Verify Your Account", EMAIL_CHANGE_MESSAGE);
    }

    @Override
    public void resetPassword(String email, String token) throws AddressException {
        String RESET_PASSWORD_MESSAGE =
            "Hi there!\n" +
            "\n" +
            "Someone (hopefully you) try to change password to your account at developersecurity.herokuapp.com.\n" +
            "\n" +
            "Before we do anything else, we need to confirm that this request came from you, " +
            "so if it did then please click the link below to confirm to change password for your account:\n" +
            "\n" +
            "https://developersecurity.herokuapp.com/reset?token="+token+"\n" +
            "\n" +
            "If did not - just ignoring this letter.";
        mailerService.sendMail(email,"Reset Password", RESET_PASSWORD_MESSAGE);
    }
}
