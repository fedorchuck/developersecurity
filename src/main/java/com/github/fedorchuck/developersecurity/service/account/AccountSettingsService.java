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

package com.github.fedorchuck.developersecurity.service.account;

import com.github.fedorchuck.developersecurity.throwable.ValidationException;

import javax.mail.internet.AddressException;

/**
 * @author fedorchuck.
 */
public interface AccountSettingsService {
    void updateEnabled(String confirm_string);
    boolean confirmEmail(String confirm_email, String confirm_string);
    void changeEmail(String oldEmail, String newEmail) throws ValidationException;
    String setEmail(String confirm_email, String confirm_string, String emailStatus);
    boolean updateEmail(String developerToken);
    void setPassword(String developerToken, String new_password);
    void resetPassword(String email) throws AddressException;
    void resetPassword(String tmpToken, String newPassword);
    boolean isResetPassword(String token);
}
