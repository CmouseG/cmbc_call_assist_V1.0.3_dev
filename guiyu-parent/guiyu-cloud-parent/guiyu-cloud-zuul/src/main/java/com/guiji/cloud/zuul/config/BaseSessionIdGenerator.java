package com.guiji.cloud.zuul.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.Base64;
import java.util.UUID;

public class BaseSessionIdGenerator implements SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		return Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
	}

}
