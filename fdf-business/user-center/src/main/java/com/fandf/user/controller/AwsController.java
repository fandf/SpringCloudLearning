package com.fandf.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fandf.common.model.Result;
import com.fandf.user.model.AwsUploadVO;
import com.fandf.user.model.FrameImageInfo;
import com.fandf.user.service.impl.FileUpServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author fandongfeng
 * @date 2022/7/14 10:34
 */
@RequestMapping("file")
@Api(tags = "aws管理")
@RestController
public class AwsController {

    @Resource
    private FileUpServiceImpl fileUpService;

    @PostMapping(value = "/upload")
    @ApiOperation(value = "aws上传文件")
    public Result<AwsUploadVO> upload(@RequestParam("file") MultipartFile file) throws IOException {
        FrameImageInfo imageInfo = fileUpService.uploadAws(file);
        AwsUploadVO awsUploadVO = BeanUtil.copyProperties(imageInfo, AwsUploadVO.class);
        awsUploadVO.setName(imageInfo.getFileName());
        return Result.succeed(awsUploadVO);
    }

}
