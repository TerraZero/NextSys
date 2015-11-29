package tz.net.sys.cui;

import java.util.ArrayList;
import java.util.List;

import tz.net.sys.udp.UDPSocket;
import tz.sys.cui.CUIUtil;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.cui.api.ResolveString;
import tz.sys.vui.VUI;

public class NCUI implements CUICommand {

	@Override
	public String name() {
		return "net";
	}
	
	@Override
	public List<ResolveString> resolve(String[] param, String resolve) {
		List<String> list = new ArrayList<String>();
		list.add("host:localhost");
		list.add("port:0");
		return CUIUtil.startsWith(list, resolve);
	}

	@Override
	public CUIState command(CommandString command) {
		if (command.params().length == 3) {
			UDPSocket socket = new UDPSocket();
			socket.bind(command.number(2).number());
			VUI.write("Connect socket on: " + socket.address().toString());
		}
		return CUIState.OK;
	}

}
