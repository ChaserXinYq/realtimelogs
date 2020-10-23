package com.xyq.web.service;

import com.xyq.web.domain.ServiceDetail;

public interface SSHService {

    //返回根据ip和服务名查询到的整条连接记录的数据
    ServiceDetail getServiceDeatil(String ip, String service);

}
