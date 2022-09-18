package com.fandf.demo.design.责任链;

public interface Processor {
    boolean process(Product request, ProcessorChain chain);
}