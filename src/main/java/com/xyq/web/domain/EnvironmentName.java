package com.xyq.web.domain;

/**
 * 封装查询返回的环境名
 */
public class EnvironmentName {

    private String environmentName;

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public String toString() {
        return "EnvironmentName{" +
                "environmentName='" + environmentName + '\'' +
                '}';
    }
}
