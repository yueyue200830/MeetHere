package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Yiqing Tao
 * @date 2019-12-28 22:45
 */
public class NewsInterfaceTest {
    @Test
    public void should_get_news() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/getNews";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("delete")),
                ()->assertTrue(jsonObject.keySet().contains("newsContent")),
                ()->assertTrue(jsonObject.keySet().contains("newsTitle")),
                ()->assertTrue(jsonObject.keySet().contains("newsPhoto")),
                ()->assertTrue(jsonObject.keySet().contains("title")));
        httpClient.close();
    }

    @Test
    public void should_delete_news() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081//deleteNews/{id}").build();
        URI uri = uriComponents.expand("116").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
        httpClient.close();
    }

    @Test
    public void should_modify_news() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/modifyNews";
        String json = "{\"title\":\"testt\", \"isDelete\":false,\"id\": 115, \"newsContent\":\"news content\", \"newsTitle\":\"title\", \"newsPhoto\":\"photo\"}";
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(json, "utf-8");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
        httpClient.close();
    }

    @Test
    public void should_search_news() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081//searchNews/{id}").build();
        URI uri = uriComponents.expand("115").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("delete")),
                ()->assertTrue(jsonObject.keySet().contains("newsContent")),
                ()->assertTrue(jsonObject.keySet().contains("newsTitle")),
                ()->assertTrue(jsonObject.keySet().contains("newsPhoto")),
                ()->assertTrue(jsonObject.keySet().contains("title")));
        httpClient.close();
    }

    @Test
    public void should_add_news() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/addNews";
        String json = "{\"title\":\"添加新闻\", \"isDelete\":false,\"id\": 0, \"newsContent\":\"fake content\", \"newsTitle\":\"fake title\", \"newsPhoto\":\"photo\"}";
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(json, "utf-8");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
        httpClient.close();
    }

    @Test
    public void should_select_photo_by_id() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/getPhoto/{id}").build();
        URI uri = uriComponents.expand("115").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        String result = TestUtils.inputStream2String(inputStream);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == null || result.length() != 0));
        httpClient.close();
    }
}
