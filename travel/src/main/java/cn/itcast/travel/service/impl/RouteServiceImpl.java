package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.util.JedisUtil;
import cn.itcast.travel.util.PageBeanUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;


public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    /**
     * 根据类别进行分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(Integer cid, Integer currentPage, Integer pageSize,String rname) {
        //1.查找总的记录数TotalCount
        Integer totalCount=routeDao.findTotalCount(cid,rname,0.0,0.0);
        //2.查找当前显示的数据集合
        Integer start = (currentPage-1)*pageSize;
        List<Route> routeList=routeDao.findByPage(cid,0,start,pageSize,rname,0.0,0.0,false);
        //3.设置总页数=总的记录数/每页显示条数
        Integer totalPage=totalCount % pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        //4.把参数这是到PageBean里并返回
        return PageBeanUtils.setPageBean(totalCount,totalPage,currentPage,pageSize,start,routeList);
    }

    /**
     * 根据rid查询Route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //根据rid查询对象的Route对象
        Route route=routeDao.findByRid(Integer.parseInt(rid));
        //根据route的id来查询图片集合信息
        List<RouteImg> routeImg = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImg);
        //根据route的id查询商家信息
        Seller seller = sellerDao.findByRid(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        //根据cid获取redis中的分类信息
        Jedis jedis= JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        for(Tuple tuple:category){
            if(tuple.getScore()==route.getCid()){
                Category cat=new Category();
                cat.setCid(route.getCid());
                cat.setCname(tuple.getElement());
                route.setCategory(cat);
                break;
            }
        }
        return route;
    }

    /**
     * 根据路线的添加时间找到最新的前4条旅游路线
     */
    @Override
    public List<Route> findNewestRoute(Integer pageSize) {
        return routeDao.findNewestRoute(pageSize);
    }

    /**
     * 找到前4主题旅游
     */
    public List<Route> findThemeTravel(Integer pageSize){
        return routeDao.findThemeTravel(pageSize);
    }

    /**
     * 根据收藏次数推荐排行前4的旅游信息
     */
    @Override
    public List<Route> findHotTravel(Integer pageSize) {
        //根据收藏次数高低查询前4的路线
        //List<Route> routeList=favoriteDao.findRouteList(0,0,pageSize,0.0,0.0,null);
        List<Route> routeList=routeDao.findByPage(0,0,0,pageSize,null,0.0,0.0,true);
        //根据路线id查询对应的smallPic(小图)
        for(Route route:routeList){
            List<RouteImg> routeImgList=routeImgDao.findByRid(route.getRid());
            route.setRouteImgList(routeImgList);
        }
        return routeList;
    }

}
