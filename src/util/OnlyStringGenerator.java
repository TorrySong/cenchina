package util;

import dao.basicDao.BasicDao;
import dao.basicDao.BasicDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

/**
 * Created by guodecai on 2015/12/11.
 */
public class OnlyStringGenerator {

    private static final int LENGTH = 8;

    //创建id，自动生成8位字符串，前边拼接特定字符串str，生成唯一的字符串
    public static String creatId (Connection conn,String str){
        String st=null;
        for (int i = 0;i<LENGTH;i++){
            st =save(conn, str);
            if (st != null){
                break;
            }
        }
       return st;
    }

    //创建id，自动生成8位字符串，前边拼接特定字符串str，生成唯一的字符串
    public static String creatId (String str,String table){
        String st=null;
        for (int i = 0;i<LENGTH;i++){
            st =save( str,table);
            if (st != null){
                break;
            }
        }
        return st;
    }
    //创建id，自动生成8位唯一字符串
    public static String creatId (Connection conn){
        String st=null;
        for (int i = 0;i<LENGTH;i++){
            st =save(conn);
            if (st != null){
                break;
            }
        }
        return st;
    }

    /**
     * 创建ID，xxx-xxxxxxxx
     * @param tableName，创建完验证表
     * @param prefix，id前缀xxx-
     * @return
     */
    public static String createId(String tableName,String prefix){
        Connection connection = JDBCConn.MysqlWrite();
        String id=prefix+generateNumber();
            for(int rows=0;rows<1;id=prefix+generateNumber()){
                String sql = " insert into "+tableName+" values ('"+id+"')";
               // System.out.println(sql);
                try {
                    rows = connection.createStatement().executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        try {
            if(!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String createId(Statement statement,String tableName,String prefix){
        String id=prefix+generateNumber();
        for(int rows=0;rows<1;id=prefix+generateNumber()){
            String sql = " insert into "+tableName+" values ('"+id+"')";
            System.out.println(sql);
            try {
                rows = statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    private static String save (Connection conn){

        return save(conn, "");
    }

    private static String save (Connection conn,String str){

        try {
            String string = str+generateNumber();
            String sql = " insert into str_generator values ('"+string+"')";
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(sql);
            if (rs>0){
                return string;
            }else {
                return null;
            }

        } catch (SQLException e) {
            return null;
        }
    }
    private static String save (String str,String table){
            String string = str+generateNumber();
            String sql = " insert into "+table+" values (?)";
            Object [] params = {string};
            BasicDao dao = new BasicDaoImpl();
            int rs = dao.update(sql, params, "mywrite");
            if (rs>0){
                return string;
            }else {
                return null;
            }
    }
    private static String generateNumber() {
        String no="";
        Random random = new Random();
        char[] defaultNums  ={'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] nums = new char[LENGTH];
        int canBeUsed =defaultNums.length ;
        for (int i = 0; i < nums.length; i++) {
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
        }

        if (nums.length>0) {
            for (int i = 0; i < nums.length; i++) {
                no+=nums[i];
            }
        }
        return no;

    }

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }


}
