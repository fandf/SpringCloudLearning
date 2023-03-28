package com.fandf.demo.oom;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 内存溢出测试类
 *
 * @author fandongfeng
 * @date 2023/3/5 22:02
 */
public class OOMTest {

    /**
     * JVM设置, -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/dongfengfan/Desktop/jvm.dump 内存溢出前导出文件
     * -Xms5m -Xmx5m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/dongfengfan/Desktop/jvm.dump
     */
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        int a = 0;
        int b = 0;
        while (true) {
            list.add(new User(a++, UUID.randomUUID().toString()));
            new User(b--, UUID.randomUUID().toString());
        }
    }

}
