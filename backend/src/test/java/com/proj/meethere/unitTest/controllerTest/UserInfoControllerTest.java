package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.controller.UserInfoController;
import com.proj.meethere.service.UserInfoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.SQLException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Tresaresa
 * @Date 2019/12/20 18:40
 */
@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;

    @ParameterizedTest
    @MethodSource("userNameProvider")
    void should_return_status_of_user_existence(String name) throws Exception {
        mockMvc.perform(get("/checkUserNameExist")
                .param("user_name", String.valueOf(name)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).checkUserNameExist(name);
    }

    @ParameterizedTest
    @MethodSource("updateFormIdProvider")
    void should_update_specific_user_info(String form, String name, String pass, int id) throws Exception {
        mockMvc.perform(get("/updateUserById")
                .param("updateForm", String.valueOf(form)).param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).updateUserById(id, pass, name);
    }

    @ParameterizedTest
    @MethodSource("addFormProvider")
    void should_add_one_user(String form, String name, String password) throws Exception {
        mockMvc.perform(get("/Register")
                .param("form", String.valueOf(form)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).insertNewUser(name, password, 0);
    }

    @ParameterizedTest
    @MethodSource("loginFormProvider")
    void should_return_user_login_status(String form, String name, String password) throws Exception {
        mockMvc.perform(get("/Login")
                .param("login", String.valueOf(form)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).loginValidation(name, password);
    }

    @ParameterizedTest
    @MethodSource("fileIdProvider")
    void should_update_user_with_photo(MockMultipartFile file, int id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/UploadPhoto")
                .file(file).param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).updateUserPhoto(anyString(), eq(id));
    }

    @ParameterizedTest
    @MethodSource("userIdProvider")
    void should_return_specific_user_photo(int id) throws Exception {
        mockMvc.perform(get("/GetPhoto")
                .param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userInfoService, times(1)).selectUserPhoto(id);
    }

    static Stream<Arguments> userNameProvider() {
        return Stream.of(Arguments.of("管理员"));
    }

    static Stream<Arguments> updateFormIdProvider() {
        return Stream.of(Arguments.of("{\"name\":\"testuser1\",\"pass\":\"test123456\"}", "testuser1", "test123456", 5));
    }

    static Stream<Arguments> addFormProvider() {
        return Stream.of(Arguments.of("{\"name\":\"testuser2\",\"password\":\"test123456\"}", "testuser2", "test123456"));
    }

    static Stream<Arguments> loginFormProvider() {
        return Stream.of(Arguments.of("{\"name\":\"testuser3\",\"password\":\"test123456\"}", "testuser3", "test123456"));
    }

    static Stream<Arguments> fileIdProvider() {
        String fileName = "test.txt";
        byte[] content = "Hallo Word".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", content);
        return Stream.of(Arguments.of(mockMultipartFile, 5));
    }

    static Stream<Arguments> userIdProvider() {
        return Stream.of(Arguments.of(5));
    }
}
