package dao.basicDao;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Repository;
import util.JDBCConn;

import java.util.Vector;

/**
 * Created by guodecai on 2015/12/4.
 */
@Repository(value="basicDao")
public class BasicDaoImpl implements BasicDao{

    public int update (String sql,Object [] params,String c3named) {
        return JDBCConn.getUpdate(c3named,sql,params);
    }
    public JSONArray query (String sql,Object [] params,String c3named){
       return JDBCConn.getJson(c3named,params,sql);

    }


    public JSONArray query(String sql, String c3named) {
        return JDBCConn.getJson(c3named,sql);
    }

    public int count(String sql, String c3named) {
       Vector a =JDBCConn.getVector(c3named, sql, 1);
        return Integer.parseInt(a.get(0).toString());
    }

    public int count(String sql, Object[] params, String c3named) {
        Vector a = JDBCConn.getVector(c3named,params ,sql, 1);
        return Integer.parseInt(a.get(0).toString());
    }


}
