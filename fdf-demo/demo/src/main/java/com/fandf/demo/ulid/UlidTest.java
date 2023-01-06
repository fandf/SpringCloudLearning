package com.fandf.demo.ulid;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.IdUtil;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

/**
 * @author fandongfeng
 * @date 2023-1-6 18:12
 */
public class UlidTest {

    public static void main(String[] args) {
//        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
//        Long next = snowflakeGenerator.next();
//        System.out.println(next);
//
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        System.out.println(snowflakeNextId);

        Ulid ulid = UlidCreator.getUlid();
        System.out.println(ulid.getLeastSignificantBits());
        System.out.println(ulid.getMostSignificantBits());
        Ulid monotonicUlid = UlidCreator.getMonotonicUlid();
        System.out.println(monotonicUlid);

    }

}
