package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019/12/18 13:51
 */
@Service
public class UserRevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    public List<Revenue> getAllVenue() {
        return revenueRepository.getAllRvnInfo();
    }

    public List<String> getAllenueName() {
        return revenueRepository.getRvnName();
    }
}
