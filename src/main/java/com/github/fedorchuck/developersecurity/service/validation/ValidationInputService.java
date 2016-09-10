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

import java.io.IOException;

/**
 * @author fedorchuck.
 */
public interface ValidationInputService {
    boolean validationName(String name);
    boolean validationEmail(String email);
    boolean validationPassword(String password, String confirmPassword);
    boolean validationCaptcha(String captchaResponse) throws IOException;
    boolean validationSingUp(String name, String email, String password, String confirmPassword, String captchaResponse) throws IOException;
}
