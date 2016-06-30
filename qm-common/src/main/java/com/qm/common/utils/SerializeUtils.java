package com.qm.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtils.class);

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			LOGGER.error("serialize failed:", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					LOGGER.error("close object output stream failed:", e);
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					LOGGER.error("close byte array output stream failed:", e);
				}
			}
		}
		return null;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			LOGGER.error("deserialize failed:", e);
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					LOGGER.error("close byte array input stream failed:", e);
				}
			}
		}
		return null;
	}
}