package tz.sys.cui;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.sys.Sys;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.cui.api.ResolveString;
import tz.sys.cui.vui.CUIInput;
import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.api.Program;
import tz.sys.vui.VUI;
import tz.sys.vui.util.VUIUtil;

@Program(name="CUI", tags={"vui"})
public class CUI {
	
	private static Map<String, CUICommand> commands;
	private static int runs;
	
	static {
		CUI.commands = new HashMap<String, CUICommand>();
		ReflectUtil util = ReflectUtil.implement(CUICommand.class);
		List<CUICommand> commands = util.create();
		for (CUICommand c : commands) {
			CUI.commands.put(c.name(), c);
		}
		ReflectUtil.trigger("CUI");
	}

	public static void program() {
		CUI.runs++;
		VUI.clear();
		CUIInput input = null;
		do {
			input = VUI.write(new CUIInput());
			CUIState state = CUI.command(input.input());
			if (state == CUIState.EXIT) {
				CUI.runs--;
			}
		} while (CUI.runs > 0);
		
		System.exit(0);
	}
	
	public static CUIState command(String input) {
		CommandString cs = new CommandString(input);
		CUICommand c = CUI.commands.get(cs.params()[0]);
		if (c == null) {
			VUI.write(VUIUtil.color(Color.YELLOW) + "Command '" + cs.params()[0] + "' not found!" + VUIUtil.colorReset());
		} else {
			try {
				return c.command(cs);
			} catch (Exception e) {
				Sys.exception(e);
				return CUIState.ERROR;
			}
		}
		return CUIState.OK;
	}
	
	public static List<ResolveString> resolve(String command) {
		return CUIUtil.startsWith(CUI.commands.keySet(), command);
	}
	
	public static void exit() {
		VUI.write("CUI >>> exit (run " + CUI.runs + ")");
	}
	
	public static CUICommand getCommand(String name) {
		return CUI.commands.get(name);
	}
	
	public static int runs() {
		return CUI.runs;
	}
	
}
