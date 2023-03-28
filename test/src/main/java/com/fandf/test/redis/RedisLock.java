package com.fandf.test.redis;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * redis 单机锁
 *
 * @author fandongfeng
 * @date 2023/3/29 06:52
 */
@Slf4j
@Service
public class RedisLock {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private static final String SELL_LOCK = "sell:";

    /**
     * 模拟卖货
     *
     * @return 是否成功
     */
    public String sell() {
        String productId = "123";
        String key = SELL_LOCK + productId;
        //锁value,解锁时 用来判断当前锁是否是自己加的
        String value = IdUtil.fastSimpleUUID();
        //加锁 十秒钟过期 防死锁
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value, 10, TimeUnit.SECONDS);
        if (!flag) {
            log.info("加锁失败");
            return "加锁失败";
        }
        try {
            //获取商品库存
            Integer stock = (Integer) redisTemplate.opsForValue().get(productId);
            if (stock == null || stock <= 0) {
                return "卖完了，下次早点来吧";
            }
            //扣减库存, 模拟随机卖出数量
            int randomInt = RandomUtil.randomInt(5);
            redisTemplate.opsForValue().set(key, stock - randomInt);
            return "成功卖出" + randomInt + "个，库存剩余" + (stock - randomInt) + "个";
        } finally {

//            //这种方法会存在删除别人加的锁的可能
//            redisTemplate.delete(key);

//            if(value.equals(redisTemplate.opsForValue().get(key))){
//                //因为if条件的判断和 delete不是原子性的，
//                //if条件判断成功后，恰好锁到期自己解锁
//                //此时别的线程如果持有锁了，就会把别人的锁删除掉
//                redisTemplate.delete(key);
//            }

            //使用lua脚本保证判断和删除的原子性
            String luaScript =
                    "if (redis.call('get',KEYS[1]) == ARGV[1]) then " +
                            "return redis.call('del',KEYS[1]) " +
                            "else " +
                            "return 0 " +
                            "end";
            redisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class), Collections.singletonList(key), value);
        }
    }


}
