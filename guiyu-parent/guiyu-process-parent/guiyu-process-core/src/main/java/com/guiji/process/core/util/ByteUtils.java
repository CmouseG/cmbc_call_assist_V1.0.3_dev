package com.guiji.process.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import io.netty.buffer.ByteBuf;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class ByteUtils {

	public static byte[] read(ByteBuf datas) {
		byte[] bytes = new byte[datas.readableBytes()];
		datas.readBytes(bytes);
		return bytes;
	}

	public static Object byteToObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = null;
		try {
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bi);
			IOUtils.closeQuietly(oi);
		}
		return obj;
	}

	public static byte[] objectToByte(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = null;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bo);
			IOUtils.closeQuietly(oo);
		}
		return bytes;
	}
}
