package com.xyq.web.dao;

import com.xyq.web.domain.EnvironmentName;
import com.xyq.web.domain.ServiceDetail;

import java.util.List;

public interface LogDao {


    List<EnvironmentName> getEnvironmentName();

    List<ServiceDetail> getServiceUpdateDetailList(String environmentName,Integer currentPage);

    List<ServiceDetail> getAllServiceDetail();

    int updateServiceDetail(String preServiceName, String preIp, ServiceDetail service);

    int insertServiceDetail(ServiceDetail serviceDetail);

    int deleteServiceDetail(String serviceName, String ip);

    int countServiceDetail(String environmentName);

    List<ServiceDetail> getServiceDetailList(String environmentName);
}
