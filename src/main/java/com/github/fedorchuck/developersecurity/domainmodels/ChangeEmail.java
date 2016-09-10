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

import java.util.Objects;

/**
 * @author fedorchuck.
 */
@SuppressWarnings("unused")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeveloperToken() {
        return developerToken;
    }

    public void setDeveloperToken(String developerToken) {
        this.developerToken = developerToken;
    }

    public String getOld_email() {
        return old_email;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
    }

    public Boolean getOld_email_confirm() {
        return old_email_confirm;
    }

    public void setOld_email_confirm(Boolean old_email_confirm) {
        this.old_email_confirm = old_email_confirm;
    }

    public String getNew_email() {
        return new_email;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
    }

    public Boolean getNew_email_confirm() {
        return new_email_confirm;
    }

    public void setNew_email_confirm(Boolean new_email_confirm) {
        this.new_email_confirm = new_email_confirm;
    }

    public Long getDateOfRecordCreated() {
        return dateOfRecordCreated;
    }

    public void setDateOfRecordCreated(Long dateOfRecordCreated) {
        this.dateOfRecordCreated = dateOfRecordCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeEmail that = (ChangeEmail) o;
        return Objects.equals(developerToken, that.developerToken) &&
                Objects.equals(old_email, that.old_email) &&
                Objects.equals(old_email_confirm, that.old_email_confirm) &&
                Objects.equals(new_email, that.new_email) &&
                Objects.equals(new_email_confirm, that.new_email_confirm) &&
                Objects.equals(dateOfRecordCreated, that.dateOfRecordCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerToken, old_email, old_email_confirm, new_email, new_email_confirm, dateOfRecordCreated);
    }

    @Override
    public String toString() {
        return "ChangeEmail{" +
                "id=" + id +
                ", developerToken='" + developerToken + '\'' +
                ", old_email='" + old_email + '\'' +
                ", old_email_confirm=" + old_email_confirm +
                ", new_email='" + new_email + '\'' +
                ", new_email_confirm=" + new_email_confirm +
                ", dateOfRecordCreated=" + dateOfRecordCreated +
                '}';
    }
}
