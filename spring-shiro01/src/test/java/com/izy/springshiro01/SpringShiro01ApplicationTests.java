package com.izy.springshiro01;

import com.alibaba.fastjson.JSON;
import com.izy.springshiro01.pojo.User;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringShiro01ApplicationTests {
    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;
    @Test
    public void contextLoads() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("zy_index");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    public void contextLoads1() throws IOException {
        //删除索引
        DeleteIndexRequest request1 = new DeleteIndexRequest("zy_index");
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request1, RequestOptions.DEFAULT);
    }


    @Test
    public void index() throws IOException {
        User user = new User(1,"邹宇","aaa","add");

        IndexRequest request = new IndexRequest("zy_index");
        request.timeout("1s");
        request.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status().getStatus());

    }

    @Test
    public void get() throws IOException {

        GetRequest getRequest = new GetRequest("zy_index", "cHaDanIBFF8ZLr_zRiiM");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSource().toString());
    }

    @Test
    public void blukInsert() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<User> userList = new ArrayList<>();
        userList.add(new User(1,"邹宇","aaa","add"));
        userList.add(new User(2,"邹宇2","aaa2","delete"));
        userList.add(new User(3,"邹宇3","aaa3","update"));
        userList.add(new User(4,"邹宇4","aaa4","select"));
        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest("zy_index")
                    .id(""+(i+1))
                    .source(JSON.toJSONString(userList.get(i)),XContentType.JSON));
        }
        bulkRequest.timeout("2m");
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }
}
