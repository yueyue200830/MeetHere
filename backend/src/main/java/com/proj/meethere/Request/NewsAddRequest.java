package com.proj.meethere.Request;

import lombok.Data;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

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
