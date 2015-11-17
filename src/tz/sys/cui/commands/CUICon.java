package tz.sys.cui.commands;

import java.util.ArrayList;
import java.util.List;

import tz.sys.SysUtil;
import tz.sys.cui.CUI;
import tz.sys.cui.CUIUtil;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.cui.api.ResolveString;
import tz.sys.vui.VUI;
import tz.sys.vui.util.VUIUtil;

public class CUICon implements CUICommand {

	@Override
	public String name() {
		return "con";
	}
	
	@Override
	public List<ResolveString> resolve(String[] param, String resolve) {
		List<String> list = new ArrayList<String>();
		
		switch (param.length) {
			case 1 :
				list.add("debug");
				list.add("runs");
				break;
			case 2 :
				list.add("true");
				list.add("false");
				break;
		}
		
		return CUIUtil.startsWith(list, resolve);
	}

	@Override
	public CUIState command(CommandString command) {
		if (command.params().length == 3) {
			if (command.params()[1].equals("debug")) {
				VUIUtil util = command.bool(2);
				if (util.isOk()) {
					VUI.debug(util.bool());
				} else {
					SysUtil.error(util.error());
				}
			}
		}
		if (command.params().length > 1) {
			if (command.params()[1].equals("runs")) {
				VUI.write("runs " + CUI.runs());
			}
		}
		return CUIState.OK;
	}

}
