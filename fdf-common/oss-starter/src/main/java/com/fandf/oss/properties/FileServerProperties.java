package com.fandf.oss.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author dongfengfan
 */
@Setter
@Getter
@ConfigurationProperties(prefix = FileServerProperties.PREFIX)
public class FileServerProperties {
    public static final String PREFIX = "fandf.file-server";
    public static final String TYPE_FDFS = "fastdfs";
    public static final String TYPE_S3 = "s3";

    /**
     * 为以下2个值，指定不同的自动化配置
     * s3：aws s3协议的存储（七牛oss、阿里云oss、minio等）
     * fastdfs：本地部署的fastDFS
     */
    private String type;

    /**
     * aws s3配置
     */
    S3Properties s3 = new S3Properties();

    /**
     * fastDFS配置
     */
    FdfsProperties fdfs = new FdfsProperties();
}
