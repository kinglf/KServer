package cn.trafficdata.KServer.common.model;

import java.io.Serializable;

/**
 * Created by Kinglf on 2016/10/18.
 */
public class HttpClientConfig implements Serializable {
    private static final long serialVersionUID = 79883978095616969L;

//    Header	解释	                    示例
//    Accept	指定客户端能够接收的内容类型	Accept: text/plain, text/html
//    Accept-Charset	浏览器可以接受的字符编码集。	Accept-Charset: iso-8859-5
//    Accept-Encoding	指定浏览器可以支持的web服务器返回内容压缩编码类型。	Accept-Encoding: compress, gzip
//    Accept-Language	浏览器可接受的语言	Accept-Language: en,zh
//    Accept-Ranges	可以请求网页实体的一个或者多个子范围字段	Accept-Ranges: bytes
//    Authorization	HTTP授权的授权证书	Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
//    Cache-Control	指定请求和响应遵循的缓存机制	Cache-Control: no-cache
//    Connection	表示是否需要持久连接。（HTTP 1.1默认进行持久连接）	Connection: close
//    Cookie	HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器。	Cookie: $Version=1; Skin=new;
//    Content-Length	请求的内容长度	Content-Length: 348
//    Content-Type	请求的与实体对应的MIME信息	Content-Type: application/x-www-form-urlencoded
//    Date	请求发送的日期和时间	Date: Tue, 15 Nov 2010 08:12:31 GMT
//    Expect	请求的特定的服务器行为	Expect: 100-continue
//    From	发出请求的用户的Email	From: user@email.com
//    Host	指定请求的服务器的域名和端口号	Host: www.zcmhi.com
//    If-Match	只有请求内容与实体相匹配才有效	If-Match: “737060cd8c284d8af7ad3082f209582d”
//    If-Modified-Since	如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码	If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
//    If-None-Match	如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变	If-None-Match: “737060cd8c284d8af7ad3082f209582d”
//    If-Range	如果实体未改变，服务器发送客户端丢失的部分，否则发送整个实体。参数也为Etag	If-Range: “737060cd8c284d8af7ad3082f209582d”
//    If-Unmodified-Since	只在实体在指定时间之后未被修改才请求成功	If-Unmodified-Since: Sat, 29 Oct 2010 19:43:31 GMT
//    Max-Forwards	限制信息通过代理和网关传送的时间	Max-Forwards: 10
//    Pragma	用来包含实现特定的指令	Pragma: no-cache
//    Proxy-Authorization	连接到代理的授权证书	Proxy-Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
//    Range	只请求实体的一部分，指定范围	Range: bytes=500-999
//    Referer`先前网页的地址，当前请求网页紧随其后,即来路	Referer: http://www.zcmhi.com/archives/71.html
//    TE	客户端愿意接受的传输编码，并通知服务器接受接受尾加头信息	TE: trailers,deflate;q=0.5
//    Upgrade	向服务器指定某种传输协议以便服务器进行转换（如果支持）	Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
//    User-Agent	User-Agent的内容包含发出请求的用户信息	User-Agent: Mozilla/5.0 (Linux; X11)
//    Via	通知中间网关或代理服务器地址，通信协议	Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)
//    Warning	关于消息实体的警告信息	Warn: 199 Miscellaneous warning

    public String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER";
    public String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    public String CONNECTION = "keep-alive";
    public String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";
    public String ACCEPT_ENCODING = "gzip, deflate, sdch";
    public boolean UseTheLastCookies = true;//使用最后使用的cookie,如果没有则新建,在client中建立一个cookiemap<site,Cookies>,重拨IP时,cookie选择是否清空
    public boolean deleteCookiesWhenDialing = true;//当拨号时是否清空当前site的cookies
    public boolean UseReferer = true;//请求中是否包含referer;
    public boolean UseTheLastReferer = true;//使用最后使用的url作为Referer提交,如果不使用,则默认使用subdomain.domain.com/cn/org等首页作业提交
    public int politenessDelay = 200;//延时
    public boolean includeHttpsPages = true;//https支持
    public int socketTimeout = 20000;//socket超时
    public int connectionTimeout = 30000;//连接超时
    public boolean followRedirects = true;//

}