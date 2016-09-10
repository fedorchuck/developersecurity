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

import com.github.fedorchuck.developersecurity.domainmodels.Response;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpringRestTestClient {

	/* http://localhost:8080/check?developer=00000000-0000-0000-0000-000000000000&application=11111111-1111-1111-1111-111111111111 */
	private static final String REST_SERVICE_URI = "http://localhost:8080/check";

	/* GET */
	private static void get(){
		System.out.println("Testing get API----------");
		RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject(REST_SERVICE_URI+"?developer=00000000-0000-0000-0000-000000000000&application=22222222-2222-2222-2222-222222222222", Response.class);
        System.out.println(response);
		response = restTemplate.getForObject(REST_SERVICE_URI+"?developer=00000000-0000-0000-0000-000000000000&application=11111111-1111-1111-1111-111111111111", Response.class);
		System.out.println(response);
		response = restTemplate.getForObject(REST_SERVICE_URI+"?developer=00000000-0000-0000-0000-222222222222&application=22222222-2222-2222-2222-000000000000", Response.class);
		System.out.println(response);
	}

	private static void example() {
		System.out.println("Testing example use API----------");

		try {
			URL url = new URL("https://developersecurity.herokuapp.com/check?developer=03a617f3-d983-4893-b35b-03bb40c834e3&application=5be393f1-5406-4152-b08b-299c62ad8e72");

			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
			httpURLConnection.setRequestMethod("GET");

			InputStreamReader data = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader br = new BufferedReader(data);
			Gson gson = new Gson();
			Response reply = gson.fromJson(br, Response.class);

			System.out.println(reply);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static void main(String args[]){
//		get();
		example();
    }
}