package com.fandf.oss.config;

import com.fandf.oss.properties.FileServerProperties;
import com.fandf.oss.template.FdfsTemplate;
import com.fandf.oss.template.S3Template;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;


/**
 * @author dongfengfan
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
