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

package com.github.fedorchuck.developersecurity.service.validation;

import com.github.fedorchuck.developersecurity.dao.impl.Internal;
import com.github.fedorchuck.developersecurity.web.models.ReCaptcha;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author fedorchuck.
 */
@Service("validationInputService")
@Transactional
public class ValidationInputServiceImpl implements ValidationInputService{
    private final Logger log = Logger.getLogger(ValidationInputServiceImpl.class);
    private final Internal internal;

    @Autowired
    public ValidationInputServiceImpl(Internal internal) {
        this.internal = internal;
    }

    @Override
    public boolean validationPassword(String password, String confirmPassword) {
        return !password.trim().isEmpty()
                && !confirmPassword.trim().isEmpty()
                && password.equals(confirmPassword)
                && password.trim().length() > 6
                && password.trim().length() < 23;
    }

    @Override
    public boolean validationCaptcha(String captchaResponse) throws IOException {
        String SECRET = internal.getReCapture_secret();//secret that was generated in key value pair
        URL url  = new URL("https://www.google.com/recaptcha/api/siteverify?secret="+SECRET+"&response="+captchaResponse);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
        httpURLConnection.setRequestMethod("GET");
        InputStreamReader data = new InputStreamReader(httpURLConnection.getInputStream());
        BufferedReader br = new BufferedReader(data);
        Gson gson = new Gson();
        ReCaptcha reply = gson.fromJson(br, ReCaptcha.class);

        return reply.getSuccess();
    }

    @Override
    public boolean validationName(String name) {
        String tmp = name.trim();
        return !tmp.isEmpty()
                && tmp.length() > 2;
    }

    @Override
    public boolean validationEmail(String email) {
        String tmp = email.trim();
        return !tmp.isEmpty()
                && tmp.length() > 4
                && tmp.contains("@");
    }

    @Override
    public boolean validationSingUp(String name, String email, String password, String confirmPassword, String captchaResponse) throws IOException {
        return validationName(name)
                && validationEmail(email)
                && validationPassword(password,confirmPassword)
                && validationCaptcha(captchaResponse);
    }
}
