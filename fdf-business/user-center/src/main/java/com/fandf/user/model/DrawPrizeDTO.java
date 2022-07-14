package com.fandf.user.model;

import lombok.Data;

/**
 * @author fandongfeng
 * @date 2022/7/13 23:10
 */
@Data
public class DrawPrizeDTO {

    private Long id;
    private String name;
    private String url;
    private String value;
    private Double probability;
    private Integer type;
    private Integer status;
    private Integer position;
    private Integer dayMaxTimes;
    private Integer monthMaxTimes;
    private Integer show;

}
