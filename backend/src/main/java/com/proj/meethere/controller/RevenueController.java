package com.proj.meethere.controller;


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

    @RequestMapping(value = "/searchVenue", method = RequestMethod.POST)
    @ResponseBody
    public String searchVenue(@RequestParam(name = "id") int id) {
        List<Revenue> revenueList = revenueRepository.getSpecificRvn(id);
        String result = "";
        if(revenueList.size() == 1) {
            result = new JSONObject(revenueList.get(0)).toString();
        } else{
            result = "duplicate";
        }
        return result;
    }

    @RequestMapping(value = "/checkVenue", method = RequestMethod.GET)
    @ResponseBody
    public String checkVenue(@RequestParam(name = "id") int id) {
        List<Revenue> revenueList = revenueRepository.getSpecificRvn(id);
        JSONObject jsonObject = new JSONObject();
        if(revenueList.size() == 1) {
            Revenue venue = revenueList.get(0);
            jsonObject.put("id", venue.getId());
            jsonObject.put("rvn_price",venue.getRvnPrice());
            jsonObject.put("rvn_intro",venue.getRvnIntro());
            return jsonObject.toString();
        }else {
            return "duplicate";
        }
    }

    @RequestMapping(value = "/modifyVenue", method = RequestMethod.POST)
    @ResponseBody
    public int modifyVenue(@RequestBody String venueInfo) {
        JSONObject jsonObject = new JSONObject(venueInfo);
        int id = jsonObject.getInt("id");
        int rvnPrice = jsonObject.getInt("rvn_price");
        String rvnIntro = jsonObject.getString("rvn_intro");
        return revenueRepository.updateRvnInfo(rvnPrice,rvnIntro,id);
    }

    @RequestMapping(value = "/addRevenue", method = RequestMethod.POST)
    @ResponseBody
    public int addNewRevenue(@RequestParam("rvn_name") String rvn_name, @RequestParam("rvn_roomnum") int rvn_roomnum,
                             @RequestParam("rvn_price") int rvn_price, @RequestParam("rvn_intro") String rvn_intro) {
        return revenueRepository.insertNewRevenue(rvn_name, rvn_roomnum, rvn_price, rvn_intro);
    }
}
