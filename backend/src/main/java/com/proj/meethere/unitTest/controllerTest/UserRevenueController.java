package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.entity.Revenue;
import com.proj.meethere.unitTest.serviceTest.UserRevenueService;
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
    private UserRevenueService userRevenueService;

    /**
     * @return 获取所有场馆信息，按照id排序
     */
    @RequestMapping(value = "/getVenueUserPage", method = RequestMethod.POST)
    @ResponseBody
    public String getAllVenueUserPage() {
        List<Revenue> revenueList = userRevenueService.getAllVenue();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(revenueList);
        return jsonArray.toString();
    }

    /**
     * @return 返回所有场馆的名称，按照id排序
     */
    @RequestMapping(value = "/getVenueName", method = RequestMethod.POST)
    @ResponseBody
    public String getVenueName() {
        List<String> revenueList = userRevenueService.getAllenueName();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(revenueList);
        return jsonArray.toString();
    }
}
