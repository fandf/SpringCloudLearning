package com.fandf.demo.design.组合模式.service.engine;

import com.fandf.demo.design.组合模式.service.logic.LogicFilter;
import com.fandf.demo.design.组合模式.service.logic.impl.UserAgeFilter;
import com.fandf.demo.design.组合模式.service.logic.impl.UserGenderFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fandongfeng
 * @date 2022-9-19 18:03
 */
public class EngineConfig {

    static Map<String, LogicFilter> logicFilterMap;

    static {
        logicFilterMap = new ConcurrentHashMap<>();
        logicFilterMap.put("userAge", new UserAgeFilter());
        logicFilterMap.put("userGender", new UserGenderFilter());
    }

    public Map<String, LogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public void setLogicFilterMap(Map<String, LogicFilter> logicFilterMap) {
        EngineConfig.logicFilterMap = logicFilterMap;
    }

}
