package com.fandf.test.entity;

import com.fandf.test.Application;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = Application.class)
public class OrderTest {

    @Test
    public void test(){
        KieServices kieServices = KieServices.Factory.get();
        // 获取Kie容器对象 默认容器对象
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        // 从Kie容器对象中获取会话对象（默认session对象
        KieSession kieSession = kieContainer.newKieSession();

        Order order = new Order();
        order.setOriginalPrice(BigDecimal.valueOf(180));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(300));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(600));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(1200));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(3000));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(8000));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());

        kieSession = kieContainer.newKieSession();
        order.setOriginalPrice(BigDecimal.valueOf(12000));
        // 将order对象插入工作内存
        kieSession.insert(order);
        // 匹配对象
        // 激活规则，由drools框架自动进行规则匹配。若匹配成功，则执行
        kieSession.fireAllRules();
        System.out.println("优惠前价格：" + order.getOriginalPrice() + ",优惠后价格：" + order.getRealPrice());



        // 关闭会话
        kieSession.dispose();

    }

}