package com.proj.meethere.controller;


import com.proj.meethere.response.RevenueResponse;
import com.proj.meethere.service.RevenueService;
import com.proj.meethere.entity.Revenue;
import com.proj.meethere.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * Revenue Controller
 */
@Controller
@CrossOrigin(origins = "*")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @RequestMapping(value = "/getVenue", method = RequestMethod.POST)
    @ResponseBody
    List<Revenue> getAllVenue() {
       return revenueService.getAllRevenue();
    }

    @RequestMapping(value = "/searchVenue/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<Revenue> searchVenue(@PathVariable String condition) {
        return revenueService.searchRevenue(Integer.parseInt(condition));
    }

    @RequestMapping(value = "/checkVenue/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public List<RevenueResponse> checkVenue(@PathVariable String temp) {
       return revenueService.getRevenue(Integer.parseInt(temp));
    }

    @RequestMapping(value = "/modifyVenue", method = RequestMethod.POST)
    @ResponseBody
    public int modifyVenue(@RequestBody RevenueResponse revenueResponse) {
        System.out.println(revenueResponse.getRvnPrice());
        return revenueService.modifyRevenue(revenueResponse.getRvnPrice(), revenueResponse.getRvnIntro(), revenueResponse.getId());
    }

    @RequestMapping(value = "/getVenueNameForChart/{value}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getVenueName(@PathVariable String value) {
        System.out.println(value);
        return revenueService.getVenueName();
    }

    @RequestMapping(value = "/getStatistic/{value}", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getOrderNum(@PathVariable String value) {
        System.out.println("orderNum");
        return revenueService.getOrderNum(value);
    }

}
