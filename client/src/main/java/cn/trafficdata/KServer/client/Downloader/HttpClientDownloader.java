package cn.trafficdata.KServer.client.Downloader;

import cn.trafficdata.KServer.client.Controller.Controller;
import cn.trafficdata.KServer.client.Utils.URLCanonicalizer;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.UrlUtils;
import org.apache.http.*;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kinglf on 2016/11/8.
 */
public class HttpClientDownloader implements Downloader {
    protected PoolingHttpClientConnectionManager connectionManager;
    private CloseableHttpClient httpClient;
    private HttpClientConfig httpClientConfig;

    private long theLastRequestTime;


    /**
     * 需要整理,太特么的乱了
     *
     * @param httpClientConfig
     */
    public HttpClientDownloader(final HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
        //1.根据配置初始化httpClient
        //
        CredentialsProvider credentialsProvider = null;
        //
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        //
        httpClientBuilder.setUserAgent(httpClientConfig.getUSER_AGENT());
        //
        httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                if (!httpRequest.containsHeader("Accept-Encoding")) {
                    httpRequest.addHeader("Accept-Encoding", httpClientConfig.getACCEPT_ENCODING());
                }
            }
        });
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(5, true));

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpClientConfig.getConnectionTimeout())
                .setSocketTimeout(httpClientConfig.getSocketTimeout())
                .setConnectTimeout(httpClientConfig.getConnectionTimeout())
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();

        RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
        connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

        if (httpClientConfig.isIncludeHttpsPages()) {//copy of crawler4j[PageFetcher.java]
            try { // Fixing: https://code.google.com/p/crawler4j/issues/detail?id=174
                // By always trusting the ssl certificate
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(final X509Certificate[] chain, String authType) {
                        return true;
                    }
                }).build();
                SSLConnectionSocketFactory sslsf =
                        new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                connRegistryBuilder.register("https", sslsf);
            } catch (Exception e) {
//                logger.warn("Exception thrown while trying to register https");
//                logger.debug("Stacktrace", e);
            }
        }
        Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
        connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
        connectionManager.setMaxTotal(100);//最大连接数
        connectionManager.setDefaultMaxPerRoute(100);

        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setConnectionManager(connectionManager);
        httpClient = httpClientBuilder.build();


    }

    public Page download(WebUrl webUrl) {
        /**
         * before
         */
        Page page = null;
        HttpUriRequest httpUriRequest = getHttpUriRequest(webUrl);
        /**
         * doing
         */
        try {
            long now = (new Date()).getTime();
            if ((now - theLastRequestTime) < httpClientConfig.getPolitenessDelay()) {
                Thread.sleep(httpClientConfig.getPolitenessDelay() - (now - theLastRequestTime));
            }

            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            theLastRequestTime = (new Date()).getTime();
            page=new Page();
            page.setWebUrl(webUrl);
            page.setProjectId(webUrl.getProjectID());
            page.load(response.getEntity());
            page.setFetchResponseHeaders(response.getAllHeaders());
            page.setStatusCode(response.getStatusLine().getStatusCode());
            /**
             * 重定向处理
             */
            Header header = response.getFirstHeader("Location");
            if (header != null) {//进行过重定向
                page.setRedirect(true);
                String movedToUrl = URLCanonicalizer.getCanonicalURL(header.getValue(), webUrl.getUrl());
                page.setRedirectedToUrl(movedToUrl);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }


    private HttpUriRequest getHttpUriRequest(WebUrl webUrl) {
        RequestBuilder requestBuilder = selectRequestMethod(webUrl).setUri(webUrl.getUrl());
        String referer=null;
        if((referer=webUrl.getReferer())!=null){
            requestBuilder.setHeader("Referer", referer);
        }
        String cookies=null;
        if((cookies=webUrl.getCookies())!=null){
            requestBuilder.setHeader("Cookie", cookies);
        }

        return requestBuilder.build();
    }

    protected RequestBuilder selectRequestMethod(WebUrl webUrl) {
        int method = webUrl.getType();
        switch (method) {
            case 0:
                return RequestBuilder.get();
            case 1:
                RequestBuilder requestBuilder = RequestBuilder.post();
                Map<String, String> prams = webUrl.getPrams();
                for (Map.Entry<String, String> map : prams.entrySet()) {
                    requestBuilder.addParameter(map.getKey(), map.getValue());
                }
                return requestBuilder;
            default:
                return RequestBuilder.get();
        }
    }




}
