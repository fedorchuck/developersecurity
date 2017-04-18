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

import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import com.github.fedorchuck.developersecurity.utils.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static com.github.fedorchuck.developersecurity.utils.Constants.developerEmail;
import static com.github.fedorchuck.developersecurity.utils.Constants.developerToken;

/**
 * @author fedorchuck.
 */
public class DevelopersJDBCTemplateTest {
    private ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/webapp/WEB-INF/application-context.xml",
            "/src/main/webapp/WEB-INF/spring-database.xml",
            "/src/main/webapp/WEB-INF/spring-security.xml",
            "/src/main/webapp/WEB-INF/spring-web-config.xml",
            "/src/main/webapp/WEB-INF/mail-service.xml"
    );
    private DevelopersJDBCTemplate developersJDBCTemplate =
            (DevelopersJDBCTemplate) context.getBean("developersJDBCTemplate");

    @Test       //save
    public void test000(){
        Developer developer = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",false,"ROLE_USER", 0L, 0L);
        developersJDBCTemplate.save(developer);
    }

    @Test       //tokenExist
    public void test001(){
//        Assert.assertEquals(false, developersJDBCTemplate.tokenExist(UUID.randomUUID().toString()));
        Assert.assertEquals(true, developersJDBCTemplate.tokenExist("00000000-0000-0000-0000-000000000001"));
    }

    @Test       //emailExist
    public void test002(){
        Assert.assertEquals(false, developersJDBCTemplate.emailExist("00000000-0000-0000-0000-000000000001"));
        Assert.assertEquals(true, developersJDBCTemplate.emailExist("randomholy@gmail.com"));
    }

    @Test       //updateEnabled
    public void test003(){
        developersJDBCTemplate.updateEnabled("00000000-0000-0000-0000-000000000001");
    }

    @Test       //getDeveloper
    public void test004() {
        Developer expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 0L);
        Developer actual = developersJDBCTemplate.getDeveloper(
                "00000000-0000-0000-0000-000000000001", developerToken);
        Assert.assertEquals(expected,actual);
    }

    @Test       //getDeveloper
    public void test005() {
        Developer expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 0L);
        Developer actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);
    }

    @Test       //updateDateOfLastUsed
    public void test006(){
        Developer expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.updateDateOfLastUsed("00000000-0000-0000-0000-000000000001",1L);
        Developer actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);
    }

    @Test       //setPassword
    public void test007(){
        Developer expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.setPassword("00000000-0000-0000-0000-000000000001","qwerty");
        Developer actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);

        expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qWerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.setPassword("00000000-0000-0000-0000-000000000001","qWerty");
        actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);

        expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.setPassword("00000000-0000-0000-0000-000000000001","qwerty");
        actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);
    }

    @Test       //setEmail
    public void test008(){
        Developer expected = new Developer(
                "00000000-0000-0000-0000-000000000001","skype.v.v@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.setEmail("00000000-0000-0000-0000-000000000001","skype.v.v@gmail.com");
        Developer actual = developersJDBCTemplate.getDeveloper("skype.v.v@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);
        expected = new Developer(
                "00000000-0000-0000-0000-000000000001","randomholy@gmail.com",
                "qwerty","Volodymyr",true,"ROLE_USER", 0L, 1L);
        developersJDBCTemplate.setEmail("00000000-0000-0000-0000-000000000001","randomholy@gmail.com");
        actual = developersJDBCTemplate.getDeveloper("randomholy@gmail.com", developerEmail);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test009(){
        try {
            developersJDBCTemplate.getDeveloper("randomholy@gmail.com", Constants.applicationToken);
        } catch (java.lang.IllegalAccessError ex){
            Assert.assertTrue(true);
        }
    }

    @Test       //delete
    public void test999() {
        Assert.assertEquals(true, developersJDBCTemplate.delete("randomholy@gmail.com"));
    }


}