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
import com.github.fedorchuck.developersecurity.service.validation.ValidationInputService;
import com.github.fedorchuck.developersecurity.throwable.ValidationException;
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author fedorchuck.
 */
@Controller
@Scope("session")
@RequestMapping("/signup")
public class SingUpController extends ControllerUtil {

    private final ValidationInputService validationInputService;
    private final AccountService accountService;

    @Autowired
    public SingUpController(ValidationInputService validationInputService,
                            AccountService accountService,
                            Session session) {
        super(accountService,session);
        this.validationInputService = validationInputService;
        this.accountService = accountService;
    }

    @RequestMapping(method = GET)
    public ModelAndView showSingUp(HttpServletRequest request) {
        updateFooter(request);
        return new ModelAndView("signup").addObject("errors",0);
    }

    @RequestMapping(method = POST)
    public ModelAndView processSingUp(HttpServletRequest request) throws IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String captchaResponse = request.getParameter("g-recaptcha-response");

        if (!validationInputService.validationSingUp(name,email,password,confirmPassword,captchaResponse))
            return getModelAndView(request, new ModelAndView("signup").addObject("errors", 1), null,null);

        try {
            accountService.createAccount(name, email, password);
        } catch (ValidationException e) {
            return getModelAndView(request, new ModelAndView("signup").addObject("errors", 1), "Error.", e.getMessage());
        }

        return getModelAndView(request,"redirect:/index",null,"We'll send you a link to create your account. You can't use this service, until confirm email.");
    }
}
