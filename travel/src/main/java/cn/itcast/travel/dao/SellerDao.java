package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    /**
     * 根据routeId查询对应的商家信息
     * @param sid
     * @return
     */
    public Seller findByRid(int sid);
}
