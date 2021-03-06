package com.proj.meethere.interfaceTest;

import com.proj.meethere.utils.TestUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author Tresaresa
 * @Date 2019/12/27 23:40
 */
class UserInfoInterfaceTest {

    static private CloseableHttpClient httpClient;

    @BeforeAll
    static void init() {
        httpClient = HttpClients.createDefault();
    }

    @AfterAll
    static void after() throws IOException {
        httpClient.close();
    }

    @ParameterizedTest
    @MethodSource("existNameProvider")
    void should_return_1_when_name_exist(String userName) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/checkUserNameExist")
                .setParameter("user_name", userName)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 1);

        response.close();
    }

    @ParameterizedTest
    @MethodSource("noExistNameProvider")
    void should_return_0_when_name_not_exist(String userName) throws URISyntaxException, IOException {
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/checkUserNameExist")
                .setParameter("user_name", userName)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result == 0);

        response.close();
    }

    @Test
    void should_return_0_or_1_when_check_my_name() throws URISyntaxException, IOException {
        String userName = "admin";
        int id = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/checkUserNameWithId")
                .setParameter("user_name", userName)
                .setParameter("id", String.valueOf(id))
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
    void should_return_0_or_1_when_update_name() throws URISyntaxException, IOException {
        String newName = "admin";
        int id = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/updateUserNameById")
                .setParameter("newName", newName)
                .setParameter("id", String.valueOf(id))
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
    void should_return_0_or_1_or_minus1_when_update_pass() throws URISyntaxException, IOException {
        String form = "{\"originalPass\":\"pass12.3\",\"pass\":\"newpass!!\"}";
        int id = 1;
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/updateUserPassById")
                .setParameter("updateForm", form)
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
    void should_return_0_or_1_when_register() throws URISyntaxException, IOException {
        String form = "{\"name\":\"pass12.3\",\"password\":\"momo-123456\"}";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/Register")
                .setParameter("form", form)
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
    void should_return_minus1_or_id_or_0_when_login() throws URISyntaxException, IOException {
        String form = "{\"name\":\"pass12.3\",\"password\":\"newpass!!\"}";
        URI uri = new URIBuilder().setScheme("http")
                .setHost("localhost:8081")
                .setPath("/Login")
                .setParameter("login", form)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode(), "Status isn't 200");

        InputStream inputStream = response.getEntity().getContent();
        int result = Integer.parseInt(TestUtils.inputStream2String(inputStream));

        assertTrue(result >= -1);

        response.close();
    }

    static Stream<Arguments> existNameProvider() {
        return Stream.of(Arguments.of("admin"),
                Arguments.of("ballballtang"));
    }

    static Stream<Arguments> noExistNameProvider() {
        return Stream.of(Arguments.of("一个绝对不存在的名字"),
                Arguments.of("a1fake2name3"));
    }
}
