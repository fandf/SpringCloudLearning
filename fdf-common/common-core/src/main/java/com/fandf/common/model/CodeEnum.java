package com.fandf.common.model;

/**
 * 统一结果返回码
 * @author fandf
 */
public enum CodeEnum {

    SUCCESS(0),
    ERROR(1);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
