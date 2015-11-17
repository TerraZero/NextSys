package tz.sys.cui.api;

import tz.sys.vui.util.VUIUtil;

public class CommandString {
	
	private String line;
	private String[] params;

	public CommandString(String line) {
		this.line = line;
		this.params = this.line.split(" ");
	}
	
	public String line() {
		return this.line;
	}
	
	public String[] params() {
		return this.params;
	}
	
	public VUIUtil number(int pos) {
		return VUIUtil.number(this.params[pos], "Expect a number at argpos " + pos + " instead it was \"[val]\"!");
	}
	
	public VUIUtil bool(int pos) {
		return VUIUtil.bool(this.params[pos], "Expect a boolean at argpos " + pos + " instead it was \"[val]\"!");
	}
	
}
