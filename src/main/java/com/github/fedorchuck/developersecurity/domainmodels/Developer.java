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
public class Developer {
    private Long developerId;
    private String developerToken;
    private String email;
    private String password;
    private String name;
    private Boolean enabled;
    private String role;
    private Long dateOfRecordCreated;
    private Long dateOfLastUsed;

    public Developer(String developerToken, String email, String password, String name, Boolean enabled, String role, Long dateOfRecordCreated, Long dateOfLastUsed) {
        this.developerToken = developerToken;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.role = role;
        this.dateOfRecordCreated = dateOfRecordCreated;
        this.dateOfLastUsed = dateOfLastUsed;
    }

    public Developer(Long developerId, String developerToken, String email, String password, String name, Boolean enabled, String role, Long dateOfRecordCreated, Long dateOfLastUsed) {
        this.developerId = developerId;
        this.developerToken = developerToken;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.role = role;
        this.dateOfRecordCreated = dateOfRecordCreated;
        this.dateOfLastUsed = dateOfLastUsed;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }

    public String getDeveloperToken() {
        return developerToken;
    }

    public void setDeveloperToken(String developerToken) {
        this.developerToken = developerToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getDateOfRecordCreated() {
        return dateOfRecordCreated;
    }

    public void setDateOfRecordCreated(Long dateOfRecordCreated) {
        this.dateOfRecordCreated = dateOfRecordCreated;
    }

    public Long getDateOfLastUsed() {
        return dateOfLastUsed;
    }

    public void setDateOfLastUsed(Long dateOfLastUsed) {
        this.dateOfLastUsed = dateOfLastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return
                Objects.equals(developerToken, developer.developerToken) &&
                Objects.equals(email, developer.email) &&
                Objects.equals(password, developer.password) &&
                Objects.equals(name, developer.name) &&
                Objects.equals(enabled, developer.enabled) &&
                Objects.equals(role, developer.role) &&
                Objects.equals(dateOfRecordCreated, developer.dateOfRecordCreated) &&
                Objects.equals(dateOfLastUsed, developer.dateOfLastUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerToken, email, password, name, enabled, role, dateOfRecordCreated, dateOfLastUsed);
    }

    @Override
    public String toString() {
        return  " email='" + email + "\'\n" +
                " name='" + name + "\'\n" +
                " enabled=" + enabled +
                " role='" + role + "\'\n" +
                " dateOfRecordCreated=" + dateOfRecordCreated + "\'\n" +
                " dateOfLastUsed=" + dateOfLastUsed + "\'\n";
    }
}
