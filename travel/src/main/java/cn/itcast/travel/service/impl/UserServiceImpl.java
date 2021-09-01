package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();

    /**
     * 用户注册方法
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findUserByUsername(user.getUsername());
        //2.判断u是否为null
        if(u!=null){
            //用户名存在 注册失败
            return false;
        }
        //用户名不存在
        userDao.saveUser(user);
        return true;
    }

    /**
     * 用户登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }


}
