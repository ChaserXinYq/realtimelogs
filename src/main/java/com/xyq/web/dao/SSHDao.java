package com.xyq.web.dao;

import com.xyq.web.domain.ServiceDetail;

public interface SSHDao {

    ServiceDetail getServiceDetial(String ip, String service);

}
