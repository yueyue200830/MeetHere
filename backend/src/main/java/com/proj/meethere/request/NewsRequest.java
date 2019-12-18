package com.proj.meethere.request;
import lombok.Data;

/**
 * @author Yiqing Tao
 * @date 2019-12-04 16:40
 */
@Data
public class NewsRequest {
    private String title;
    private boolean isDelete;
    private int id;
    private String newsContent;
    private String newsTitle;
    private String newsPhoto;

}
