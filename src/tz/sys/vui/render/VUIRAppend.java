package tz.sys.vui.render;

import tz.sys.vui.VUIRendering;
import tz.sys.vui.render.api.VUIRender;

public class VUIRAppend implements VUIRender {

	@Override
	public boolean render(String param, VUIGraphics v) {
		v.w -= VUIRendering.getTextWidth(v.g, param);
		v.g.drawString(param, v.w + v.lineStart, v.y);
		return true;
	}

	@Override
	public String ident() {
		return "end";
	}

}
