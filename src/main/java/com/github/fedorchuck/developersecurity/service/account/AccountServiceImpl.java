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

import com.github.fedorchuck.developersecurity.dao.impl.postgresql.ApplicationsJDBCTemplate;
import com.github.fedorchuck.developersecurity.dao.impl.postgresql.DevelopersJDBCTemplate;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.service.mail.AccountEmailService;
import com.github.fedorchuck.developersecurity.throwable.ValidationException;
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

/**
 * @author fedorchuck.
 */
@Service("accountService")
@Transactional
public class AccountServiceImpl extends TimeUtil implements AccountService {
    private final Logger log = Logger.getLogger(AccountServiceImpl.class);
    private final AccountEmailService accountEmailService;
    private final DevelopersJDBCTemplate developersJDBCTemplate;
    private final ApplicationsJDBCTemplate applicationsJDBCTemplate;

    @Autowired
    public AccountServiceImpl(ApplicationsJDBCTemplate applicationsJDBCTemplate,
                              DevelopersJDBCTemplate developersJDBCTemplate,
                              AccountEmailService accountEmailService) {
        this.accountEmailService = accountEmailService;
        this.applicationsJDBCTemplate = applicationsJDBCTemplate;
        this.developersJDBCTemplate = developersJDBCTemplate;
    }

    @Override
    public void createAccount(String name, String email, String password) throws ValidationException {
        if (developersJDBCTemplate.emailExist(email)) {
            log.log(Level.WARN, "try to create account with email: "+email+", but account with this email already exist.");
            throw new ValidationException("Account with this email exist.");
        }
        else {
            String token = getUniqueTokenDev();

            try {
                accountEmailService.confirmEmail(email, token);
                log.log(Level.DEBUG, "sanded confirm email to: "+email);
            } catch (AddressException e) {
                log.log(Level.WARN, "try to create account with email: "+email);
                throw new ValidationException("Bad address.");
            }

            Developer newUser = new Developer(token,email, password,name,false,"ROLE_USER",
                    convertDateToUnix(new Date()), convertDateToUnix(new Date()));
            developersJDBCTemplate.save(newUser);
            log.log(Level.DEBUG, "added record: "+newUser.toString());
        }
    }

    @Override
    public Developer getDeveloper(String email) {
        return developersJDBCTemplate.getDeveloper(email, developerEmail);
    }

    @Override
    public void updateDateOfLastUsed(String developerToken) {
        developersJDBCTemplate.updateDateOfLastUsed(developerToken, convertDateToUnix(new Date()));
        log.log(Level.DEBUG, "updated date of last used for record: "+developerToken);
    }

    @Override
    public void deleteAccount(String developerToken, String developerEmail) {
        log.log(Level.DEBUG, "try to remove account: "+developerToken+", with email: "+developerEmail);
        applicationsJDBCTemplate.deleteApplications(developerToken);
        developersJDBCTemplate.delete(developerEmail);
        log.log(Level.DEBUG, "account: "+developerToken+", with email: "+developerEmail+" was removed.");
    }

    private String getUniqueTokenDev(){
        String tmp = UUID.randomUUID().toString();
        if (!developersJDBCTemplate.tokenExist(tmp))
            return tmp;
        else
            return getUniqueTokenDev();
    }
}
