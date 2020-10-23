package com.xyq.web.domain;

/**
 * 封装了配置文件中服务对应的ip，服务名，路径等...
 */
public class ServiceDetail {

    //serviceName,ip,port,username,password,directory
    private String serviceName;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String directory;
    private String environmentName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public String toString() {
        return "ServiceDetail{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", directory='" + directory + '\'' +
                ", environmentName='" + environmentName + '\'' +
                '}';
    }
}
