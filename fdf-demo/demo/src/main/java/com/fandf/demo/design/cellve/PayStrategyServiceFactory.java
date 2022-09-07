package com.fandf.demo.design.cellve;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工厂设计模式
 *
 * @author fandongfeng
 * @date 2022-9-7 10:49
 */
public class PayStrategyServiceFactory  {

    /**
     * 存放对应的类型和实现类
     */
    private static final Map<String, AbstractHandler> STRATEGY_MAP = new ConcurrentHashMap<>();


    /**
     *
     * @param type 枚举
     * @return
     */
    public static AbstractHandler getInvokeStrategyMap(String type) {
        return STRATEGY_MAP.get(type);
    }


    /**
     * 注册
     *
     * @param type
     * @param handler
     */
    public static void register(String type, AbstractHandler handler) {
        if (StringUtils.isEmpty(type) || null == handler) {
            return;
        }
        STRATEGY_MAP.put(type, handler);
    }


}
