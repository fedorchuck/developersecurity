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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>
<html>
<head><jsp:include page="fragment_head.jsp"/></head>

<body>
<jsp:include page="yandex_metrica.jsp"/>
<div id="content" class="content">
    <jsp:include page="fragment_header_full.jsp"/>

    <div class="container">
        <div class="col-md-2">
            <div class="btn-group-vertical" style="width: 100%; margin-left: 4%;">
                <input type="submit" class="btn btn-block"
                       value='<spring:message code="dashboard.button.projects"/>' onclick="window.location='/dashboard';"/>
                <input type="submit" class="btn btn-block"
                       value='<spring:message code="dashboard.button.account"/>' onclick="window.location='/account';"/>
                <input type="submit" class="btn btn-block"
                       value='<spring:message code="dashboard.button.feedback"/>' onclick="window.location='/feedback';"/>
            </div>
        </div>
        <div class="col-md-10">
            <div style="margin-right: 0.7%;">
                <c:url var="changeEmailUrl" value="/account/changeEmail" />
                <div class="form-inline">
                    <form:form name="changeEmail" method="POST" action="${changeEmailUrl}">
                        <div class="form-group">
                            <input type="text" name="change_email_old" class="form-control" id="change_email_old"
                                   placeholder='<spring:message code="account.placeholder.change_email_old"/> ' />
                        </div>
                        <div class="form-group" >
                            <input type="text" name="change_email_new" class="form-control" id="change_email_new"
                                   placeholder='<spring:message code="account.placeholder.change_email_new"/> ' />
                        </div>
                        <div class="form-group">
                            <button value="Change email" type="submit" id="change_email_submit" class="btn btn-default btn-sm btn-block"
                                    style="background-color: #f2f2f2; color: #2e6da4;">
                                <spring:message code="account.button.change_email"/>
                            </button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form:form>
                </div>
                <c:url var="changePassUrl" value="/account/changePass" />
                <div class="form-inline">
                    <form:form name="changePass" method="POST" action="${changePassUrl}">
                        <div class="form-group">
                            <input type="password" name="current_password" class="form-control" id="current_password"
                                   placeholder='<spring:message code="account.placeholder.current_password"/> ' />
                        </div>
                        <div class="form-group" >
                            <input type="password" name="new_password" class="form-control" id="new_password"
                                   placeholder='<spring:message code="account.placeholder.new_password"/> ' />
                        </div>
                        <div class="form-group">
                            <input type="password" name="confirm_password" class="form-control" id="confirm_password"
                                   placeholder='<spring:message code="account.placeholder.confirm_password"/> ' />
                        </div>
                        <div class="form-group">
                            <button value="Change Password" type="submit" id="change_password_submit" class="btn btn-default btn-sm btn-block"
                                    style="background-color: #f2f2f2; color: #2e6da4;">
                                <spring:message code="account.button.change_password"/>
                            </button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form:form>
                </div>
                <c:url var="deleteAccountUrl" value="/account/deleteAccount" />
                <div class="form-inline">
                    <form:form name="deleteAccount" method="POST" action="${deleteAccountUrl}">
                        <div class="form-group">
                            <input type="email" name="delete_account_email" class="form-control" id="delete_account_email"
                                   placeholder='<spring:message code="account.placeholder.delete_account_email"/> ' />
                        </div>
                        <div class="form-group" >
                            <input type="password" name="delete_account_password" class="form-control" id="delete_account_password"
                                   placeholder='<spring:message code="account.placeholder.delete_account_password"/> ' />
                        </div>
                        <div class="form-group">
                            <button value="Delete Account" type="submit" id="delete_submit" class="btn btn-danger btn-sm btn-block"
                                    style="color: #f2f2f2;">
                                <spring:message code="account.button.delete"/>
                            </button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form:form>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="fragment_footer.jsp"/>
</body>
</html>