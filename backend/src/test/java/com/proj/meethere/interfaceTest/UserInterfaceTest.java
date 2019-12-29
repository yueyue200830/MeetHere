package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
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

/**
 * @author Yiqing Tao
 * @date 2019-12-28 21:54
 */
public class UserInterfaceTest {
    @Test
    public void should_change_user_role() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081//changeUserRole/{id}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
    }

    @Test
    public void should_check_manager_valid() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/managerLoginCheck";
        String json = "{\"user_name\":\"momo\", \"user_key\":\"123456\"}";
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
    }

    @Test
    public void admin_should_login() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/modifyManagerPassword";
        String json = "{\"user_name\":\"momo\", \"oldPassword\":\"123456\",\"newPassword\":\"54321\",\"confirmPassword\":\"54321\"}";
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
    }

    @Test
    public void should_get_user() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/getUser";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("userName")),
                ()->assertTrue(jsonObject.keySet().contains("userType")));
    }

    @Test
    public void should_delete_user() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/deleteUser/{temp}").build();
        URI uri = uriComponents.expand("3").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
    }

    @Test
    public void should_search_user() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/searchUser/{condition}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("userName")),
                ()->assertTrue(jsonObject.keySet().contains("userType")));
    }
}
