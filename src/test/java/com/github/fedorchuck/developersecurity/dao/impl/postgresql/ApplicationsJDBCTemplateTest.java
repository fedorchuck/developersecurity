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

package com.github.fedorchuck.developersecurity.dao.impl.postgresql;

import com.github.fedorchuck.developersecurity.domainmodels.Application;
import com.github.fedorchuck.developersecurity.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fedorchuck.
 */
public class ApplicationsJDBCTemplateTest {
    private ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/webapp/WEB-INF/application-context.xml",
            "/src/main/webapp/WEB-INF/spring-database.xml",
            "/src/main/webapp/WEB-INF/spring-security.xml",
            "/src/main/webapp/WEB-INF/spring-web-config.xml",
            "/src/main/webapp/WEB-INF/mail-service.xml"
    );
    private ApplicationsJDBCTemplate applicationsJDBCTemplate =
            (ApplicationsJDBCTemplate) context.getBean("applicationsJDBCTemplate");

    @Test       //save
    public void test000(){
        Application application = new Application(
                0L,"00000000-0000-0000-0000-000000000001",
                "11111111-1111-1111-1111-111111111111", "first test app",
                "PERMITTED","user message - answer for question why","link to website",
                0L,0L);
        applicationsJDBCTemplate.save(application);
        application = new Application(
                1L,"00000000-0000-0000-0000-000000000001",
                "22222222-2222-2222-2222-222222222222", "first test app",
                "PERMITTED","user message - answer for question why","link to website",
                0L,0L);
        applicationsJDBCTemplate.save(application);
    }

    @Test       //getApplications
    public void test001(){
//        Assert.assertEquals(true, applicationsJDBCTemplate.tokenExist("11111111-1111-1111-1111-111111111111"));

        Set<String> expected = new HashSet<>(Arrays.asList("11111111-1111-1111-1111-111111111111","22222222-2222-2222-2222-222222222222"));
        List<Application> tmp = applicationsJDBCTemplate.getApplications("00000000-0000-0000-0000-000000000001");
        Set<String> actual = new HashSet<>(tmp.stream().map(Application::getApplicationToken).collect(Collectors.toList()));

        Assert.assertEquals(expected, actual);
    }

    @Test       //tokenExist
    public void test002(){
        Assert.assertEquals(true, applicationsJDBCTemplate.tokenExist("11111111-1111-1111-1111-111111111111"));
    }

    @Test       //updateEnabled
    public void test003(){
        applicationsJDBCTemplate.updateApplication(
                "tested method updateApi","DENIED","some message",
                new TimeUtil().convertDateToUnix(new Date()),"11111111-1111-1111-1111-111111111111");
    }

    @Test       //updateDateOfLastUsedByApi
    public void test004(){
        applicationsJDBCTemplate.updateDateOfLastUsedByApi(
                "11111111-1111-1111-1111-111111111111", new TimeUtil().convertDateToUnix(new Date()));
    }

    @Test       //deleteApplication
    public void test998() {
        Assert.assertEquals(true, applicationsJDBCTemplate.deleteApplication("22222222-2222-2222-2222-222222222222"));
    }

    @Test       //deleteApplications
    public void test999() {
        Assert.assertEquals(true, applicationsJDBCTemplate.deleteApplications("00000000-0000-0000-0000-000000000001"));
    }
}