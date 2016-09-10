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
public class Application {
    private Long developerId;
    private String developerToken;
    private Long applicationId;
    private String applicationToken;
    private String shortName;
    private String response;
    private String message;
    private String documentationUrl;
    private Long dateOfRecordCreated;
    private Long dateOfLastUsedByApi;

    public Application(Long developerId, String developerToken,
                       String applicationToken, String shortName,
                       String response, String message, String documentationUrl,
                       Long dateOfRecordCreated, Long dateOfLastUsedByApi) {
        this.developerId = developerId;
        this.developerToken = developerToken;
        this.applicationToken = applicationToken;
        this.shortName = shortName;
        this.response = response;
        this.message = message;
        this.documentationUrl = documentationUrl;
        this.dateOfRecordCreated = dateOfRecordCreated;
        this.dateOfLastUsedByApi = dateOfLastUsedByApi;
    }

    public Application(Long developerId, String developerToken,
                       Long applicationId, String applicationToken, String shortName,
                       String response, String message, String documentationUrl,
                       Long dateOfRecordCreated, Long dateOfLastUsedByApi) {
        this.developerId = developerId;
        this.developerToken = developerToken;
        this.applicationId = applicationId;
        this.applicationToken = applicationToken;
        this.shortName = shortName;
        this.response = response;
        this.message = message;
        this.documentationUrl = documentationUrl;
        this.dateOfRecordCreated = dateOfRecordCreated;
        this.dateOfLastUsedByApi = dateOfLastUsedByApi;
    }

    public Long getDeveloperId() {
        return developerId;
    }

    public String getDeveloperToken() {
        return developerToken;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public String getApplicationToken() {
        return applicationToken;
    }

    public void setApplicationToken(String applicationToken) {
        this.applicationToken = applicationToken;
    }

    public void setDeveloperToken(String developerToken) {
        this.developerToken = developerToken;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public Long getDateOfRecordCreated() {
        return dateOfRecordCreated;
    }

    public void setDateOfRecordCreated(Long dateOfRecordCreated) {
        this.dateOfRecordCreated = dateOfRecordCreated;
    }

    public Long getDateOfLastUsedByApi() {
        return dateOfLastUsedByApi;
    }

    public void setDateOfLastUsedByApi(Long dateOfLastUsedByApi) {
        this.dateOfLastUsedByApi = dateOfLastUsedByApi;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(developerId, that.developerId) &&
                Objects.equals(developerToken, that.developerToken) &&
                Objects.equals(applicationToken, that.applicationToken) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(response, that.response) &&
                Objects.equals(message, that.message) &&
                Objects.equals(documentationUrl, that.documentationUrl) &&
                Objects.equals(dateOfRecordCreated, that.dateOfRecordCreated) &&
                Objects.equals(dateOfLastUsedByApi, that.dateOfLastUsedByApi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, developerToken, applicationToken, shortName, response, message, documentationUrl, dateOfRecordCreated, dateOfLastUsedByApi);
    }

    @Override
    public String toString() {
        return "Application{" +
                "developerId=" + developerId +
                ", developerToken='" + developerToken + '\'' +
                ", applicationId=" + applicationId +
                ", applicationToken='" + applicationToken + '\'' +
                ", shortName='" + shortName + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                ", documentationUrl='" + documentationUrl + '\'' +
                ", dateOfRecordCreated=" + dateOfRecordCreated +
                ", dateOfLastUsedByApi=" + dateOfLastUsedByApi +
                '}';
    }
}
