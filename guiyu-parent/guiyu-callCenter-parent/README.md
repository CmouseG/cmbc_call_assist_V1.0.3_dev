##  通过serviceid获取该服务实例列表
    示例：ServerUtil.getInstances(discoveryClient,"guiyu-callcenter-ccmanager");

##  获取自身的instance-id
    @Value("${eureka.instance.instance-id:}")  

    private String instanceId;