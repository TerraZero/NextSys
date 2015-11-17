package tz.sys.vui.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.annot.Loader;
import tz.sys.vui.render.api.VUIRender;

@Loader(triggers={"VUI"})
public class VUIRending {
	
	private static Map<String, VUIRender> render;
	private static boolean active;
	
	static {
		VUIRending.active = true;
		VUIRending.render = new HashMap<String, VUIRender>();
	}
	
	public static void init(String trigger) {
		List<VUIRender> list = ReflectUtil.implement(VUIRender.class).create();
		for (VUIRender render : list) {
			VUIRending.add(render);
		}
	}
	
	public static void add(VUIRender render) {
		VUIRending.render.put(render.ident(), render);
	}

	public static boolean render(String param, VUIGraphics v) {
		if (VUIRending.active && param.length() > 2 && param.startsWith("{") && param.endsWith("}")) {
			param = param.substring(1, param.length() - 1);
			String[] p = param.split(":");
			VUIRender render = VUIRending.render.get(p[0]);
			if (render != null && p.length > 1) {
				return render.render(p[1], v);
			}
		}
		return false;
	}
	
	public static boolean rendable(String param) {
		if (VUIRending.active && param.length() > 2 && param.startsWith("{") && param.endsWith("}")) {
			param = param.substring(1, param.length() - 1);
			String[] p = param.split(":");
			VUIRender render = VUIRending.render.get(p[0]);
			if (render != null && p.length > 1) {
				return true;
			}
		}
		return false;
	}
	
}
