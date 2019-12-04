package com.proj.meethere.controller;

import com.proj.meethere.dao.RevenueRepository;
import com.proj.meethere.entity.Revenue;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019-12-02
 * User Revenue Controller
 */
@Controller
@CrossOrigin(origins = "*")
public class UserRevenueController {
    @Autowired
    private RevenueRepository revenueRepository;

    @RequestMapping(value = "/getVenueUserPage", method = RequestMethod.POST)
    @ResponseBody
    public String getAllVenueUserPage() {
        List<Revenue> revenueList = revenueRepository.getAllRvnInfo();

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(revenueList);

        return jsonArray.toString();
    }
}
