#####  通过serviceid获取该服务实例列表
    使用guiyu-utils下的ServerUtil类
    示例：ServerUtil.getInstances(discoveryClient,"guiyu-callcenter-ccmanager");

#####  获取服务自身的instance-id
    @Value("${eureka.instance.instance-id}")  
    private String instanceId;