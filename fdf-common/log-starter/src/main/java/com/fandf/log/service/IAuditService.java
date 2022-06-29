package com.fandf.log.service;

import com.fandf.log.model.Audit;

/**
 * 审计对象接口
 * @author fandongfeng
 */
public interface IAuditService {
    /**
     * 保存日志
     * @param audit 审计对象
     */
    void save(Audit audit);
}
