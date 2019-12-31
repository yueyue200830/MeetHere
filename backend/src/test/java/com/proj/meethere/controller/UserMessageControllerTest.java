package com.proj.meethere.controller;

import com.proj.meethere.service.UserMessageService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Tresaresa
 * @Date 2019/12/20 18:41
 */
@WebMvcTest(UserMessageController.class)
class UserMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMessageService userMessageService;

    @Test
    void should_return_ten_latest_message() throws Exception {
        mockMvc.perform(post("/getLatestMessage")).andExpect(status().isOk());
        verify(userMessageService, times(1)).selectMessagesPartial(10);
    }

    @ParameterizedTest
    @MethodSource("userIdProvider")
    void should_return_ten_latest_message_of_specific_user(int userId) throws Exception {
        mockMvc.perform(get("/getMyMessage")
                .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).selectMessagesPartialById(10, userId);
    }

    @ParameterizedTest
    @MethodSource("timeNumberProvider")
    void should_return_message_before_time(String lastTime, int number) throws Exception {
        mockMvc.perform(get("/getMoreMessage")
                .param("lastTime", lastTime).param("number", String.valueOf(number)))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).selectMoreMessagesBeforePartial(lastTime, number);
    }

    @ParameterizedTest
    @MethodSource("timeNumberUserIdProvider")
    void should_return_message_before_time_of_specific_user(String lastTime, int number, int userId) throws Exception {
        mockMvc.perform(get("/getMoreMyMessage")
                .param("lastTime", lastTime).param("number", String.valueOf(number)).param("userId", String.valueOf(userId)))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).selectMoreMessagesBeforePartialById(lastTime, number, userId);
    }

    @ParameterizedTest
    @MethodSource("addMessageFormProvider")
    void should_add_one_message(String form, String title, String content, int userId) throws Exception {
        mockMvc.perform(get("/addMessage")
                .param("addMessageForm", form).param("id", String.valueOf(userId)))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).addNewMessage(title, content, userId);
    }

    @ParameterizedTest
    @MethodSource("messageIdProvider")
    void should_delete_one_message(int id) throws Exception {
        mockMvc.perform(get("/deleteMessage")
                .param("id", String.valueOf(id)))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).deleteMessage(id);
    }

    @ParameterizedTest
    @MethodSource("editMessageFormProvider")
    void should_update_one_message(String form, String messageTitle, String messageContent, int id) throws Exception {
        mockMvc.perform(get("/editMessage")
                .param("editMessageForm", form))
                .andExpect(status().isOk());
        verify(userMessageService, times(1)).updateMessage(messageTitle, messageContent, id);
    }

    static Stream<Arguments> userIdProvider() {
        return Stream.of(
                Arguments.of(5));
    }

    static Stream<Arguments> messageIdProvider() {
        return Stream.of(
                Arguments.of(1));
    }

    static Stream<Arguments> timeNumberProvider() {
        return Stream.of(
                Arguments.of("2019-12-19", 3));
    }

    static Stream<Arguments> timeNumberUserIdProvider() {
        return Stream.of(
                Arguments.of("2019-12-19", 3, 5));
    }

    static Stream<Arguments> addMessageFormProvider() {
        return Stream.of(
                Arguments.of("{\"title\":\"testtitle\",\"content\":\"testcontent\"}", "testtitle", "testcontent", 5));
    }

    static Stream<Arguments> editMessageFormProvider() {
        return Stream.of(
                Arguments.of("{\"messageTitle\":\"testtitle\"" +
                             ",\"messageContent\":\"testcontent\"" +
                             ",\"id\":5}", "testtitle", "testcontent", 5));
    }
}
