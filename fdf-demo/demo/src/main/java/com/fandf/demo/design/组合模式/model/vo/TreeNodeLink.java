package com.fandf.demo.design.组合模式.model.vo;

import lombok.Data;

/**
 * @author fandongfeng
 * @date 2022-9-19 18:03
 */
@Data
public class TreeNodeLink {

    private Long nodeIdFrom;        //节点From
    private Long nodeIdTo;          //节点To
    private Integer ruleLimitType;  //限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围]
    private String ruleLimitValue;  //限定值


}
