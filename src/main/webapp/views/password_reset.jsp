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
<head><jsp:include page="fragment_head.jsp"/></head>
<body onload='document.loginForm.username.focus();'>
<jsp:include page="yandex_metrica.jsp"/>
<div id="content" class="content">
    <jsp:include page="fragment_header_full.jsp"/>
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
        } else {
        %>
        <div class="row">
            <div style="display:flex;justify-content:center;align-items:center;">
                <div class="form-signin">
                    <h3><spring:message code="reset.label"/></h3>
                    <h4><spring:message code="reset.label.description"/></h4>

                    <form name='resetForm' action="<c:url value='/password/reset' />" method='POST'>
                        <table>
                            <input type="email" name="email" class="form-control" style="margin-bottom: 5%"
                                   placeholder='<spring:message code="login.placeholder.email"/>' />
                            <button type="submit" value="reset" class="btn btn-lg btn-primary btn-block"
                                    style="margin-bottom: 5%"><spring:message code="reset.button.password"/></button>
                        </table>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </div>
        <% } %>

    </div>
</div>

<jsp:include page="fragment_footer.jsp"/>
</body>
</html>
