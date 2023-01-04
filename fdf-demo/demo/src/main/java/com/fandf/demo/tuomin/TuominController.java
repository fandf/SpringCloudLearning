package com.fandf.demo.tuomin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 * @date 2023-1-4 10:25
 */
@RestController
public class TuominController {

    @GetMapping("tuomin")
    public Test get() {
        Test test = new Test();
        test.setEmail("123@qq.com");
        test.setPhone("17845678945");
        test.setName("张有志");
        return test;
    }

}
