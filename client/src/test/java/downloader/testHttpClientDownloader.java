package downloader;

import cn.trafficdata.KServer.client.Downloader.HttpClientDownloader;
import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.WebUrl;

import java.io.UnsupportedEncodingException;

/**
 * Created by Kinglf on 2016/11/9.
 */
public class testHttpClientDownloader {

    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpClientConfig httpClientConfig=new HttpClientConfig();
        HttpClientDownloader httpClientDownloader=new HttpClientDownloader(httpClientConfig);
        WebUrl webUrl=new WebUrl();
        webUrl.setUrl("http://t.mb5u.com/header/?k=www.baidu.com");
        webUrl.setType(0);
        Page page = httpClientDownloader.download(webUrl);
        byte[] contentData = page.getContentData();
        System.out.println(page.toString());
//        System.out.println(new String(contentData,page.getContentEncoding()));
    }
}
