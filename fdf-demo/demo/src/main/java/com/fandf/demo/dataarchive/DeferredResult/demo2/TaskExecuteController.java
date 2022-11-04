package com.fandf.demo.dataarchive.DeferredResult.demo2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskExecuteController {

    private static final Logger log = LoggerFactory.getLogger(TaskExecuteController.class);

    @Autowired
    private TaskSet taskSet;

    @RequestMapping(value = "/set",method = RequestMethod.GET)
    public String setResult(){

        ResponseMsg<String> res = ResponseMsg.of(0,"正在处理中", null);


        return "收到 over ！！！";
    }
}