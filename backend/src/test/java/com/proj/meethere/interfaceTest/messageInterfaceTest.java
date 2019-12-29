package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yiqing Tao
 * @date 2019-12-25 9:24
 */

public class messageInterfaceTest {
    @Test
    public void should_get_unapproved_message() throws URISyntaxException,IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //URI uri = new URIBuilder().setScheme("http").setHost("localhost:8080").setPath("/searchUnapproveMessage/1").build();
        HttpPost httpPost = new HttpPost("http://localhost:8081/getUnapproveMessage");
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        System.out.println(jsonArray);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("messageTime")),
                ()->assertTrue(jsonObject.keySet().contains("messageTitle")),
                ()->assertTrue(jsonObject.keySet().contains("messageVisibility")),
                ()->assertTrue(jsonObject.keySet().contains("userId")),
                ()->assertTrue(jsonObject.keySet().contains("messageContent")),
                ()->assertTrue(jsonObject.keySet().contains("id")));
        httpClient.close();
    }

    @Test
    public void should_search_unapproved_message() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/searchUnapproveMessage/{condition}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("messageTime")),
                ()->assertTrue(jsonObject.keySet().contains("messageTitle")),
                ()->assertTrue(jsonObject.keySet().contains("messageVisibility")),
                ()->assertTrue(jsonObject.keySet().contains("userId")),
                ()->assertTrue(jsonObject.keySet().contains("messageContent")),
                ()->assertTrue(jsonObject.keySet().contains("id")));
        httpClient.close();
    }

    @Test
    public void should_approve_message() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/approveMessage/{temp}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
    }



}
