package com.fandf.test.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * lru算法demo
 * 最近最少使用算法
 *
 * @author fandongfeng
 * @date 2023/4/2 17:30
 */
@Slf4j
public class LRUCache<K, V> {

    private final Map<K, V> cache = new HashMap<>(8);
    private final LinkedList<K> lruList = new LinkedList<>();
    private final Integer capacity;

    public LRUCache(Integer capacity) {
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        LRUCache<String, Object> cache = new LRUCache<>(2);
        cache.put("第一天", "好好学技术");
        cache.put("第二天", "我想出去玩");
        log.info("第一天数据 => {}", cache.get("第一天"));
        log.info("第二天数据 => {}", cache.get("第二天"));
        cache.put("第三天", "学不动了");
        //这里调用了 cache.get("第二天")，所以第二天变为最新使用过得缓存
        log.info("放入第三天数据，第二天数据 => {}", cache.get("第二天"));
        //这里调用了cache.put("第四天", "马什么梅?");，所以最后只剩了第二天和第四天
        cache.put("第四天", "马什么梅?");
        log.info("放入第四天数据");
        log.info("第一天数据 => {}", cache.get("第一天"));
        log.info("第二天数据 => {}", cache.get("第二天"));
        log.info("第三天数据 => {}", cache.get("第三天"));
        log.info("第四天数据 => {}", cache.get("第四天"));
    }

    /**
     * 获取元素
     *
     * @param k key
     * @return value
     */
    public V get(K k) {
        if (!cache.containsKey(k)) {
            return null;
        }
        V v = cache.get(k);
        // 更新 k 在LRU 队列中的位置
        if (!lruList.getFirst().equals(k)) {
            //移除K，在放入队头
            lruList.remove(k);
            lruList.addFirst(k);
        }
        return v;
    }

    /**
     * 存入元素
     *
     * @param k Key
     * @param v value
     */
    public void put(K k, V v) {
        // 判断容量
        if (lruList.size() >= capacity && !lruList.contains(k)) {
            // 移除最近最少使用的数据
            cache.remove(lruList.getLast());
            lruList.removeLast();
        }
        cache.put(k, v);
        //放入队头
        if (!lruList.contains(k)) {
            lruList.addFirst(k);
        }
    }

}
