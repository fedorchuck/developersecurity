<%@ page import="com.github.fedorchuck.developersecurity.web.models.Session" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

<div id="footer" class="footer">
    <div class="container" style="margin-left: 5%;">
        <h6>Copyright © 2016, 2017 <a href="http://vl-fedorchuck.rhcloud.com/" target="_blank">Volodymyr Fedorchuk</a></h6>
        <h6>
            This software is licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0.html" target="_blank">Apache License</a>.
            <a href="https://github.com/fedorchuck/developersecurity" target="_blank">source code</a>
        </h6>
        <% Session z = (Session) request.getSession().getAttribute("session");
        if (z!=null) { %>
        <h6>Updated: <%= z.getDataOfSiteUpdate() %> </h6>
        <% } %>
    </div>
</div>