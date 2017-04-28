package filter;

import net.sf.json.JSONObject;
import sun.misc.Regexp;
import util.PropsUtil;
import util.Result;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songtao on 2017/4/25.
 */
public class UserFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;
//            System.err.println(" request.getServletPath() = " +  request.getServletPath());
//            System.err.println("  request.getRequestURI() = " +   request.getRequestURI());
            String url = request.getRequestURI();
            if(whenIsUrl(url)){
                if("/".equals(url)){
                    String code = request.getParameter("code");
                    boolean a = code==null;
                    String state = request.getParameter("state");
                    if(isEmpty(code)){
                        request.getRequestDispatcher("/html/no_login.html").forward(request, response);
                    }else{
                        System.out.println("code = " + code);
                        getAccessToken(false,code,"");
                    }
                }


            }

                filterChain.doFilter(servletRequest, servletResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    /**
     * 当请求路径为路径，非静态资源时。
     * @param url
     * @return
     */
    public boolean whenIsUrl(String url){
        Pattern p = Pattern.compile("^(/\\w*)+$");
        Matcher matcher= p.matcher(url);
        return  matcher.find();
    }
    //判断字符串是否为空
    public boolean isEmpty(String val){
        return val==null||val.equals("null")||val.equals("");
    }

    /**
     * 调微信接口，返回access_token值。
     * @param isRefresh
     * @param code
     * @param refresh_token
     * @return
     */
    public String getAccessToken(boolean isRefresh,String code,String refresh_token){
        String app_id = getWeChatProp("appid");
        String secret = getWeChatProp("appsecret");
        if(app_id!=null || secret!=null){
            String url = isRefresh ? ("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+app_id+"&grant_type=refresh_token&refresh_token="+refresh_token):("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+app_id+"&secret="+secret+"&code="+code+"&grant_type=authorization_code");
            try {
                JSONObject result =  JSONObject.fromObject(Result.getResult(url));//{ "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN","openid":"OPENID", "scope":"SCOPE" }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            System.err.println("wechat.properties app_id or secret is null ");
        }
        return null;
    }

    /**
     * 从微信配置文件中获取参数
     * @param key
     * @return
     */
    public String getWeChatProp(String key){
        Properties pros = PropsUtil.loadProps("wechat.properties");
        Object keyO = pros.get(key);
        if(keyO!=null){
            String val = keyO.toString();
            System.err.println(key+":"+val);
            return val;
        }else{
            System.err.println("wechat.properties not exist "+key+" ! ");
            return null;
        }
    }


    public static void main(String[] args){

    }

}
