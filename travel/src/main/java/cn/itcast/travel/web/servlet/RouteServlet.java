package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.util.HandleParamsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service=new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=======进去pageQuery方法========");
        //获取int类型的cid
        int cid = HandleParamsUtils.setCid(request);
        //获取int类型的当前页码currentPage
        int currentPage = HandleParamsUtils.setCurrentPage(request, 1);
        //获取每页显示大小pageSize
        int pageSize = HandleParamsUtils.setPageSize(request, 5);
        //获取路线名称rname
        String rname = request.getParameter("rname");
        //3.调用service查询PageBean对象
        PageBean<Route> pageBean = service.pageQuery(cid, currentPage, pageSize,rname);
        //4.将PageBean对象序列化为json并返回
        writeValue(pageBean,response);

    }

    /**
     * 根据id获取路线详情
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route=service.findOne(rid);
        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路id
        String rid=request.getParameter("rid");
        //2.判断用户是否已登录
        boolean isLogin = isLogin(request);
        if(!isLogin){
            //用户尚未登录 直接返回 不需要再去访问数据库
            writeValue(false,response);
        }else {
            //用户已经登录
            int uid=getUid(request);
            //调用FavoriteService查询是否收藏
            boolean flag = favoriteService.isFavorite(rid, uid);
            writeValue(flag,response);
        }
    }

    /**
     * 添加收藏的方法
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response){
        //1.获取线路rid
        String rid = request.getParameter("rid");
        //2.判断用户是否登录
        boolean isLogin = isLogin(request);
        if(isLogin){
            //说明用户已登录
            int uid=getUid(request);
            //3.调用service添加的操作
            favoriteService.add(rid,uid);
        }
    }

    /**
     * 取消收藏
     * @param request
     * @param response
     */
    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response){
        //1.获取rid
        String ridStr = request.getParameter("rid");
        //2.判断用户是否已登录
        boolean isLogin = isLogin(request);
        if(isLogin){
            //表示用户已登录
            int uid=getUid(request);
            //3.调用service方法完成delete操作
            favoriteService.delete(ridStr,uid);
        }
    }

    /**
     * 根据路线的添加时间找到最新的前4条旅游路线
     * @param request
     * @param response
     */
    public void findNewestTravel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取int类型每页显示条数
        int pageSize = HandleParamsUtils.setPageSize(request, 4);
        //调用service方法
        List<Route> routeList = service.findNewestRoute(pageSize);
        //写回客户端
        writeValue(routeList,response);
    }

    /**
     * 找到前4主题旅游
     * @param request
     * @param response
     */
    public void findThemeTravel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取int类型每页显示条数
        int pageSize = HandleParamsUtils.setPageSize(request, 4);
        //调用service方法
        List<Route> routeList = service.findThemeTravel(pageSize);
        //写回客户端
        writeValue(routeList,response);
    }

    /**
     * 根据收藏次数推荐排行前4的旅游信息
     * @param request
     * @param response
     */
    public void findHotTravel(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取int类型显示条数
        int pageSize = HandleParamsUtils.setPageSize(request, 5);
        //调用service方法
        List<Route> routeList=service.findHotTravel(pageSize);
        //将数据写回客户端
        writeValue(routeList,response);
    }

    /**
     * 判断用户是否已登录
     * @param request
     * @return
     */
    private boolean isLogin(HttpServletRequest request){
        //1.从session中获取user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //用户存在返回true,用户不存在返回false
        return user != null;
    }
}
