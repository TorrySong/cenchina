package controller;

import net.sf.json.JSONArray;
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
    public JSONArray firstMethod(HttpServletRequest request,HttpServletResponse response){
        /*PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JSONArray arr = new JSONArray();
        for(int i=0;i<10;i++){
            JSONObject obj = new JSONObject();
            obj.put("num",i);
            obj.put("name","name"+i);
            obj.put("p",i*2+1);
            obj.put("z",100-i);
            arr.add(obj);
        }
      /*  out.print(obj);
        out.flush();
        out.close();*/
        return arr;
    }

}
