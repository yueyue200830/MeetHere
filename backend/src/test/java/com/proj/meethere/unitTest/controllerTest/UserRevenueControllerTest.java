package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.controller.UserRevenueController;
import com.proj.meethere.entity.Revenue;
import com.proj.meethere.service.UserRevenueService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Tresaresa
 * @Date 2019/12/20 14:23
 */
@WebMvcTest(UserRevenueController.class)
class UserRevenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRevenueService userRevenueService;

    @Test
    public void should_return_all_revenue_info() throws Exception {
        mockMvc.perform(post("/getVenueUserPage")).andExpect(status().isOk());
        verify(userRevenueService, times(1)).getAllVenue();
    }

    @Test
    public void should_return_all_revenue_name() throws Exception {
        mockMvc.perform(post("/getVenueName")).andExpect(status().isOk());
        verify(userRevenueService, times(1)).getAllenueName();
    }
}
