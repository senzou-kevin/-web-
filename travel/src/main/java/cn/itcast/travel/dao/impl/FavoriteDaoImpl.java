package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid和uid来查询收藏信息
     * 如果查到，说明该线路已被用户收藏过。
     * 未查到，说明该线路未被用户收藏过。
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(Integer rid, Integer uid) {
        Favorite favorite=null;
        try {
            //1.定义sql
            String sql = "select * from tab_favorite where rid=? and uid=?";
            //2.执行sql
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (DataAccessException e){

        }
        return favorite;
    }

    /**
     * 根据rid查询该线路已经被收藏的次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(Integer rid) {
        //1.定义sql语句
        String sql="select count from tab_route where rid=?";
        //2.执行sql
        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(Integer rid, Integer uid) {
        //1定义sql语句
        String sql="insert into tab_favorite(rid,date,uid)" +
                "values(?,?,?)";
        //2.执行sql
        template.update(sql,rid,new Date(),uid);
    }

    /**
     * 根据用户id查询该用户总收藏个数
     * @param uid
     * @return
     */
    @Override
    public Integer findCountByUid(Integer uid) {
        //定义sql
        String sql="select count(*) from tab_favorite where uid=?";
        //2.执行sql
        return template.queryForObject(sql,Integer.class,uid);
    }

    /**
     * 根据rid更新该路线的收藏次数
     * @param rid
     * @param count
     */
    @Override
    public void updateCountByRid(Integer rid, Integer count) {
        //定义sql
        String sql="update tab_route set count=? where rid=?";
        //执行sql
        template.update(sql,count,rid);
    }
    /**
     * 取消收藏
     * @param rid
     * @param uid
     */
    @Override
    public void delete(Integer rid, Integer uid) {
        //1.定义sql
        String sql="delete from tab_favorite where rid=? and uid=?";
        //2.执行sql
        template.update(sql,rid,uid);
    }
}
