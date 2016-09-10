<%@ page import="com.github.fedorchuck.developersecurity.web.models.Session" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>
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

<div class="container" >
    <div class="row">
        <div class="well well-sm" style="margin-bottom: 0;">
            <h6>
                <% String locale = pageContext.getResponse().getLocale().toString();
                    switch (locale){
                        case "en": %>
                            <strong><a href="?locale=en">English[En]</a></strong>
                            <a href="?locale=ua">Українська[Uk]</a>
                            <a href="?locale=ru">Русский[Ru]</a>
                            <% break;
                        case "ua": %>
                            <a href="?locale=en">English[En]</a>
                            <strong><a href="?locale=ua">Українська[Uk]</a></strong>
                            <a href="?locale=ru">Русский[Ru]</a>
                            <% break;
                        case "ru": %>
                            <a href="?locale=en">English[En]</a>
                            <a href="?locale=ua">Українська[Uk]</a>
                            <strong><a href="?locale=ru">Русский[Ru]</a></strong>
                            <% break;
                    }
                %>
            </h6>
        </div>
    </div>
    <%
        Session z = (Session) request.getSession().getAttribute("session");
        if (z!=null&&z.isInfo()) {
    %>
    <div class="row">
        <div class="alert alert-warning" style="margin-bottom: 0;">
            <strong><c:out value="<%= z.getStrong() %>"/> </strong> <c:out value="<%= z.getMessage() %>"/>
        </div>
    </div>
    <%
            z.setInfo(false);
            z.setStrong("");
            z.setMessage("");
        } else {
    %>

    <% } %>
    <div class="row">
        <div style="display:flex;justify-content:flex-end;align-items:center;">
            <div class="col-md-3 text-left" >
                <h3 style="margin-top: 10px;">
                    <strong>
                        <a href="<c:url value='/' />" title="home" style="display:flex;justify-content:flex-end;align-items:center;">
                            <img src="../resources/image/icon.png" />
                            Developer Security
                        </a>
                    </strong>
                </h3>
            </div>
            <div class="col-md-4 text-left">
                <h4>
                    <a href="<c:url value="/documentation"/>" ><spring:message code="head.link.documentation"/></a>
                </h4>
            </div>
            <div class="col-md-5 text-right" style="display:flex;justify-content:flex-end;align-items:center;">
                <% if (request.isUserInRole("ROLE_USER") | request.isUserInRole("ROLE_ADMIN")) { %>
                    <h4><a href="<c:url value="/dashboard"/>">${pageContext.request.userPrincipal.name}</a></h4>
                    <h4 style="margin-right: 2%;margin-left: 2%">|</h4>
                    <c:url value="/j_spring_security_logout" var="logoutUrl" />
                    <form:form action="${logoutUrl}" method="POST" id="logoutForm" style="margin-bottom: 0;" >
                        <h4><button class="btn-link" type="submit" style="padding-left: 0;">
                            <spring:message code="head.link.logout"/>
                        </button></h4>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form:form>
                <% } else { %>
                    <h4><a href="<c:url value="/login"/>"><spring:message code="head.link.login"/></a></h4>
                    <h4 style="margin-right: 2%;margin-left: 2%"> <spring:message code="head.label.or"/> </h4>
                    <h4><a href="<c:url value="/signup"/>"><spring:message code="head.link.signup"/></a></h4>
                <% } %>
            </div>
        </div>
    </div>
</div>
