package tz.sys.cui.commands;

import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.vui.VUI;

public class CUIClear implements CUICommand {

	@Override
	public String name() {
		return "clear";
	}

	@Override
	public CUIState command(CommandString command) {
		VUI.clear();
		return CUIState.OK;
	}
	
}
