package tz.sys.vui;

import tz.sys.vui.render.VUIGraphics;
import tz.sys.vui.render.VUIRending;

public class VUIString {

	protected String string;
	protected VUIPanel panel;
	
	public VUIString() {
		this.string = "";
	}

	public VUIString(String string) {
		this.string = string;
	}
	
	public void setPanel(VUIPanel panel) {
		this.panel = panel;
	}
	
	public void set(String line) {
		this.string = line;
		this.refresh();
	}
	
	public void refresh() {
		this.panel.refresh();
	}
	
	public String getParse() {
		return this.string;
	}
	
	public String[] split() {
		return this.getParse().replaceAll("\\{", "|:|{").replaceAll("\\}", "}|:|").split("\\|:\\|");
	}
	
	public boolean print(VUIGraphics v) {
		String[] p = this.split();
		boolean empty = true;
		
		for (int i = 0; i < p.length; i++) {
			if (p[i].length() > 0 && !VUIRending.render(p[i], v)) {
				int x = VUIRendering.getTextWidth(v.g, p[i]);
				
				v.g.setColor(v.bg);
				v.g.fillRect(v.x, v.y - v.size, x, v.h);
				v.g.setColor(v.fg);
				v.g.drawString(p[i], v.x, v.y);
				v.x += x;
				empty = false;
			}
		}
		if (empty) {
			v.y -= v.h;
		}
		return !empty;
	}
	
	public boolean empty() {
		String[] p = this.split();
		
		for (int i = 0; i < p.length; i++) {
			if (p[i].length() > 0 && !VUIRending.rendable(p[i])) return false;
		}
		return true;
	}
	
	public String get() {
		return this.string;
	}
	
	public void append(String append) {
		this.string += append;
		this.panel.refresh();
	}
	
	public void prepend(String prepend) {
		this.string = prepend + this.string;
		this.panel.refresh();
	}
	
	@Override
	public String toString() {
		return this.string;
	}
	
	@SuppressWarnings("unchecked")
	public <type extends VUIString> type adding(VUIPanel panel, VUILines lines, type line, boolean newLine) {
		if (newLine) {
			lines.add(this);
			line = null;
		} else {
			line = (type)this;
		}
		panel.refresh();
		return line;
	}
	
}
