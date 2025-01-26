package com.fandf.generator.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fandf.common.model.PageResult;
import com.fandf.generator.service.SysGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SysGeneratorControllerTest {

    @Resource
    SysGeneratorController sysGeneratorController;
    @Resource
    SysGeneratorService sysGeneratorService;

    @Test
    public void test() {
        List<String> names = new ArrayList<>();
        PageResult result = sysGeneratorController.getTableList(1, 50, null);
        List tableList = result.getData();
        for (Object o : tableList) {
            String tableName = JSONUtil.parseObj(o).getStr("tableName");
            names.add(tableName);
        }
        System.out.println("=============================================================================");
        System.out.println("表总数："+names.size());
        String tables = StrUtil.join(",", names);
        System.out.println(tables);
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));
        FileUtil.writeBytes(data, "E:\\aaa\\generatorCode.zip");
    }

}