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
<head><jsp:include page="fragment_head.jsp"/></head>
<head>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body>
<jsp:include page="yandex_metrica.jsp"/>

<div id="content" class="content">
    <jsp:include page="fragment_header_light.jsp"/>

    <div class="container">
        <div class="row" style="margin-top: 25px;">
            <div class="col-md-6">
                <ul class="lists" style="list-style-type: none; padding: 0;">
                    <li><h4 style="color: #2e6da4;"><spring:message code="signup.content.h1"/></h4></li>
                    <li><h4><spring:message code="signup.content.h1_1"/></h4></li>
                    <li><h4 style="color: #2e6da4;"><spring:message code="signup.content.h2"/></h4></li>
                    <li><h4><spring:message code="signup.content.h2_2"/></h4></li>
                    <li><h4 style="color: #2e6da4;"><spring:message code="signup.content.h3"/></h4></li>
                    <li><h4><spring:message code="signup.content.h3_3"/></h4></li>
                </ul>
            </div>
            <div class="col-md-6 <c:if test="${errors == 1}"> alert alert-danger</c:if>" style="padding: 0;">
                <c:url var="saveUrl" value="/signup" />
                <form:form name="signupForm" method="POST" action="${saveUrl}">
                    <div class="form-group">
                        <input type="text" name="name" class="form-control"
                               placeholder='<spring:message code="signup.placeholder.name"/>' />
                        <span class="help-block" style="display: none;"></span>
                    </div>
                    <div class="form-group" >
                        <input type="email" name="email" class="form-control"
                               placeholder='<spring:message code="signup.placeholder.email"/>' />
                        <span class="help-block" style="display: none;"></span>
                    </div>
                    <div class="form-group">
                        <input type="password" name="password" class="form-control"
                               placeholder='<spring:message code="signup.placeholder.password"/>' />
                        <span class="help-block" style="display: none;"></span>
                    </div>
                    <div class="form-group">
                        <input type="password" name="confirm_password" class="form-control"
                               placeholder='<spring:message code="signup.placeholder.confirm_password" />' />
                        <span class="help-block" style="display: none;"></span>
                    </div>
                    <div class="col-md-12" style="padding: 0;">
                        <div class="col-md-7 text-left" style="padding: 0;">
                            <div class="g-recaptcha" data-sitekey="6LfW-icTAAAAAPJD9R_j6OgU8kX8-Lfvd17DmrjO"></div>
                        </div>
                        <div class="col-md-5 text-right" style="padding: 0;">
                            <button value="add" type="submit" id="submit" class="btn btn-lg btn-block"
                            style="margin-top: 13px; background-color: #f2f2f2; color: #2e6da4;">
                                <spring:message code="signup.button" />
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <c:if test="${errors == 1}">
                        <strong><spring:message code="signup.error.strong"/> </strong>
                        <spring:message code="signup.error.message"/>
                    </c:if>
                </form:form>
            </div>
        </div>
    </div>

</div>
<jsp:include page="fragment_footer.jsp"/>
</body>
</html>
