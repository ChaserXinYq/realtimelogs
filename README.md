# realtimelogs
由于微服务日志太多，每次查看都需要连接登录等一系列操作，很麻烦
由此写出这个工具平台，web展示日志
通过一个配置文件记录ip,port,username,password,serviceName,logDirector。然后进行ssh连接并获取需要读取的日志文件
