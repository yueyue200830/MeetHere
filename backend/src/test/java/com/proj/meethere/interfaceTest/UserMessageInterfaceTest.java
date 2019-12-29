package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Tresaresa
 * @Date 2019/12/25 9:33
 */
class UserMessageInterfaceTest {

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
    void should_get_latest_messages() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/getLatestMessage");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("messageTime");
        keys.add("messageTitle");
        keys.add("id");
        keys.add("userId");
        keys.add("messageVisibility");
        keys.add("messageContent");
        assertTrue(jsonArray.toString().equals("[[]]") || jsonArray.getJSONArray(0).getJSONObject(0).keySet().containsAll(keys));

        response.close();
    }

    @Test
    void should_return_message_of_specific_user() throws IOException, URISyntaxException {
        int userId = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/getMyMessage")
                .setParameter("userId", String.valueOf(userId))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("messageTime");
        keys.add("messageTitle");
        keys.add("id");
        keys.add("userId");
        keys.add("messageVisibility");
        keys.add("messageContent");
        assertTrue(jsonArray.toString().equals("[[]]")
                || jsonArray.getJSONArray(0).getJSONObject(0).keySet().containsAll(keys)
                && jsonArray.getJSONArray(0).getJSONObject(0).getInt("userId") == userId);

        response.close();
    }

    @Test
    void should_return_message_before_time() throws IOException, URISyntaxException {
        int number = 3;
        String lastTime = "2020-12-27 15:01:19";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/getMoreMessage")
                .setParameter("lastTime", lastTime)
                .setParameter("number", String.valueOf(number))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("messageTime");
        keys.add("messageTitle");
        keys.add("id");
        keys.add("userId");
        keys.add("messageVisibility");
        keys.add("messageContent");
        assertTrue(jsonArray.toString().equals("[[]]")
                || jsonArray.getJSONArray(0).getJSONObject(0).keySet().containsAll(keys)
                && number >= jsonArray.getJSONArray(0).length());

        response.close();
    }

    @Test
    void should_return_message_before_time_of_specific_user() throws IOException, URISyntaxException {
        int userId = 1;
        int number = 3;
        String lastTime = "2020-12-27 15:01:19";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/getMoreMyMessage")
                .setParameter("userId", String.valueOf(userId))
                .setParameter("lastTime", lastTime)
                .setParameter("number", String.valueOf(number))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("messageTime");
        keys.add("messageTitle");
        keys.add("id");
        keys.add("userId");
        keys.add("messageVisibility");
        keys.add("messageContent");
        assertTrue(jsonArray.toString().equals("[[]]")
                || jsonArray.getJSONArray(0).getJSONObject(0).keySet().containsAll(keys)
                && number >= jsonArray.getJSONArray(0).length()
                && jsonArray.getJSONArray(0).getJSONObject(0).getInt("userId") == userId);

        response.close();
    }

    @Test
    void should_add_message() throws IOException, URISyntaxException {
        int userId = 1;
        String form = "{\"title\":\"testtitle\",\"content\":\"testcontent\"}";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/addMessage")
                .setParameter("id", String.valueOf(userId))
                .setParameter("addMessageForm", form)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 1 || result == 0);

        response.close();
    }

    @Test
    void should_delete_message() throws IOException, URISyntaxException {
        int messageId = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/deleteMessage")
                .setParameter("id", String.valueOf(messageId))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 1 || result == 0);

        response.close();
    }

    @Test
    void should_update_message() throws IOException, URISyntaxException {
        String form = "{\"messageTitle\":\"testtitle\"" +
                ",\"messageContent\":\"testcontent\"" +
                ",\"id\":5}";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/editMessage")
                .setParameter("editMessageForm", form)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 1 || result == 0);

        response.close();
    }
}
