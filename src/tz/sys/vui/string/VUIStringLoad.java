package tz.sys.vui.string;

import java.text.DecimalFormat;

import tz.sys.vui.VUIString;

public class VUIStringLoad extends VUIString {
	
	private double state;

	public VUIStringLoad() {
		
	}
	
	public VUIStringLoad(String name) {
		super(name);
	}
	
	public VUIStringLoad(double state) {
		this.state = state;
	}
	
	public double load() {
		return this.state;
	}
	
	public void load(double state) {
		this.state = state;
		this.refresh();
	}
	
	@Override
	public String getParse() {
		String num = new DecimalFormat("000.00").format(this.state);
		String s = "{end:] " + num + "%}" + this.string + " [{balk:" + this.state + "}";
		if (this.state == 100) {
			s = "{#:00ff00}" + s + "{#:#}";
		}
		return s;
	}

}
