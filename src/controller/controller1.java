package controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by songtao on 2017/4/20.
 */
@Controller
@RequestMapping("controller1")
public class controller1 {

    @RequestMapping("/first")
    @ResponseBody
    public JSONObject firstMethod(HttpServletRequest request,HttpServletResponse response){
        /*PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JSONObject obj = new JSONObject();
        obj.put("a","aVal");
      /*  out.print(obj);
        out.flush();
        out.close();*/
        return obj;
    }

}
