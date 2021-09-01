package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 通过姓名查找用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     */
    public void saveUser(User user);

    /**
     * 通过用户名和密码查找对象
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username,String password);
}
