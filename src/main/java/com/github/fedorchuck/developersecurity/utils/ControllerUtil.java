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

package com.github.fedorchuck.developersecurity.utils;

import com.github.fedorchuck.developersecurity.dao.impl.Internal;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.service.account.AccountService;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fedorchuck.
 */
@Scope("session")
public abstract class ControllerUtil {
    private final Logger log = Logger.getLogger(ControllerUtil.class);
    private final AccountService accountService;
    private final Session session;

    @Autowired
    private Internal internal;

    protected ControllerUtil(AccountService accountService, Session session) {
        this.accountService = accountService;
        this.session = session;
    }

    protected void updateSession(HttpServletRequest request, Authentication authentication){
        String email = authentication.getName();
        Developer developer = accountService.getDeveloper(email);
        String developerToken = developer.getDeveloperToken();
        accountService.updateDateOfLastUsed(developerToken);
        session.setUser(developer);
        request.getSession().setAttribute("session", session);
        log.log(Level.DEBUG, "update session. request "+request.toString());
    }

    protected void updateFooter(HttpServletRequest request){
        if (session.getDataOfSiteUpdate()==null) {
            session.setDataOfSiteUpdate(internal.getDataOfSiteUpdate());
            request.getSession().setAttribute("session", session);
            log.log(Level.DEBUG, "update footer. request "+request.toString());
        }
    }

    protected ModelAndView getModelAndView(HttpServletRequest request, String view, String strong_message, String message){
        ModelAndView model = new ModelAndView(view);

        if (session.getDataOfSiteUpdate()==null)
            session.setDataOfSiteUpdate(internal.getDataOfSiteUpdate());

        if (strong_message!=null || message!=null)
            session.setInfo(true);

        if (strong_message!=null)
            session.setStrong(strong_message);
        if (message!=null)
            session.setMessage(message);

        request.getSession().setAttribute("session", session);
        log.log(Level.DEBUG, "update session. request "+request.toString());
        return model;
    }

    protected ModelAndView getModelAndView(HttpServletRequest request, ModelAndView model, String strong_message, String message){

        if (session.getDataOfSiteUpdate()==null)
            session.setDataOfSiteUpdate(internal.getDataOfSiteUpdate());

        if (strong_message!=null || message!=null)
            session.setInfo(true);

        if (strong_message!=null)
            session.setStrong(strong_message);
        if (message!=null)
            session.setMessage(message);

        request.getSession().setAttribute("session", session);
        log.log(Level.DEBUG, "update session. request "+request.toString());
        return model;
    }
}
