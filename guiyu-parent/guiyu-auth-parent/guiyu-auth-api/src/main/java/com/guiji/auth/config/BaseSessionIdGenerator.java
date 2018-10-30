package com.guiji.auth.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class BaseSessionIdGenerator implements SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		return Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
	}

}
