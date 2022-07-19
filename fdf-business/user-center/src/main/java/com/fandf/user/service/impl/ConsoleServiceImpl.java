package com.fandf.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fandf.user.mapper.AppPointMapper;
import com.fandf.user.mapper.UserInfoMapper;
import com.fandf.user.model.AppPoint;
import com.fandf.user.model.ConsoleDateVO;
import com.fandf.user.model.UserInfo;
import com.fandf.user.service.IConsoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author fandongfeng
 * @date 2022/7/18 15:10
 */
@Service
public class ConsoleServiceImpl implements IConsoleService {

    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    AppPointMapper appPointMapper;

    @Override
    public Map<String, Object> requestStat() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("userCnt", userInfoMapper.selectCount(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getFlag, 0)));
        //天pv, uv
        String date = DateUtil.formatDate(new Date()) + " 00:00:00";
        Long count = appPointMapper.selectCount(new LambdaQueryWrapper<AppPoint>().ge(AppPoint::getCreated, date));
        map.put("currDate_pv", count == null ? 0 : count / 2);
        map.put("currDate_uv", appPointMapper.selectCount(new QueryWrapper<AppPoint>().select("distinct token").ge("created", date)));
        //周、月pv
        String week = DateUtil.formatDate(DateUtil.offsetWeek(new Date(), -1)) + " 00:00:00";
        Long weekCount = appPointMapper.selectCount(new LambdaQueryWrapper<AppPoint>().ge(AppPoint::getCreated, week));
        map.put("currWeek_pv", weekCount == null ? 0 : weekCount / 2);
        String month = DateUtil.formatDate(DateUtil.offsetMonth(new Date(), -1)) + " 00:00:00";
        Long monthCount = appPointMapper.selectCount(new LambdaQueryWrapper<AppPoint>().ge(AppPoint::getCreated, month));
        map.put("currMonth_pv", monthCount == null ? 0 : monthCount / 2);

        //日活
        Date dateTime = DateUtil.offsetHour(new Date(), -25);
        List<ConsoleDateVO> dayPvList = appPointMapper.getDayPv(dateTime);
        List<ConsoleDateVO> dayUvList = appPointMapper.getDayUv(dateTime);
        Map<String, Object> dayPvMap = new HashMap<>(8);
        if (CollUtil.isNotEmpty(dayPvList)) {
            dayPvList.forEach(d -> dayPvMap.put(d.getDateStr(), d.getCount()));
        }
        Map<String, Object> dayUvMap = new HashMap<>(8);
        if (CollUtil.isNotEmpty(dayUvList)) {
            dayUvList.forEach(d -> dayUvMap.put(d.getDateStr(), d.getCount()));
        }
        List<String> dateItems = new ArrayList<>();
        List<Integer> datePv = new ArrayList<>();
        List<Integer> dateUv = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            dateTime = DateUtil.offsetHour(dateTime, 1);
            String time = getTimeByDatetimeStr(DateUtil.formatDateTime(dateTime));
            dateItems.add(time);
            datePv.add(MapUtil.getInt(dayPvMap, time, 0));
            dateUv.add(MapUtil.getInt(dayUvMap, time, 0));
        }
        map.put("statDate_items", dateItems.toArray());
        map.put("statDate_pv", datePv.toArray());
        map.put("statDate_uv", dateUv.toArray());

        //周活
        Date dateWeek = DateUtil.offsetDay(new Date(), -8);
        List<ConsoleDateVO> weekPvList = appPointMapper.getWeekPv(dateWeek);
        List<ConsoleDateVO> weekUvList = appPointMapper.getWeekUv(dateWeek);
        Map<String, Object> weekPvMap = new HashMap<>(8);
        if (CollUtil.isNotEmpty(weekPvList)) {
            weekPvList.forEach(d -> {
                weekPvMap.put(d.getDateStr(), d.getCount());
            });
        }
        Map<String, Object> weekUvMap = new HashMap<>(8);
        if (CollUtil.isNotEmpty(weekUvList)) {
            weekUvList.forEach(d -> {
                weekUvMap.put(d.getDateStr(), d.getCount());
            });
        }
        List<String> weekItems = new ArrayList<>();
        List<Integer> weekPv = new ArrayList<>();
        List<Integer> weekUv = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dateWeek = DateUtil.offsetDay(dateWeek, 1);
            String formatDate = DateUtil.formatDate(dateWeek);
            weekItems.add(formatDate);
            weekPv.add(MapUtil.getInt(weekPvMap, formatDate, 0));
            weekUv.add(MapUtil.getInt(weekUvMap, formatDate, 0));
        }
        map.put("statWeek_items", weekItems.toArray());
        map.put("statWeek_pv", weekPv.toArray());
        map.put("statWeek_uv", weekUv.toArray());
        //手机型号
        List<Map<String, Integer>> osvList = userInfoMapper.getOsv();
        if(CollUtil.isNotEmpty(osvList)) {
            JSONArray mobileData = new JSONArray();
            Set<String> mobileLegendData = new HashSet<>();
            int other = 0;
            for (Map<String, Integer> osvMap : osvList) {
                Integer cnt = MapUtil.getInt(osvMap, "cnt", 0);
                if(cnt >= 10) {
                    JSONObject json = new JSONObject();
                    json.set("name", MapUtil.getStr(osvMap, "osv"));
                    json.set("value", cnt);
                    mobileData.add(json);
                    mobileLegendData.add(MapUtil.getStr(osvMap, "osv"));
                }else {
                    other += cnt;
                    mobileLegendData.add("其他");
                }
            }
            if(other > 0){
                JSONObject json = new JSONObject();
                json.set("name", "其他");
                json.set("value", other);
                mobileData.add(json);
            }
            map.put("mobile_datas", mobileData);
            map.put("mobile_legendData", mobileLegendData);
        }
        return map;
    }

    /**
     * 2020-03-10 01:30:00 获取时间值：03-10 01:00
     *
     * @return
     */
    private String getTimeByDatetimeStr(String datetimeStr) {
        if (StrUtil.isNotEmpty(datetimeStr)) {
            return datetimeStr.substring(5, 14) + "00";
        }
        return "";
    }

}


