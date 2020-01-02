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
import org.junit.Ignore;
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
 * @date 2019-12-28 23:52
 */
public class RevenueInterfaceTest {
    @Test
    public void should_get_venue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/getVenue";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("rvnName")),
                ()->assertTrue(jsonObject.keySet().contains("rvnRoomnum")),
                ()->assertTrue(jsonObject.keySet().contains("rvnIntro")),
                ()->assertTrue(jsonObject.keySet().contains("rvnPrice")));
        httpClient.close();
    }

    @Test
    public void should_search_venue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/searchVenue/{condition}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonArray);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("rvnName")),
                ()->assertTrue(jsonObject.keySet().contains("rvnRoomnum")),
                ()->assertTrue(jsonObject.keySet().contains("rvnIntro")),
                ()->assertTrue(jsonObject.keySet().contains("rvnPrice")));
        httpClient.close();
    }


    @Ignore
    @Test
    public void should_add_venue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/addVenueManager";
        String json = "{\"title\":\"添加场馆\", \"isDelete\":false,\"rvnName\": \"venue name\", \"rvnRoomNum\":10, \"rvnPrice\":120, \"rvnIntro\":\"intro\"}";
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
    public void should_modify_venue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/modifyVenue";
        String json = "{\"title\":\"添加场馆\", \"isDelete\":false,\"id\": 2,\"rvnPrice\":120, \"rvnIntro\":\"intro\"}";
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
    public void should_check_venue() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/checkVenue/{temp}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("title")),
                ()->assertTrue(jsonObject.keySet().contains("rvnIntro")),
                ()->assertTrue(jsonObject.keySet().contains("rvnPrice")),
                ()->assertTrue(jsonObject.keySet().contains("delete")));
        httpClient.close();
    }

    @Test
    public void should_get_venue_name_for_chart() throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/getVenueNameForChart";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        System.out.println(jsonArray);
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()));
        httpClient.close();
    }

    @Test
    public void should_get_order_num() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/getStatistic/{value}").build();
        URI uri = uriComponents.expand("2019-10-29").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        System.out.println(jsonArray);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()));
        httpClient.close();
    }
}
