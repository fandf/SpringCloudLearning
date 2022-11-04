package com.fandf.demo.design.组合模式.service.logic.impl;

import com.fandf.demo.design.组合模式.service.logic.BaseLogic;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022-9-19 18:05
 */
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(Long treeId, String userId, Map<String, String> decisionMatter) {
        return decisionMatter.get("gender");
    }
}
