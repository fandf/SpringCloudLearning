package com.fandf.storage.controller;

import com.fandf.storage.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fandongfeng
 * @date 2022/6/28 14:41
 */
@RestController
@RequestMapping("storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 减库存
     * @param commodityCode 商品代码
     * @param count 数量
     */
    @GetMapping("/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }
}
