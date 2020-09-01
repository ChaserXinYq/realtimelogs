package com.xyq.web.domain;

public class ResultInfo {

    private boolean flag;
    private Object obj;
    private String errorMsg;

    public ResultInfo() {
    }

    public ResultInfo(boolean flag, Object obj, String errorMsg) {
        this.flag = flag;
        this.obj = obj;
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", obj=" + obj +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
