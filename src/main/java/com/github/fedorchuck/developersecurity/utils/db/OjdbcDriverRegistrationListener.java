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

package com.github.fedorchuck.developersecurity.utils.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @link http://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered .
 *
 */
public class OjdbcDriverRegistrationListener implements ServletContextListener {
    private final Logger log = LoggerFactory.getLogger(OjdbcDriverRegistrationListener.class);
    private Driver driver = null;

    /**
     * Registers the postgres JDBC driver
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.driver = new org.postgresql.Driver(); // load and instantiate the class
        boolean skipRegistration = false;
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver instanceof org.postgresql.Driver) {
                org.postgresql.Driver alreadyRegistered = (org.postgresql.Driver) driver;
                if (alreadyRegistered.getClass() == this.driver.getClass()) {
                    // same class in the VM already registered itself
                    skipRegistration = true;
                    this.driver = alreadyRegistered;
                    break;
                }
            }
        }

        try {
            if (!skipRegistration)
                DriverManager.registerDriver(driver);
            else
                log.debug("driver was registered automatically");
            log.info(String.format("registered jdbc driver: %s v%d.%d", driver, driver.getMajorVersion(), driver.getMinorVersion()));
        } catch (SQLException e) {
            log.error("Error registering oracle driver: database connectivity might be unavailable!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deregisters JDBC driver
     *
     * Prevents Tomcat 7 from complaining about memory leaks.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (this.driver != null) {
            try {
                DriverManager.deregisterDriver(driver);
                log.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                log.warn(String.format("Error deregistering driver %s", driver),e);
            }
            this.driver = null;
        } else
            log.warn("No driver to deregister");
    }
}
