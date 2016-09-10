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

package com.github.fedorchuck.developersecurity.web.models;

import java.util.Arrays;

/**
 * @link 'https://developers.google.com/recaptcha/docs/verify#api-request'
 * @link 'https://www.google.com/recaptcha/admin#site/320945753?setup'
 * */
public class ReCaptcha {

    private Boolean success;        //": true|false,
    private String challenge_ts;    //": timestamp, // timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
    private String hostname;        //": string,    // the hostname of the site where the reCAPTCHA was solved
    private String[] error_codes;   //": [...]      // optional

    public Boolean getSuccess() {
        return success;
    }

    public String getChallenge_ts() {
        return challenge_ts;
    }

    public String getHostname() {
        return hostname;
    }

    public String[] getError_codes() {
        return error_codes;
    }

    @Override
    public String toString() {
        return ":{" +
                "success=" + success +
                ", challenge_ts='" + challenge_ts + '\'' +
                ", hostname='" + hostname + '\'' +
                ", error_codes=" + Arrays.toString(error_codes) +
                '}';
    }
}
