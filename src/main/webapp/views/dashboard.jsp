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
            <c:url var="createUrl" value="/dashboard/createApi" />
            <div class="form-inline">
                <form:form name="createApi" method="POST" action="${createUrl}">
                <div class="form-group">
                    <input type="text" name="shortName" class="form-control" id="create_shortName"
                           placeholder='<spring:message code="dashboard.placeholder.short_name"/>' />
                </div>
                <div class="form-group" >
                    <select class="form-control" id="sel11" name="response">
                        <option style="background-color: #2B8E00; color: #F5F5F5">PERMITTED</option>
                        <option style="background-color: #ac2925; color: #F5F5F5">DENIED</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" name="message" class="form-control" id="create_message"
                           placeholder='<spring:message code="dashboard.placeholder.message"/>' />
                </div>
                <div class="form-group">
                    <button value="Create" type="submit" id="create_submit" class="btn btn-default btn-sm btn-block"
                            style="background-color: #f2f2f2;
                            color: #2e6da4;"><spring:message code="dashboard.button.create"/></button>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form:form>
            </div>

            <table class="table table-condensed" style="border-collapse:collapse;">
                <thead>
                    <tr>
                        <th><h4><spring:message code="dashboard.application_name"/></h4></th>
                        <th><h4><spring:message code="dashboard.application_token"/></h4></th>
                        <th><h4><spring:message code="dashboard.response"/></h4></th>
                        <th><h4><spring:message code="dashboard.message"/></h4></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${applications}" var="application" varStatus="status">
                    <tr data-toggle="collapse" data-target="#demo${application.applicationId}" class="accordion-toggle">
                        <c:choose>
                            <c:when test="${application.shortName == ''}">
                                <td><h5><i><spring:message code="dashboard.absent.application_name"/> </i></h5></td>
                            </c:when>
                            <c:otherwise>
                                <td><h5>${application.shortName}</h5></td>
                            </c:otherwise>
                        </c:choose>
                        <td><h5>${application.applicationToken}</h5></td>
                        <c:choose>
                            <c:when test="${application.response == 'DENIED'}">
                                <td style="color: #AC2925">${application.response}</td>
                            </c:when>
                            <c:otherwise>
                                <td style="color: #2B8E00">${application.response}</td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${application.message == ''}">
                                <td><h5><i><spring:message code="dashboard.absent.message"/> </i></h5></td>
                            </c:when>
                            <c:otherwise>
                                <td><h5>${application.message}</h5></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr >
                        <td colspan="4" class="hiddenRow">
                            <div class="accordian-body collapse" id="demo${application.applicationId}">
                                <spring:message code="dashboard.api_message"/>
                                <h5>
                                    <a href="https://developersecurity.herokuapp.com/check?developer=${application.developerToken}&application=${application.applicationToken}">
                                    developersecurity.herokuapp.com/check?developer=${application.developerToken}&application=${application.applicationToken}</a>
                                </h5>
                                <div class="form-inline">
                                    <div class="form-group">
                                        <h5 id="created${application.applicationId}"></h5>
                                        <script>
                                            var date = ${application.dateOfRecordCreated};
                                            document.getElementById("created${application.applicationId}").innerHTML =
                                                    '<spring:message code="dashboard.api_created"/> ' + new Date(date * 1000);
                                        </script>
                                    </div>
                                    <div class="form-group">
                                        <h5 id="lastUsed${application.applicationId}"></h5>
                                        <script>
                                            var date = ${application.dateOfLastUsedByApi};
                                            if (date!=0) {
                                                document.getElementById("lastUsed${application.applicationId}")
                                                        .innerHTML = '<spring:message code="dashboard.api_used"/> ' + new Date(date * 1000);
                                            } else {
                                                document.getElementById("lastUsed${application.applicationId}")
                                                        .innerHTML = '<spring:message code="dashboard.api_used_newer"/> ';
                                            }
                                        </script>
                                    </div>
                                    <div class="form-group">
                                        <h5><a data-toggle="collapse" href="#edit${application.applicationId}">
                                            <spring:message code="dashboard.button.edit"/></a></h5>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="hiddenRow">
                            <div class="accordian-body collapse" id="edit${application.applicationId}">
                                <c:url var="saveUrl" value="/dashboard/updateApi" />
                                <c:url var="removeUrl" value="/dashboard/removeApi" />
                                <table style="display:inline-table;">
                                    <tr>
                                        <td>
                                            <div class="form-inline">
                                                <form:form name="updateApi" method="POST" action="${saveUrl}">
                                                <div class="form-group">
                                                    <c:choose>
                                                        <c:when test="${application.shortName == ''}">
                                                        <input type="text" name="shortName" class="form-control"
                                                               id="shortName" placeholder='<spring:message code="dashboard.absent.application_name"/> ' />
                                                        </c:when>
                                                        <c:otherwise>
                                                        <input value="${application.shortName}" type="text" name="shortName"
                                                               class="form-control" id="shortName"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="form-group" >
                                                    <select class="form-control" id="sel1" name="response">
                                                        <c:choose>
                                                            <c:when test="${application.response == 'DENIED'}">
                                                                <option style="background-color: #ac2925; color: #F5F5F5">DENIED</option>
                                                                <option style="background-color: #2B8E00; color: #F5F5F5">PERMITTED</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option style="background-color: #2B8E00; color: #F5F5F5">PERMITTED</option>
                                                                <option style="background-color: #ac2925; color: #F5F5F5">DENIED</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <c:choose>
                                                        <c:when test="${application.message == ''}">
                                                            <input type="text" name="message" class="form-control"
                                                                   id="message" placeholder='<spring:message code="dashboard.absent.message"/> '/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input value="${application.message}" type="text" name="message"
                                                                   class="form-control" id="message"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="form-group" style="padding: 0;">
                                                    <button value="Save" type="submit" id="submit" class="btn btn-default btn-sm btn-block"
                                                            style="background-color: #f2f2f2; color: #2e6da4;">
                                                        <spring:message code="dashboard.button.update"/>
                                                    </button>
                                                </div>
                                                <input type="hidden" name="applicationToken" value="${application.applicationToken}" />
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </form:form>
                                            </div>
                                        </td>
                                        <td style="padding-left: 0.6%; padding-top: 1.8%;">
                                            <form:form name="removeApi" method="POST" action="${removeUrl}">
                                                <div class="form-group" style="padding: 0;">
                                                    <button value="Remove" type="submit" id="remove" class="btn btn-default btn-sm btn-block"
                                                            style="background-color: #f2f2f2; color: #2e6da4;">
                                                        <spring:message code="dashboard.button.remove"/>
                                                    </button>
                                                </div>
                                                <input type="hidden" name="applicationToken" value="${application.applicationToken}" />
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            </form:form>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </div>
    </div>

</div>
<jsp:include page="fragments/fragment_footer.jsp"/>
</body>
</html>
