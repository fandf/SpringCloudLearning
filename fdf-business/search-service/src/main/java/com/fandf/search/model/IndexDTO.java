package com.fandf.search.model;

import lombok.Data;

/**
 * @author fandongfeng
 * @date 2022/7/10 14:04
 */
@Data
public class IndexDTO {

    /**
     * 索引名
     */
    private String indexName;
    /**
     * 分片数 number_of_shards
     */
    private Integer numberOfShards;
    /**
     * 副本数 number_of_replicas
     */
    private Integer numberOfReplicas;
    /**
     * 类型
     */
    private String type;
    /**
     * mappings内容
     */
    private String mappingsSource;

}
