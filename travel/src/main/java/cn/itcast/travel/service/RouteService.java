package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * 线路的Service
 */
public interface RouteService {

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(Integer cid,Integer currentPage,Integer pageSize,String rname);

    /**
     * 根据rid查询Route对象
     * @param rid
     * @return
     */
    public Route findOne(String rid);

    /**
     * 根据路线的添加时间找到最新的前4条旅游路线
     */
    public List<Route> findNewestRoute(Integer pageSize);

    /**
     * 找到前4主题旅游
     */
    public List<Route> findThemeTravel(Integer pageSize);

    /**
     * 根据收藏次数推荐排行前4的旅游信息
     */
    public List<Route> findHotTravel(Integer pageSize);
}
