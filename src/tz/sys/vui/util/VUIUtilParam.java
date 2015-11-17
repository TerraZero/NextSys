package tz.sys.vui.util;

import tz.sys.vui.render.VUIGraphics;

public class VUIUtilParam {

	public VUIUOpts opts;
	
	public int x;
	public int y;
	public int w;
	public int h;
	
	public void pack(VUIGraphics v) {
		v.x = this.x + this.w + 1;
	}
	
}
