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

package com.github.fedorchuck.developersecurity.dao.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fedorchuck.
 */
@Transactional
@Repository("internal")
public class Internal {
    private Logger log = Logger.getLogger(Internal.class);

    private String dataOfSiteUpdate;//day month 3 22:39:31 2016
    private String reCapture_secret;//"6LfW-icTAAAAAJv4LMTXveVRgsYePX9TXCUAKOBx"
    private String whereSendFeedback;

    public void setDataOfSiteUpdate(String dataOfSiteUpdate) {
        this.dataOfSiteUpdate = dataOfSiteUpdate;
    }

    public void setReCapture_secret(String reCapture_secret) {
        this.reCapture_secret = reCapture_secret;
    }

    public void setWhereSendFeedback(String whereSendFeedback) {
        this.whereSendFeedback = whereSendFeedback;
    }

    public String getDataOfSiteUpdate() {
        return dataOfSiteUpdate;
    }

    public String getReCapture_secret() {
        return reCapture_secret;
    }

    public String getWhereSendFeedback() {
        log.log(Level.DEBUG, "get record: whereSendFeedback with value: "+whereSendFeedback);
        return whereSendFeedback;
    }
}
