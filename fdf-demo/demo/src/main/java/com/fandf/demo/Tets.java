package com.fandf.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author fandongfeng
 * @date 2022/11/13 17:05
 */
@RestController
@RequestMapping("test1")
public class Tets {

    private Integer a;

    @PostConstruct
    public void init() {
        a = 1;
    }

    @GetMapping
    public Integer get() {
        return a;
    }

}
