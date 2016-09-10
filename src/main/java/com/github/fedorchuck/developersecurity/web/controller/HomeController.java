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
import com.github.fedorchuck.developersecurity.utils.ControllerUtil;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author fedorchuck.
 */
@Controller
@Scope("session")
@RequestMapping("/")
public class HomeController extends ControllerUtil {

    @Autowired
    public HomeController(AccountService accountService, Session session) {
        super(accountService,session);
    }

    @RequestMapping(method = GET)
    public ModelAndView showWelcomePage(HttpServletRequest request) {

        return getModelAndView(request, "welcomePage", null, null);
    }

    @RequestMapping(value = "index", method = GET)
    public ModelAndView showIndex(HttpServletRequest request) {

        return getModelAndView(request, "index", null, null);
    }

    @RequestMapping(value="login", method = GET)
    public ModelAndView showLogIN(HttpServletRequest request){

        return getModelAndView(request, "login", null, null);
    }

    @RequestMapping(value = "denied", method = GET)
    public ModelAndView showDeniedAccess(HttpServletRequest request){

        return getModelAndView(request, "denied", null, null);
    }

    @RequestMapping(value="documentation", method = GET)
    public ModelAndView showDocumentation(HttpServletRequest request){

        return getModelAndView(request, "documentation", null, null);
    }
}
