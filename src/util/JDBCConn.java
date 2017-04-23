package util;

/**
 * Created with IntelliJ IDEA.
 * User: fyong
 * Date: 13-1-15
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */

import com.mysql.jdbc.Driver;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

public class JDBCConn {
    /****
     * 付永 2012.3.23
     * database 使用JDBC 联接
     */

    private static final String DRIVER;
    private static final String READURL;
    private static final String READUSERNAME;
    private static final String READPASSWORD;
/*    private static final String WRITEURL;
    private static final String WRITEUSERNAME;
    private static final String WRITEPASSWORD;*/


    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");

        READURL = conf.getProperty("read_jdbc.url");
        READUSERNAME = conf.getProperty("read_jdbc.username");
        READPASSWORD = conf.getProperty("read_jdbc.password");
/*
        WRITEURL = conf.getProperty("write_jdbc.url");
        WRITEUSERNAME = conf.getProperty("write_jdbc.username");
        WRITEPASSWORD = conf.getProperty("write_jdbc.password");*/
    }

    //mysql 只读
    public static Connection MysqlRead()
    {
        Connection  con =null;

        try
        {

            Class.forName(DRIVER);//com.mysql.jdbc.
            //
            //使用联接池
//            Context context = new InitialContext();
//            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
//            con=ds.getConnection();

            //使用的JDBC驱动
            con = DriverManager.getConnection(READURL,READUSERNAME,READPASSWORD);

        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return con;
    }
    /**
     * DB2 jdbc 联接
     * @return
     */
    public static Connection MysqlWrite()
    {
        Connection  con =null;

        try
        {


            Class.forName(DRIVER);//com.mysql.jdbc.

//            Context context = new InitialContext();
//            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
//            con=ds.getConnection();

            //使用的JDBC驱动
//            con = DriverManager.getConnection(WRITEURL, WRITEUSERNAME,WRITEPASSWORD);
            con = DriverManager.getConnection(null, null,null);

        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return con;
    }
    public static Connection MysqlPor()
    {
        Connection  con =null;

        try
        {


            Class.forName("com.mysql.jdbc.Driver");//com.mysql.jdbc.

//            Context context = new InitialContext();
//            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
//            con=ds.getConnection();

            //使用的JDBC驱动
            con = DriverManager.getConnection("jdbc:mysql://10.130.152.11:3310/por?characterEncoding=utf8","poruser","77SHIbian08");

        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return con;
    }


    /**
     * 运行普通SQL的方法
     * @return
     */
    public static Vector getVector (String database,String sql,int i)
    {
        Vector vector = new Vector();
        Connection con = null;
        Statement stmt = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            psmt = con.prepareStatement(sql) ;
            rs  = psmt.executeQuery();

            while (rs.next())
            {
                for (int j = 1; j <= i; j++)
                {
                    //System.out.println(rs.getString(j));
                    vector.addElement(rs.getString(j));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

            if (psmt != null)
            {
                try
                {
                    psmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return vector;

    }
    public static Vector getVector (String database,Object[] params,String sql,int i)
    {
        Vector vector = new Vector();
        Connection con = null;
        Statement stmt = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try
        {
            Driver a = new Driver();
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            psmt = con.prepareStatement(sql) ;
            for (int b=0;b<params.length;b++){
                psmt.setObject(b+1,params[b]);
            }
            rs  = psmt.executeQuery();

            while (rs.next())
            {
                for (int j = 1; j <= i; j++)
                {
                    //System.out.println(rs.getString(j));
                    vector.addElement(rs.getString(j));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

            if (psmt != null)
            {
                try
                {
                    psmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return vector;

    }
    /**
     * 2012-05-12 fy
     * 运行INSERT UPDATE的方法
     * @return
     */
    public static int getUpdate (String database,String sql)
    {
        int rs = 0;
        Connection con=null;
        Statement stmt = null;
        PreparedStatement psmt = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            psmt = con.prepareStatement(sql) ;
            rs  = psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (psmt != null)
            {
                try
                {
                    psmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

        }
        return rs;

    }
    public static int getUpdate (String database,String sql,Object [] params)
    {
        int rs = 0;
        Connection con=null;
        Statement stmt = null;
        PreparedStatement psmt = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            psmt = con.prepareStatement(sql) ;
            for (int a=0;a<params.length;a++){
                psmt.setObject(a+1,params[a]);
            }
//            psmt.executeUpdate();

            rs  = psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (psmt != null)
            {
                try
                {
                    psmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

        }
        return rs;

    }
    public static int batchUpdate(String database,Vector vector,int vecsize)
    {
        int rs =0;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            con.setAutoCommit(false);//
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for (int i=0;i<vecsize; i++)
            {
                sql=vector.get(i).toString();

//                System.out.println(sql);
                stmt.execute(sql);
            }

            con.commit();
            con.setAutoCommit(true);
            rs = 1;
            return rs;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return rs;
        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

        }

    }

    public static int batchUpdate(String database,Vector vector)
    {
        int rs =0;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            con.setAutoCommit(false);//
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for (int i=0;i<vector.size(); i++)
            {
                sql=vector.get(i).toString();
                stmt.addBatch(sql);
            }
            int[] row = stmt.executeBatch();
            for(int j=0;j<row.length;j++){
                rs+=row[j];
            }
            con.commit();
            return rs;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            return rs;
        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 批处理，每条影响一行，影响行数等于sql集合数时提交，否则，回滚。
     * @param database
     * @param vector
     * @return
     */
    public static boolean batchUpdateTran(String database,Vector vector)
    {
        int rs =0;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            con.setAutoCommit(false);//
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for (int i=0;i<vector.size(); i++)
            {
                sql=vector.get(i).toString();
                stmt.addBatch(sql);
            }
            int[] row = stmt.executeBatch();
            for(int j=0;j<row.length;j++){
                rs+=row[j];
            }
            if(rs==vector.size()&&rs>0){
                con.commit();
                return true;
            }else{
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
            return false;
        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 更新方法事务管理
     * @param database
     * @param sql1
     * @param sql2
     * @return
     */
    public static boolean updateBuTran(String database,String sql1,String sql2)
    {
        int rs =0;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            con.setAutoCommit(false);//
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int rows1 = stmt.executeUpdate(sql1);
            int rows2 = stmt.executeUpdate(sql2);
            if(rows1==rows2){
                con.commit();
                return true;
            }else{
                con.rollback();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }

        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * vector中所有第i条跟i+1条执行结果相同，且影响行数不为0时提交返回true，否则回滚返回false
     * @param database
     * @param vector
     * @return
     */
    public static boolean batchUpdateBuTran(String database,Vector vector)
    {
        int rs =0;
        Connection con = null;
        Statement stmt = null;
        String sql = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }
            con.setAutoCommit(false);//
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            boolean rs_b = true;
            for (int i=0;i<vector.size(); i+=2)
            {
                int rows1 = stmt.executeUpdate(vector.get(i).toString());
                int rows2 = stmt.executeUpdate(vector.get(i+1).toString());
                if(!(rows1==rows2&&rows1>0)){
                    rs_b=false;
                    break;
                }
            }
            if(rs_b){
                con.commit();
                return true;
            }else{
                con.rollback();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                con.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }

        } finally {
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static JSONArray getJson (String database,String sql)
    {
        JSONArray jsonArray =new JSONArray();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            ResultSetMetaData metaDate = rs.getMetaData();
            int number = metaDate.getColumnCount();
            String[] column = new String[number];
            for (int j = 0; j < column.length; j++)
            {
                column[j] = metaDate.getColumnName(j + 1);
            }
            while (rs.next())
            {
                JSONObject o = new JSONObject();
                for (int j = 1; j <= column.length; j++)
                {
                    o.put(column[j - 1], rs.getString(j) == null ? "" : rs.getString(j).trim());
                }
                jsonArray.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }
    //生成的json对象key是列别名，不是表结构字段名
    public static JSONArray getJson1 (String database,String sql)
    {
        JSONArray jsonArray =new JSONArray();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            ResultSetMetaData metaDate = rs.getMetaData();
            int number = metaDate.getColumnCount();
            String[] column = new String[number];
            for (int j = 0; j < column.length; j++)
            {
                //区别
                column[j] = metaDate.getColumnLabel(j + 1);
            }
            while (rs.next())
            {
                JSONObject o = new JSONObject();
                for (int j = 1; j <= column.length; j++)
                {
                    o.put(column[j - 1], rs.getString(j) == null ? "" : rs.getString(j).trim());
                }
                jsonArray.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }

    //生成的json对象key是列别名，不是表结构字段名
    public static JSONArray getJson1 (String database,Object[] params,String sql)
    {
        JSONArray jsonArray =new JSONArray();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }


            stmt =con.prepareStatement(sql);
            for (int a=0;a<params.length;a++){
                stmt.setObject(a+1,params[a]);
            }

            rs = stmt.executeQuery();
            ResultSetMetaData metaDate = rs.getMetaData();
            int number = metaDate.getColumnCount();
            String[] column = new String[number];
            for (int j = 0; j < column.length; j++)
            {
                //区别
                column[j] = metaDate.getColumnLabel(j + 1);
            }
            while (rs.next())
            {
                JSONObject o = new JSONObject();
                for (int j = 1; j <= column.length; j++)
                {
                    o.put(column[j - 1], rs.getString(j) == null ? "" : rs.getString(j).trim());
                }
                jsonArray.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }

    public static JSONArray getJson (String database,Object[] params,String sql)
    {
        JSONArray jsonArray =new JSONArray();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }


            stmt =con.prepareStatement(sql);
            for (int a=0;a<params.length;a++){
                stmt.setObject(a+1,params[a]);
            }

            rs = stmt.executeQuery();
//            rs = stmt.executeQuery(sql);

            ResultSetMetaData metaDate = rs.getMetaData();
            int number = metaDate.getColumnCount();
            String[] column = new String[number];
            for (int j = 0; j < column.length; j++)
            {
                column[j] = metaDate.getColumnName(j + 1);
            }
            while (rs.next())
            {
                JSONObject o = new JSONObject();
                for (int j = 1; j <= column.length; j++)
                {
                    o.put(column[j-1], rs.getString(j) == null ? "": rs.getString(j).trim());
                }
                jsonArray.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }
    public static JSONArray getJsonNullable (String database,String sql)
    {
        JSONArray jsonArray =new JSONArray();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            stmt = con.createStatement();

            rs = stmt.executeQuery(sql);

            ResultSetMetaData metaDate = rs.getMetaData();
            int number = metaDate.getColumnCount();
            String[] column = new String[number];
            for (int j = 0; j < column.length; j++)
            {
                column[j] = metaDate.getColumnName(j + 1);
            }
            while (rs.next())
            {
                JSONObject o = new JSONObject();
                for (int j = 1; j <= column.length; j++)
                {
                    o.put(column[j-1], rs.getString(j) == null ? null: rs.getString(j).trim());
                }
                jsonArray.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }
    //测试联接
    public static void main(String[] args)
    {
        String sql = null;
        Vector vector = new Vector();
        sql = "select * from qca_user where user_id ='fyong' ";
//
//        vector =  getVector("mywrite",sql,8);
//
//        if (vector != null)
//        {
//            for (int i=0;i<vector.size()-7;i=i+8)
//            {
//                System.out.print(vector.get(i)+" ");
//                System.out.print(vector.get(i+1)+" ");
//                System.out.print(vector.get(i+2)+" ");
//                System.out.print(vector.get(i+3)+" ");
//                System.out.print(vector.get(i+4)+" ");
//                System.out.print(vector.get(i+5)+" ");
//                System.out.print(vector.get(i+6)+" ");
//                System.out.print(vector.get(i+7)+" ");
//                System.out.println();
//            }
//        }

        JSONArray jsonArray = JDBCConn.getJsonNullable("myread", sql);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jo = (JSONObject) jsonArray.get(i);
            System.out.println(jo.get("user_id"));
            System.out.println(jo.get("create_time"));
        }
    }

    /**
     * 分页查询返回总计录数
     * @return
     */
    public static int getTotalCount(String database, String sql) {
       int total_count = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                total_count =rs.getInt("count");
                System.out.println("JDBCConn中的总记录数"+total_count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return total_count;
    }

    /**
     * 分页查询返回总计录数  列名为count（*）
     * @return
     */
    public static int getTotalCount1(String database, String sql) {
        int total_count = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            if  (database.equals("mywrite"))
            {
                con = MysqlWrite();
            }else if (database.equals("por")){
                con = MysqlPor();
            }
            else
            {
                con = MysqlRead();
            }

            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                total_count =rs.getInt("count(*)");
                System.out.println("JDBCConn中的总记录数"+total_count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return total_count;
    }

    public static ArrayList<String> getStArray(String database, String sql) {

        ArrayList<String> list = new ArrayList();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
                if  (database.equals("mywrite"))
                {
                    con = MysqlWrite();
                }else if (database.equals("por")){
                    con = MysqlPor();
                }
                else
                {
                    con = MysqlRead();
                }

            stmt = con.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()){
               list.add(rs.getString("area")) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return list;


    }
}
