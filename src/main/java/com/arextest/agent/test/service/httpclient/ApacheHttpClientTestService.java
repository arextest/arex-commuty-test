package com.arextest.agent.test.service.httpclient;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.nio.charset.StandardCharsets;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.client.entity.GzipCompressingEntity;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @author yongwuhe
 */
@Component
@Slf4j
public class ApacheHttpClientTestService extends HttpClientTestServiceBase {
    private CloseableHttpAsyncClient HTTP_ASYNC_CLIENT;

    public String test(String parameterData) {
        String asyncGetResponse = "", asyncPostResponse = "";
        try {
            HTTP_ASYNC_CLIENT = HttpAsyncClients.createDefault();
            HTTP_ASYNC_CLIENT.start();
            asyncGetResponse = "asyncGet response: " + asyncGet().get();
            asyncPostResponse = "asyncPost response: " + asyncPost(parameterData).get();
        } catch (Exception ex) {
            log.error("ApacheHttpClientTestService exception", ex);
        } finally {
            try {
                HTTP_ASYNC_CLIENT.close();
            } catch (IOException ex) {
                log.error("HTTP_ASYNC_CLIENT.close exception", ex);
            }
        }
        String syncGetResponse = syncGet();
        String syncPostResponse = syncPost();
        return asyncGetResponse + "\n" +
                asyncPostResponse + "\n" +
                syncGetResponse + "\n" +
                syncPostResponse + "\n";
    }

    public String testGzip(){
        return  syncPostGzip1();
    }

    private String syncPostGzip1() {
        String result;
        String request = null;
        String input = "{\"userId\":\"5\", \"title\":\"json\", \"body\":\"learning\"}";

        HttpPost httpPost = new HttpPost(String.format(POST_URL));
        httpPost.setConfig(createRequestConfig());
        try {
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(input.getBytes(StandardCharsets.UTF_8));
            GzipCompressingEntity gzippedEntity = new GzipCompressingEntity(byteArrayEntity);
            httpPost.setEntity(gzippedEntity);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
            httpPost.setHeader(gzippedEntity.getContentEncoding());
        } catch (Exception ex) {
            log.error("UnsupportedEncodingException", ex);
        }
        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(httpPost);
        ) {

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
                httpPost.getEntity().writeTo(byteArrayOutputStream);
                request = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                System.out.printf("syncPostGzip1: %s\n", request);
            } catch (Exception e) {
                System.out.println("syncPostGzip1 read request exception: " + e.getMessage());
            }

            result = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
        } catch (Exception ex) {
            result = "apacheHttpClientPost exception: " + ex.getMessage();
        }
        return "syncPostGzip1 request: " + request + "\n"
                +  "syncPostGzip1 response: " + result;
    }

    private String syncGet() {
        String result;
        HttpGet httpGet = new HttpGet(String.format("%s?id=2", POST_URL));
        httpGet.setConfig(createRequestConfig());
        try (
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet)
        ) {
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            result = "apacheHttpClientGet exception: " + ex.getMessage();
        }
        return "syncGet response: " + result;
    }

    private String syncPost() {
        String result;
        List<BasicNameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("userId", "5"));
        formParams.add(new BasicNameValuePair("title", "json"));
        formParams.add(new BasicNameValuePair("body", "learning"));
        HttpPost httpPost = new HttpPost(String.format(POST_URL));
        httpPost.setConfig(createRequestConfig());
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
        } catch (Exception ex) {
            log.error("UnsupportedEncodingException", ex);
        }
        try (
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost)
        ) {
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            result = "apacheHttpClientPost exception: " + ex.getMessage();
        }
        return "syncPost response: " + result;
    }

    private RequestConfig createRequestConfig() {
        return RequestConfig.custom()
            .setConnectionRequestTimeout(2000)
            .setConnectTimeout(2000)
            .setSocketTimeout(5000).build();
    }

    private CompletableFuture<String> asyncGet() {
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.setConfig(createRequestConfig());
        return asyncExecute(httpGet);
    }

    private CompletableFuture<String> asyncPost(String parameterData) {
        HttpPost httpPost = new HttpPost(POST_URL);
        List<NameValuePair> list = new LinkedList<>();
        list.add( new BasicNameValuePair("userId","4" ));
        list.add( new BasicNameValuePair("title","Delphi" ));
        list.add( new BasicNameValuePair("body","Starter" ));

        if(StringUtil.isNotBlank(parameterData) && StringUtil.isNotEmpty(parameterData)){
            list.add( new BasicNameValuePair("input", parameterData ));
        }

        UrlEncodedFormEntity entityParam = null;
        try {
            entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            log.error("UnsupportedEncodingException", ex);
        }
        httpPost.setConfig(createRequestConfig());
        httpPost.setEntity(entityParam);
        return asyncExecute(httpPost);
    }

    private CompletableFuture<String> asyncExecute(HttpRequestBase httpRequest) {
        CompletableFuture<String> responseFuture = new CompletableFuture<>();
        try {
            HTTP_ASYNC_CLIENT.execute(httpRequest, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    String result = "";
                    try {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            result = EntityUtils.toString(entity, "UTF-8");
                        }
                    } catch (Exception e) {
                        log.error("httpAsyncClient completed exception", e);
                    }
                    responseFuture.complete(result);
                }

                public void failed(final Exception ex) {
                    responseFuture.complete("exception: " + ex.getMessage());
                }

                public void cancelled() {
                    responseFuture.complete("cancelled");
                }
            });
        } catch (Exception ex) {
            log.error("httpAsyncClient exception", ex);
            responseFuture.complete("exception: " + ex.getMessage());
        }

        return responseFuture;
    }
}
