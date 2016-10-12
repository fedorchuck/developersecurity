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

<body>
    <jsp:include page="fragments/yandex_metrica.jsp"/>
<div id="content" class="content">
    <jsp:include page="fragments/fragment_header_full.jsp"/>
    <div class="container">
        <div class="row">
            <ul class="lists" style="list-style-type:none">
                <li><h3><strong><spring:message code="home.content.bold.1"/></strong></h3></li>
                <li><h5><spring:message code="home.content.text.1"/></h5></li>
                <li><h3><strong><spring:message code="home.content.bold.2"/></strong></h3></li>
                <li><h5><spring:message code="home.content.text.2"/></h5></li>
                <li><h3><strong><spring:message code="home.content.bold.3"/></strong></h3></li>
                <li><h5><spring:message code="home.content.text.3_1"/></h5></li>
                <li><h5><spring:message code="home.content.text.3_2"/></h5></li>
                <li><h3><strong><spring:message code="home.content.bold.4"/></strong></h3></li>
                <li><h5><spring:message code="home.content.text.4"/></h5></li>
                <li><h3><strong><spring:message code="home.content.bold.5"/></strong></h3></li>
                <li><h5><a href="<c:url value="/documentation#createKey"/>">
                        <spring:message code="home.content.text.5_1"/></a></h5></li>
                <li><h5><a href="<c:url value="/documentation#embedding"/>">
                        <spring:message code="home.content.text.5_2"/></a></h5></li>
                <li><h5><a href="<c:url value="/documentation#changingAccess"/>">
                        <spring:message code="home.content.text.5_3"/></a></h5></li>
            </ul>
        </div>
        <div class="row text-center">
            <% if (request.isUserInRole("ROLE_USER") | request.isUserInRole("ROLE_ADMIN")) { %>
                <a href="<c:url value="/dashboard"/>"
                   class="btn btn-primary btn-lg" role="button">
                    <spring:message code="home.button.your_account"/>
                </a>
            <% } else { %>
                <a href="<c:url value="/signup"/>"
                   class="btn btn-primary btn-lg" role="button">
                    <spring:message code="home.button.try"/>
                </a>
            <% } %>
        </div>
    </div>

</div>
<jsp:include page="fragments/fragment_footer.jsp"/>
</body>
</html>
