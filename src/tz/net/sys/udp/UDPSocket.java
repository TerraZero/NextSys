package tz.net.sys.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import tz.net.sys.IP;
import tz.net.sys.dns.DNS;
import tz.sys.Sys;

public class UDPSocket {
	
	private DatagramSocket socket;
	private DatagramPacket in;
	private DatagramPacket out;
	private int buffer;
	
	public UDPSocket() {
		
	}
	
	public DatagramSocket socket() {
		return this.socket;
	}
	
	public boolean isBinded() {
		return this.socket != null;
	}
	
	public Exception bind(int port) {
		try {
			this.socket = new DatagramSocket();
			this.socket.connect(InetAddress.getLocalHost(), port);
		} catch (SocketException | UnknownHostException e) {
			Sys.error("Error by bind UDPSocket at port " + port);
			Sys.exception(e);
			return e;
		}
		return null;
	}
	
	public SocketException timeout(int timeout) {
		try {
			this.socket.setSoTimeout(timeout);
		} catch (SocketException e) {
			return e;
		}
		return null;
	}
	
	public int timeout() {
		try {
			return this.socket.getSoTimeout();
		} catch (SocketException e) {
			Sys.exception(e);
		}
		return -1;
	}
	
	public void buffer(int length) {
		this.buffer = length;
	}
	
	public int buffer() {
		return this.buffer;
	}
	
	public UDPAddress address() {
		return new UDPAddress(this.socket.getInetAddress(), this.socket.getLocalPort());
	}
	
}
