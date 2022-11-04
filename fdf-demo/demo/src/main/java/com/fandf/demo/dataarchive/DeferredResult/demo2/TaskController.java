package com.fandf.demo.dataarchive.DeferredResult.demo2;

import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {

    private Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskSet taskSet;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseMsg<Object> getResult(String id) {
        Cache<String, Object> cacheMap = taskSet.getCacheMap();
        Object present = cacheMap.getIfPresent(id);
        if (present == null) {
            return ResponseMsg.of(1, "没有找到数据", null);
        }
        return ResponseMsg.of(1, "", present);

    }
}