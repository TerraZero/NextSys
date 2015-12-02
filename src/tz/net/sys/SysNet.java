package tz.net.sys;

import java.nio.ByteBuffer;

import tz.net.sys.udp.UDPSocket;
import tz.sys.reflect.annot.Program;
import tz.sys.vui.VUI;

@Program(name="Test Net")
public class SysNet {

	public static void program() {
		VUI.clear();
		VUI.write("Start SysNet");
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putInt(555);
		for (byte bb : b.array()) {
			VUI.write(bb + "");
		}
		ByteBuffer buffer = ByteBuffer.wrap(b.array());
		VUI.write(buffer.getInt() + "");
		UDPSocket s = new UDPSocket();
		s.bind(31579);
		VUI.write(s.address().toString());
	}
	
}
