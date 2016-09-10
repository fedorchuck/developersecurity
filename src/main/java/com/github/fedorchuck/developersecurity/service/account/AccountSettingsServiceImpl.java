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

package com.github.fedorchuck.developersecurity.service.account;

import com.github.fedorchuck.developersecurity.dao.impl.postgresql.ChangeEmailJDBCTemplate;
import com.github.fedorchuck.developersecurity.dao.impl.postgresql.DevelopersJDBCTemplate;
import com.github.fedorchuck.developersecurity.dao.impl.postgresql.ResetPasswordJDBCTemplate;
import com.github.fedorchuck.developersecurity.domainmodels.ChangeEmail;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.domainmodels.ResetPassword;
import com.github.fedorchuck.developersecurity.service.mail.AccountEmailService;
import com.github.fedorchuck.developersecurity.throwable.ValidationException;
import com.github.fedorchuck.developersecurity.utils.Constants;
import com.github.fedorchuck.developersecurity.utils.TimeUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import java.util.Date;
import java.util.UUID;

import static com.github.fedorchuck.developersecurity.utils.Constants.developerEmail;
import static com.github.fedorchuck.developersecurity.utils.Constants.developerToken;

/**
 * @author fedorchuck.
 */
@Service("accountSettingsService")
@Transactional
public class AccountSettingsServiceImpl extends TimeUtil implements AccountSettingsService {
    private final Logger log = Logger.getLogger(AccountSettingsServiceImpl.class);
    private final AccountEmailService accountEmailService;
    private final DevelopersJDBCTemplate developersJDBCTemplate;
    private final ResetPasswordJDBCTemplate resetPasswordJDBCTemplate;
    private final ChangeEmailJDBCTemplate changeEmailJDBCTemplate;

    @Autowired
    public AccountSettingsServiceImpl(AccountEmailService accountEmailService,
                                      DevelopersJDBCTemplate developersJDBCTemplate,
                                      ResetPasswordJDBCTemplate resetPasswordJDBCTemplate,
                                      ChangeEmailJDBCTemplate changeEmailJDBCTemplate) {
        this.accountEmailService = accountEmailService;
        this.developersJDBCTemplate = developersJDBCTemplate;
        this.resetPasswordJDBCTemplate = resetPasswordJDBCTemplate;
        this.changeEmailJDBCTemplate = changeEmailJDBCTemplate;
    }


    @Override
    public void updateEnabled(String confirm_string) {
        developersJDBCTemplate.updateEnabled(confirm_string);
        log.log(Level.DEBUG, "updated enabled account by string: "+confirm_string);
    }

    @Override
    public boolean confirmEmail(String confirm_email, String confirm_string) {
        Developer developer = developersJDBCTemplate.getDeveloper(confirm_string, developerToken);
        return developer != null && developer.getEmail().equals(confirm_email);
    }

    @Override
    public void changeEmail(String oldEmail, String newEmail) throws ValidationException {
        if (developersJDBCTemplate.emailExist(newEmail)) {
            log.log(Level.DEBUG, "account with this email exist: "+newEmail);
            throw new ValidationException("Account with this email exist.");
        }

        Developer tmp = developersJDBCTemplate.getDeveloper(oldEmail,developerEmail);
        String token = tmp.getDeveloperToken();

        try {
            accountEmailService.confirmChangeEmail(oldEmail, token, "old");
            accountEmailService.confirmChangeEmail(newEmail, token, "new");
            changeEmailJDBCTemplate.addRecord(token,oldEmail,false,newEmail,false,convertDateToUnix(new Date()));
        } catch (AddressException e) {
            log.log(Level.DEBUG, "try to sand email. "+e);
            throw new ValidationException("Bad address.");
        }
    }

    @Override
    public String setEmail(String confirm_email, String confirm_string, String emailStatus) {
        ChangeEmail changeEmail = changeEmailJDBCTemplate.getCurrent(confirm_string);
        switch (emailStatus){
            case "new":
                if (changeEmail.getNew_email().equals(confirm_email) && !changeEmail.getNew_email_confirm()) {
                    changeEmailJDBCTemplate.changeStatusForNew(confirm_string);
                    return "New email confirmed.";
                } else
                    return "Bad data";
            case "old":
                if (changeEmail.getOld_email().equals(confirm_email) && !changeEmail.getOld_email_confirm()) {
                    changeEmailJDBCTemplate.changeStatusForOld(confirm_string);
                    return "Old email confirmed.";
                } else
                    return "Bad data";
            default:
                return "Bad data";
        }
    }

    /**
     * @return true if email was updated*/
    @Override
    public boolean updateEmail(String developerToken) {
        ChangeEmail changeEmail = changeEmailJDBCTemplate.getCurrent(developerToken);
        if(changeEmail.getNew_email_confirm()&&changeEmail.getOld_email_confirm()) {
            developersJDBCTemplate.setEmail(developerToken, changeEmail.getNew_email());
            changeEmailJDBCTemplate.delete(developerToken);
            return true;
        } else
            return false;
    }

    @Override
    public void setPassword(String developerToken, String newPassword) {
        log.log(Level.DEBUG, "set password for user: "+developerToken);
        developersJDBCTemplate.setPassword(developerToken, newPassword);
    }

    @Override
    public void resetPassword(String email) throws AddressException {
        String token = getUniqueTokenResetPassword();
        resetPasswordJDBCTemplate.save(new ResetPassword(token,email));
        accountEmailService.resetPassword(email, token);
    }

    @Override
    public void resetPassword(String tmpToken, String newPassword) {
        ResetPassword resetPassword = resetPasswordJDBCTemplate.getRecord(tmpToken);
        Developer developer = developersJDBCTemplate.getDeveloper(resetPassword.getEmail(), Constants.developerEmail);
        developersJDBCTemplate.setPassword(developer.getDeveloperToken(),newPassword);
        resetPasswordJDBCTemplate.delete(tmpToken);
    }

    /**
     * @return true if token exist.
     * */
    @Override
    public boolean isResetPassword(String token) {
        return resetPasswordJDBCTemplate.tokenExist(token);
    }

    private String getUniqueTokenResetPassword(){
        String tmp = UUID.randomUUID().toString();
        if (!resetPasswordJDBCTemplate.tokenExist(tmp))
            return tmp;
        else
            return getUniqueTokenResetPassword();
    }
}
