package com.xyq.web.service;

import com.xyq.web.domain.EnvironmentName;
import com.xyq.web.domain.ServiceDetail;

import java.util.List;

public interface LogService {

    //获取数据库中所有的环境名
    List<EnvironmentName> getEnvironmentName();

    //根据环境名获取对应的修改列表
    List<ServiceDetail> getServiceUpdateDetail(String environmentName,Integer currentPage);

    //获取所有服务信息
    List<ServiceDetail> getAllServiceDetail();

    int updateServiceDetail(String preServiceName, String preIp, ServiceDetail service);

    int insertServiceDetail(ServiceDetail serviceDetail);

    int deleteServiceDetail(String serviceName, String ip);

    int countServiceDetail(String environmentName);

    List<ServiceDetail> getServiceDetail(String environmentName);
}
