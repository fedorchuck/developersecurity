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
            <div style="margin-right: 0.7%; margin-top: -1%;">
            <c:choose>
                <c:when test="${send == 1}">
                    <h4><strong><spring:message code="feedback.thank_you"/></strong></h4>
                </c:when>
                <c:otherwise>
                    <h4><spring:message code="feedback.label"/></h4>
                    <c:url var="sendFeedbackUrl" value="/feedback/send" />
                    <form:form name="sendFeedback" method="POST" action="${sendFeedbackUrl}">
                        <div class="form-group">
                            <textarea class="form-control" rows="5" name="feedback_message"></textarea>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-default btn-sm btn-block"
                                    style="background-color: #f2f2f2;
                                    color: #2e6da4;"><spring:message code="feedback.button.send"/></button>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form:form>
                </c:otherwise>
            </c:choose>
            <spring:message code="feedback.star.1"/>
            <a href="https://github.com/fedorchuck/developersecurity" target="_blank">github.com</a>
            <spring:message code="feedback.star.2"/>
            </div>
        </div>
    </div>

</div>
<jsp:include page="fragment_footer.jsp"/>
</body>
</html>
