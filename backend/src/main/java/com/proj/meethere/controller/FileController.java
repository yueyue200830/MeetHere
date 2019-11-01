package com.proj.meethere.controller;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Tresaresa
 * @Date 2019/11/1 12:18
 */
public class FileController {

    public static final String BASE_PATH = "/upload/";

    @RequestMapping(value = "/UploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public String uploadNewPhoto(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            System.out.println("upload photo failed");
            return null;
        }

        String origionalFilename = file.getOriginalFilename();

        // file suffix
        String ext = null;
        if(origionalFilename.contains(".")) {
            ext = origionalFilename.substring(origionalFilename.lastIndexOf("."));
        }
        else {
            ext = "";
        }

        // generate random file name
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = uuid + ext;
        String dirPath = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String filePath = BASE_PATH.endsWith("/") ? BASE_PATH + dirPath : BASE_PATH + "/" + dirPath;

        File targetFile = new File(filePath, filename);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }
        else {
            targetFile.delete();
        }

        try {
            file.transferTo(targetFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "/" + filePath;

    }
}
