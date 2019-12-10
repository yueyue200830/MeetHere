package com.proj.meethere.Service;

import com.proj.meethere.Response.RevenueResponse;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 21:56
 */
@Service
public class RevenueService {
    @Autowired
    RevenueRepository revenueRepository;

    public List<Revenue> getAllRevenue() {
        return revenueRepository.getAllRvnInfo();
    }

    public List<Revenue> searchRevenue(int id) {
        return revenueRepository.getSpecificRvn(id);
    }

    public List<RevenueResponse> getRevenue(int id) {
        List<Revenue> revenueList = revenueRepository.getSpecificRvn(id);

        List<RevenueResponse> revenueResponsesList = new ArrayList<>();
        for(Revenue revenue: revenueList) {
            RevenueResponse revenueResponse = new RevenueResponse();
            revenueResponse.setId(revenue.getId());
            revenueResponse.setRvnIntro(revenue.getRvnIntro());
            revenueResponse.setRvnPrice(revenue.getRvnPrice());
            revenueResponsesList.add(revenueResponse);
        }
        return revenueResponsesList;
    }

    public int modifyRevenue(int price, String intro, int id) {
        return revenueRepository.updateRvnInfo(price, intro, id);
    }
}
