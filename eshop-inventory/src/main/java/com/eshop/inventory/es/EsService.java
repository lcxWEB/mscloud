package com.eshop.inventory.es;

import com.alibaba.fastjson.JSONObject;
import com.eshop.inventory.mapper.UserMapper;
import com.eshop.inventory.model.User;
import com.eshop.inventory.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class EsService {

    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
    @Value("${elasticsearch.ip}")
    private String hostname;
    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.indexName}")
    private String indexName;
    @Value("${elasticsearch.typeName}")
    private String typeName;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    private RestHighLevelClient client;

    public RestHighLevelClient getClient() {
        return client;
    }


    /**
     * @PostContruct是spring框架的注解 spring容器初始化的时候执行该方法
     */
    @PostConstruct
    private void init() {
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//        client = new RestHighLevelClient(RestClient.builder(new HttpHost[]{new HttpHost(hostname, port, "http")})
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
        client = new RestHighLevelClient(RestClient.builder(new HttpHost[]{new HttpHost(hostname, port, "http")}));
    }

    public boolean checkIndexExist() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(new String[]{indexName});
        request.local(false);
        request.humanReadable(true);
        boolean exists = client.indices().exists(request, new Header[0]);
//        log.info("判断索引是否存在,索引状态为:{}", exists);
        return exists;
    }

    public boolean createIndex() {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        try {
            CreateIndexResponse indexResponse = client.indices().create(request, new Header[0]);
            if (indexResponse.isAcknowledged()) {
                log.info("索引：{};创建成功", indexName);
            } else {
                log.info("索引：{};创建失败", indexName);
            }

            return indexResponse.isAcknowledged();
        } catch (IOException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public boolean indexDelete() throws Exception {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, new Header[0]);
            log.info("索引删除,状态为:{}", deleteIndexResponse.isAcknowledged());
            return deleteIndexResponse.isAcknowledged();
        } catch (ElasticsearchException var4) {
            return var4.status() == RestStatus.NOT_FOUND;
        }
    }

    public void addAll() throws IOException {
        if (!checkIndexExist()) {
            createIndex();
        }
        // List<User> list = Arrays.asList(userService.findUserInfo());
        // test数据库的所有用户
        List<User> list = userMapper.findAllUser();
        BulkRequest request = new BulkRequest();
        for (User baseInfo : list) {
            ObjectMapper mapper = new ObjectMapper();
            IndexRequest indexRequest = new IndexRequest(indexName, typeName, baseInfo.getId().toString());
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(baseInfo);
            indexRequest.source(mapper.writeValueAsBytes(jsonObject), XContentType.JSON);
            request.add(indexRequest);
        }
        client.bulk(request);
    }

    public String add(User baseInfo) {
        IndexRequest indexRequest = new IndexRequest(indexName, typeName, baseInfo.getId().toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(baseInfo);
            indexRequest.source(mapper.writeValueAsBytes(jsonObject), XContentType.JSON);
            IndexResponse indexResponse = client.index(indexRequest);
            return indexResponse.getId();
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public void deleteByUid(String uniqueInfoId) throws IOException {
        DeleteRequest request = new DeleteRequest(indexName, typeName, uniqueInfoId);
        client.delete(request);
    }

    public boolean setMapping() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                    .startObject("properties")
                    .startObject("all_text").field("type", "keyword")
                    // .field("normalizer", "my_normalizer")
                    .endObject();
            // .startObject("uuid").field("type", "keyword").endObject()
            // .startObject("state").field("type", "keyword").endObject();
            // .startObject("level1CategoryCode").field("type", "keyword").endObject()
            // .startObject("level2CategoryCode").field("type", "keyword").endObject()
            // .startObject("level3CategoryCode").field("type", "keyword").endObject()
            // .startObject("level4CategoryCode").field("type", "keyword").endObject()
            // .startObject("level5CategoryCode").field("type", "keyword").endObject()
            // .startObject("level6CategoryCode").field("type", "keyword").endObject();

            String type = "type";
            String keyword = "keyword";
            String allText = "all_text";

            Field[] fields = User.class.getDeclaredFields();
            for (Field field : fields) {
                final String name = field.getName();
                final Class<?> fieldType = field.getType();
                final Class<?> superclass = fieldType.getSuperclass();
                final String typeName = fieldType.getName();
                if (typeName.contains("date")) {
                    builder.startObject(name).field(type, "date").field("format", "yyyy-MM-dd HH:mm:ss||epoch_millis").endObject();
                } else if (Number.class.equals(superclass)) {
                    builder.startObject(name).field("type", "long").endObject();
                } else {
                    builder.startObject(name).field(type, keyword);
                    builder.field("copy_to", allText);
                    builder.endObject();
                }
            }

            builder.endObject()
                    .endObject();
            PutMappingRequest request = new PutMappingRequest(indexName);
            request.type(typeName);
            request.source(builder);
            PutMappingResponse putMappingResponse = client.indices().putMapping(request);
            log.info("设置mapping,状态为:{}", putMappingResponse.isAcknowledged());
            return putMappingResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

