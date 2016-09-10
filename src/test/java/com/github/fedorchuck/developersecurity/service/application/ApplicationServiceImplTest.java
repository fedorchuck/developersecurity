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

package com.github.fedorchuck.developersecurity.service.application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author fedorchuck.
 */
public class ApplicationServiceImplTest {
    private ApplicationContext context = new FileSystemXmlApplicationContext(
            "/src/main/webapp/WEB-INF/application-context.xml",
            "/src/main/webapp/WEB-INF/spring-database.xml",
            "/src/main/webapp/WEB-INF/spring-security.xml",
            "/src/main/webapp/WEB-INF/spring-web-config.xml",
            "/src/main/webapp/WEB-INF/mail-service.xml"
    );
    private ApplicationService applicationService =
            (ApplicationService) context.getBean("applicationService");

    @Test       //isOwner
    public void test000(){
        Assert.assertEquals(true, applicationService.isOwner("00000000-0000-0000-0000-000000000001", "11111111-1111-1111-1111-111111111111"));
        Assert.assertEquals(false, applicationService.isOwner("00000000-0000-0000-0000-000000000001", "11111111-1111-2222-1111-111111111111"));
    }

}