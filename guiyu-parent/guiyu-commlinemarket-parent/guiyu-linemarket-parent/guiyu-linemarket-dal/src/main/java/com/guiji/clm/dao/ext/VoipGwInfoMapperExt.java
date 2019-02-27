package com.guiji.clm.dao.ext;

public interface VoipGwInfoMapperExt {
   
	/**
	 * 查询目前最大的SIP账号
	 * @return
	 */
	Integer queryMaxSipAccount();
}