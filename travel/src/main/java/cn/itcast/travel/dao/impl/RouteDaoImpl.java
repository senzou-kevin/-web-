package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template =new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     * @param cid
     * @return
     */
    @Override
    public Integer findTotalCount(Integer cid,String rname,Double price_1,Double price_2) {
        //1.定义sql模板
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List params=new ArrayList();
        //2.判断参数是否有值
        cidCheck(params,sb,cid);
        priceCheck(params,sb,price_1,price_2);
        rnameCheck(params,sb,rname);
        sql=sb.toString();
        //2.执行sql
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    /**
     * 根据 cid start pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(Integer cid,Integer uid, Integer start, Integer pageSize,String rname,Double price_1,Double price_2,boolean isOrdered) {
        //1.定义sql模板
        String sql="select * from tab_route where 1=1 ";
        StringBuilder sb =new StringBuilder(sql);
        List params =new ArrayList();
        //判断参数是否有值
        cidCheck(params,sb,cid);
        if(uid!=0){
            sb.append(" and rid in (\n" +
                    "  select rid\n" +
                    "  from tab_favorite\n" +
                    "  where uid=? \n" +
                    " ) ");
            params.add(uid);
        }
        priceCheck(params,sb,price_1,price_2);
        rnameCheck(params,sb,rname);
        if(isOrdered){
            sb.append(" order by count desc ");
        }
        //分页条件
        sb.append(" limit ? , ? ");
        sql=sb.toString();
        params.add(start);
        params.add(pageSize);

        //2.执行sql
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     * 根据rid查询Route对象
     * @param rid
     * @return
     */
    @Override
    public Route findByRid(int rid) {
        String sql="select * from tab_route where rid=? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    /**
     * 根据路线的添加时间找到最新的前4条旅游路线
     */
    @Override
    public List<Route> findNewestRoute(Integer pageSize) {
        //1.定义sql语句
        String sql="select * from tab_route order by rdate desc limit 0, ?";
        //2.执行sql
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),pageSize);
    }

    /**
     * 找到前4主题旅游
     */
    @Override
    public List<Route> findThemeTravel(Integer pageSize) {
        //1.定义SQL
        String sql="select * from tab_route where isThemeTour=1 limit 0,?";
        //2.执行SQL
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),pageSize);
    }

    /**
     * 检查cid是否有值
     * @param params
     * @param sb
     * @param cid
     */
    private void cidCheck(List params,StringBuilder sb,Integer cid){
        if(cid!=0){
            sb.append(" and cid=? ");
            params.add(cid);
        }
    }

    /**
     * 检查价格是否有值
     * @param params
     * @param sb
     * @param price_1
     * @param price_2
     */
    private void priceCheck(List params,StringBuilder sb,Double price_1,Double price_2){
        if(price_1!=0.0 && price_2!=0.0){
            sb.append(" and price between ? and ? ");
            params.add(price_1);
            params.add(price_2);
        }else if(price_1!=0.0){
            sb.append(" and price >= ? ");
            params.add(price_1);
        }else if(price_2!=0.0){
            sb.append(" and price <= ? ");
            params.add(price_2);
        }
    }

    /**
     * 检查线路名是否有值
     * @param params
     * @param sb
     * @param rname
     */
    private void rnameCheck(List params,StringBuilder sb,String rname){
        if(rname!=null && rname.length()>0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
    }

}
