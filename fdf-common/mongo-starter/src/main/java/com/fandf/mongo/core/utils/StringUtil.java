
package com.fandf.mongo.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class StringUtil {
    
    /**
     * Check if a string is empty
     * @param s
     * @return 
     */
    public static boolean isBlank(String s){
        return s == null || s.trim().length() == 0;
    }
    
    /**
     * Encrypt string s with MD5.
     * @param s
     * @return 
     */
    public static String encodeMD5(String s){
        if(isBlank(s)){
            return null;
        }
        return encodeMD5(s.getBytes());
    }
    
    public static String encodeMD5(byte[] bytes){
        if(bytes == null || bytes.length == 0){
            return null;
        }
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException ex) {
            //just ignore ex
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4',
                             '5', '6', '7', '8', '9',
                             'A', 'B', 'C', 'D', 'E', 'F' };
        md.update(bytes);
        byte[] datas = md.digest();
        int len = datas.length;
        char str[] = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = datas[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    
}
