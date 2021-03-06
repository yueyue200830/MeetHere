package com.proj.meethere.utils;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * @Author Tresaresa
 * @Date 2019/12/27 12:08
 */
public class TestUtils {
    static public JSONArray inputStream2JSONArray(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] b = new byte[4096];
        int n;
        while((n = inputStream.read(b)) != -1) {
            stringBuffer.append(new String(b, 0, n));
        }
        JSONArray jsonArray = new JSONArray(stringBuffer.toString());
        return jsonArray;
    }

    static public String inputStream2String(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] b = new byte[4096];
        int n;
        while((n = inputStream.read(b)) != -1) {
            stringBuffer.append(new String(b, 0, n));
        }
        return stringBuffer.toString();
    }
    static public String generateString(int length) {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
