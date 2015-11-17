package tz.sys.vui.render;

import tz.sys.vui.render.api.VUIRender;
import tz.sys.vui.util.VUIUtil;

public class VUIRBalk implements VUIRender {

	@Override
	public boolean render(String param, VUIGraphics v) {
		VUIUtil util = VUIUtil.decimal(param, "Percentage of '[val]' for VUIBalk is not conform.");
		if (util.isOk()) {
			v.g.fillRect(v.x, v.y - v.h + v.h / 2, (int)((v.w + v.lineStart - v.x) * util.decimal() / 100), v.h / 2);
			v.x = v.w + v.lineStart;
			return true;
		}
		return false;
	}

	@Override
	public String ident() {
		return "balk";
	}

}
