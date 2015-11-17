package tz.sys.vui.util;

import java.awt.Color;

import tz.sys.vui.VUIRendering;
import tz.sys.vui.render.VUIGraphics;

public class VUIUtil {

	public static VUIUtil number(String number, String error) {
		VUIUtil util = new VUIUtil();
		try {
			boolean negative = number.startsWith("!");
			if (negative) number = number.substring(1);
			util.number = Integer.parseInt(number);
			if (negative) util.number = -util.number;
			util.ok = true;
		} catch (NumberFormatException e) {
			util.ok = false;
			util.e = e;
			if (error != null) {
				util.error = error.replaceAll("\\[val\\]", number);
			}
		}
		return util;
	}
	
	public static VUIUtil decimal(String decimal, String error) {
		VUIUtil util = new VUIUtil();
		try {
			boolean negative = decimal.startsWith("!");
			if (negative) decimal = decimal.substring(1);
			util.decimal = Double.parseDouble(decimal);
			if (negative) util.decimal = -util.decimal;
			util.ok = true;
		} catch (NumberFormatException e) {
			util.ok = false;
			util.e = e;
			if (error != null) {
				util.error = error.replaceAll("\\[val\\]", decimal);
			}
		}
		return util;
	}
	
	public static VUIUtil bool(String bool, String error) {
		VUIUtil util = new VUIUtil();
		
		util.bool = Boolean.parseBoolean(bool);
		util.ok = true;
		if (!bool.toLowerCase().equals("true") && !bool.toLowerCase().equals("false")) {
			util.ok = false;
			if (error != null) {
				util.error = error.replaceAll("\\[val\\]", bool);
			}
		}
		return util;
	}
	
	public static VUIUtil split(String string, String split, int length, String error) {
		VUIUtil util = new VUIUtil();
		util.ok = true;
		
		util.split = string.split(split);
		if (util.split.length < length) {
			util.warn = true;
			String[] f = new String[length];
			for (int i = 0; i < f.length; i++) {
				if (i < util.split.length) {
					f[i] = util.split[i];
				} else {
					f[i] = "";
				}
			}
			util.split = f;
		}
		return util;
	}
	
	public static VUIUtilParam param(String string, VUIGraphics v) {
		VUIUtilParam param = new VUIUtilParam();
		VUIUOpt opt = null;
		param.opts = VUIUtil.opts(string).opts();
		
		// w
		param.w = v.w;
		opt = param.opts.opt("w", true);
		if (opt.type() == VUIOptType.NUMBER) {
			param.w = opt.number();
		}
		
		// h
		param.h = v.h - 1;
		opt = param.opts.opt("h", true);
		if (opt.type() == VUIOptType.NUMBER) {
			param.h = opt.number() - 1;
		}
		
		// x
		param.x = v.x;
		opt = param.opts.opt("x", true);
		if (opt.type() == VUIOptType.NUMBER) {
			param.x += opt.number();
		}
		
		// y
		param.y = v.y;
		opt = param.opts.opt("y", true);
		if (opt.type() == VUIOptType.NUMBER) {
			param.y += opt.number();
		} else if (opt.type() == VUIOptType.STRING) {
			switch (opt.string()) {
				case "top" :
					param.y -= v.h;
					break;
				case "top+height" :
					param.y -= param.h;
					break;
				case "middle" :
					param.y -= VUIRendering.getFontSize(v.g);
					break;
			}
		}
		
		return param;
	}
	
	public static String color(Color fg) {
		return VUIUtil.color(fg, null);
	}
	
	public static String color(Color fg, Color bg) {
		String sfg = "";
		String sbg = "";
		if (fg != null) {
			sfg = String.format("%02x%02x%02x", fg.getRed(), fg.getGreen(), fg.getBlue());
		} 
		if (bg != null) {
			sbg = String.format("%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue());
		}
		return "{#:" + sfg + "-" + sbg + "}"; 
	}
	
	public static String colorReset() {
		return VUIUtil.colorReset(null, null);
	}
	
	public static String colorReset(Color fg) {
		return VUIUtil.colorReset(fg, null);
	}
	
	public static String colorReset(Color fg, Color bg) {
		String sfg = "#";
		String sbg = "#";
		if (fg != null) {
			sfg = String.format("%02x%02x%02x", fg.getRed(), fg.getGreen(), fg.getBlue());
		} 
		if (bg != null) {
			sbg = String.format("%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue());
		}
		return "{#:" + sfg + "-" + sbg + "}"; 
	}
	
	public static String colorRevert() {
		return VUIUtil.colorRevert(null, null);
	}
	
	public static String colorRevert(Color fg) {
		return VUIUtil.colorRevert(fg, null);
	}
	
	public static String colorRevert(Color fg, Color bg) {
		String sfg = "!";
		String sbg = "!";
		if (fg != null) {
			sfg = String.format("%02x%02x%02x", fg.getRed(), fg.getGreen(), fg.getBlue());
		} 
		if (bg != null) {
			sbg = String.format("%02x%02x%02x", bg.getRed(), bg.getGreen(), bg.getBlue());
		}
		return "{#:" + sfg + "-" + sbg + "}";
	}
	
	public static VUIUtil opts(String string) {
		VUIUtil util = new VUIUtil();
		util.ok = true;
		util.opts = new VUIUOpts(string);
		return util;
	}
	
	private boolean ok;
	private boolean warn;
	private String error;
	private Exception e;
	
	private boolean bool;
	private int number;
	private double decimal;
	private String[] split;
	private VUIUOpts opts;
	
	public VUIUtil() {
		
	}
	
	public boolean bool() {
		return this.bool;
	}
	
	public int number() {
		return this.number;
	}
	
	public double decimal() {
		return this.decimal;
	}
	
	public String[] split() {
		return this.split;
	}
	
	public String error() {
		if (this.error == null) {
			return this.e.toString();
		}
		return this.error;
	}
	
	public Exception exception() {
		return this.e;
	}
	
	public boolean isOk() {
		return this.isOk(false);
	}
	
	public boolean isOk(boolean strict) {
		return this.ok && !strict || this.ok && !this.isWarn();
	}
	
	public boolean isWarn() {
		return this.warn;
	}
	
	public VUIUOpts opts() {
		return this.opts;
	}
	
}
