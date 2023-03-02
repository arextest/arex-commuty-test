package com.arextest.agent.test.service.httpclient;

import com.arextest.agent.test.entity.HttpMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * @author yongwuhe
 */
@Slf4j
@Component
public class CommonClientTestService extends HttpClientTestServiceBase{

    public String commonClientTest(String parameterData, boolean isGzip) {
        String commonHttpClientGetResponse = commonHttpClient(HttpMethodEnum.GET, parameterData,isGzip);
        String commonHttpClientPostResponse = commonHttpClient(HttpMethodEnum.POST, parameterData, isGzip);
        return String.format("commonClientGetResponse: %s, commonHttpClientPostResponse: %s", commonHttpClientGetResponse, commonHttpClientPostResponse);
    }

    private String commonHttpClient(HttpMethodEnum type, String parameterData, boolean isGzip) {
        HttpURLConnection connection;
        String result = null;

        try {
            URL url = null;
            if (type.equals(HttpMethodEnum.POST)) {
                url = new URL(POST_URL);
            }

            if (type.equals(HttpMethodEnum.GET)) {
                url = new URL(GET_URL);
            }

            if (url == null) {
                return "url is null";
            }
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type.toString());
            connection.setDoOutput(true);

            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            if(isGzip){
                connection.setRequestProperty("accept-encoding","gzip, deflate, br");
            }


            if (type.equals(HttpMethodEnum.POST)) {
//                String parameterData = "{\"userId\": 3, \"title\": \"Programmer\", \"body\":\"C++\"}";
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                byte[] t = parameterData.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.write(t);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            InputStream is = null;

            try {
                String contentEncoding = connection.getHeaderField("content-encoding");
                if((contentEncoding != null)&&(contentEncoding.equals("gzip"))){
                    is = new GZIPInputStream(connection.getInputStream());
                }else{
                    is = connection.getInputStream();
                }

                Reader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                }
                result = sbf.toString();
            } catch (Throwable ex) {
                log.error("CommonClientTestService", ex);
            }

            connection.disconnect();
            return result;
        } catch (Exception ex) {
            log.error("CommonClientTestService", ex);
        }
        return result;
    }
}
