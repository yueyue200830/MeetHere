package com.proj.meethere.unitTest.controllerTest;

import com.proj.meethere.entity.Message;
import com.proj.meethere.unitTest.serviceTest.UserMessageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Tresaresa
 * @Date 2019-12-02
 * User Message Controller
 */
@Controller
@CrossOrigin(origins = "*")
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    /**
     * @return 获取最新的10条消息
     */
    @RequestMapping(value = "/getLatestMessage", method = RequestMethod.POST)
    @ResponseBody
    public String getLatestMessagesTen() {
        List<Message> latestMessages = userMessageService.selectMessagesPartial(10);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(latestMessages);
        System.out.println("新闻"+jsonArray.toString());
        return jsonArray.toString();
    }

    /**
     * @param id 用户id
     * @return 获取当前用户的最新10条消息（含未通过审核的消息）
     */
    @RequestMapping(value = "/getMyMessage", method = RequestMethod.GET)
    @ResponseBody
    public String getMyLatestMessagesTen(@RequestParam("userId") int id) {
        List<Message> latestMessages = userMessageService.selectMessagesPartialById(10, id);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(latestMessages);
        return jsonArray.toString();
    }

    /**
     * @param lastTime 当前页面显示的最新message的时间
     * @param number 获取的message条数
     * @return 返回最新未显示的最多10条消息
     */
    @RequestMapping(value = "/getMoreMessage", method = RequestMethod.GET)
    @ResponseBody
    public String getMoreMessages(@RequestParam("lastTime") String lastTime, @RequestParam("number") int number) {
        List<Message> moreMessages = userMessageService.selectMoreMessagesBeforePartial(lastTime, number);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(moreMessages);
        return jsonArray.toString();
    }

    /**
     * @param lastTime 当前页面显示的最新message的时间
     * @param number 获取的message条数
     * @param id 当前用户id
     * @return 返回当前用户最新未显示的最多10条消息（含未通过审核的消息）
     */
    @RequestMapping(value = "/getMoreMyMessage", method = RequestMethod.GET)
    @ResponseBody
    public String getMoreMyMessages(@RequestParam("lastTime") String lastTime, @RequestParam("number") int number, @RequestParam("userId") int id) {
        List<Message> moreMessages = userMessageService.selectMoreMessagesBeforePartialById(lastTime, number, id);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(moreMessages);
        return jsonArray.toString();
    }

    /**
     * @param newForm 用户输入的消息title和content
     * @param userId 用户id
     * @return 返回受影响行数
     */
    @RequestMapping(value = "/addMessage", method = RequestMethod.GET)
    @ResponseBody
    public int addNewMessage(@RequestParam("addMessageForm") String newForm, @RequestParam("id") int userId) {
        JSONObject jsonObject = new JSONObject(newForm);
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        return userMessageService.addNewMessage(title, content, userId);
    }

    /**
     * @param id 消息id
     * @return 成功返回1，失败返回0
     */
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.GET)
    @ResponseBody
    public int deleteMessage(@RequestParam("id") int id) {
        return userMessageService.deleteMessage(id);
    }

    /**
     * @param editMessageForm 包括content，title，id
     * @return 成功返回1，失败返回0
     */
    @RequestMapping(value = "/editMessage", method = RequestMethod.GET)
    @ResponseBody
    public int updateMessage(@RequestParam("editMessageForm") String editMessageForm) {
        JSONObject jsonObject = new JSONObject(editMessageForm);
        String title = jsonObject.getString("messageTitle");
        String content = jsonObject.getString("messageContent");
        int id = jsonObject.getInt("id");
        return userMessageService.updateMessage(title, content, id);
    }
}
