package com.proj.meethere.controller;


import com.proj.meethere.Response.RevenueResponse;
import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private RevenueRepository revenueRepository;

    @RequestMapping(value = "/getVenue", method = RequestMethod.POST)
    @ResponseBody
    List<String> getAllVenue() {
        List<Revenue> revenueList = revenueRepository.getAllRvnInfo();
        List<String> result = new ArrayList<>();

        for(Revenue revenue:revenueList) {
            JSONObject jsonObject = new JSONObject(revenue);
            result.add(jsonObject.toString());
        }
        return result;
    }

    @RequestMapping(value = "/searchVenue/{condition}", method = RequestMethod.GET)
    @ResponseBody
    public List<RevenueResponse> searchVenue(@PathVariable String condition) {
        List<Revenue> revenueList = revenueRepository.getSpecificRvn(Integer.parseInt(condition));
        List<RevenueResponse> revenueResponseList = new ArrayList<>();
        for(Revenue revenue : revenueList) {
            RevenueResponse revenueResponse = new RevenueResponse();
            revenueResponse.setRvnPrice(revenue.getRvnPrice());
            revenueResponse.setRvnIntro(revenue.getRvnIntro());
            revenueResponse.setId(revenue.getId());
            revenueResponseList.add(revenueResponse);
        }
        return revenueResponseList;
    }

    @RequestMapping(value = "/checkVenue/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public List<RevenueResponse> checkVenue(@PathVariable String temp) {
        List<Revenue> revenueList = revenueRepository.getSpecificRvn(Integer.parseInt(temp));
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

    @RequestMapping(value = "/modifyVenue", method = RequestMethod.POST)
    @ResponseBody
    public int modifyVenue(@RequestBody RevenueResponse revenueResponse) {
        return revenueRepository.updateRvnInfo(revenueResponse.getRvnPrice(), revenueResponse.getRvnIntro(), revenueResponse.getId());
    }

    @RequestMapping(value = "/addRevenue", method = RequestMethod.POST)
    @ResponseBody
    public int addNewRevenue(@RequestParam("rvn_name") String rvn_name, @RequestParam("rvn_roomnum") int rvn_roomnum,
                             @RequestParam("rvn_price") int rvn_price, @RequestParam("rvn_intro") String rvn_intro) {
        return revenueRepository.insertNewRevenue(rvn_name, rvn_roomnum, rvn_price, rvn_intro);
    }

    @RequestMapping(value = "/getVenueUserPage", method = RequestMethod.POST)
    @ResponseBody
    public String getAllVenueUserPage() {
        List<Revenue> revenueList = revenueRepository.getAllRvnInfo();

        JSONArray result = new JSONArray();
        result.put(revenueList);
        String jsonstring = result.toString();
        System.out.println(jsonstring);

        return jsonstring;
    }
}
