package com.fandf.common.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fandf
 * @date 2022/6/27 11:54
 */
@AllArgsConstructor
public class ZLock implements AutoCloseable{
    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}
