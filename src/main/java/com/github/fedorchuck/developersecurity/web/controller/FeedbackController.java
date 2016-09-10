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
import com.github.fedorchuck.developersecurity.service.mail.AccountEmailService;
import com.github.fedorchuck.developersecurity.web.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/feedback")
public class FeedbackController {

    private final Session session;
    private final AccountEmailService accountEmailService;

    @Autowired
    public FeedbackController(Session session, AccountEmailService accountEmailService) {
        this.session = session;
        this.accountEmailService = accountEmailService;
    }

    @RequestMapping(method = GET)
    public ModelAndView showDashboard() {
        return new ModelAndView("feedback");
    }

    @RequestMapping(value = "/send", method = POST)
    public ModelAndView create(HttpServletRequest request){
        String feedback_message = request.getParameter("feedback_message");
        Developer developer = session.getUser();

        try {
            accountEmailService.sendFeedback(developer, feedback_message);
        } catch (AddressException ignored) {
        }

        ModelAndView model = new ModelAndView("feedback");
        model.addObject("send", 1);
        return model;
    }
}
