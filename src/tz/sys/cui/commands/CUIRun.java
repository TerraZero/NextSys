package tz.sys.cui.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tz.sys.Sys;
import tz.sys.cui.CUIUtil;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.CUIState;
import tz.sys.cui.api.CommandString;
import tz.sys.cui.api.ResolveString;
import tz.sys.reflect.Reflect;
import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.annot.Program;
import tz.sys.vui.VUI;

public class CUIRun implements CUICommand {
	
	private ReflectUtil util;
	
	public CUIRun() {
		this.util = ReflectUtil.annotation(Program.class);
	}

	@Override
	public String name() {
		return "run";
	}
	
	@Override
	public List<ResolveString> resolve(String[] param, String resolve) {
		List<String> list = new ArrayList<String>();
		for (Reflect r : this.util.reflects()) {
			Program p = r.annotation(Program.class);
			if (Sys.isIntern(p.tags(), "vui")) {
				list.add(this.key(p.name()));
			}
		}
		return CUIUtil.startsWith(list, resolve);
	}

	@Override
	public CUIState command(CommandString command) {
		if (command.params().length > 1) {
			String program = command.params()[1];
			boolean ok = false;
			for (Reflect r : this.util.reflects()) {
				Program p = r.annotation(Program.class);
				if (this.key(p.name()).equals(program)) {
					if (Sys.isIntern(p.tags(), "vui")) {
						VUI.run(p.name());
						ok = true;
						break;
					} else {
						Sys.error("Program has not the 'vui' tag.");
						return CUIState.ERROR;
					}
				}
			}
			if (!ok) {
				Sys.error("Program not found.");
				return CUIState.ERROR;
			}
		} else {
			Sys.error("Not enough params.");
			return CUIState.ERROR;
		}
		System.out.println(Arrays.toString(command.params()));
		return CUIState.OK;
	}
	
	public String key(String name) {
		return name.replace(" ", "_").toLowerCase();
	}

}
