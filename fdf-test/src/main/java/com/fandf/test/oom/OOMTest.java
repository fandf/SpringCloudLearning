package com.fandf.test.oom;

import java.util.HashSet;
import java.util.Set;

public class OOMTest {

    /**
     * 静态变量，保存在堆中,防止被回收
     */
//    public static List<Object> list = new ArrayList<>();

    /**
     * JVM设置, -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=G:\dump.hprof 内存溢出前导出文件
     * -Xms5M -Xmx5M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=G:\dump.hprof
     */
    public static void main(String[] args) throws InterruptedException {
//        int a = 0;
//        int b = 0;
//        while (true) {
//            Thread.sleep(100);
////            list.add(new User(a++, UUID.randomUUID().toString()));
//            new User(b--, UUID.randomUUID().toString());
//        }

        String a = "123";
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add(a);
        set2.add(a);
        System.out.println(set1.containsAll(set2));


    }
}