package tz.sys.vui.render;

import tz.sys.vui.render.api.VUIRender;
import tz.sys.vui.util.VUIOptType;
import tz.sys.vui.util.VUIUtil;
import tz.sys.vui.util.VUIUtilParam;

public class VUIRRect implements VUIRender {

	@Override
	public boolean render(String param, VUIGraphics v) {
		VUIUtilParam p = VUIUtil.param(param, v);
		String mode = "fill";
		if (p.opts.opt("mode", true).type() == VUIOptType.STRING) mode = p.opts.opt("mode").string();
		if (mode.equals("fill")) {
			v.g.fillRect(p.x, p.y, p.w, p.h);
		} else if (mode.equals("draw")) {
			v.g.drawRect(p.x, p.y, p.w, p.h);
		}
		if (p.opts.has("text")) {
			String text = p.opts.opt("text").string();
			int tx = (p.opts.has("tx") ? p.opts.opt("tx").number() + 1 : 1);
			v.g.drawString(text, v.x + tx, v.y);
		}
		p.pack(v);
		return true;
	}

	@Override
	public String ident() {
		return "rect";
	}

}
