package com.xyq.web.service.impl;

import com.xyq.web.dao.LogDao;
import com.xyq.web.dao.impl.LogDaoImpl;
import com.xyq.web.domain.EnvironmentName;
import com.xyq.web.domain.ServiceDetail;
import com.xyq.web.service.LogService;

import java.util.List;

public class LogServiceImpl implements LogService {

    private LogDao logDao = new LogDaoImpl();

    @Override
    public List<EnvironmentName> getEnvironmentName() {
        return logDao.getEnvironmentName();
    }

    @Override
    public List<ServiceDetail> getServiceUpdateDetail(String environmentName, Integer currentPage) {
        return logDao.getServiceUpdateDetailList(environmentName, currentPage);
    }

    @Override
    public List<ServiceDetail> getAllServiceDetail() {
        return logDao.getAllServiceDetail();
    }

    @Override
    public int updateServiceDetail(String preServiceName, String preIp, ServiceDetail service) {
        return logDao.updateServiceDetail(preServiceName, preIp, service);

    }

    @Override
    public int insertServiceDetail(ServiceDetail serviceDetail) {
        return logDao.insertServiceDetail(serviceDetail);
    }

    @Override
    public int deleteServiceDetail(String serviceName, String ip) {
        return logDao.deleteServiceDetail(serviceName, ip);
    }

    @Override
    public int countServiceDetail(String environmentName) {
        return logDao.countServiceDetail(environmentName);
    }

    @Override
    public List<ServiceDetail> getServiceDetail(String environmentName) {
        return logDao.getServiceDetailList(environmentName);
    }
}
