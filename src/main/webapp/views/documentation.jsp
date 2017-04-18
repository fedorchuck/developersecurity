<%--
  ~ Copyright 2016 Volodymyr Fedorchuk <vl.fedorchuck@gmail.com>
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>
<html>
<head><jsp:include page="fragments/fragment_head.jsp"/></head>
<style>
    .affix {
        top: 1%;
    }
</style>
<body>
<jsp:include page="fragments/yandex_metrica.jsp"/>
<div id="content" class="content">
    <%--<div class="container-fluid">--%>
        <jsp:include page="fragments/fragment_header_full.jsp"/>
    <%--</div>--%>
    <div class="container">
        <div class="row">
            <nav class="col-md-2" style="padding-right: 0;">
                <div class="btn-group-vertical nav nav-pills nav-stacked" data-spy="affix" data-offset-top="205">
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.create"/>'
                           onclick="window.location='#createKey';"/>
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.schema"/>'
                           onclick="window.location='#schema';"/>
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.embedding"/>'
                           onclick="window.location='#embedding';"/>
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.changing_access"/>'
                           onclick="window.location='#changingAccess';"/>
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.remove"/>'
                           onclick="window.location='#removeKey';"/>
                    <input type="submit" class="btn btn-block"
                           value='<spring:message code="documentation.button.profile_settings"/>'
                           onclick="window.location='#profileSettings';"/>
                </div>
            </nav>
            <div class="col-md-10" style="margin-top: -2%;">
                <div id="createKey">
                    <a href="<c:url value="#createKey"/> "><h3><spring:message code="documentation.button.create"/></h3></a>
                    <img src="../resources/images/record_create_0.png" class="screenshot"/>
                    <img src="../resources/images/record_create_1.png" class="screenshot"/>
                </div>
                <div id="schema">
                    <a href="<c:url value="#schema"/> "><h3><spring:message code="documentation.button.schema"/></h3></a>
                    <spring:message code="documentation.content.schema"/>
                    <pre>
                        <code>
{
  "response": "DENIED",
  "message": "",
  "documentationUrl": "https://developersecurity.herokuapp.com/documentation"
}
                            or
{
  "response": "PERMITTED",
  "message": "",
  "documentationUrl": "https://developersecurity.herokuapp.com/documentation"
}
                        </code>
                    </pre>
                </div>
                <div id="embedding">
                    <a href="<c:url value="#embedding"/> "><h3><spring:message code="documentation.button.embedding"/></h3></a>
                    <img src="../resources/images/record_view.png" class="screenshot"/>

                    <pre>
                        <code>
import java.util.Objects;

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
                        </code>
                    </pre>

                    <pre>
                        <code>
import Response;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

.......................................................................................................................

URL url = new URL("https://developersecurity.herokuapp.com/check?developer=DEVELOPER_KEY&application=APPLICATION_KEY");

HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
httpURLConnection.setRequestMethod("GET");

InputStreamReader data = new InputStreamReader(httpURLConnection.getInputStream());
BufferedReader br = new BufferedReader(data);
Gson gson = new Gson();
Response reply = gson.fromJson(br, Response.class);
                        </code>
                    </pre>

                    <pre>
                        <code>
if (reply.getResponse().equals("DENIED"))
    throw new IllegalAccessError();
                        </code>
                    </pre>

                </div>
                <div id="changingAccess">
                    <a href="<c:url value="#changingAccess"/> "><h3><spring:message code="documentation.button.changing_access"/></h3></a>
                    <img src="../resources/images/record_update_0.png" class="screenshot"/>
                    <img src="../resources/images/record_update_1.png" class="screenshot"/>
                </div>
                <div id="removeKey">
                    <a href="<c:url value="#removeKey"/> "><h3><spring:message code="documentation.button.remove"/></h3></a>
                    <spring:message code="documentation.content.remove.1"/>
                    <img src="../resources/images/record_remove.png" class="screenshot"/>
                    <spring:message code="documentation.content.remove.2"/>
                </div>
                <div id="profileSettings">
                    <a href="<c:url value="#profileSettings"/> "><h3><spring:message code="documentation.button.profile_settings"/></h3></a>
                    <img src="../resources/images/profile_settings.png" class="screenshot"/>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="fragments/fragment_footer.jsp"/>
</body>
</html>
