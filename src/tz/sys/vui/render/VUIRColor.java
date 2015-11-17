package tz.sys.vui.render;

import java.awt.Color;

import tz.sys.vui.VUI;
import tz.sys.vui.render.api.VUIRender;

public class VUIRColor implements VUIRender {

	@Override
	public boolean render(String param, VUIGraphics v) {
		String[] cs = param.split("-");
		Color fg = v.fg;
		if (cs[0].length() != 0) {
			if (cs[0].equals("#")) {
				v.fg = VUI.get().frame().cp().getForeground();
			} else if (cs[0].equals("!")) {
				v.fg = v.bg;
			} else {
				v.fg = Color.decode("#" + cs[0]);
			}
			v.g.setColor(v.fg);
		}
		
		if (cs.length == 2 && cs[1].length() != 0) {
			if (cs[1].equals("#")) {
				v.bg = VUI.get().frame().cp().getBackground();
			} else if (cs[1].equals("!")) {
				v.bg = fg;
			} else {
				v.bg = Color.decode("#" + cs[1]);
			}
		}
		return true;
	}

	@Override
	public String ident() {
		return "#";
	}

}
