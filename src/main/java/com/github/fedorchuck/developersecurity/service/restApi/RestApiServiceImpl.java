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
import com.github.fedorchuck.developersecurity.domainmodels.Application;
import com.github.fedorchuck.developersecurity.domainmodels.Response;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("apiService")
@Transactional
public class RestApiServiceImpl implements RestApiService {
	private final Logger log = Logger.getLogger(RestApiServiceImpl.class);
	private final ApplicationsJDBCTemplate applicationsJDBCTemplate;

	@Autowired
	public RestApiServiceImpl(ApplicationsJDBCTemplate applicationsJDBCTemplate) {
		this.applicationsJDBCTemplate = applicationsJDBCTemplate;
	}

	public Response getResponse(String developerToken, String applicationToken) {
		for(Application application : applicationsJDBCTemplate.getApplications(developerToken)){
			if(application.getDeveloperToken().equals(developerToken) &&
					application.getApplicationToken().equals(applicationToken)) {
                log.log(Level.DEBUG, "get record: "+developerToken+"; app token: "+applicationToken);
				return new Response(
						application.getResponse(),
						application.getMessage(),
						application.getDocumentationUrl());
			}
		}
        log.log(Level.DEBUG, "try to get record: "+developerToken+"; app token: "+applicationToken);
		return new Response("PERMITTED","Record with this signature was not found.","https://developersecurity.herokuapp.com/documentation");
	}
}
