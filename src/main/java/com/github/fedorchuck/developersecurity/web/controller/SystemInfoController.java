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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.TreeMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/sysinfo")
public class SystemInfoController {

    @RequestMapping(value = "", method = GET)
    public ModelAndView index(){
        ModelAndView model = new ModelAndView("sysinfo");

        final double mb = 1024*1024;
        Runtime runtime = Runtime.getRuntime();
        long usedRam = runtime.totalMemory() - runtime.freeMemory();

        Map<String,Double> mem = new TreeMap<>();
        mem.put("Used memory", usedRam/mb);
        mem.put("Total memory", runtime.totalMemory()/mb);
        mem.put("Free memory",  runtime.freeMemory()/mb);

        model.getModel().put("mem",mem);

        return model;
    }
}
