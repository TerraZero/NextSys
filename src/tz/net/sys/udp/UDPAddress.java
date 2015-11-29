package tz.net.sys.udp;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UDPAddress {

	private InetSocketAddress address;
	
	public UDPAddress(String ip, int port) {
		this.address = new InetSocketAddress(ip, port);
	}
	
	public UDPAddress(InetAddress address, int port) {
		this.address = new InetSocketAddress(address, port);
	}
	
	public InetSocketAddress address() {
		return address;
	}
	
	public String ip() {
		return this.address.getHostString();
	}
	
	public int port() {
		return this.address.getPort();
	}
	
	public String lookup() {
		return this.address.getHostName();
	}
	
	public String toString() {
		return this.ip() + ":" + this.port() + " [" + this.lookup() + "]";
	}
	
}
