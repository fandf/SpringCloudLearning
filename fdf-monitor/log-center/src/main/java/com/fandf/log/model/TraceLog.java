package com.fandf.log.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志链路对象
 *
 * @author fandongfeng
 * @date 2022/7/12 12:05
 */
@Setter
@Getter
public class TraceLog {
    private String spanId;
    private String parentId;
    private String appName;
    private String serverIp;
    private String serverPort;
}
