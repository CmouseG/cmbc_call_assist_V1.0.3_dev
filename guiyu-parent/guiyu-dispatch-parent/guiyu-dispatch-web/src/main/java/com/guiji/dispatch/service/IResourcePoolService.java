package com.guiji.dispatch.service;

/**
 * Created by ty on 2019/1/7.
 */
public interface IResourcePoolService {
    /**
     * 初始化资源池
     * @return
     */
    boolean initResourcePool();

    /**
     * 整点或有新用户加入时重新分配比例
     * @return
     */
    boolean distributeByUser() throws Exception;
}
