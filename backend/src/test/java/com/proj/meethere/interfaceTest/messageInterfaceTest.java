package com.proj.meethere.interfaceTest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

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
        HttpEntity entity = response.getEntity();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        System.out.println(entity.getContent());
        httpClient.getConnectionManager().shutdown();

    }

    @AfterClass
    public static void afterClass() {
        //断开连接

    }

}
