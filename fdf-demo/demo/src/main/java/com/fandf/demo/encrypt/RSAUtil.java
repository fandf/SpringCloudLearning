package com.fandf.demo.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SHA256withRSA 工具类
 */
@SuppressWarnings("all")
public class RSAUtil {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    // MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
    // 128 对应 1024，256对应2048
    private static final int KEYSIZE = 2048;
    // RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    // RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    // 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    // 默认种子
    public static final String DEFAULT_SEED = "$%^*%^()(ED47d784sde78";
    // 编码格式
    private static final String CODE_FORMATE_UTF8 = "UTF-8";

    // - - - - - - - - - - - - - - - - - - - - RSA 生成秘钥对 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 生成密钥对：Base64 转码的字符串
     *
     * @param key
     * @return
     */
    public static Map<String, String> initKeyBase64Str() throws Exception {
        Map<String, String> map = new HashMap<>(2);
        Map<String, Key> keyMap = initKey();
        PublicKey publicKey = (PublicKey) keyMap.get("PublicKey");
        PrivateKey privateKey = (PrivateKey) keyMap.get("PrivateKey");
        map.put("PublicKey", new String(Base64.encodeBase64(publicKey.getEncoded())));
        map.put("PrivateKey", new String(Base64.encodeBase64(privateKey.getEncoded())));
        logger.info("生成密钥 = " + JSON.toJSONString(map));
        return map;
    }

    /**
     * 生成默认密钥
     *
     * @return 密钥对象
     */
    public static Map<String, Key> initKey() throws Exception {
        return initKey(DEFAULT_SEED);
    }

    /**
     * 生成密钥对：若seed为null，那么结果是随机的；若seed不为null且固定，那么结果也是固定的；
     *
     * @param seed 种子
     * @return 密钥对象
     */
    public static Map<String, Key> initKey(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 如果指定seed，那么secureRandom结果是一样的，所以生成的公私钥也永远不会变
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        // Modulus size must range from 512 to 1024 and be a multiple of 64
        keygen.initialize(KEYSIZE, secureRandom);

        // 生成一个密钥对，保存在keyPair中
        KeyPair keys = keygen.genKeyPair();
        PublicKey publicKey = keys.getPublic();
        PrivateKey privateKey = keys.getPrivate();

        // 将公钥和私钥保存到Map
        Map<String, Key> map = new HashMap<>(2);
        map.put("PublicKey", publicKey);
        map.put("PrivateKey", privateKey);
        logger.info("生成密钥 = " + JSON.toJSONString(map));
        return map;
    }

    // - - - - - - - - - - - - - - - - - - - - RSA 加密、解密 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 获取公钥 PublicKey 信息
     *
     * @param 公钥
     * @return
     */
    public static PublicKey getPublicKey(String pubKeyStr) throws Exception {
        byte[] publicKeys = Base64.decodeBase64(pubKeyStr);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeys);
        KeyFactory mykeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = mykeyFactory.generatePublic(publicKeySpec);
        logger.info("传入的公钥为：【" + pubKeyStr + "】，转义后的公钥为：【" + publicKey + "】");
        return publicKey;
    }

    /**
     * 公钥加密，指定 RSA 方式的 PublicKey 对象
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(CODE_FORMATE_UTF8)));
        return outStr;
    }

    /**
     * 公钥加密，任意 PublicKey 对象
     *
     * @param publicKey
     * @param encryptData
     * @param encode
     */
    public static String encrypt(PublicKey publicKey, String encryptData, String encode) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空，请设置。");
        }
        try {
            final Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(encryptData.getBytes(encode));
            return Base64.encodeBase64String(output);
        } catch (Exception e) {
            logger.info("加密异常:" + e.getMessage());
            return null;
        }
    }

    /**
     * 私钥解密，指定 RSA 方式的 PrivateKey 对象
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(CODE_FORMATE_UTF8));
        // base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * RSA 公钥加密，【限制长度】
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encryptByPublicKey(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes));
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] data = str.getBytes("UTF-8");
        // 加密时超过117字节就报错。为此采用分段加密的办法来加密
        byte[] enBytes = null;
        for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
            // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_ENCRYPT_BLOCK));
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }
        String outStr = encryptBASE64(enBytes);
        return outStr;
    }

    /**
     * RSA 私钥解密，【限制长度】
     *
     * @param encryStr   加密字符串
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decryptByPrivateKey(String encryStr, String privateKey) throws Exception {
        // base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 64位解码加密后的字符串
        byte[] data = decryptBASE64(encryStr);
        // 解密时超过128字节报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal));
        }
        return sb.toString();
    }

    /**
     * BASE64Encoder 加密
     *
     * @param data 要加密的数据
     * @return 加密后的字符串
     */
    private static String encryptBASE64(byte[] data) {
        return new String(Base64.encodeBase64(data));
    }

    /**
     * BASE64Encoder 解密
     *
     * @param data 要解密的数据
     * @return 解密后的字节
     */
    private static byte[] decryptBASE64(String data) {
        return Base64.decodeBase64(data);
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 签名，验签 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 加签：生成报文签名
     *
     * @param content    报文内容
     * @param privateKey 私钥
     * @param encode     编码
     * @return
     */
    public static String doSign(String content, String privateKey, String encode) {
        try {
            String unsign = Base64.encodeBase64String(content.getBytes(StandardCharsets.UTF_8));
            byte[] privateKeys = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeys);
            KeyFactory mykeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey psbcPrivateKey = mykeyFactory.generatePrivate(privateKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(psbcPrivateKey);
            signature.update(unsign.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            logger.error("生成报文签名出现异常");
        }
        return null;
    }

    /**
     * 验证：验证签名信息
     *
     * @param content   签名报文
     * @param signed    签名信息
     * @param publicKey 公钥
     * @param encode    编码格式
     * @return
     */
    public static boolean doCheck(String content, String signed, PublicKey publicKey, String encode) {
        try {
            // 解密之前先把content明文，进行base64转码
            String unsigned = Base64.encodeBase64String(content.getBytes(encode));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(unsigned.getBytes(encode));
            boolean bverify = signature.verify(Base64.decodeBase64(signed));
            return bverify;
        } catch (Exception e) {
            logger.error("报文验证签名出现异常");
        }
        return false;
    }
}