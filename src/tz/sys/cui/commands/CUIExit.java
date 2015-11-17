package tz.sys.cui.commands;

import java.util.List;

import tz.sys.SysUtil;
import tz.sys.cui.CUI;
import tz.sys.cui.CUIUtil;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.cui.api.ResolveString;
import tz.sys.vui.util.VUIUtil;

public class CUIExit implements CUICommand {

	@Override
	public String name() {
		return "exit";
	}

	@Override
	public List<ResolveString> resolve(String[] param, String resolve) {
		String[] list = new String[]{"number:0"};
		return CUIUtil.startsWith(list, resolve);
	}

	@Override
	public CUIState command(CommandString command) {
		if (command.params().length > 1) {
			VUIUtil util = command.number(1);
			if (util.isOk()) {
				CUI.exit();
				System.exit(util.number());
			} else {
				SysUtil.error(util.error());
				return CUIState.ERROR;
			}
		} else {
			CUI.exit();
			return CUIState.EXIT;
		}
		return CUIState.OK;
	}
	
}
