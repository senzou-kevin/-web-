package cn.itcast.travel.util;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public class PageBeanUtils {


    public static PageBean<Route> setPageBean(Integer totalCount, Integer totalPage, Integer currentPage, Integer pageSize, Integer start, List<Route> list){
        PageBean<Route> pageBean=new PageBean<Route>();
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setStart(start);
        pageBean.setList(list);
        return pageBean;
    }
}
