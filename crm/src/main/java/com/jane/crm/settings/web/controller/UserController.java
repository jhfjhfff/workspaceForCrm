package com.jane.crm.settings.web.controller;

import com.jane.crm.settings.domain.User;
import com.jane.crm.settings.service.Impl.UserServiceImpl;
import com.jane.crm.settings.service.UserService;
import com.jane.crm.utils.MD5Util;
import com.jane.crm.utils.PrintJson;
import com.jane.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)){
            login(request,response);

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到登录验证的操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //response.setContentType("application/json;charset=utf-8");

        //将密码的明文形式转换成密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        System.out.println(loginPwd);

        //接收浏览器端的ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip---------:"+ip);

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        //UserService userService = new UserServiceImpl();

        /*
            我们声明一个自定义的异常的作用：
                当业务层执行登陆操作一旦失败的时候的时候，我们可以通过controller类来捕捉登录失败抛出的异常
                    登陆失败就会直接走catch代码块，不会往下执行
                当没有走catch块的时候，说明了登录成功。 程序继续执行，
                所以自定义异常可以省下我们写判断登录是否失败/成功的 if 语句
         */
        //登陆成功返回{"success":true} , 反之返回{"success":false,"msg":错哪儿了}
        try {
            User user = userService.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();

            String msg = e.getMessage();
            /*
                我们现在作为controller，需要为ajax请求提供多项信息

                可以使用两种方法来处理：
                    （1）将多项信息打包成为map，将map解析为json串
                    （2）创建一个vo
                        private boolean success;
                        private String msg;

                     如果对于展现的信息将来还会重复的使用，建议创建vo类；反之临时使用map
             */
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("success",false);
            map.put("msg",msg);

            PrintJson.printJsonObj(response,map);
        }
    }
}
