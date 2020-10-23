package com.xyq.web.dao.impl;

import com.xyq.web.dao.SSHDao;
import com.xyq.web.domain.ServiceDetail;
import com.xyq.web.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SSHDaoImpl implements SSHDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public ServiceDetail getServiceDetial(String ip, String service) {
        ServiceDetail serviceDetail = null;
        try {
            String sql = "select * from servicedetail where ip = ? and servicename = ?";
            serviceDetail = template.queryForObject(sql, new BeanPropertyRowMapper<ServiceDetail>(ServiceDetail.class), ip, service);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return serviceDetail;
    }
}
