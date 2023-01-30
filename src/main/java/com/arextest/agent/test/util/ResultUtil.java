package com.arextest.agent.test.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author daixq
 * @date 2023/01/17
 */
@Slf4j
public class ResultUtil {
    private ResultUtil(){}

    public static void responseJson(ServletResponse response, Map<String, Object> resultMap){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(resultMap));
        } catch (Exception ex) {
            log.error("[responseJson]" + ex);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap){
        resultMap.put("message","Success");
        resultMap.put("code", 200);
        return resultMap;
    }

    public static Map<String, Object> resultError(Map<String, Object> resultMap){
        resultMap.put("message","Fail");
        resultMap.put("code",500);
        return resultMap;
    }

    public static Map<String, Object> resultCode(Integer code,String msg){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message",msg);
        resultMap.put("code",code);
        return resultMap;
    }

}
