package com.fandf.common.redis.lock;

import com.fandf.common.constant.CommonConstant;
import com.fandf.common.exception.LockException;
import com.fandf.common.lock.DistributedLock;
import com.fandf.common.lock.ZLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author fandongfeng
 * @date 2022/6/29 16:08
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "fdf.lock", name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {

    @Resource
    private RedissonClient redisson;

    private ZLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        } else {
            lock =  redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        }
        return new ZLock(lock, this);
    }

    /**
     * 读写锁
     * @param key
     * @return
     */
    private ZLock getReadWriteLock(String key) {
        RReadWriteLock lock = redisson.getReadWriteLock(CommonConstant.LOCK_KEY_PREFIX + ":" + key);
        return new ZLock(lock, this);
    }

    @Override
    public ZLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        ZLock zLock = getLock(key, isFair);
        RLock lock = (RLock)zLock.getLock();
        lock.lock(leaseTime, unit);
        return zLock;
    }

    /**
     *
     * @param key 锁的key
     * @param waitTime 获取锁的最大尝试时间(单位 {@code unit})
     * @param leaseTime 加锁的时间，超过这个时间后锁便自动解锁；
     *                  如果leaseTime为-1，则保持锁定直到显式解锁
     * @param unit {@code waitTime} 和 {@code leaseTime} 参数的时间单位
     * @param isFair 公平锁
     * @return
     * @throws Exception
     */
    @Override
    public ZLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        ZLock zLock = getLock(key, isFair);
        RLock lock = (RLock)zLock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return zLock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
