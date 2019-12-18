package com.proj.meethere.response;
import lombok.Data;
/**
 * @author Yiqing Tao
 * @date 2019-12-04 17:01
 */
@Data
public class RevenueResponse {
    private String title;
    private boolean isDelete;
    private int id;
    private int rvnPrice;
    private  String rvnIntro;
}
