package tz.sys.vui.input;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.sys.Sys;
import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.api.Loader;
import tz.sys.util.sync.Sync;
import tz.sys.util.sync.SyncResult;
import tz.sys.vui.VUI;
import tz.sys.vui.VUILines;
import tz.sys.vui.VUIPanel;
import tz.sys.vui.VUIString;
import tz.sys.vui.input.api.VUIInputValidate;
import tz.sys.vui.input.keys.VUIInputKeys;
import tz.sys.vui.util.VUIUtil;

@Loader(triggers={"vui-input"})
public class VUIInput extends VUIString {
	
	private static Map<String, VUIInputValidate> validatetypes;
	
	static {
		VUIInput.validatetypes = new HashMap<String, VUIInputValidate>();
		ReflectUtil.trigger("vui-input");
	}
	
	public static void init(String trigger) {
		List<VUIInputValidate> list = ReflectUtil.implement(VUIInputValidate.class).create();
		for (VUIInputValidate validate : list) {
			VUIInput.validatetypes.put(validate.name(), validate);
		}
	}

	protected String input;
	protected KeyListener adapter;	
	private VUIString string;
	private VUIInputValidate[] validates;
	private int pos;
	
	public VUIInput() {
		this.init();
	}
	
	public VUIInput(String prefix) {
		super(prefix);
		this.init();
	}
	
	public VUIInput(String prefix, String... validators) {
		super(prefix);
		this.init();
		this.validates = new VUIInputValidate[validators.length];
		for (int i = 0; i < validators.length; i++) {
			this.validates[i] = VUIInput.validatetypes.get(validators[i]);
		}
	}
	
	public VUIInput(String prefix, VUIInputValidate... validators) {
		super(prefix);
		this.init();
		this.validates = validators;
	}
	
	protected void init() {
		this.input = "";
		this.validates = new VUIInputValidate[0];
		this.initKeys();
	}
	
	protected void initKeys() {
		this.adapter = new VUIInputKeys(this);
	}
	
	@Override
	public void refresh() {
		this.string.set(this.stringRefresh());
		super.refresh();
	}
	
	public int pos() {
		return this.pos;
	}

	public VUIInput pos(int pos) {
		this.pos = pos;
		this.refresh();
		return this;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public String input() {
		return this.input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public void enter() {
		if (this.validate()) {
			this.freezeOut();
		}
	}
	
	public boolean validate() {
		if (this.validates.length == 0) return true;
		for (VUIInputValidate v : this.validates) {
			if (!v.validate(this.input)) {
				this.panel.write(this.stringValidateMessage(v));
				this.string.set(this.stringReleasedInput());
				this.input = "";
				this.pos = 0;
				this.string = new VUIString(this.stringRefresh());
				this.panel.write(this.string, true);
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <type extends VUIString> type adding(VUIPanel panel, VUILines lines, type line, boolean newLine) {
		this.string = new VUIString(this.stringRefresh());
		panel.write(this.string, true);
		VUI.get().relFrame().addKeyListener(this.adapter);
		this.freezeIn();
		this.string.set(this.stringAcceptString());
		VUI.get().relFrame().removeKeyListener(this.adapter);
		if (newLine) {
			return null;
		} else {
			return (type)this;
		}
	}
	
	public void freezeIn() {
		SyncResult result = Sync.stop("vuiinput");
		if (!result.isOk()) {
			Sys.exception(result.exception());
		}
	}
	
	public void freezeOut() {
		Sync.play("vuiinput");
	}
	
	public String stringRefresh() {
		String text = "";
		String prefix = "";
		String suffix = "";
		if (this.pos == this.input.length()) {
			prefix = this.input;
		} else {
			text = this.input.substring(this.pos, this.pos + 1);
			prefix = this.input.substring(0, this.pos);
			suffix = this.input.substring(this.pos + 1);
		}
		return this.get() + ": {#:00ff00}" + prefix + "{rect:mode-'draw';w-12;y-'middle';x-2;text-'" + text + "';tx-3}{util:x-2}" + suffix + "{#:#}";
	}
	
	public String stringValidateMessage(VUIInputValidate validate) {
		return VUIUtil.color(Color.YELLOW) + validate.messege() + VUIUtil.colorReset();
	}
	
	public String stringReleasedInput() {
		return this.get() + ": " + VUIUtil.color(Color.GREEN) + this.input + VUIUtil.colorReset();
	}
	
	public String stringAcceptString() {
		return this.get() + ": " + VUIUtil.color(Color.GREEN) + this.input + "{end:[ OK ]}" + VUIUtil.colorReset(); 
	}
	
}
