package com.fandf.db.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.fandf.common.context.TenantContextHolder;
import com.fandf.common.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 多租户自动配置
 * @EnableConfigurationProperties 配置文件转化为bean
 * @author fandf
 * @date 2022/6/27 13:28
 */
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfigure {

    @Autowired
    private TenantProperties tenantProperties;

    @Bean
    public TenantLineHandler tenantLineHandler() {
        return new TenantLineHandler() {
            /**
             * 获取租户id
             */
            @Override
            public Expression getTenantId() {
                String tenant = TenantContextHolder.getTenant();
                if (tenant != null) {
                    return new StringValue(TenantContextHolder.getTenant());
                }
                return new NullValue();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * @param tableName 表名
             */
            @Override
            public boolean ignoreTable(String tableName) {
                return tenantProperties.getIgnoreTables().stream().anyMatch(
                        (e) -> e.equalsIgnoreCase(tableName)
                );
            }
        };
    }

}
