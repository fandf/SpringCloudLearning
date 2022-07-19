package com.fandf.user.service;

import com.fandf.common.model.PageResult;
import com.fandf.user.model.TableVO;

import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/18 10:17
 */
public interface ISystemService {

    PageResult<TableVO> pageTable(Map<String, Object> params);
}
