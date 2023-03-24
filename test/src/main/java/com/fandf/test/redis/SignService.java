package com.fandf.test.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.fandf.common.model.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author fandongfeng
 */
@Service
public class SignService {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    private static final String USER_SIGN_KEY = "sign:";


    /**
     * 当日签到接口
     * <p>
     * #向指定位置（offset）存入0或1
     * SETBIT key offset value
     * <p>
     * #获取指定位置（offset）的bit值
     * GETBIT key offset
     * <p>
     * #统计bitmap中(从start到end，如果不写起始位置，就统计整个key)值为1的bit数量
     * BITCOUNT key [start end]
     * <p>
     * #操作（查询，修改，自增）Bitmap中指定的位置（offset）的值
     * BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]
     * <p>
     * #获取Bitmap中的bit数组，并以十进制返回
     * BITFIELD_RO
     */
    public Boolean sign() {
        // 1.获取当前登录用户
        long userId = 123L;
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = DateUtil.format(now, ":yyyyMM");
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //bitmap 坐标从0开始， 所以 offset 为 dayOfMonth - 1
        redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return true;
    }

    /**
     * 指定日期签到
     */
    public Boolean signDate(String date) {
        // 1.获取当前登录用户
        long userId = 123L;
        // 2.获取日期
        LocalDateTime signDate = Convert.toLocalDateTime(date);
        // 3.拼接key
        String keySuffix = DateUtil.format(signDate, ":yyyyMM");
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = signDate.getDayOfMonth();
        //bitmap 坐标从0开始， 所以 offset 为 dayOfMonth - 1
        redisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return true;
    }

    /**
     * 当月连续签到次数  BITFIELD key GET u[dayOfMonth] 0
     */
    public Integer signCount() {
        // 1.获取当前登录用户
        long userId = 123L;
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = DateUtil.format(now, ":yyyyMM");
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.获取本月截止今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:123:202303 GET u24 0
        List<Long> result = redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        if (result == null || result.isEmpty()) {
            // 没有任何签到结果
            return 0;
        }
        Long num = result.get(0);
        if (num == null || num == 0) {
            return 0;
        }
        // 6.循环遍历
        int count = 0;
        while (true) {
            // 6.1.让这个数字与1做与运算，得到数字的最后一个bit位
            // 判断这个bit位是否为0
            if ((num & 1) == 0) {
                // 如果为0，说明未签到，结束
                break;
            } else {
                // 如果不为0，说明已签到，计数器+1
                count++;
            }
            // 把数字右移一位，抛弃最后一个bit位，继续下一个bit位
            num >>>= 1;
        }
        return count;
    }


    /**
     * 统计指定月总共签到次数
     */
    public Integer signCountByMonth(String date) {
        long userId = 123L;
        LocalDateTime dateOfSign = Convert.toLocalDateTime(date);
        String keySuffix = DateUtil.format(dateOfSign, ":yyyyMM");
        String key = USER_SIGN_KEY + userId + keySuffix;
        Long count = redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.bitCount(key.getBytes()));
        return count == null ? 0 : count.intValue();
    }

}
