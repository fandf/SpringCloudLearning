package com.fandf.demo.tuomin;

import lombok.Getter;

/**
 * 隐私数据类型枚举
 *
 * @author fandongfeng
 * @date 2023-1-4 10:11
 */
@Getter
public enum PrivacyTypeEnum {
    /**
     * 自定义（此项需设置脱敏的范围）
     */
    CUSTOMER,

    /**
     * 姓名
     */
    NAME,

    /**
     * 身份证号
     */
    ID_CARD,

    /**
     * 手机号
     */
    PHONE,

    /**
     * 邮箱
     */
    EMAIL,
}
