package com.guiji.auth.util;

import java.util.Random;

import org.apache.shiro.crypto.hash.Md2Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class AuthUtil {
	
	private static final Random random =new Random();

	public static String encrypt(String original){
		SimpleHash hash=new SimpleHash(Sha512Hash.ALGORITHM_NAME,original);
		return hash.toString();
	}
	
	
	public static String encryptMd2(){
		String original=String.valueOf(random.nextLong());
		SimpleHash hash=new SimpleHash(Md2Hash.ALGORITHM_NAME,original);
		return hash.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(encryptMd2());
		System.out.println(encryptMd2());
		System.out.println(encryptMd2());
	}
}
