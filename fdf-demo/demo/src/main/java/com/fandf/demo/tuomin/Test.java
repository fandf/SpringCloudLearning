package com.fandf.demo.tuomin;

import lombok.Data;

/**
 * @author fandongfeng
 * @date 2023-1-4 10:23
 */
@Data
public class Test {

    @PrivacyEncrypt(type = PrivacyTypeEnum.NAME)
    private String name;
    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL)
    private String email;
    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE)
    private String phone;
    @PrivacyEncrypt(type = PrivacyTypeEnum.ID_CARD)
    private String idCard;

    public static void main(String[] args) {
        Test test = new Test();
        test.setEmail("123@qq.com");
        test.setPhone("17845678945");
        test.setName("张有志");
        test.setIdCard("123456");
        System.out.println(test);
    }

}
