package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * 根据rid和uid来查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(Integer rid, Integer uid);

    /**
     * 根据rid查询该线路已经被收藏的次数
     * @param rid
     * @return
     */
    public int findCountByRid(Integer rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(Integer rid,Integer uid);


    /**
     * 根据用户id查询该用户总收藏个数
     * @param uid
     * @return
     */
    public Integer findCountByUid(Integer uid);

    /**
     * 根据rid更新该路线的收藏次数
     * @param rid
     * @param count
     */
    public void updateCountByRid(Integer rid,Integer count);




    /**
     * 取消收藏
     * @param rid
     * @param uid
     */
    public void delete(Integer rid,Integer uid);
}
