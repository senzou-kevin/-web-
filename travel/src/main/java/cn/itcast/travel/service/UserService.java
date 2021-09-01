package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    /**
     * 用户注册方法
     * @param user
     * @return
     */
    public boolean register(User user);

    /**
     * 用户登录方法
     * @param user
     * @return
     */
    public User login(User user);
}
