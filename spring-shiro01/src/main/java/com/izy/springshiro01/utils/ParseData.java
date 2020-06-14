package com.izy.springshiro01.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author zouyu
 * @description
 * @date 2020/6/1
 */
@Component
public class ParseData {

    @Autowired
    RestHighLevelClient restHighLevelClient;


    //构建索引数据
    public boolean parseContent(String keyWord) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        List<?> data = new HtmlParseUtil().getData(keyWord);
        for (int i = 0; i < data.size(); i++) {
            bulkRequest.add(new IndexRequest("jd_index").source(JSON.toJSONString(data.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }
}
