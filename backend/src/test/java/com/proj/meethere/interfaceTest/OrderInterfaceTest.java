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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Yiqing Tao
 * @date 2019-12-29 0:23
 */
public class OrderInterfaceTest {
    @Test
    public void should_get_order() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8081/getUnapproveOrder";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        assertAll(()->assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("userId")),
                ()->assertTrue(jsonObject.keySet().contains("orderPhone")),
                ()->assertTrue(jsonObject.keySet().contains("rvnRoomNum")),
                ()->assertTrue(jsonObject.keySet().contains("rvnId")),
                ()->assertTrue(jsonObject.keySet().contains("timeSlot")),
                ()->assertTrue(jsonObject.keySet().contains("orderApproved")),
                ()->assertTrue(jsonObject.keySet().contains("orderPrice")),
                ()->assertTrue(jsonObject.keySet().contains("orderDate")));
        httpClient.close();
    }

    @Test
    public void should_search_order() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/searchUnapproveOrder/{condition}").build();
        URI uri = uriComponents.expand("1").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonArray);
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(jsonObject.keySet().contains("id")),
                ()->assertTrue(jsonObject.keySet().contains("userId")),
                ()->assertTrue(jsonObject.keySet().contains("orderPhone")),
                ()->assertTrue(jsonObject.keySet().contains("rvnRoomNum")),
                ()->assertTrue(jsonObject.keySet().contains("rvnId")),
                ()->assertTrue(jsonObject.keySet().contains("timeSlot")),
                ()->assertTrue(jsonObject.keySet().contains("orderApproved")),
                ()->assertTrue(jsonObject.keySet().contains("orderPrice")),
                ()->assertTrue(jsonObject.keySet().contains("orderDate")));
        httpClient.close();
    }

    @Test
    public void should_approve_order() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://localhost:8081/approveOrder/{temp}").build();
        URI uri = uriComponents.expand("2").encode().toUri();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        InputStream inputStream = httpResponse.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));
        assertAll(()->assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode()),
                ()->assertTrue(result == 1 || result == 0));
        httpClient.close();
    }
}
