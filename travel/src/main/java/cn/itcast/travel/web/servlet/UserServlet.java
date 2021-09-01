package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.HandleParamsUtils;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 继承了BaseServlet，因为BaseServlet是一个Servlet
 * 所以UserServlet也是一个servlet。
 * 因为BaseServlet中有一个service方法，那么谁继承了BaseServlet谁
 * 就会有service方法
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService=new UserServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();


    /**
     * 注册功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //调用isValid方法判断验证码是否正确
        boolean isValid = isValid(request);
        ResultInfo info=new ResultInfo();
        if(!isValid){
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }else {
            //验证码正确
            //5.获取数据并封装在User对象中
             User user=getUser(request);
            //7.调用service完成注册操作
            boolean flag=userService.register(user);
            //8.响应结果
            if(flag){
                //注册成功
                info.setFlag(true);
            }else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败,用户名已存在!!");
            }
        }
        //写回客户端
        writeValue(info,response);
    }


    /**
     * 登录功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //调用isValid方法判断验证码是否正确
        boolean isValid = isValid(request);
        //获取ResultInfo用于存储信息供前端使用
        ResultInfo info=new ResultInfo();
        if(!isValid){
            //验证码有误
            //设置ResultInfo中的信息
            info.setFlag(false);
            info.setErrorMsg("验证码有误");
        }else {
            //验证码正确
            //5.获取用户名和密码数据并封装在User中
            User user=getUser(request);
            //6.调用service进行查询
            User u=userService.login(user);
            //7.判断用户对象是否存在
            if(u==null){
                //用户名或密码错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }else {
                //用户存在
                info.setFlag(true);
                //将用户存到session中
                HttpSession session = request.getSession();
                session.setAttribute("user",u);
            }
        }
        //8写回客户端
        writeValue(info,response);
    }

    /**
     * 查询单个对象
     * @param request
     * @param response
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.从session中获取登录用户
        User user =(User) request.getSession().getAttribute("user");
        //2.将user写回客户端
        writeValue(user,response);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 展示用户个人收藏并分页展示
     * @param request
     * @param response
     * @throws IOException
     */
    public void showFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取int类型当前页码currentPage
        int currentPage = HandleParamsUtils.setCurrentPage(request, 1);
        //获取int类型每页显示条目数pageSize
        int pageSize = HandleParamsUtils.setPageSize(request, 12);
        //获取用户id uid
        int uid = getUid(request);
        //调用service查询pageBean对象
        PageBean<Route> pb=favoriteService.pageQuery(uid,currentPage,pageSize);
        //返回前端
        writeValue(pb,response);
    }

    /**
     * 将前端的数据封装到User对象中
     * @param request
     * @return
     */
    private User getUser(HttpServletRequest request){
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 判断验证码是否正确
     * @param request
     * @return
     */
    private boolean isValid(HttpServletRequest request){
        //1.获取request中的验证码
        String checkCode = request.getParameter("check");
        //2.从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server =(String) session.getAttribute("CHECKCODE_SERVER");
        //3.把session中的验证码移除，防止二次使用
        session.removeAttribute("CHECKCODE_SERVER");
        //4.判断2个验证码是否相等
        //验证码不相等
        return checkcode_server != null && checkcode_server.equalsIgnoreCase(checkCode);
    }
}
