package com.proj.meethere.interfaceTest;

import org.json.JSONObject;
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
import org.json.JSONArray;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author Tresaresa
 * @Date 2019/12/25 9:33
 */
public class userMessageInterfaceTest {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void should_get_latest_messages_when_message_exist() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/getLatestMessage");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getEntity());
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        StringBuffer stringBuffer = new StringBuffer();
        byte[] b = new byte[4096];
        int n;
        while((n = inputStream.read(b)) != -1) {
            stringBuffer.append(new String(b, 0, n));
        }
        JSONArray jsonArray = new JSONArray(stringBuffer.toString());
        jsonArray.put(stringBuffer.toString());
        System.out.println(jsonArray.get(0));
        JSONObject firstObject = (jsonArray.getJSONArray(0).getJSONObject(0));
        List<String> keys =  new ArrayList<>(firstObject.keySet());
        assertEquals("messageTime", keys.get(0));
        response.close();
    }
}
