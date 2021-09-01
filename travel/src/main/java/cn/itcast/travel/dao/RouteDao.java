package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    public Integer findTotalCount(Integer cid,String rname,Double price_1,Double price_2);

    /**
     * 根据 cid start pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(Integer cid,Integer uid,Integer start,Integer pageSize,String rname,Double price_1,Double price_2,boolean isOrdered);

    /**
     * 根据rid查询Route对象
     * @param rid
     * @return
     */
    public Route findByRid(int rid);

    /**
     * 根据路线的添加时间找到最新的前4条旅游路线
     */
    public List<Route> findNewestRoute(Integer pageSize);

    /**
     * 找到前4主题旅游
     */
    public List<Route> findThemeTravel(Integer pageSize);



}
