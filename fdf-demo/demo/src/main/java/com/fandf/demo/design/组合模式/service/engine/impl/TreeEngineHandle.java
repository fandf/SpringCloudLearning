package com.fandf.demo.design.组合模式.service.engine.impl;

import com.fandf.demo.design.组合模式.model.aggregates.TreeRich;
import com.fandf.demo.design.组合模式.model.vo.EngineResult;
import com.fandf.demo.design.组合模式.model.vo.TreeNode;
import com.fandf.demo.design.组合模式.service.engine.EngineBase;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022-9-19 18:04
 */
public class TreeEngineHandle extends EngineBase {

    @Override
    public EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter) {
        // 决策流程
        TreeNode treeNode = engineDecisionMaker(treeRich, treeId, userId, decisionMatter);
        // 决策结果
        return new EngineResult(userId, treeId, treeNode.getTreeNodeId(), treeNode.getNodeValue());
    }
}
