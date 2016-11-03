package Database;

import cn.trafficdata.KServer.common.model.HttpClientConfig;
import cn.trafficdata.KServer.common.model.Project;
import cn.trafficdata.KServer.common.model.WebUrl;
import com.cybermkd.kit.MongoKit;
import com.cybermkd.kit.MongoQuery;
import com.cybermkd.plugin.MongoPlugin;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kinglf on 2016/11/3.
 */

public class testMongDb {
    private Project project;
    private MongoDatabase mongoDatabase;
    @Before
    public void getInstance(){

        MongoPlugin mongoPlugin=new MongoPlugin();
        mongoPlugin.add("127.0.0.1",27017);
        mongoPlugin.setDatabase("test");
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());

        project=new Project();
        project.setId(1);
        project.setName("testProject");
        project.setCreator("admin");
        project.setCreate_timeStamp(System.currentTimeMillis());
        project.setDescrption("this is a test");
        project.setLevel(1);
        project.setStatus(1);
        project.setUseJavaScript(true);
        List<WebUrl> webUrlList=new ArrayList<WebUrl>();
        for(int i=0;i<100;i++){
            WebUrl webUrl=new WebUrl();
            webUrl.setId(i);
            Map<String,String> prams=new HashMap<String, String>();
            prams.put("a","b");
            prams.put("1","2");
            prams.put("lation","80.232323");
            webUrl.setPrams(prams);
            webUrl.setType(1);

            webUrl.setUrl("www.baidu.com");
            webUrlList.add(webUrl);
        }
        project.setWebUrlList(webUrlList);
        project.setHttpClientConfig(new HttpClientConfig());
    }

    @Test
    public void testMongoDB(){
        /* 原生的mongoDb操作,对对象的插入还得写Bson注册类,太麻烦
        MongoCollection<Project> projectCollection = mongoDatabase.getCollection("projects", Project.class);
        CodecRegistry codecRegistry = mongoDatabase.getCodecRegistry();
        codecRegistry.
        projectCollection.insertOne(project);
         */

        /**
         * 试用MongoDBPlugin
         */

        MongoQuery query=new MongoQuery();
        query.use("projects").set(project).save();
        query.use("projects").set(project).save();
        query.use("projects").set(project).save();
        query.use("projects").set(project).save();
        query.use("projects").set(project).save();
        query.use("projects").set(project).save();
        List<Project> projects = query.use("projects").findAll(Project.class);//遍历所有
//        query.use("item").eq("b","2").find(xx.class) //单个查找
//        query.use("item").byId("5710a81ab73a87092e17a02b").find() //根据ID查找
        for(Project pro:projects){

                System.out.println(project.getDescrption());
        }
    }

    @Test
    public void del(){
        MongoQuery query=new MongoQuery();
        long delete = query.use("projects").eq("id", 1).delete();
        System.out.println(delete);
    }
    @Test
    public void find(){
        MongoQuery query=new MongoQuery();
        List<Project> projects = query.use("projects").eq("httpClientCofig>UseReferer", true).find(Project.class);
        for(Project pro:projects){
            System.out.println(pro.getId());
        }

    }
}
