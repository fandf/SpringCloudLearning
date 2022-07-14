package com.fandf.user.utils;

import com.github.xiaoymin.knife4j.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author fandongfeng
 * @date 2021/11/27 下午1:47
 */
public class AwsPathUtil {
    //图片
    private static final List<String> photoList = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "svg");
    //文件
    private static final List<String> fileList = Arrays.asList("iso", "rar", "zip", "pdf", "txt", "doc", "docx", "xls", "xlsx", "ppt", "pptx");
    //视频
    private static final List<String> videoList = Arrays.asList("avi", "mov", "qt", "asf", "rm", "rmvb", "mpeg", "mpg", "mkv", "mp4", "wma");
    //音频
    private static final List<String> audioList = Arrays.asList("mp3", "acm", "aif", "aifc", "aife", "asf", "asp");

    public static String pathFileName(String suffix) {
        if (StrUtil.isBlank(suffix)) {
            return "other";
        }
        suffix = suffix.replace(".", "");
        String path;
        suffix = suffix.toLowerCase();
        if (photoList.contains(suffix)) {
            path = "photo";
        } else if (fileList.contains(suffix)) {
            path = "file";
        } else if (videoList.contains(suffix)) {
            path = "video";
        } else if (audioList.contains(suffix)) {
            path = "audio";
        } else {
            path = "other";
        }
        return path;
    }

}
