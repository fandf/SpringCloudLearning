package com.fandf.test.service;

import org.springframework.stereotype.Service;

/**
 * @author fandongfeng
 * @date 2023/5/2 15:39
 */
@Service
public class MyUserService {
    public String getByName(String name) {
        return name + "123";
    }
}
