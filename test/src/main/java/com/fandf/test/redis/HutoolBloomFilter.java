package com.fandf.test.redis;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;

/**
 * @author fandongfeng
 */
public class HutoolBloomFilter {
    public static void main(String[] args) {
        BitMapBloomFilter bloomFilter = BloomFilterUtil.createBitMap(1000);
        bloomFilter.add("好好学技术");
        System.out.println(bloomFilter.contains("不好好学技术"));
        System.out.println(bloomFilter.contains("好好学技术"));
    }

}
