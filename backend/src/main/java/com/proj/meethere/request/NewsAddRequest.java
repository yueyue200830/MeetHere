package com.proj.meethere.request;

import lombok.Data;

/**
 * @author Yiqing Tao
 * @date 2019-12-10 13:59
 */
@Data
public class NewsAddRequest {
    private String title;
    private boolean isDelete;
    private int id;
    private String newsContent;
    private String newsTitle;
    private String newsPhoto;
}
