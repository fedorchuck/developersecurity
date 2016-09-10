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

package com.github.fedorchuck.developersecurity.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TimeUtilTest {
    private TimeUtil timeUtil = new TimeUtil();

    @Test       //Date convertUnixToDate(long unixTime)
    public void test01() {
        try {
            Date check = timeUtil.convertUnixToDate(1458432000);
            Date expect = timeUtil.convertStringDateToDate("Sun Mar 20 02:00:00 EET 2016");
            assertEquals(expect, check);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test       //Date convertStringDateToDate(String time)
    public void test02() {
        try {
            timeUtil.convertStringDateToDate("Sun Mar 20 02:00:00 EET 2016");
            Assert.assertTrue(true);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test       //Long convertDateToUnix(java.util.Date date)
    public void test03() {
        try {
            Long check = timeUtil.convertDateToUnix(
                    timeUtil.convertStringDateToDate("Sun Mar 20 02:00:00 EET 2016"));
            Long expect = 1458432000L;
            assertEquals(expect, check);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test       //Long convertDateToUnix(String time)
    public void test04() {
        try {
            Long check = timeUtil.convertDateToUnix("Sun Mar 20 02:00:00 EET 2016");
            Long expect = 1458432000L;
            assertEquals(expect, check);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test       //convertISO8607timeToUnix(String time)
    public void test05() {
        try {
            Long check = timeUtil.convertISO8607timeToUnix("2016-02-18T16:52:02Z");
            Long expect = 1455814322L;
            assertEquals(expect,check);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test       //Date convertISO8607timeToDate(String time)
    public void test06() {
        try {
            timeUtil.convertISO8607timeToDate("2016-02-18T16:52:02Z");
            assertTrue(true);
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test       //boolean checkInRange(long unknownUnixTime, long weekEndUnixTime)
    public void test07() {
        assertEquals(true, timeUtil.checkInRange(1455814322, 1456012800));
    }

}