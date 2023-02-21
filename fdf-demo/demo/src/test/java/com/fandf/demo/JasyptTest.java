package com.fandf.demo;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 */
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JasyptTest {

    @Resource
    private StringEncryptor stringEncryptor;

    /**
     * example:
     *  redis:password= 123
     *  123 加密后的内容：
     * 修改后的配置文件
     *  redis:password= ENC(QvHbj+BCPR/bnSdDuNKzpoB3Kw9Pvm1bEIaSqlD5Ohhp8rPKBUcj0f5V+QqWsPP0)
     * ENC(mVTvp4IddqdaYGqPl9lCQbzM3H/b0B6l)
     */
    @Test
    public void encrypt() {
        String encrypt = stringEncryptor.encrypt("UUID");
        System.out.println(" UUID  加密后的内容：" + encrypt);
        String decrypt = stringEncryptor.decrypt(encrypt);
        System.out.println("解密后的内容：" + decrypt);
    }

}
