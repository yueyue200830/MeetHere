package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author Tresaresa
 * @Date 2019/12/29 11:06
 */
public class UserOrderInterfaceTest {

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
    void should_return_0_or_1_when_add_one_order() throws IOException, URISyntaxException {
        String form = "{\"revenue\":\"篮球馆\",\"phoneNumber\":\"12312312312\"," +
                "\"date\":\"2019-12-10\",\"timeSlot\":\"2\"," +
                "\"room\":\"4\",\"price\":\"30\"}";
        int id = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/addOrder")
                .setParameter("addOrderForm", form)
                .setParameter("id", String.valueOf(id))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 1 || result == 0 || result == -1);

        response.close();
    }

    @Test
    void should_return_available_table_when_get_available() throws IOException, URISyntaxException {
        String revenueName = "篮球馆";
        String date = "2019-12-26";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/getAvailable")
                .setParameter("revenueName", revenueName)
                .setParameter("date", date)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONObject jsonObject = new JSONObject(TestUtils.inputStream2String(inputStream));

        assertTrue(jsonObject.keySet().contains("available"));

        response.close();
    }

    @Test
    void should_return_all_order_of_specific_user() throws IOException, URISyntaxException {
        int id = 43;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/getMyOrder")
                .setParameter("id", String.valueOf(id))
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        JSONArray jsonArray = TestUtils.inputStream2JSONArray(inputStream);

        List<String> keys = new ArrayList<>();
        keys.add("orderApproved");
        keys.add("revenue");
        keys.add("orderId");
        keys.add("timeSlot");
        keys.add("rvnRoomNum");
        keys.add("orderPrice");
        keys.add("orderDate");
        keys.add("orderPhone");
        Assertions.assertTrue(jsonArray.toString().equals("[]")
                || jsonArray.getJSONArray(0).getJSONObject(0).keySet().containsAll(keys));

        response.close();
    }

    @Test
    void should_return_0_or_1_when_update_phone() throws IOException, URISyntaxException {
        int id = 1;
        String phone = "12322211122";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/updatePhone")
                .setParameter("id", String.valueOf(id))
                .setParameter("phone", phone)
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
    void should_return_0_or_1_when_delete_phone() throws IOException, URISyntaxException {
        int id = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/deleteOrder")
                .setParameter("deleteOrderId", String.valueOf(id))
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
