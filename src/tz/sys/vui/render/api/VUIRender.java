package tz.sys.vui.render.api;

import tz.sys.vui.render.VUIGraphics;

public interface VUIRender {

	public boolean render(String param, VUIGraphics v);
	
	public String ident();
	
}
