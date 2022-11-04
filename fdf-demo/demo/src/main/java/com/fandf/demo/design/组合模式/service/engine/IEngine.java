package com.fandf.demo.design.组合模式.service.engine;

import com.fandf.demo.design.组合模式.model.aggregates.TreeRich;
import com.fandf.demo.design.组合模式.model.vo.EngineResult;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022-9-19 18:04
 */
public interface IEngine {

    EngineResult process(final Long treeId, final String userId, TreeRich treeRich, final Map<String, String> decisionMatter);

}
