package com.proj.meethere.service;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import com.proj.meethere.service.UserRevenueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @Author Tresaresa
 * @Date 2019/12/26 19:43
 */
@SpringBootTest
public class UserRevenueServiceTest {

    @Autowired
    private UserRevenueService userRevenueService;

    @MockBean
    private RevenueRepository revenueRepository;

    static private final int rvnId = 1;
    static private final String rvnName = "篮球馆";
    static private final int rvnRoomnum = 4;
    static private final String rvnIntro = "场馆介绍";
    static private final int rvnPrice = 40;

    static private Revenue revenue;
    static private List<Revenue> revenues = new ArrayList<>();

    @BeforeAll
    static void init() {
        revenue = new Revenue(rvnId, rvnName, rvnRoomnum, rvnIntro, rvnPrice);
    }

    @Test
    void should_return_all_revenue() {
        // stubbing
        when(revenueRepository.getAllRvnInfo()).thenReturn(revenues);

        // calling
        List<Revenue> result = userRevenueService.getAllVenue();

        // verifying result
        assertEquals(revenues, result);

        // verifying invoked times
        verify(revenueRepository, times(1)).getAllRvnInfo();

        verifyNoMoreInteractions(revenueRepository);
    }

    @Test
    void should_return_all_revenue_name() {
        List<String> names = new ArrayList<>();
        names.add(rvnName);

        // stubbing
        when(revenueRepository.getRvnName()).thenReturn(names);

        // calling
        List<String> result = userRevenueService.getAllenueName();

        // verifying result
        assertEquals(names, result);

        // verifying invoked times
        verify(revenueRepository, times(1)).getRvnName();

        verifyNoMoreInteractions(revenueRepository);
    }


}
