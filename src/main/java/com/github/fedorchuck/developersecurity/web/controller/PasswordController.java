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

import com.github.fedorchuck.developersecurity.service.account.AccountService;
import com.github.fedorchuck.developersecurity.service.account.AccountSettingsService;
import com.github.fedorchuck.developersecurity.service.validation.ValidationInputService;
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author fedorchuck.
 */
@Controller
@Scope("session")
@RequestMapping("/password")
public class PasswordController extends ControllerUtil {

    private final ValidationInputService validationInputService;
    private final AccountSettingsService accountSettingsService;
    private final Session session;

    public PasswordController(ValidationInputService validationInputService,
                              AccountSettingsService accountSettingsService,
                              AccountService accountService,
                              Session session) {
        super(accountService,session);
        this.validationInputService = validationInputService;
        this.accountSettingsService = accountSettingsService;
        this.session = session;
    }

    @RequestMapping(method = GET)
    public ModelAndView showPasswordResetForm(HttpServletRequest request){
        return getModelAndView(request, "password_reset", null,null);
    }

    @RequestMapping(value = "/reset", method = POST)
    public ModelAndView passwordReset(HttpServletRequest request) {
        String email = request.getParameter("email");

        try {
            accountSettingsService.resetPassword(email);
        } catch (AddressException e) {
            return getModelAndView(request, "password_reset","Error. ", "Email is invalid.");
        }

        return getModelAndView(request, "password_reset",null, "We send you a link to reset your password.");
    }

    @RequestMapping(value = "/set", method = POST)
    public ModelAndView passwordSet(HttpServletRequest request) {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (!validationInputService.validationPassword(password,confirmPassword))
            return getModelAndView(request,"password_new","Invalid new password.", null);

        accountSettingsService.resetPassword(session.getTmp(),password);

        return getModelAndView(request, "redirect:/login", "Password was reset. New password was applied.",null);
    }
}
