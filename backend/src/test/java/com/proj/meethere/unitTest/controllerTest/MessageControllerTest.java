package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.unitTest.serviceTest.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yiqing Tao
 * @date 2019-12-26 13:33
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MessageService messageService;

    @Test
    public void should_get_unapproved_message() throws Exception {
        ResultActions action = mockMvc.perform(post("/getUnapproveMessage"));
        action.andExpect(status().isOk());
        verify(messageService, times(1)).getUnapprovedMessage();
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void should_approve_message() throws Exception {
        ResultActions actions = mockMvc.perform(get("/approveMessage/{temp}", "1"));
        actions.andExpect(status().isOk());
        verify(messageService, times(1)).approveMessage(1);
        verifyNoMoreInteractions(messageService);
    }

    @Test
    public void should_search_unapproved_specific_message() throws Exception {
        ResultActions actions = mockMvc.perform(get("/searchUnapproveMessage/{condition}", "1"));
        actions.andExpect(status().isOk());
        verify(messageService, times(1)).searchSpecificMessage(1);
        verifyNoMoreInteractions(messageService);
    }

}
