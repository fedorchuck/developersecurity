<%@ page import="com.github.fedorchuck.developersecurity.web.models.Session" %>
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
<body onload='document.loginForm.username.focus();'>
<jsp:include page="fragments/yandex_metrica.jsp"/>
<div id="content" class="content">
    <jsp:include page="fragments/fragment_header_full.jsp"/>
    <div class="container">
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
        } else { } %>
        <div class="row">
            <div style="display:flex;justify-content:center;align-items:center;">
                <div class="form-signin">
                    <h2 class="text-center" style="margin-bottom: 8%"><spring:message code="login.label"/></h2>

                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            <p><spring:message code="login.label.error"/></p>
                        </div>
                    </c:if>

                    <form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
                        <table>
                            <input type="text" name="username" class="form-control" style="margin-bottom: 5%"
                                   placeholder='<spring:message code="login.placeholder.email"/>' />
                            <input type="password" name="password" class="form-control" style="margin-bottom: 5%"
                                   placeholder='<spring:message code="login.placeholder.password"/>' />
                            <button type="submit" value="Log in" class="btn btn-lg btn-primary btn-block"
                                    style="margin-bottom: 5%"><spring:message code="login.button.login"/></button>
                        </table>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>

                    <div class="text-right">
                        <a href="<c:url value="/password"/>"><spring:message code="login.link.forgot"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/fragment_footer.jsp"/>
</body>
</html>