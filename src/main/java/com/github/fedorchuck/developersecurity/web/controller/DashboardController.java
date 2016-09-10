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

package com.github.fedorchuck.developersecurity.web.controller;

import com.github.fedorchuck.developersecurity.domainmodels.Application;
import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.service.account.AccountService;
import com.github.fedorchuck.developersecurity.service.application.ApplicationService;
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author fedorchuck.
 */
@Controller
@Scope("session")
@RequestMapping("/dashboard")
public class DashboardController extends ControllerUtil {

    private final ApplicationService applicationService;
    private final AccountService accountService;
    private final Session session;

    @Autowired
    public DashboardController(ApplicationService applicationService,
                               AccountService accountService,
                               Session session) {
        super(accountService, session);
        this.applicationService = applicationService;
        this.accountService = accountService;
        this.session = session;
    }

    @RequestMapping(method = GET)
    public ModelAndView showDashboard(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (session.getUser()==null)
            updateSession(request,auth);

        List<Application> applications = getApplications(auth);
        model.addObject("applications", applications);
        return model;
    }

    @RequestMapping(value = "/createApi", method = POST)
    public ModelAndView create(HttpServletRequest request){

        String shortName = request.getParameter("shortName");
        String response = request.getParameter("response");
        String message = request.getParameter("message");

        String developerToken = session.getUser().getDeveloperToken();

        applicationService.createApi(shortName,response,message,developerToken);

        return getModelAndView(request, "redirect:/dashboard", "Record created.", null);
    }

    @RequestMapping(value = "/updateApi", method = POST)
    public ModelAndView update(HttpServletRequest request){

        String shortName = request.getParameter("shortName");
        String response = request.getParameter("response");
        String message = request.getParameter("message");
        String applicationToken = request.getParameter("applicationToken");

        if (!applicationService.isOwner(session.getUser().getDeveloperToken(), applicationToken))
            return getModelAndView(request,"redirect:/dashboard","You tried :)", null);

        applicationService.updateApi(shortName,response,message,applicationToken);

        return getModelAndView(request,"redirect:/dashboard","Record updated.", null);
    }

    @RequestMapping(value = "/removeApi", method = POST)
    public ModelAndView remove(HttpServletRequest request){
        String applicationToken = request.getParameter("applicationToken");

        if (!applicationService.isOwner(session.getUser().getDeveloperToken(), applicationToken))
            return getModelAndView(request,"redirect:/dashboard","You tried :)", null);

        applicationService.removeApi(applicationToken);

        return getModelAndView(request,"redirect:/dashboard","Record removed.",null);
    }

    private List<Application> getApplications(Authentication auth) {
        String email = auth.getName(); //get logged in username //String user_role = (String) auth.getAuthorities().toArray()[0];
        Developer developer = accountService.getDeveloper(email);
        String developerToken = developer.getDeveloperToken();
        return applicationService.getApplications(developerToken);
    }
}
