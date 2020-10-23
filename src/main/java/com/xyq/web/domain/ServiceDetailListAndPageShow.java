package com.xyq.web.domain;

import java.util.List;

public class ServiceDetailListAndPageShow {

    private List<ServiceDetail> list;

    private Integer pageCount;

    private Integer currentPage;

    @Override
    public String toString() {
        return "ServiceDetailListAndPageShow{" +
                "list=" + list +
                ", pageCount=" + pageCount +
                ", currentPage=" + currentPage +
                '}';
    }

    public List<ServiceDetail> getList() {
        return list;
    }

    public void setList(List<ServiceDetail> list) {
        this.list = list;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
