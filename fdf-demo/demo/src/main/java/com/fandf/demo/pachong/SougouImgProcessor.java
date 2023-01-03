package com.fandf.demo.pachong;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SougouImgProcessor {

    private String url;
    private SougouImgPipeline pipeline;
    private List<JSONObject> dataList;
    private List<String> urlList;
    private String word;

    public SougouImgProcessor(String url,String word) {
        this.url = url;
        this.word = word;
        this.pipeline = new SougouImgPipeline();
        this.dataList = new ArrayList<>();
        this.urlList = new ArrayList<>();
    }

    public void process(int idx, int size) {
        String res = HttpClientUtils.get(String.format(this.url, idx, size, this.word));
        JSONObject object = JSONObject.parseObject(res);
        List<JSONObject> items = (List<JSONObject>)((JSONObject)object.get("data")).get("items");
        for(JSONObject item : items){
            this.urlList.add(item.getString("picUrl"));
        }
        this.dataList.addAll(items);
    }

    // 下载
    public void pipelineData(){
        // 多线程
        pipeline.processSync(this.urlList, this.word);
    }


    public static void main(String[] args) {
        String url = "https://pic.sogou.com/napi/pc/searchList?mode=1&start=%s&xml_len=%s&query=%s";
        SougouImgProcessor processor = new SougouImgProcessor(url,"美女");

        int start = 0, size = 50, limit = 1000; // 定义爬取开始索引、每次爬取数量、总共爬取数量

        for(int i=start;i<start+limit;i+=size) {
            processor.process(i, size);
        }

        processor.pipelineData();

    }

}