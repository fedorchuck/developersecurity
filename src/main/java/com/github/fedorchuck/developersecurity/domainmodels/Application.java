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
}
