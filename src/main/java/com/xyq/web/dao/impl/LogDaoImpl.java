package com.xyq.web.dao.impl;

import com.xyq.web.dao.LogDao;
import com.xyq.web.domain.EnvironmentName;
import com.xyq.web.domain.ServiceDetail;
import com.xyq.web.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class LogDaoImpl implements LogDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<EnvironmentName> getEnvironmentName() {
        String sql = "select distinct environmentname from servicedetail";
        return template.query(sql, new BeanPropertyRowMapper<EnvironmentName>(EnvironmentName.class));
    }

    @Override
    public List<ServiceDetail> getServiceUpdateDetailList(String environmentName, Integer currentPage) {
        Integer offset = (currentPage - 1) * 20;
        String sql = "select * from servicedetail where environmentname = ? LIMIT ? OFFSET ? ";
        return template.query(sql, new BeanPropertyRowMapper<ServiceDetail>(ServiceDetail.class), environmentName, 20, offset);
    }


    @Override
    public List<ServiceDetail> getAllServiceDetail() {
        String sql = "select * from servicedetail";
        return template.query(sql, new BeanPropertyRowMapper<ServiceDetail>(ServiceDetail.class));
    }

    @Override
    public int updateServiceDetail(String preServiceName, String preIp, ServiceDetail service) {
        String sql = "update servicedetail set servicename = ?, ip = ?, port = ?, username = ?, password = ?, directory = ?, environmentname = ? where servicename = ? and ip = ?";
        return template.update(sql, service.getServiceName(), service.getIp(), service.getPort(), service.getUsername(), service.getPassword(), service.getDirectory(), service.getEnvironmentName(), preServiceName, preIp);
    }

    @Override
    public int insertServiceDetail(ServiceDetail serviceDetail) {
        String sql = "insert into servicedetail values(?,?,?,?,?,?,?)";
        int rows = 0;
        try {
            rows = template.update(sql, serviceDetail.getServiceName(), serviceDetail.getIp(), serviceDetail.getPort(), serviceDetail.getUsername(), serviceDetail.getPassword(), serviceDetail.getDirectory(), serviceDetail.getEnvironmentName());
        } catch (Exception e) {
            return 0;
        }
        return rows;
    }

    @Override
    public int deleteServiceDetail(String serviceName, String ip) {
        String sql = "delete from servicedetail where serviceName = ? and ip = ?";
        return template.update(sql, serviceName, ip);
    }

    @Override
    public int countServiceDetail(String environmentName) {
        String sql = "select count(*) from servicedetail where environmentname = ?";
        return template.queryForObject(sql, Integer.class, environmentName);
    }

    @Override
    public List<ServiceDetail> getServiceDetailList(String environmentName) {
        String sql = "select * from servicedetail where environmentname = ?";
        return template.query(sql, new BeanPropertyRowMapper<ServiceDetail>(ServiceDetail.class), environmentName);
    }
}
