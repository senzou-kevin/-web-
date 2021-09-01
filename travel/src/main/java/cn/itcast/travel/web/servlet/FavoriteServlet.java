package cn.itcast.travel.web.servlet;



import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.util.HandleParamsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService=new FavoriteServiceImpl();


    /**
     * 收藏排行榜功能+分页效果
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void rank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取int类型的当前页码currentPage
        int currentPage = HandleParamsUtils.setCurrentPage(request, 1);
        //获取int类型每页显示条数pageSize
        int pageSize = HandleParamsUtils.setPageSize(request, 8);
        //获取开区间价格price_1
        double price_1 = HandleParamsUtils.setPrice(request.getParameter("price_1"));
        //获取闭区间价格price_2
        double price_2 = HandleParamsUtils.setPrice(request.getParameter("price_2"));
        //获取路线名称rname
        String rname = request.getParameter("rname");
        PageBean<Route> pb=favoriteService.rank(currentPage,pageSize,price_1,price_2,rname);
        //写回客户端
        writeValue(pb,response);
    }

    /**
     * 根据cid展示推荐的国内游或者国外游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void suggestedTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取int类型cid
        int cid = HandleParamsUtils.setCid(request);
        //获取每页显示条数
        int pageSize = HandleParamsUtils.setPageSize(request, 6);
        List<Route> routeList=favoriteService.findTravelList(cid,pageSize);
        writeValue(routeList,response);
    }

}
