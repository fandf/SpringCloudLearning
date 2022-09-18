package com.fandf.demo.design.抽象工厂;

import com.fandf.demo.design.抽象工厂.adapter.impl.IIRCacheAdapter;
import com.fandf.demo.design.抽象工厂.application.CacheService;
import com.fandf.demo.design.抽象工厂.factory.JDKProxyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fandongfeng
 * @date 2022/9/18 18:02
 */
@RestController
@RequestMapping("factory")
public class Test {

    @GetMapping
    public void test() throws Exception {
        CacheService proxy = JDKProxyFactory.getProxy(CacheService.class, IIRCacheAdapter.class);
        proxy.set("FDF", "哈哈哈");
        proxy.get("FDF");
    }


}
