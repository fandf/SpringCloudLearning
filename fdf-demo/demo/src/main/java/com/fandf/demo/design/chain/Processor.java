package com.fandf.demo.design.chain;

public interface Processor {
    boolean process(Product request, ProcessorChain chain);
}