package com.proj.meethere.service;

import com.proj.meethere.response.RevenueResponse;
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

    /**
     * get all of the information of the revenue.
     * @return a list of revenue entity to front end.
     */
    public List<Revenue> getAllRevenue() {
        return revenueRepository.getAllRvnInfo();
    }

    /**
     * get specified revenue information
     * @param id the id of the desired revenue.
     * @return a list of revenue information.
     */
    public List<Revenue> searchRevenue(int id) {
        if(id < 0) {
            return new ArrayList<Revenue>();
        } else {
            return revenueRepository.getRevenueById(id);
        }
    }

    /**
     * to get the revenue information
     * @param id the revenue id
     * @return the desired response entity of revenue
     */
    public List<RevenueResponse> getRevenue(int id) {
        if(id < 0) {
            return new ArrayList<RevenueResponse>();
        }
        List<Revenue> revenueList = revenueRepository.getRevenueById(id);

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

    /**
     * add revenue
     * @param rvnIntro revenue introduction (1000 characters limit)
     * @param price revenue price
     * @param rvnName revenue name (40 characters limit)
     * @param roomNum max revenue room number
     * @return whether add operation is successful. 1 for success, 0 for failure.
     */
    public int addRevenue(String rvnIntro, int price, String rvnName,int roomNum) {
        if(rvnIntro == null || price < 0 || rvnName == null|| roomNum < 0 || rvnIntro.length() > 1000 || rvnName.length() > 40) {
            return 0;
        } else {
            if(revenueRepository.searchDuplicateByName(rvnName).size() != 0) {
                return 0;
            } else {
                return revenueRepository.insertNewRevenue(rvnName, roomNum, price, rvnIntro);
            }
        }
    }

    /**
     * modify the revenue by id
     * @param price revenue price
     * @param intro revenue introduction (1000 characters limit)
     * @param id revenue id
     * @return whether modification is successful. 1 for success, 0 for failure.
     */
    public int modifyRevenue(int price, String intro, int id) {
        if(id < 0 || price < 0 || intro == null || intro.length() > 1000) {
            return 0;
        } else {
            return revenueRepository.updateRvnInfo(price, intro, id);
        }
    }

    /**
     * to get the names of revenue
     * @return a list of name string.
     */
    public List<String> getVenueName() {
        List<String> result = revenueRepository.selectRevenueName();
        System.out.println(result);
        return result;
    }

    /**
     * to get the order numbers at the assigned date
     * @param date the date specified
     * @return a list of int, indicating the order numbers.
     */
    public List<Integer> getOrderNum(String date) {
        if(date == null){
            return new ArrayList<Integer>();
        }
        if(! date.contains("-")) {
            return new ArrayList<Integer>();
        } else {
            List<Integer> revenueCount = revenueRepository.selectStatistic(date);
            return revenueCount;
        }
    }
}
