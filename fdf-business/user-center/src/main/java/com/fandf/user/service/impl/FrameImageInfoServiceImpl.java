package com.fandf.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fandf.user.mapper.FrameImageInfoMapper;
import com.fandf.user.model.FrameImageInfo;
import org.springframework.stereotype.Service;

@Service
public class FrameImageInfoServiceImpl extends ServiceImpl<FrameImageInfoMapper, FrameImageInfo> {

    /**
     * 增加性上传
     *
     * @param entity
     * @throws Exception
     */
    public void addFrameImageInfo(FrameImageInfo entity) {
        FrameImageInfo frameImageInfo = getFrameImageInfo(entity.getId());
        if (frameImageInfo == null) {
            baseMapper.insert(entity);
        }
    }

    public FrameImageInfo getFrameImageInfo(String id) {
        return baseMapper.selectById(id);
    }



}
