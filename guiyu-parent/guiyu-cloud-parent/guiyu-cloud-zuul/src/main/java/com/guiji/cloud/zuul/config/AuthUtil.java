package com.guiji.cloud.zuul.config;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class AuthUtil {

	public static String encrypt(String original){
		SimpleHash hash=new SimpleHash(Sha512Hash.ALGORITHM_NAME,original);
		return hash.toString();
	}
}
