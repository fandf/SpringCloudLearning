package com.fandf.demo.dataarchive.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fandf.demo.dataarchive.service.ArchiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fandongfeng
 * @date 2022-9-14 13:48
 */
@RestController
@RequestMapping("archive")
public class ArchiveApi {

    @Resource
    ArchiveService archiveService;

    @GetMapping
    public String saveArchive() {

        List<String> results = archiveService.saveArchive();

        if (CollUtil.isNotEmpty(results)) {
            results.forEach(System.out::println);
            return StrUtil.join(";", results);
        }
        return "success";
    }


}
