package com.fandf.demo.design.组合模式.model.aggregates;

import com.fandf.demo.design.组合模式.model.vo.TreeNode;
import com.fandf.demo.design.组合模式.model.vo.TreeRoot;

import java.util.Map;

/**
 * 规则树聚合
 * @author fandongfeng
 * @date 2022-9-19 18:03
 */
public class TreeRich {

    private TreeRoot treeRoot;                          //树根信息
    private Map<String, TreeNode> treeNodeMap;        //树节点ID -> 子节点

    public TreeRich(TreeRoot treeRoot, Map<String, TreeNode> treeNodeMap) {
        this.treeRoot = treeRoot;
        this.treeNodeMap = treeNodeMap;
    }

    public TreeRoot getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRoot treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<String, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<String, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }

}
