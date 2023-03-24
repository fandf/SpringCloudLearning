package com.fandf.test.oom;

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
     * 静态变量，保存在堆中,防止被回收
     */
    public static List<Object> list = new ArrayList<>();

    /**
     * JVM设置, -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=G:\dump.hprof 内存溢出前导出文件
     * -Xms5M -Xmx5M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=G:\dump.hprof
     */
    public static void main(String[] args) {
        int a = 0;
        int b = 0;
        while (true) {
            list.add(new User(a++, UUID.randomUUID().toString()));
            new User(b--, UUID.randomUUID().toString());
        }
    }
}