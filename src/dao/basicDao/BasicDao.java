package dao.basicDao;

import net.sf.json.JSONArray;

/**
 * Created by guodecai on 2015/12/4.
 */
public interface BasicDao {

    public  int update(String sql, Object[] params, String c3named);

    public JSONArray query(String sql, String c3named);

    public JSONArray query(String sql, Object[] params, String c3named);

    public int count(String sql, String c3named);

    public int count(String sql, Object[] params, String c3named);
}
