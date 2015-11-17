package tz.sys.net.udp.api;

public interface NetPacketType {

	public int type();
	
	public byte[] data();
	
	public int sector(int id);
	
}
