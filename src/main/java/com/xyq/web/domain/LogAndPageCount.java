package com.xyq.web.domain;

public class LogAndPageCount {

    private StringBuilder log;
    private int pageCount;
    private int currentPage;

    public StringBuilder getLog() {
        return log;
    }

    public void setLog(StringBuilder log) {
        this.log = log;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "LogAndPageCount{" +
                "log=" + log +
                ", pageCount=" + pageCount +
                ", currentPage=" + currentPage +
                '}';
    }
}
