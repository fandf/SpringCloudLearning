package com.fandf.demo.design.chain;

import java.util.ArrayList;
import java.util.List;
 
public class ProcessorChain{
 
    // 保存处理节点
    private List<Processor> processorList = new ArrayList<>();
    
    // 处理节点下标
    private int index = 0;
 
    // 动态扩展处理节点
    public ProcessorChain addProcessor(Processor processor) {
        processorList.add(processor);
        return this;
    }
 
    // 获取处理器处理
    public boolean process(Product product, ProcessorChain chain) {
        if(index == processorList.size()) {
            return true;
        }
        Processor processor = processorList.get(index);
        index++;
        return processor.process(product, chain);
    }
 
}