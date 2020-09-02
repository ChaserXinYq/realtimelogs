package com.xyq.web.domain;

public class LogAndPageCount {

    private StringBuilder log;
    private int pageCount;

    @Override
    public String toString() {
        return "LogAndPageCount{" +
                "log=" + log +
                ", pageCount=" + pageCount +
                '}';
    }

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
}
