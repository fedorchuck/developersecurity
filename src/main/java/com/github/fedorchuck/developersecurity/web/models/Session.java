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

import com.github.fedorchuck.developersecurity.domainmodels.Developer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author fedorchuck.
 */
@Component
@Scope("session")
public class Session {
    private Developer user;
    private String language;
    private String dataOfSiteUpdate;
    private boolean info;
    private String strong;
    private String message;
    private String tmp;

    public Session() {
    }

    public Developer getUser() {
        return user;
    }

    public void setUser(Developer user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDataOfSiteUpdate() {
        return dataOfSiteUpdate;
    }

    public void setDataOfSiteUpdate(String dataOfSiteUpdate) {
        this.dataOfSiteUpdate = dataOfSiteUpdate;
    }

    public boolean isInfo() {
        return info;
    }

    public void setInfo(boolean info) {
        this.info = info;
    }

    public String getStrong() {
        return strong;
    }

    public void setStrong(String strong) {
        this.strong = strong;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return info == session.info &&
                Objects.equals(user, session.user) &&
                Objects.equals(language, session.language) &&
                Objects.equals(dataOfSiteUpdate, session.dataOfSiteUpdate) &&
                Objects.equals(strong, session.strong) &&
                Objects.equals(message, session.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, language, dataOfSiteUpdate, info, strong, message);
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", language='" + language + '\'' +
                ", dataOfSiteUpdate='" + dataOfSiteUpdate + '\'' +
                ", info=" + info +
                ", strong='" + strong + '\'' +
                ", message='" + message + '\'' +
                ", tmp='" + tmp + '\'' +
                '}';
    }
}
