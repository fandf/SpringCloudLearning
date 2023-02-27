package com.fandf.demo.propertyeditor;

import com.fandf.demo.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description:
 * @author: fandongfeng
 * @date: 2023-2-2719:32
 */
@SpringBootTest(classes = DemoApplication.class)
class PersonServiceTest {

    @Resource
    PersonService personService;

    @Test
    void test() {
        personService.test();
    }
}