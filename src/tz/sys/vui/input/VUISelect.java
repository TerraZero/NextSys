package tz.sys.vui.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import tz.sys.vui.VUI;
import tz.sys.vui.VUILines;
import tz.sys.vui.VUIPanel;
import tz.sys.vui.VUIString;
import tz.sys.vui.util.VUIUtil;

public class VUISelect extends VUIInput {
	
	private String[] o;
	private VUIString[] options;
	private int selected;
	private String description;

	public VUISelect(String description, String... options) {
		this.description = description;
		this.o = options;
		this.options = new VUIString[this.o.length];
		for (int i = 0; i < this.options.length; i++) {
			this.options[i] = new VUIString("{rect:y-!6;h-5;w-5}{util:x-5}" + this.o[i]);
		}
	}
	
	public VUISelect(String description, List<String> options) {
		this.description = description;
		this.o = new String[options.size()];
		this.o = options.toArray(this.o);
		this.options = new VUIString[this.o.length];
		for (int i = 0; i < this.options.length; i++) {
			this.options[i] = new VUIString("{rect:y-!6;h-5;w-5}{util:x-5}" + this.o[i]);
		}
	}
	
	@Override
	protected void init() {
		this.selected = 0;
		this.adapter = new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case 9 :
					case 40 :
						VUISelect.this.setSelect((VUISelect.this.selected + 1) % VUISelect.this.o.length);
						break;
					case 38 :
						VUISelect.this.setSelect(VUISelect.this.selected == 0 ? VUISelect.this.o.length - 1 : VUISelect.this.selected - 1);
						break;
					case 10 :
						VUISelect.this.enter();
						break;
				}
			}
			
		};
	}

	public void setSelect(int select) {
		this.options[this.selected].set("{rect:y-!6;h-5;w-5}{util:x-5}" + this.o[this.selected]);
		this.options[select].set("{rect:y-!6;h-5;w-5}{util:x-5}" + VUIUtil.colorRevert() + this.o[select] + VUIUtil.colorRevert());
		this.selected = select;
	}
	
	@Override
	public String input() {
		return this.o[this.selected];
	}
	
	public void enter() {
		this.freezeOut();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <type extends VUIString> type adding(VUIPanel panel, VUILines lines, type line, boolean newLine) {
		panel.write(this.description);
		for (int i = 0; i < this.options.length; i++) {
			panel.write(this.options[i], true);
		}
		this.setSelect(this.selected);
		VUI.get().frame().addKeyListener(this.adapter);
		this.freezeIn();
		VUI.get().frame().removeKeyListener(this.adapter);
		if (newLine) {
			return null;
		} else {
			return (type)this;
		}
	}
	
}
