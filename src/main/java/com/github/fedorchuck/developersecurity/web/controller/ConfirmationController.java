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
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author fedorchuck.
 */
@Scope("session")
@Controller
public class ConfirmationController extends ControllerUtil {

    private final AccountSettingsService accountSettingsService;
    private final Session session;

    @Autowired
    public ConfirmationController(AccountSettingsService accountSettingsService,
                                  AccountService accountService,
                                  Session session) {
        super(accountService,session);
        this.accountSettingsService = accountSettingsService;
        this.session = session;
    }

    //    https://developersecurity.herokuapp.com/confirm?confirm_email="+email+"&confirm_string="+token+"&email="old/new
    @RequestMapping(value = "/confirm", method = GET)
    public ModelAndView confirmEmail(@RequestParam("confirm_email") String confirm_email,
                                     @RequestParam(value = "confirm_string") String confirm_string,
                                     @RequestParam(value = "email", required = false) String email,
                                     HttpServletRequest request){
        ModelAndView model;
        if (email==null) {
            if (accountSettingsService.confirmEmail(confirm_email, confirm_string)) {
                accountSettingsService.updateEnabled(confirm_string);

                model = getModelAndView(request,"dashboard","Your email address confirmed.",null);
                return model;
            } else {
                model = getModelAndView(request,"index","You tried.","Bad url parameters.");
                return model;
            }
        } else {
            String response = accountSettingsService.setEmail(confirm_email,confirm_string,email);

            accountSettingsService.updateEmail(confirm_string);

            model = getModelAndView(request, "index", response, null);
            return model;
        }
    }

//    "https://developersecurity.herokuapp.com/reset?token="+token+"
    @RequestMapping(value = "/reset", method = GET)
    public ModelAndView resetPassword(@RequestParam("token") String token,
                                      HttpServletRequest request){

        if (accountSettingsService.isResetPassword(token)) {
            session.setTmp(token);
            return getModelAndView(request, "password_new", null, null);
        } else
            return getModelAndView(request,"index","You tried.","Bad url parameters.");
    }
}
