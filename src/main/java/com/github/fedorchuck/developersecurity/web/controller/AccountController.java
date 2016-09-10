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

import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.service.account.AccountService;
import com.github.fedorchuck.developersecurity.service.account.AccountSettingsService;
import com.github.fedorchuck.developersecurity.service.validation.ValidationInputService;
import com.github.fedorchuck.developersecurity.throwable.ValidationException;
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author fedorchuck.
 */
@Controller
@Scope("session")
@RequestMapping("/account")
public class AccountController extends ControllerUtil {

    private final ValidationInputService validationInputService;
    private final AccountSettingsService accountSettingsService;
    private final AccountService accountService;
    private final Session session;

    @Autowired
    public AccountController(ValidationInputService validationInputService,
                             AccountSettingsService accountSettingsService,
                             AccountService accountService,
                             Session session) {
        super(accountService, session);
        this.validationInputService = validationInputService;
        this.accountSettingsService = accountSettingsService;
        this.accountService = accountService;
        this.session = session;
    }

    @RequestMapping(method = GET)
    public ModelAndView showProfile(HttpServletRequest request){
        ModelAndView model = new ModelAndView("account");

        if (session.getUser()==null)
            updateSession(request,SecurityContextHolder.getContext().getAuthentication());

        return model;
    }

    @RequestMapping(value = "/changeEmail", method = POST)
    public ModelAndView changeEmail(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model;

        String change_email_old = request.getParameter("change_email_old");
        String change_email_new = request.getParameter("change_email_new");

        try {
            accountSettingsService.changeEmail(change_email_old,change_email_new);
        } catch (ValidationException e) {
            return getModelAndView(request,"redirect:/account","Exception.",e.getMessage());
        }

        model = getModelAndView(request,"redirect:/account","For this two email was sanded letters. Please confirm your suggestions.",null);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);

        return model;
    }

    @RequestMapping(value = "/changePass", method = POST)
    public ModelAndView changePass(HttpServletRequest request){

        String current_password = request.getParameter("current_password");
        String new_password = request.getParameter("new_password");
        String confirm_password = request.getParameter("confirm_password");

        Developer developer = session.getUser();
        String developerToken = developer.getDeveloperToken();

        if (!developer.getPassword().equals(current_password))
            return getModelAndView(request, "redirect:/account", "Wrong current password.","Is it really You?");

        if (!validationInputService.validationPassword(new_password,confirm_password))
            return getModelAndView(request, "redirect:/account", "Bad new password.",null);

        accountSettingsService.setPassword(developerToken,new_password);

        return getModelAndView(request, "redirect:/account", "Password changed.", null);
    }

    @RequestMapping(value = "/deleteAccount", method = POST)
    public ModelAndView deleteAccount(HttpServletRequest request, HttpServletResponse response) {

        String delete_account_email = request.getParameter("delete_account_email");
        String delete_account_password = request.getParameter("delete_account_password");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Developer developer = session.getUser();

        if (!email.equals(delete_account_email))
            new SecurityContextLogoutHandler().logout(request, response, auth); //log out

        if (!developer.getPassword().equals(delete_account_password))
            return getModelAndView(request,"redirect:/","Wrong password.","Is it really owner of"+email+"?");

        new SecurityContextLogoutHandler().logout(request, response, auth);//log out

        accountService.deleteAccount(developer.getDeveloperToken(), email);

        return getModelAndView(request, "redirect:/", "Your account was removed.",null);
    }
}
