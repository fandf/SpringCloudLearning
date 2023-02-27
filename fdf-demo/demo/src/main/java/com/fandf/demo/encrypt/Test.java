package com.fandf.demo.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fandongfeng
 */
public class Test {

    public static void main(String[] args) {
        String sendMesg = "天王盖地虎，小鸡炖蘑菇";
        System.out.println("需要加密传输的数据（sendMesg）为：" + sendMesg);
        // 用于封装 RSA 随机产生的公钥与私钥
        Map<Integer, String> keyMap = new HashMap<Integer, String>();

        /** AES：随机生成加密秘钥 */
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretKey key = keyGen.generateKey();
        String AESKeyStr = Base64.encodeBase64String(key.getEncoded());

        System.out.println("随机生成的AES加密秘钥（AESKeyStr）为：" + AESKeyStr);

        /** RSA：生成公钥和私钥 */
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 初始化密钥对生成器，指定位数，不指定种子
        keyPairGen.initialize(2048, new SecureRandom());

        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到 RSA 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到 RSA 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 得到公钥字符串
        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        System.out.println("随机生成的公钥（publicKey）为：" + publicKeyStr);
        // 得到私钥字符串
        String privateKeyStr = new String(Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("随机生成的私钥（privateKey）为：" + privateKeyStr);

        // 1.客户端：AES秘钥key加密数据
        String encryptData = AESUtil.encrypt(AESKeyStr, sendMesg, "UTF-8");
        System.out.println("AES秘钥key加密后的字符串为：" + encryptData);
        // 2.客户端：RSA公钥加密AES秘钥key（keyEn）
        String encryptKey = null;
        try {
            encryptKey = RSAUtil.encrypt(AESKeyStr, publicKeyStr);
            System.out.println("RSA加密后的AES秘钥key为：" + encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("==============================客户端 将AES秘钥key加密后的数据  和  RSA加密后的AES秘钥 发送给服务端");

        System.out.println("============================== 服务端收到数据，开始工作");
        // 3.服务端：RSA私钥解密AES秘钥key（keyEn）
        String aeskey = null;
        try {
            aeskey = RSAUtil.decrypt(encryptKey, privateKeyStr);
            System.out.println("服务端，RSA解密后的AES秘钥key为：" + aeskey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 4.服务端：AES秘钥key解密数据
        String data = AESUtil.decrypt(aeskey, encryptData, "UTF-8");
        System.out.println("服务端，AES秘钥key解密后的字符串为：" + data);
    }

}
