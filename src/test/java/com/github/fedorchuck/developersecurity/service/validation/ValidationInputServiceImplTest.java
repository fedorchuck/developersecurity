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

package com.github.fedorchuck.developersecurity.service.validation;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author fedorchuck.
 */
public class ValidationInputServiceImplTest {

    private ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/webapp/WEB-INF/application-context.xml",
            "/src/main/webapp/WEB-INF/spring-database.xml",
            "/src/main/webapp/WEB-INF/spring-security.xml",
            "/src/main/webapp/WEB-INF/spring-web-config.xml",
            "/src/main/webapp/WEB-INF/mail-service.xml"
    );
    private ValidationInputService validationInputService =
            (ValidationInputService) context.getBean("validationInputService");

    @Test   //validation name
    public void test001(){
        Assert.assertEquals(false, validationInputService.validationName("a"));
        Assert.assertEquals(false, validationInputService.validationName("ab"));
        Assert.assertEquals(true, validationInputService.validationName("abc"));
        Assert.assertEquals(true, validationInputService.validationName("abcd"));
    }

    @Test   //validation email
    public void test002(){
        Assert.assertEquals(false, validationInputService.validationEmail("a"));
        Assert.assertEquals(false, validationInputService.validationEmail("ab"));
        Assert.assertEquals(false, validationInputService.validationEmail("ab@"));
        Assert.assertEquals(false, validationInputService.validationEmail("ab@d"));
        Assert.assertEquals(true, validationInputService.validationEmail("abc@d"));
        Assert.assertEquals(true, validationInputService.validationEmail("abc@dd"));
    }

    @Test   //validation password
    public void test003(){
        Assert.assertEquals(false, validationInputService.validationPassword("a", "a"));
        Assert.assertEquals(false, validationInputService.validationPassword("123456","123456"));
        Assert.assertEquals(false, validationInputService.validationPassword("123456789","987654321"));
        Assert.assertEquals(true, validationInputService.validationPassword("1234567","1234567"));
    }
}