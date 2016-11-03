package cn.trafficdata.KServer.common.service;

import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.Project;
import cn.trafficdata.KServer.common.utils.Configuration;
import com.cybermkd.kit.MongoKit;
import com.cybermkd.kit.MongoQuery;
import com.cybermkd.plugin.MongoPlugin;
import com.mongodb.MongoClient;

import java.util.List;

/**
 * Created by Kinglf on 2016/11/3.
 * 增删改查
 */
public class MongoDBServiceImpl {
    private static String MongoDB_IP;
    private static int MongoDB_PORT;
    private static String MongoDB_DATABASE;
    private final static String Project_Collection_Name = "Projects";
    private final static String Page_Collection_Name = "Pages";

    static {
        Configuration conf = Configuration.getInstance();
        MongoDB_IP = conf.getString("mongodb.ip", "127.0.0.1");
        MongoDB_PORT = conf.getInt("mongodb.port", 27017);
        MongoDB_DATABASE = conf.getString("mongodb.database", "KServer");
        MongoPlugin mongoPlugin = new MongoPlugin();
        mongoPlugin.add(MongoDB_IP, MongoDB_PORT);
        mongoPlugin.setDatabase(MongoDB_DATABASE);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
    }

    public static boolean addProject(Project project) {
        MongoQuery query = new MongoQuery();
        return query.use(Project_Collection_Name).set(project).save();
    }

    public static boolean delProject(int id) {
        MongoQuery query = new MongoQuery();
        long delete = query.use(Project_Collection_Name).eq("id", id).delete();
        if (delete > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean delProject(String _id) {
        MongoQuery query = new MongoQuery();
        long delete = query.use(Project_Collection_Name).byId(_id).delete();
        if (delete > 0) {
            return true;
        }
        return false;
    }

    public static boolean updateProject(Project project) {
        MongoQuery query = new MongoQuery();
        long update = query.use(Project_Collection_Name).byId(project.getMongoId()).modify(project).update();
        if (update > 0) {
            return true;
        }
        return false;
    }
    public static Project findProject(String key,Object value){
        MongoQuery query=new MongoQuery();
        return query.use(Project_Collection_Name).eq(key,value).findOne(Project.class);
    }
    public static Project findProject(int id){
        return findProject("id",id);
    }

    public static List<Project> listProject() {
        MongoQuery query = new MongoQuery();
        return query.use(Project_Collection_Name).findAll(Project.class);
    }

    public static List<Project> listProject(int num) {
        MongoQuery query = new MongoQuery();
        return query.use(Project_Collection_Name).limit(num).find(Project.class);
    }

    public static List<Project> unFinishProjectList() {
        MongoQuery query = new MongoQuery();
        return query.use(Project_Collection_Name)
                .ne("status", 2)
                .find(Project.class);
    }

    public static Project getNextProject() {
        MongoQuery query = new MongoQuery();
        return query.use(Project_Collection_Name)
                .eq("status", 0)
                .ascending("level")//优先级,升序排列,数字越小,优先级越高
                .limit(1)
                .findOne(Project.class);
    }

    /*=========================以下是Page的操作方法===================*/
    public static boolean savePage(Page page){
        MongoQuery query=new MongoQuery();
        return query.use(Page_Collection_Name).set(page).save();
    }
    public static boolean delPage(Page page){
        MongoQuery query=new MongoQuery();
        long delete = query.use(Page_Collection_Name).byId(page.getMongoId()).delete();
        if(delete>0){
            return true;
        }
        return false;
    }
    public static boolean updatePage(Page page){
        MongoQuery query=new MongoQuery();
        long update = query.use(Page_Collection_Name).byId(page.getMongoId()).modify(page).update();
        if(update>0){
            return true;
        }
        return false;
    }
    public static Page findPage(String key,Object value){
        MongoQuery query=new MongoQuery();
        return query.use(Page_Collection_Name).eq(key,value).findOne(Page.class);
    }

    public static List<Page> findPage(int id){
        MongoQuery query=new MongoQuery();
        return query.use(Page_Collection_Name).eq("id",id).find(Page.class);
    }
    public static boolean isFullPage(int projectId){
        MongoQuery query=new MongoQuery();
        long pageTotal = query.use(Page_Collection_Name).eq("projectId", projectId).count();
        MongoQuery query1=new MongoQuery();
        Project project = query.use(Project_Collection_Name).eq("id", projectId).findOne(Project.class);
        if(pageTotal>=project.getUrlTotal()){
            return true;
        }
        return false;
    }
}
