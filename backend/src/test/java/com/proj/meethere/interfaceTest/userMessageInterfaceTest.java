package com.proj.meethere.interfaceTest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author Tresaresa
 * @Date 2019/12/25 9:33
 */
public class userMessageInterfaceTest {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void should_get_latest_messages() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/getLatestMessage");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getEntity());
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");
        JSONObject jsonObject = new JSONObject(response.getEntity());
        assertEquals("text/html", jsonObject.get("Content-Type"));
        response.close();
    }
}
