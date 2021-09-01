package cn.itcast.travel.domain;

import java.util.List;

/**
 * 用于传递分页数据
 */
public class PageBean<T> {
    //总记录数
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //当前页码
    private Integer currentPage;
    //每页显示的条目数
    private Integer pageSize;
    //起始索引
    private Integer start;
    //每页显示的数据集合
    private List<T> list;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
