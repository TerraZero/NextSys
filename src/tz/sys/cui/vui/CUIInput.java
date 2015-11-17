package tz.sys.cui.vui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tz.sys.cui.CUI;
import tz.sys.cui.api.CUICommand;
import tz.sys.cui.api.ResolveString;
import tz.sys.vui.VUI;
import tz.sys.vui.VUIRendering;
import tz.sys.vui.VUIString;
import tz.sys.vui.input.VUIInput;
import tz.sys.vui.util.VUIUtil;

public class CUIInput extends VUIInput {
	
	private List<ResolveString> resolveList;
	private List<VUIString> options;
	private List<List<String>> resolves;
	private int index;
	
	@Override
	protected void init() {
		this.index = -1;
		super.init();
	}
	
	@Override
	protected void initKeys() {
		this.adapter = new CUIInputKeys(this);
	}

	public String stringRefresh() {
		String text = "";
		String prefix = "";
		String suffix = "";
		if (this.pos() == this.input.length()) {
			prefix = this.input;
		} else {
			text = this.input.substring(this.pos(), this.pos() + 1);
			prefix = this.input.substring(0, this.pos());
			suffix = this.input.substring(this.pos() + 1);
		}
		return "CUI > {#:00ff00}" + prefix + "{rect:mode-'draw';w-12;y-'middle';x-2;text-'" + text + "';tx-3}{util:x-2}" + suffix + "{#:#}";
	}
	
	public String stringReleasedInput() {
		return "CUI > " + VUIUtil.color(Color.GREEN) + this.input + VUIUtil.colorReset();
	}
	
	public String stringAcceptString() {
		return "CUI > " + VUIUtil.color(Color.GREEN) + this.input + VUIUtil.colorReset();
	}
	
	public String[] command() {
		return this.input.replaceAll(" +", " ").split(" ");
	}
	
	public void resolve() {
		if (this.index == -1) {
			String[] command = this.command();
			String toResolve = null;
			if (!this.input.endsWith(" ")) {
				toResolve = command[command.length - 1];
			}
			if (command.length == 1 && toResolve != null) {
				this.resolveList = CUI.resolve(toResolve);
			} else {
				CUICommand c = CUI.getCommand(command[0]);
				if (c != null) {
					this.resolveList = c.resolve(command, toResolve);
				}
			}
			if (this.resolveList != null) {
				if (this.resolveList.size() == 1 && !this.resolveList.get(0).isOptional()) {
					this.index = 0;
					this.setResolve();
					this.index = -1;
					this.refresh();
				} else {
					this.addResolve();
				}
			}
		} else {
			this.nextResolve();
		}
	}
	
	public void typed() {
		this.clearResolve();
	}
	
	@Override
	public void refresh() {
		if (this.index != -1) {
			this.refreshResolve();
		}
		super.refresh();
	}
	
	@Override
	public void enter() {
		if (this.index == -1) {
			this.freezeOut();
		} else {
			this.setResolve();
			this.clearResolve();
			this.refresh();
		}
	}
	
	public void setResolve() {
		String[] command = this.command();
		if (this.input.endsWith(" ")) {
			List<String> c = new ArrayList<String>(command.length + 1);
			for (String s : command) {
				c.add(s);
			}
			c.add(this.resolveList.get(this.index).resolve());
			command = c.toArray(new String[c.size()]);
		} else {
			command[command.length - 1] = this.resolveList.get(this.index).resolve();
		}
		this.setInput(String.join(" ", command) + " ");
		this.setPos(this.input.length());
	}
	
	public void nextResolve() {
		this.index++;
		if (this.index == this.resolveList.size()) this.index = 0;
		this.refresh();
	}
	
	public void addResolve() {
		this.index = 0;
		int w = this.panel.getWidth();
		StringBuilder sb = new StringBuilder();
		Iterator<ResolveString> it = this.resolveList.iterator();
		this.options = new ArrayList<VUIString>();
		
		this.resolves = new ArrayList<List<String>>();
		List<String> tmp = new ArrayList<String>();
		
		while (it.hasNext()) {
			ResolveString s = it.next();
			sb.append(s.label()).append("    ");
			if (w < VUIRendering.getTextWidth(this.panel.getGraphics(), sb.toString())) {
				this.resolves.add(tmp);
				tmp = new ArrayList<String>();
				sb.setLength(0);
				sb.append(s.label()).append("    ");
				tmp.add(s.label());
			} else {
				tmp.add(s.label());
			}
		}
		this.resolves.add(tmp);
		
		for (List<String> s : this.resolves) {
			this.options.add(VUI.write(String.join("    ", s)));
		}
		this.refreshResolve();
	}
	
	public void refreshResolve() {
		int i = 0;
		int il = 0;
		for (List<String> l : this.resolves) {
			for (int z = 0; z < l.size(); z++) {
				if (i == this.index) {
					l.set(z, VUIUtil.colorRevert() + this.resolveList.get(i).label() + VUIUtil.colorReset());
				} else {
					l.set(z, this.resolveList.get(i).label());
				}
				i++;
			}
			this.options.get(il).set(String.join("    ", l));
			il++;
		}
	}
	
	public void clearResolve() {
		if (this.index != -1) {
			this.resolveList = null;
			for (VUIString s : this.options) {
				VUI.remove(s);
			}
			this.options = null;
			this.index = -1;
		}
	}
	
}
