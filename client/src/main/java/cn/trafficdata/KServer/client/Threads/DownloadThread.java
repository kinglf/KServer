package cn.trafficdata.KServer.client.Threads;

import cn.trafficdata.KServer.client.Controller.Controller;
import cn.trafficdata.KServer.client.Downloader.Downloader;
import cn.trafficdata.KServer.client.Downloader.HttpClientDownloader;
import cn.trafficdata.KServer.client.configurable.Environment;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.UrlUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kinglf on 2016/11/7.
 */
public class DownloadThread implements Runnable {
    protected String domain;
    protected HttpClientConfig httpClientConfig;
    protected Downloader downloader;
    private String theLastReferer;
    private String theLastCookies;
    private boolean isNeedClearRC = false;

    public DownloadThread(String domain) {
        this.domain = domain;
        httpClientConfig = Environment.getHttpClientConfig(domain);
        //根据配置初始化个下载环境
        downloader = new HttpClientDownloader(httpClientConfig);
        isNeedClearRC = httpClientConfig.isDeleteCookiesWhenDialing();
    }

    public void run() {
        while (true) {

            if (!checkDaemonThreadIsDailing()) {
                try {
                    WebUrl webUrl = Controller.WebUrlListMap.get(domain).remove(0);
                    webUrl = addRCToWebUrl(webUrl);
                    Page page = downloader.download(webUrl);
                    boolean pass = checkPage(page);
                    if (!pass) {
                        putWebUrlList(webUrl);
                        if (httpClientConfig.isDialWhenRequestError()) {
                            //
                            //重拨
                            DaemonThread.pauseSwitch = true;
                        }
                    } else {
                        putPageList(page);
                        //存储referer和cookies
                        this.theLastReferer = webUrl.getUrl();
                        this.theLastCookies = getCookieFromHeader(page.getFetchResponseHeaders());
                    }

                } catch (IndexOutOfBoundsException iobe) {
                    // 请求不到任务.结束循环,让线程死亡
                    break;
                }
            }else{
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 检查守护线程是否要进入重拨状态
     *
     * @return
     */
    private boolean checkDaemonThreadIsDailing() {
        if (DaemonThread.pauseSwitch) {
            DaemonThread.readyForDailingMap.put(domain,"ready");
            if (isNeedClearRC) {
                this.theLastCookies = "";
                this.theLastReferer = "";
            }
            return true;
        } else {
            isNeedClearRC = httpClientConfig.isDeleteCookiesWhenDialing();
            return false;
        }
    }

    /**
     * 先检查状态码,以后可以加入其他的检测,比如内容
     *
     * @param
     * @return
     */
    private boolean checkPage(Page page) {
        int statusCode = page.getStatusCode();
        if (statusCode >= 200 && statusCode <= 299) {// is 2XX, everything looks ok
            if (httpClientConfig.isCheckRequestError()) {
                if (!checkByteArrIncludeRegex(page.getContentData(), httpClientConfig.getRequestErrorIncludeRegex())) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 检查请求错误,暂时未写
     */
    private boolean checkByteArrIncludeRegex(byte[] contentData, String requestErrorIncludeRegex) {
        String contentDataStr = new String(contentData);
        Pattern p = Pattern.compile(requestErrorIncludeRegex);
        Matcher m = p.matcher(contentDataStr);
        if (m.find()) {
            return true;
        }
        return false;
    }

    private void putWebUrlList(WebUrl webUrl) {
        Controller.WebUrlListMap.get(UrlUtils.getDomain(webUrl.getUrl())).add(webUrl);
    }

    private void putPageList(Page page) {
        Controller.PageResultList.add(page);
    }

    private WebUrl addRCToWebUrl(WebUrl webUrl) {
        //组装webUrl
        if (!webUrl.getReferer().equals("")) {
            if (httpClientConfig.isUseReferer()) {
                if (httpClientConfig.isUseTheLastReferer()) {
                    webUrl.setReferer(this.theLastReferer);
                } else {
                    String referer = httpClientConfig.getREFERER();
                    webUrl.setReferer(referer);
                }
            }
        }
        if (!webUrl.getCookies().equals("")) {
            if (httpClientConfig.isUseTheLastCookies()) {
                webUrl.setCookies(this.theLastCookies);
            } else {
                webUrl.setCookies(getStrFromCookieMap(httpClientConfig.getCookies()));
            }
        }
        return webUrl;
    }

    protected String getStrFromCookieMap(Map<String, String> cookies) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> map : cookies.entrySet()) {
            stringBuilder.append(map.getKey() + "=" + map.getValue() + "; ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    protected String getCookieFromHeader(Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equals("Cookie")) {
                return header.getValue();
            }
        }
        return "";
    }
}
