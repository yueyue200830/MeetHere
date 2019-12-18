package com.proj.meethere.controller;


import com.proj.meethere.response.RevenueResponse;
import com.proj.meethere.service.RevenueService;
import com.proj.meethere.entity.Revenue;
import org.springframework.beans.factory.annotation.Autowired;
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
//
//    @RequestMapping(value = "/addRevenue", method = RequestMethod.POST)
//    @ResponseBody
//    public int addNewRevenue(@RequestParam("rvn_name") String rvn_name, @RequestParam("rvn_roomnum") int rvn_roomnum,
//                             @RequestParam("rvn_price") int rvn_price, @RequestParam("rvn_intro") String rvn_intro) {
//        return revenueRepository.insertNewRevenue(rvn_name, rvn_roomnum, rvn_price, rvn_intro);
//    }
}
