package com.fandf.test.redis;

import java.util.BitSet;

/**
 * java布隆过滤器
 *
 * @author fandongfeng
 */
public class MyBloomFilter {

    /**
     * 位数组大小
     */
    private static final int DEFAULT_SIZE = 2 << 24;

    /**
     * 通过这个数组创建多个Hash函数
     */
    private static final int[] SEEDS = new int[]{4, 8, 16, 32, 64, 128, 256};

    /**
     * 初始化位数组，数组中的元素只能是 0 或者 1
     */
    private final BitSet bits = new BitSet(DEFAULT_SIZE);

    /**
     * Hash函数数组
     */
    private final MyHash[] myHashes = new MyHash[SEEDS.length];

    /**
     * 初始化多个包含 Hash 函数的类数组，每个类中的 Hash 函数都不一样
     */
    public MyBloomFilter() {
        // 初始化多个不同的 Hash 函数
        for (int i = 0; i < SEEDS.length; i++) {
            myHashes[i] = new MyHash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    /**
     * 添加元素到位数组
     */
    public void add(Object value) {
        for (MyHash myHash : myHashes) {
            bits.set(myHash.hash(value), true);
        }
    }

    /**
     * 判断指定元素是否存在于位数组
     */
    public boolean contains(Object value) {
        boolean result = true;
        for (MyHash myHash : myHashes) {
            result = result && bits.get(myHash.hash(value));
        }
        return result;
    }

    /**
     * 自定义 Hash 函数
     */
    private class MyHash {
        private int cap;
        private int seed;

        MyHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 计算 Hash 值
         */
        int hash(Object obj) {
            return (obj == null) ? 0 : Math.abs(seed * (cap - 1) & (obj.hashCode() ^ (obj.hashCode() >>> 16)));
        }
    }

    public static void main(String[] args) {
        String str = "好好学技术";
        MyBloomFilter myBloomFilter = new MyBloomFilter();
        System.out.println("str是否存在：" + myBloomFilter.contains(str));
        myBloomFilter.add(str);
        System.out.println("str是否存在：" + myBloomFilter.contains(str));
    }


}
