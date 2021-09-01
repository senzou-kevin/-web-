package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 通过姓名查找用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        User user=null;
        try {
            //1.定义sql语句
            String sql = "select * from tab_user where username=?";
            //2.执行查询
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    /**
     * 保存用户信息
     * @param user
     */
    @Override
    public void saveUser(User user) {
        //1.定义sql语句
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email)" +
                "values(?,?,?,?,?,?,?)";
        //2.执行添加
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail());
    }

    /**
     * 通过用户名和密码查找对象
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user=null;
        try {
            //1.定义SQL语句
            String sql = "select * from tab_user where username=? and password=?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch (Exception e){

        }
        return user;
    }
}
