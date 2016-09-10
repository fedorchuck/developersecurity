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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.File;
import java.util.List;

/**
 * @author fedorchuck.
 */
public interface MailerService {
    void sendMail(String to, String subject, String body) throws AddressException;
    void sendMailWithAttachments(String to, String subject, String body, List<File> attachments) throws MessagingException;
}
