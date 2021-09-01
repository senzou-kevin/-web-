package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao=new CategoryDaoImpl();

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //1.从redis中查询 获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //查询sortedSet中的分数(cid)和值(cname)
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);

        //2.判断查询的集合是否为空
        List<Category> cs=null;
        if(categories==null||categories.size()==0){
            //3.如果为空，第一次访问，需要从数据库查询，再将数据存入redis
            cs=categoryDao.findAll();
            //存入redis key为category
            for(int i=0;i<cs.size();i++) {
                //将分类名字和分类id 做了value 存入redis
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname() );
            }
        }else {
            //4.如果不为空
            cs=new ArrayList<Category>();
            for(Tuple tuple:categories){
                Category category = new Category();
                //获取分类的名字
                category.setCname(tuple.getElement());
                //获取分类的id
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
