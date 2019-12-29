package com.proj.meethere.request;

import lombok.Data;

/**
 * @author Yiqing Tao
 * @date 2019-12-28 13:49
 */
@Data
public class RevenueRequest {
    private String title;
    private boolean isDelete;
    private String rvnName;
    private int rvnRoomNum;
    private int rvnPrice;
    private String rvnIntro;
}
