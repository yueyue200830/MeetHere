package com.proj.meethere.service;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import com.proj.meethere.response.RevenueResponse;
import com.proj.meethere.service.RevenueService;
import com.proj.meethere.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * @author Yiqing Tao
 * @date 2019-12-26 21:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RevenueServiceTest {
    @Autowired
    RevenueService revenueService;

    @MockBean
    RevenueRepository revenueRepository;

    Revenue revenue;
    @Before
    public void init() {
        revenue = new Revenue(1,"revenue 1", 10, "introduction", 100);
    }

    @Test
    public void revenue_should_be_all_selected() {
        Revenue revenue2 = new Revenue("revenue 2", 20, "intro 2", 120);
        List<Revenue> revenueList = new ArrayList<>();
        revenueList.add(revenue);
        revenueList.add(revenue2);
        when(revenueRepository.getAllRvnInfo()).thenReturn(revenueList);
        List<Revenue> revenues = revenueService.getAllRevenue();
        Assert.assertEquals(2, revenues.size());
        Revenue r1 = revenues.get(0);
        Revenue r2 = revenues.get(1);
        Assert.assertSame(revenue, r1);
        Assert.assertSame(revenue2,r2);
        verify(revenueRepository, times(1)).getAllRvnInfo();
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void venue_should_get_empty_when_id_below_0() {
        List<RevenueResponse> rrList = revenueService.getRevenue(-1);
        assertAll(()->assertEquals(0, rrList.size()));
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void venue_should_be_searched() {
        List<Revenue> revenueList = new ArrayList<>();
        revenueList.add(revenue);
        when(revenueRepository.getRevenueById(1)).thenReturn(revenueList);
        List<Revenue> revenueList1 = revenueService.searchRevenue(1);
        Assert.assertEquals(1, revenueList1.size());
        verify(revenueRepository, times(1)).getRevenueById(1);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void venue_should_return_empty_when_id_below_0() {
        List<Revenue> result =  revenueService.searchRevenue(-1);
        assertAll(()->assertEquals(0, result.size()));
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void revenue_should_be_select_to_response() {
        List<Revenue> revenueList = new ArrayList<>();
        revenueList.add(revenue);
        when(revenueRepository.getRevenueById(1)).thenReturn(revenueList);
        List<RevenueResponse> revenueResponseList = revenueService.getRevenue(1);
        Assert.assertEquals(1, revenueResponseList.size());
        verify(revenueRepository, times(1)).getRevenueById(1);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void revenue_should_be_modified() {
        when(revenueRepository.updateRvnInfo(120,"intro", 1)).thenReturn(1);
        int result = revenueService.modifyRevenue(120,"intro", 1);
        Assert.assertEquals(1, result);
        verify(revenueRepository, times(1)).updateRvnInfo(120,"intro",1);
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void name_should_be_retrieved() {
        List<String> names = new ArrayList<>();
        names.add("name1");
        names.add("name2");
        when(revenueRepository.selectRevenueName()).thenReturn(names);
        List<String> revenueName = revenueService.getVenueName();
        Assert.assertEquals(2, revenueName.size());
        verify(revenueRepository, times(1)).selectRevenueName();
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void order_num_should_be_retrieved() {
        List<Integer> numList = new ArrayList<>();
        numList.add(1);
        numList.add(2);
        when(revenueRepository.selectStatistic("2019-10-29")).thenReturn(numList);
        List<Integer> result = revenueService.getOrderNum("2019-10-29");
        Assert.assertEquals(2, result.size());
        verify(revenueRepository, times(1)).selectStatistic("2019-10-29");
        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    public void should_get_0_when_modify_venue_if_id_below_0() {
        int result = revenueService.modifyRevenue(100, "intro", -1);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(revenueRepository);
    }

    @ParameterizedTest
    @MethodSource("provideAddVenueSource")
    public void should_return_o_if_add_source_is_invalid(String rvnName, String rvnIntro, int price, int roomNum) {
        int result = revenueService.addRevenue(rvnIntro, price, rvnName, roomNum);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(revenueRepository);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateRevenueSource")
    public void should_return_0_if_revenue_content_is_null(int id, int price, String intro) {
        int result = revenueService.modifyRevenue(price, intro, id);
        assertAll(()->assertEquals(0, result));
        verifyNoMoreInteractions(revenueRepository);
    }

    static List<Arguments>  provideAddVenueSource() {
        return Arrays.asList(Arguments.of(null, "normal intro", 100, 10),
                Arguments.of("normal name", null, 100, 10),
                Arguments.of("normal name", "normal intro", -1, 10),
                Arguments.of("normal name", "normal intro", 100, -1),
                Arguments.of(null, null, 100, 10),
                Arguments.of(null, "normal intro", -1, 10),
                Arguments.of(null, "normal intro", 100, -1),
                Arguments.of("normal name", null, -1, 10),
                Arguments.of("normal name", null, 100, -1),
                Arguments.of("normal name", "normal intro", -1, -1),
                Arguments.of(null, null, -1, 10),
                Arguments.of(null, null, 100, -1),
                Arguments.of(null, "normal intro", -1, -1),
                Arguments.of("normal name", null, -1, -1),
                Arguments.of(null, null, -1,-1),
                Arguments.of(TestUtils.generateString(41), "normal intro", 100, 10),
                Arguments.of(TestUtils.generateString(41), "normal intro", -100, 10),
                Arguments.of(TestUtils.generateString(41), "normal intro", 100, -10),
                Arguments.of(TestUtils.generateString(41), "normal intro", -100, -10),
                Arguments.of(TestUtils.generateString(41), null, 100, 10),
                Arguments.of(TestUtils.generateString(41), null, -100, 10),
                Arguments.of(TestUtils.generateString(41), null, 100, -10),
                Arguments.of(TestUtils.generateString(41), null, -100, -10),
                Arguments.of("normal name", TestUtils.generateString(1001), 100,10),
                Arguments.of("normal name", TestUtils.generateString(1001), -100,10),
                Arguments.of("normal name", TestUtils.generateString(1001), 100,-10),
                Arguments.of("normal name", TestUtils.generateString(1001), -100,-10),
                Arguments.of(null, TestUtils.generateString(1001), 100,10),
                Arguments.of(null, TestUtils.generateString(1001), -100,10),
                Arguments.of(null, TestUtils.generateString(1001), 100,-10),
                Arguments.of(null, TestUtils.generateString(1001), -100,-10),
                Arguments.of(TestUtils.generateString(41), TestUtils.generateString(1001), 100, 10),
                Arguments.of(TestUtils.generateString(41),TestUtils.generateString(1001), -1, 10),
                Arguments.of(TestUtils.generateString(41),TestUtils.generateString(1001), 100, -1),
                Arguments.of(TestUtils.generateString(41),TestUtils.generateString(1001), -1, -1));
    }
    static List<Arguments> provideUpdateRevenueSource() {
        return Arrays.asList(Arguments.of(-1, 100, "normal intro"),
                Arguments.of(1, -100, "normal intro"),
                Arguments.of(1, 100, null),
                Arguments.of(1, -100, null),
                Arguments.of(-1, -100, "normal intro"),
                Arguments.of(-1, 100, null),
                Arguments.of(-1, -100, null),
                Arguments.of(1, 100, TestUtils.generateString(1001)),
                Arguments.of(1, -100, TestUtils.generateString(1001)),
                Arguments.of(-1, -100, TestUtils.generateString(1001)),
                Arguments.of(-1, 100, TestUtils.generateString(1001)));
    }

}
