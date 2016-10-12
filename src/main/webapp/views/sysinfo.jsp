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
<%@page session="true"%>
<html>
<head>
    <title>Developer Security</title>
    <meta http-equiv="refresh" content="5"/>
</head>
<body>
<jsp:include page="fragments/yandex_metrica.jsp"/>

<div id="content">
    <h1>SystemInfo</h1>
    <div>
        <p>Click the button to display the total width of your screen, in pixels.</p>
        <button onclick="myFunction()">Try it</button>
        <p id="demo"></p>
        <script>
            function myFunction() {
                document.getElementById("demo").innerHTML = "Total Width of screen: " + screen.width + "px";
            }
        </script>

        <p <c:forEach items="${mem}" var="entry">>

                <b> ${entry.key}</b>
                <b>:</b>
                <span> ${entry.value}</span>
                <b>Mb</b>

        </p </c:forEach>>

    </div>
</div>

</body>
</html>

