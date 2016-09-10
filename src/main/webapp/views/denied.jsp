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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="yandex_metrica.jsp"/>
<br>
<h1>HTTP Status 403 - Access is denied</h1>

<c:choose>
    <c:when test="${empty username}">
        <h2>You do not have permission to access this page!</h2>
        <h5>Maybe you should <a href="<c:url value="/login" />">login</a>? </h5>
        <br/>
        <h5><a href="<c:url value="/" />">Home page.</a></h5>
    </c:when>
    <c:otherwise>
        <h2>
            Username : ${username} <br/>
            You do not have permission to access this page!
        </h2>
    </c:otherwise>
</c:choose>

</body>
</html>