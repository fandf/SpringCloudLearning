package com.fandf.oauth.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/7/11 14:37
 */
@Setter
@Getter
public class TokenVO implements Serializable {

   private static final long serialVersionUID = -6091472832338975238L;
   /**
    * token的值
    */
   private String tokenValue;
   /**
    * 到期时间
    */
   private Date expiration;
   /**
    * 用户名
    */
   private String username;
   /**
    * 所属应用
    */
   private String clientId;
   /**
    * 授权类型
    */
   private String grantType;
   /**
    * 账号类型
    */
   private String accountType;
}
