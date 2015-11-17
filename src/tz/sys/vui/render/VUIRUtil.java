package tz.sys.vui.render;

import tz.sys.vui.render.api.VUIRender;
import tz.sys.vui.util.VUIOptType;
import tz.sys.vui.util.VUIUOpts;
import tz.sys.vui.util.VUIUtil;

public class VUIRUtil implements VUIRender {

	@Override
	public boolean render(String param, VUIGraphics v) {
		VUIUOpts opts = VUIUtil.opts(param).opts();
		
		boolean absolute = false;
		if (opts.opt("a", true).type() == VUIOptType.NUMBER && opts.opt("a").number() == 1) {
			absolute = true;
		}
		
		if (opts.opt("w", true).type() ==  VUIOptType.NUMBER) {
			if (absolute) {
				v.w = opts.opt("w").number();
			} else {
				v.w += opts.opt("w").number();
			}
		}
		if (opts.opt("h", true).type() ==  VUIOptType.NUMBER) {
			if (absolute) {
				v.h = opts.opt("h").number();
			} else {
				v.h += opts.opt("h").number();
			}
		}
		if (opts.opt("x", true).type() ==  VUIOptType.NUMBER) {
			if (absolute) {
				v.x = opts.opt("x").number();
			} else {
				v.x += opts.opt("x").number();
			}
		}
		if (opts.opt("y", true).type() ==  VUIOptType.NUMBER) {
			if (absolute) {
				v.y = opts.opt("y").number();
			} else {
				v.y += opts.opt("y").number();
			}
		}
		
		return true;
	}

	@Override
	public String ident() {
		return "util";
	}

}
