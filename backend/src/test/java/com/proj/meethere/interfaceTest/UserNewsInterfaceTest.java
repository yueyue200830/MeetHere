package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author Tresaresa
 * @Date 2019/12/29 11:06
 */
class UserNewsInterfaceTest {

    static private CloseableHttpClient httpClient;

    @BeforeAll
    static void init() {
        httpClient = HttpClients.createDefault();
    }

    @AfterAll
    static void after() throws IOException {
        httpClient.close();
    }

    @Test
    void should_return_all_news() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/getNewsUserPage");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("newsContent");
        keys.add("newsTitle");
        keys.add("id");
        keys.add("newsTime");

        assertTrue(jsonArray.toString().equals("[]")
                || jsonArray.getJSONObject(0).keySet().containsAll(keys));

        response.close();
    }

    @Test
    void should_return_three_news() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/getNewsThree");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("newsContent");
        keys.add("newsTitle");
        keys.add("id");
        keys.add("newsTime");

        assertTrue(jsonArray.toString().equals("[]")
                || jsonArray.getJSONObject(0).keySet().containsAll(keys)
                && jsonArray.length() <= 3);

        response.close();
    }

}
