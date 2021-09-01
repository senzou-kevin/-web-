package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.util.PageBeanUtils;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private RouteDao routeDao=new RouteDaoImpl();

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, Integer uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //如果这个对象有值表示已经收藏过，为true。反之则为false。
        return favorite!=null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, Integer uid) {
        //添加收藏
        favoriteDao.add(Integer.parseInt(rid),uid);
        //查询当前该该路线的收藏次数
        int count = favoriteDao.findCountByRid(Integer.parseInt(rid));
        //收藏次数+1
        count+=1;
        //更新该路线的收藏次数
        favoriteDao.updateCountByRid(Integer.parseInt(rid),count);
    }

    /**
     * 通过用户id,currentPage,pageSize展示用户收藏列表+分页显示。
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(Integer uid, Integer currentPage, Integer pageSize) {
        //1查找当前页面显示的数据集合
        Integer start=(currentPage-1)*pageSize;
        List<Route> routeList=routeDao.findByPage(0,uid,start,pageSize,null,0.0,0.0,false);
        //2.设置总记录数totalCount
        Integer totalCount=favoriteDao.findCountByUid(uid);
        //3.设置总页数totalPage
        Integer totalPage=totalCount % pageSize==0? totalCount / pageSize:(totalCount/pageSize)+1;
        //4.将参数设置到PageBean里并返回
        return PageBeanUtils.setPageBean(totalCount,totalPage,currentPage,pageSize,start,routeList);
    }

    /**
     * 展示收藏排行榜+搜索+分页显示。
     * @param currentPage
     * @param pageSize
     * @param price_1
     * @param price_2
     * @return
     */
    @Override
    public PageBean<Route> rank(Integer currentPage, Integer pageSize, Double price_1, Double price_2,String rname) {
        //1.查找当前页显示数据集合，并且根据收藏次数从高到底排序
        Integer start=(currentPage-1)*pageSize;
        //List<Route> routeList=favoriteDao.findRouteList(0,start,pageSize,price_1,price_2,rname);
        List<Route> routeList=routeDao.findByPage(0,0,start,pageSize,rname,price_1,price_2,true);
        //2.查找总记录数totalCount
        Integer totalCount=routeDao.findTotalCount(0,rname,price_1,price_2);
        //3.计算总页数TotalPage=总记录数/每页显示条数 有余数+1
        Integer totalPage=totalCount % pageSize==0? totalCount / pageSize:(totalCount / pageSize)+1;
        //4.将参数设置到pageBean里并返回
        return PageBeanUtils.setPageBean(totalCount,totalPage,currentPage,pageSize,start,routeList);
    }

    /**
     * 根据cid获取对应的前6条推荐的旅游线路(收藏次数从高到底)
     * @param cid
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findTravelList(Integer cid, Integer pageSize) {
        //return favoriteDao.findRouteList(cid,0,pageSize,0.0,0.0,null);
        return routeDao.findByPage(cid,0,0,pageSize,null,0.0,0.0,true);
    }

    /**
     * 取消收藏
     * @param ridStr
     * @param uid
     */
    @Override
    public void delete(String ridStr, Integer uid) {
        //删除收藏
        favoriteDao.delete(Integer.parseInt(ridStr),uid);
        //查询该路线的收藏次数
        int count=favoriteDao.findCountByRid(Integer.parseInt(ridStr));
        //收藏次数-1
        count=count-1;
        //更新该路线的收藏次数
        favoriteDao.updateCountByRid(Integer.parseInt(ridStr),count);
    }
}
