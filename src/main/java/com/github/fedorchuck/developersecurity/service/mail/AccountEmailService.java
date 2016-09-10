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

package com.github.fedorchuck.developersecurity.service.mail;

import com.github.fedorchuck.developersecurity.domainmodels.Developer;

import javax.mail.internet.AddressException;

/**
 * @author fedorchuck.
 */
public interface AccountEmailService {
    void sendFeedback(Developer developer, String feedbackMessage) throws AddressException;
    void confirmEmail(String email, String token) throws AddressException;
    void confirmChangeEmail(String email, String token, String emailStatus) throws AddressException;
    void resetPassword(String email, String token) throws AddressException;
}
