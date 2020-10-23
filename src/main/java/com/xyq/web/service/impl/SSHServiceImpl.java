package com.xyq.web.service.impl;

import com.xyq.web.dao.SSHDao;
import com.xyq.web.dao.impl.SSHDaoImpl;
import com.xyq.web.domain.ServiceDetail;
import com.xyq.web.service.SSHService;

public class SSHServiceImpl implements SSHService {

    private SSHDao dao = new SSHDaoImpl();

    @Override
    public ServiceDetail getServiceDeatil(String ip, String service) {
        return dao.getServiceDetial(ip, service);
    }
}
