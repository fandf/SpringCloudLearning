package com.fandf.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fandf.common.model.PageResult;
import com.fandf.file.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

/**
 * 文件service
 *
 * @author dongfengfan
*/
public interface IFileService extends IService<FileInfo> {
	FileInfo upload(MultipartFile file ) throws Exception;
	
	PageResult<FileInfo> findList(Map<String, Object> params);

	void delete(String id);

	void out(String id, OutputStream os);
}
