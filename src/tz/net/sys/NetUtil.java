package tz.net.sys;

import java.nio.ByteBuffer;
import java.util.UUID;

public class NetUtil {

	public static byte[] fromInt(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(value);
		return buffer.array();
	}
	
	public static int fromByte(byte[] bytes) {
		return NetUtil.fromByte(bytes, 0);
	}

	public static int fromByte(byte[] bytes, int offset) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, 4);
		return buffer.getInt();
	}
	
	public static byte[] fill(byte[] bytes, String data, int offset) {
		byte[] d = data.getBytes();
		for (int i = 0; i < d.length; i++) {
			bytes[i + offset] = d[i];
		}
		return bytes;
	}
	
	public static byte[] fill(byte[] bytes, int data, int offset) {
		byte[] d = NetUtil.fromInt(data);
		for (int i = 0; i < d.length; i++) {
			bytes[i + offset] = d[i];
		}
		return bytes;
	}
	
	public static byte[] fill(byte[] bytes, byte[] data, int offset) {
		for (int i = 0; i < data.length; i++) {
			bytes[i + offset] = data[i];
		}
		return bytes;
	}
	
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	
}
