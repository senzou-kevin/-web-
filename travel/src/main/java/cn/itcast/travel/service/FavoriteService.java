package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,Integer uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(String rid,Integer uid);

    /**
     * 通过用户id,currentPage,pageSize展示用户收藏列表+分页显示。
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<Route> pageQuery(Integer uid,Integer currentPage,Integer pageSize);

    /**
     * 展示收藏排行榜+搜索+分页显示。
     * @param currentPage
     * @param pageSize
     * @param price_1
     * @param price_2
     * @return
     */
    public PageBean<Route> rank(Integer currentPage,Integer pageSize,Double price_1,Double price_2,String rname);

    /**
     * 根据cid获取对应的前6条推荐的旅游线路(收藏次数从高到底)
     * @param cid
     * @param pageSize
     * @return
     */
    public List<Route> findTravelList(Integer cid,Integer pageSize);

    /**
     * 取消收藏
     * @param ridStr
     * @param uid
     */
    public void delete(String ridStr,Integer uid);
}
