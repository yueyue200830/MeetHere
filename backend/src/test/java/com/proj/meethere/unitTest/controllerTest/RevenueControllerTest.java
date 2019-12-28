package com.proj.meethere.unitTest.controllerTest;

import com.alibaba.fastjson.JSONObject;
import com.proj.meethere.Request.RevenueRequest;
import com.proj.meethere.controller.OrderController;
import com.proj.meethere.controller.RevenueController;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.response.RevenueResponse;
import com.proj.meethere.service.RevenueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author Yiqing Tao
 * @date 2019-12-26 13:04
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RevenueController.class)
public class RevenueControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RevenueService revenueService;

    @Test
    public void venue_should_all_be_selected() throws Exception {
        ResultActions action = mockMvc.perform(post("/getVenue"));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).getAllRevenue();
        verifyNoMoreInteractions(revenueService);
    }

    @Test
    public void should_get_specific_venue() throws Exception{
        ResultActions action = mockMvc.perform(get("/searchVenue/{condition}", "1"));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).searchRevenue(1);
        verifyNoMoreInteractions(revenueService);
    }

    @Test
    public void should_check_venue() throws Exception{
        ResultActions action = mockMvc.perform(get("/checkVenue/{temp}","1"));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).getRevenue(1);
        verifyNoMoreInteractions(revenueService);
    }

    @Test
    public void should_modify_venue() throws Exception {
        RevenueResponse revenueResponse = new RevenueResponse();
        revenueResponse.setRvnPrice(200);
        revenueResponse.setDelete(false);
        revenueResponse.setId(1);
        revenueResponse.setRvnIntro("mockIntro");
        revenueResponse.setTitle("fake title");
        String rvnString = JSONObject.toJSONString(revenueResponse);
        ResultActions action = mockMvc.perform(post("/modifyVenue").contentType(MediaType.APPLICATION_JSON).content(rvnString));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).modifyRevenue(200,"mockIntro", 1);
        verifyNoMoreInteractions(revenueService);
    }
    @Test
    public void should_add_venue() throws Exception{
        RevenueRequest rq = new RevenueRequest();
        rq.setDelete(false);
        rq.setRvnIntro("intro");
        rq.setRvnName("name");
        rq.setRvnPrice(100);
        rq.setRvnRoomNum(19);
        rq.setTitle("添加场馆");
        String rqString = JSONObject.toJSONString(rq);
        ResultActions actions = mockMvc.perform(post("/addVenueManager").contentType(MediaType.APPLICATION_JSON).content(rqString));
        actions.andExpect(status().isOk());
        verify(revenueService, times(1)).addRevenue("intro", 100, "name", 19);
        verifyNoMoreInteractions(revenueService);
    }

    @Test
    public void should_get_venue_for_name() throws Exception {
        ResultActions action = mockMvc.perform(post("/getVenueNameForChart"));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).getVenueName();
        verifyNoMoreInteractions(revenueService);
    }

    @Test
    public void should_get_statistic()throws Exception {
        ResultActions action = mockMvc.perform(get("/getStatistic/{value}", "2019-10-29"));
        action.andExpect(status().isOk());
        verify(revenueService, times(1)).getOrderNum("2019-10-29");
        verifyNoMoreInteractions(revenueService);
    }
}
