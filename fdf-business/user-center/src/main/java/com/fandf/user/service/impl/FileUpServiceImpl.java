package com.fandf.user.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fandf.user.model.FrameImageInfo;
import com.fandf.user.utils.AwsPathUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fandongfeng
 * @date 2022/7/14 10:43
 */
@Service
public class FileUpServiceImpl {

    @Resource
    private AmazonS3 amazonS3;
    @Value("${s3.file_bucket}")
    private String fileBucket;
    @Value("${s3.url_prefix}")
    private String url;

    @Autowired
    private FrameImageInfoServiceImpl frameImageInfoService;


    public FrameImageInfo uploadAws(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String type = AwsPathUtil.pathFileName(suffix);
        //只能上传图片或者视频
        boolean flag = "photo".equals(type) || "video".equals(type);
        if (!flag) {
            return null;
        }
        byte[] bytes = multipartFile.getBytes();
        String uuid = DigestUtils.md5Hex(bytes);
        String path = type + "/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/";
        String filePath = path + uuid + suffix;
        FrameImageInfo frameImageInfo = frameImageInfoService.getFrameImageInfo(uuid);
        if (frameImageInfo == null) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            try {
                amazonS3.putObject(new PutObjectRequest(fileBucket, filePath, multipartFile.getInputStream(), objectMetadata)
                        //配置文件访问权限
                        .withCannedAcl(CannedAccessControlList.Private));
                Integer height = null;
                Integer width = null;
                if ("photo".equals(type)) {
                    BufferedImage read = ImageIO.read(multipartFile.getInputStream());
                    if (read != null) {
                        width = read.getWidth();
                        height = read.getHeight();
                    }
                }
                FrameImageInfo imageInfo = new FrameImageInfo(uuid, fileName, new BigDecimal(bytes.length),
                        null, type, new Date(), 1, url + filePath, height, width);
                frameImageInfoService.addFrameImageInfo(imageInfo);
                return imageInfo;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return frameImageInfo;
    }

}
