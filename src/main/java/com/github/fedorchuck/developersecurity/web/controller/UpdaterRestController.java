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

package com.github.fedorchuck.developersecurity.web.controller;

import com.github.fedorchuck.developersecurity.domainmodels.Response;
import com.github.fedorchuck.developersecurity.service.application.ApplicationService;
import com.github.fedorchuck.developersecurity.service.restApi.RestApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fedorchuck.
 */
@RestController
public class UpdaterRestController {

	private final RestApiService restApiService;
	private final ApplicationService applicationService;

	@Autowired
	public UpdaterRestController(RestApiService restApiService,
								 ApplicationService applicationService) {
		this.restApiService = restApiService;
		this.applicationService = applicationService;
	}

	// /check?developer={token}&application={token}
	@RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> get(
										@RequestParam("developer") String developer,
										@RequestParam("application") String application){

		Response response = restApiService.getResponse(developer,application);
		applicationService.updateDateOfLastUsedByApi(application);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
