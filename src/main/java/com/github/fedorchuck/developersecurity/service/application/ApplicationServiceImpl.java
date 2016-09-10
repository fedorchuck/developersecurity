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

package com.github.fedorchuck.developersecurity.service.application;

import com.github.fedorchuck.developersecurity.dao.impl.postgresql.ApplicationsJDBCTemplate;
import com.github.fedorchuck.developersecurity.dao.impl.postgresql.DevelopersJDBCTemplate;
import com.github.fedorchuck.developersecurity.domainmodels.Application;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.utils.Constants;
import com.github.fedorchuck.developersecurity.utils.TimeUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author fedorchuck.
 */
@Service("applicationService")
@Transactional
public class ApplicationServiceImpl extends TimeUtil implements ApplicationService {
    private final Logger log = Logger.getLogger(ApplicationServiceImpl.class);
    private final DevelopersJDBCTemplate developersJDBCTemplate;
    private final ApplicationsJDBCTemplate applicationsJDBCTemplate;

    @Autowired
    public ApplicationServiceImpl(DevelopersJDBCTemplate developersJDBCTemplate,
                                  ApplicationsJDBCTemplate applicationsJDBCTemplate) {
        this.developersJDBCTemplate = developersJDBCTemplate;
        this.applicationsJDBCTemplate = applicationsJDBCTemplate;
    }

    @Override
    public void createApi(String shortName, String response, String message, String developerToken) {
        log.log(Level.DEBUG, "try to added record: "+shortName+"; "+response+"; "+message+"; "+developerToken);
        Developer developer = developersJDBCTemplate.getDeveloper(developerToken, Constants.developerToken);
        String token = getUniqueTokenApp();
        Application application = new Application(
                developer.getDeveloperId(),developerToken,
                token, shortName,
                response,message,"https://developersecurity.herokuapp.com/documentation",
                convertDateToUnix(new Date()),0L);
        applicationsJDBCTemplate.save(application);
    }

    @Override
    public void updateApi(String shortName, String response, String message, String applicationToken) {
        applicationsJDBCTemplate.updateApplication(shortName,response,message,convertDateToUnix(new Date()),applicationToken);
    }

    @Override
    public void removeApi(String applicationToken) {
        applicationsJDBCTemplate.deleteApplication(applicationToken);
    }

    @Override
    public void updateDateOfLastUsedByApi(String applicationToken) {
        applicationsJDBCTemplate.updateDateOfLastUsedByApi(applicationToken,convertDateToUnix(new Date()));
    }

    @Override
    public boolean isOwner(String developerToken, String applicationToken) {
        List<String> apps = getApplications(developerToken).stream().map(Application::getApplicationToken).collect(Collectors.toList());
        return apps.contains(applicationToken);
    }

    @Override
    public List<Application> getApplications(String developerToken) {
        return applicationsJDBCTemplate.getApplications(developerToken);
    }


    private String getUniqueTokenApp(){
        String tmp = UUID.randomUUID().toString();
        if (!applicationsJDBCTemplate.tokenExist(tmp))
            return tmp;
        else
            return getUniqueTokenApp();
    }
}
