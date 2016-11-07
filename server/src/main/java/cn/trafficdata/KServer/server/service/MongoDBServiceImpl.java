package cn.trafficdata.KServer.server.service;

import cn.trafficdata.KServer.common.model.LogMessage;
import cn.trafficdata.KServer.common.model.Page;
import cn.trafficdata.KServer.common.model.Project;
import cn.trafficdata.KServer.server.configurable.Field;
import cn.trafficdata.KServer.server.model.Client;
import cn.trafficdata.KServer.server.utils.Configuration;
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


    static {
        Configuration conf = Configuration.getInstance();

        MongoPlugin mongoPlugin = new MongoPlugin();
        mongoPlugin.add(Field.MongoDB_IP, Field.MongoDB_PORT);
        mongoPlugin.setDatabase(Field.MongoDB_DATABASE);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());
    }

    public static String addProject(Project project) {
        MongoQuery query = new MongoQuery();
        boolean save = query.use(Field.Project_Collection_Name).set(project).save();
        if (save) {
            return query.getId();
        } else {
            return null;
        }
    }

    public static boolean delProject(int id) {
        MongoQuery query = new MongoQuery();
        long delete = query.use(Field.Project_Collection_Name).eq("id", id).delete();
        if (delete > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean delProject(String _id) {
        MongoQuery query = new MongoQuery();
        long delete = query.use(Field.Project_Collection_Name).byId(_id).delete();
        if (delete > 0) {
            return true;
        }
        return false;
    }

    public static boolean updateProject(Project project) {
        MongoQuery query = new MongoQuery();
        long update = query.use(Field.Project_Collection_Name).byId(project.getMongoId()).modify(project).update();
        if (update > 0) {
            return true;
        }
        return false;
    }

    public static boolean updateProject(String _id, String key, Object value) {
        MongoQuery query = new MongoQuery();
        long updateOne = query.use(Field.Project_Collection_Name).byId(_id).modify(key, value).updateOne();
        if (updateOne > 0) {
            return true;
        }
        return false;
    }

    public static Project findProject(String key, Object value) {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Project_Collection_Name).eq(key, value).findOne(Project.class);
    }

    public static Project findProject(int id) {
        return findProject("id", id);
    }

    public static List<Project> listProject() {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Project_Collection_Name).findAll(Project.class);
    }

    public static List<Project> listProject(int num) {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Project_Collection_Name).limit(num).find(Project.class);
    }

    public static List<Project> getUnFinishProjectList() {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Project_Collection_Name)
                .ne("status", 2)
                .find(Project.class);
    }

    public static Project getNextProject() {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Project_Collection_Name)
                .eq("status", 0)
                .ascending("level")//优先级,升序排列,数字越小,优先级越高
                .limit(1)
                .findOne(Project.class);
    }
    public static boolean changeProjectStatus(int projectId,int status){
        ////////////////
        MongoQuery query=new MongoQuery();
        long update = query.use(Field.Project_Collection_Name).eq("id", projectId).modify("status", status).update();
        if (update > 0) {
            return true;
        }
        return false;

    }

    /*=========================以下是Page的操作方法===================*/
    public static boolean savePage(Page page) {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Page_Collection_Name).set(page).save();
    }
    public static void savePage(List<Page> pageList){
        MongoQuery query=new MongoQuery();
        query.use(Field.Page_Collection_Name);
        for(Page page:pageList){
            query.set(page).save();
        }
    }

    public static boolean delPage(Page page) {
        MongoQuery query = new MongoQuery();
        long delete = query.use(Field.Page_Collection_Name).byId(page.getMongoId()).delete();
        if (delete > 0) {
            return true;
        }
        return false;
    }

    public static boolean updatePage(Page page) {
        MongoQuery query = new MongoQuery();
        long update = query.use(Field.Page_Collection_Name).byId(page.getMongoId()).modify(page).update();
        if (update > 0) {
            return true;
        }
        return false;
    }

    public static Page findPage(String key, Object value) {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Page_Collection_Name).eq(key, value).findOne(Page.class);
    }

    public static List<Page> findPage(int id) {
        MongoQuery query = new MongoQuery();
        return query.use(Field.Page_Collection_Name).eq("id", id).find(Page.class);
    }

    public static boolean isFullPage(int projectId) {
        MongoQuery query = new MongoQuery();
        long pageTotal = query.use(Field.Page_Collection_Name).eq("projectId", projectId).count();
        MongoQuery query1 = new MongoQuery();
        Project project = query.use(Field.Project_Collection_Name).eq("id", projectId).findOne(Project.class);
        if (pageTotal >= project.getUrlTotal()) {
            return true;
        }
        return false;
    }
    public static boolean isFullPage(Page page){
        return isFullPage(page.getProjectId());
    }
    public static boolean isFullPage(String domain){
        MongoQuery query=new MongoQuery();
        Project project = query.use(Field.Page_Collection_Name).eq("domain", domain).findOne(Project.class);
        return isFullPage(project.getId());
    }
    /*=========================以下是Client的操作方法===================*/
    public static void initOrUpdateClient(Client client){
        MongoQuery query = new MongoQuery();
        long update = query.use(Field.Client_Collection_Name).eq("markcode",client.getMarkcode()).modify(client).update();
        if(update==0){
            query.use(Field.Client_Collection_Name).set(client).save();
        }
    }

    /*=========================以下是远程Log的操作方法===================*/

    public static void saveLogs(List<LogMessage> logMessageList){
        MongoQuery query=new MongoQuery();
        query.use(Field.Log_Collection_Name);
        for(LogMessage logMessage:logMessageList){
            query.set(logMessage).save();
        }
    }
}
