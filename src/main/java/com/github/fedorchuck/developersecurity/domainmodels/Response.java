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

@SuppressWarnings("ALL")
public class Response {
	private String response;
    private String message;
    private String documentationUrl;

    public Response(String response, String message, String documentationUrl) {
        this.response = response;
        this.message = message;
        this.documentationUrl = documentationUrl;
    }

    public Response() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response o1 = (Response) o;
        return Objects.equals(response, o1.response) &&
                Objects.equals(message, o1.message) &&
                Objects.equals(documentationUrl, o1.documentationUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, message, documentationUrl);
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", message='" + message + '\'' +
                ", documentationUrl='" + documentationUrl + '\'' +
                '}';
    }
}
