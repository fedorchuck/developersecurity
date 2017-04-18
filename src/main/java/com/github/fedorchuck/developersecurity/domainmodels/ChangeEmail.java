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

package com.github.fedorchuck.developersecurity.domainmodels;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fedorchuck.
 */
@Getter
@Setter
@EqualsAndHashCode @ToString
public class ChangeEmail {
    private Long id;
    private String developerToken;
    private String old_email;
    private Boolean old_email_confirm;
    private String new_email;
    private Boolean new_email_confirm;
    private Long dateOfRecordCreated;

    public ChangeEmail(String developerToken,
                       String old_email, Boolean old_email_confirm,
                       String new_email, Boolean new_email_confirm,
                       Long dateOfRecordCreated) {
        this.developerToken = developerToken;
        this.old_email = old_email;
        this.old_email_confirm = old_email_confirm;
        this.new_email = new_email;
        this.new_email_confirm = new_email_confirm;
        this.dateOfRecordCreated = dateOfRecordCreated;
    }

}
