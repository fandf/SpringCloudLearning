package com.fandf.test.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author fandongfeng
 */
public class GuavaBloomFilter {

    public static void main(String[] args) {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),100000,0.01);
        bloomFilter.put("好好学技术");
        System.out.println(bloomFilter.mightContain("不好好学技术"));
        System.out.println(bloomFilter.mightContain("好好学技术"));
    }
}
