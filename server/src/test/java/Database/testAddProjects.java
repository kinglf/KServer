package Database;

import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Project;
import cn.trafficdata.KServer.common.model.WebUrl;
import cn.trafficdata.KServer.common.utils.UrlUtils;
import cn.trafficdata.KServer.server.service.MongoDBServiceImpl;
import org.junit.Before;
import org.junit.Test;
import utils.CSVFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kinglf on 2016/11/9.
 */
public class testAddProjects {
    private Project project;
    @Before
    public void init(){
        project=new Project();
        project.setCreate_timeStamp(System.currentTimeMillis());
        project.setCreator("kinglf");
        project.setDescrption("this is a test");
        project.setDomain(UrlUtils.getDomain("http://www.aifei.com/airplane/312"));
        project.setId(1);
        project.setLevel(5);
        project.setName("aifei");
        project.setStatus(0);
        project.setUseJavaScript(false);
        List<WebUrl> webUrlList = getWebUrlList(project);
        project.setUrlTotal(webUrlList.size());
        project.setWebUrlList(webUrlList);
        project.setHttpClientConfig(getHttpClientConfig(project));
    }

    @Test
    public void addProject(){
        String s = MongoDBServiceImpl.addProject(project);
        MongoDBServiceImpl.updateProject(s,"mongoId",s);
    }





    public List<WebUrl> getWebUrlList(Project project){
        List<WebUrl> list=new ArrayList<WebUrl>();
        CSVFileUtil csvFileUtil=null;
        try {
            csvFileUtil=new CSVFileUtil("d:/old/AirPlaneTypeLink.txt");
            while(true){
                String s = csvFileUtil.readLine();
                if (s == null) {
                    break;
                }
                WebUrl webUrl=new WebUrl();
                webUrl.setProjectID(project.getId());
                webUrl.setType(0);
                webUrl.setUrl(s);
                webUrl.setReferer("");
                webUrl.setCookies("");
                list.add(webUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public HttpClientConfig getHttpClientConfig(Project project){
        HttpClientConfig httpClientConfig=new HttpClientConfig();
        return httpClientConfig;
    }
}
