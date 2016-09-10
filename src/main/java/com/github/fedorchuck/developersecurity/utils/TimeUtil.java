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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fedorchuck.
 */
@SuppressWarnings("WeakerAccess")
public class TimeUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");

    /**
     * convert unix-time to date-time
     * @param unixTime take {@link Long} like 1458432000.
     * @return {@link Date} like {@code Sun Mar 20 02:00:00 EET 2016}.
     * @see "https://en.wikipedia.org/wiki/Unix_time"
     * */
    public Date convertUnixToDate(long unixTime){
        return new Date(unixTime *1000);
    }

    /**
     * convert {@link String} in format {@code dow mon dd hh:mm:ss zzz yyyy} to {@link Date}.
     * @param time take {@link String} like {@code Sun Mar 20 02:00:00 EET 2016 }.
     * @return {@link Date} like {@code Thu Feb 18 18:52:02 EET 2016}.
     * @exception ParseException if bad model was inputted.
     * @see "https://en.wikipedia.org/wiki/Unix_time"
     * */
    public Date convertStringDateToDate(String time) throws ParseException {
        return dateFormat.parse(time);
    }

    /**
     * convert date-time to unix-time
     * @param date take {@link Date} like {@code Sun Mar 20 02:00:00 EET 2016}.
     * @return {@code Long} like 1458432000.
     * @see "https://en.wikipedia.org/wiki/Unix_time"
     * */
    public Long convertDateToUnix(Date date){
        return (date.getTime() /1000);
    }

    /**
     * convert {@link String} in format {@code dow mon dd hh:mm:ss zzz yyyy} to unix-time.
     * @param time take {@link String} like {@code Sun Mar 20 02:00:00 EET 2016 }.
     * @return {@code Long} like 1458432000.
     * @exception ParseException if bad model was inputted.
     * @see "https://en.wikipedia.org/wiki/Unix_time"
     * */
    public Long convertDateToUnix(String time) throws ParseException {
        Date convertedDate = dateFormat.parse(time);
        return (convertedDate.getTime()/1000);
    }

    /**
     * convert ISO 8601 to unix time
     * @param time take ISO 8601 time as {@link String} like {@code 2016-02-18T16:52:02Z}.
     * @return {@code Long} like 1455814322.
     * @exception ParseException if bad model was inputted.
     * @see "https://en.wikipedia.org/wiki/ISO_8601"
     * @see "http://www.iso.org/iso/home/standards/iso8601.htm"
     * @see "https://en.wikipedia.org/wiki/Unix_time"
     * */
    public Long convertISO8607timeToUnix(String time) throws ParseException {
        return (convertISO8607timeToDate(time).getTime() /1000);
    }

    /**
     * convert ISO 8601 to date-time
     * @param time take ISO 8601 time as {@link String} like {@code 2016-02-18T16:52:02Z}.
     * @return {@link Date} like {@code Thu Feb 18 18:52:02 EET 2016}.
     * @exception ParseException if bad model was inputted.
     * @see "https://en.wikipedia.org/wiki/ISO_8601"
     * @see "http://www.iso.org/iso/home/standards/iso8601.htm"
     * */
    public Date convertISO8607timeToDate(String time) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(time);
    }

    /**
     * Check that user date is in range
     * @param unknownUnixTime date what we want check.
     * @param weekEndUnixTime date of end of week.
     * @return {@link Boolean} answer.
     * */
    public boolean checkInRange(long unknownUnixTime, long weekEndUnixTime){
        long week = weekEndUnixTime - 604800;//week in unix time
        return ((unknownUnixTime >= week) && (unknownUnixTime <= weekEndUnixTime));
    }

}
