/*
 * Copyright 2016 Volodymyr Fedorchuk <vl.fedorchuck@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fedorchuck.developersecurity.service.restApi;

import com.github.fedorchuck.developersecurity.dao.impl.postgresql.ApplicationsJDBCTemplate;
import com.github.fedorchuck.developersecurity.domainmodels.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class RestApiServiceImplTest {

    private ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/webapp/WEB-INF/application-context.xml",
            "/src/main/webapp/WEB-INF/spring-database.xml",
            "/src/main/webapp/WEB-INF/spring-security.xml",
            "/src/main/webapp/WEB-INF/spring-web-config.xml",
            "/src/main/webapp/WEB-INF/mail-service.xml"
    );
    private ApplicationsJDBCTemplate applicationsJDBCTemplate =
            (ApplicationsJDBCTemplate) context.getBean("applicationsJDBCTemplate");

    @Test
    public void test001() {
        RestApiServiceImpl clientServiceImplTest = new RestApiServiceImpl(applicationsJDBCTemplate);
        Response expected = new Response("DENIED","user message - answer for question why","link to website");
        Assert.assertEquals(
                expected,clientServiceImplTest.getResponse("00000000-0000-0000-0000-000000000000","11111111-1111-1111-1111-111111111111"));
    }

    @Test
    public void test002() {
        RestApiServiceImpl clientServiceImplTest = new RestApiServiceImpl(applicationsJDBCTemplate);
        Response expected = new Response("PERMITTED","user message - answer for question why","link to website");
        Assert.assertEquals(
                expected,clientServiceImplTest.getResponse("00000000-0000-0000-0000-000000000000","22222222-2222-2222-2222-222222222222"));
    }

    @Test
    public void test003() {
        RestApiServiceImpl clientServiceImplTest = new RestApiServiceImpl(applicationsJDBCTemplate);
        Response expected = new Response("PERMITTED","Record with this signature was not found.","link to website");
        Assert.assertEquals(
                expected,clientServiceImplTest.getResponse("00000000-0000-0000-0000-222222222222","22222222-2222-2222-2222-000000000000"));
    }
}