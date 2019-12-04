package com.proj.meethere.Response;
import lombok.Data;
/**
 * @author Yiqing Tao
 * @date 2019-12-04 17:01
 */
@Data
public class RevenueResponse {
    private String title;
    private int isDelete;
    private int id;
    private int rvnPrice;
    private  String rvnIntro;
}
