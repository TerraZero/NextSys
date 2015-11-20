package tz.sys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tz.sys.reflect.Reflect;
import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.annot.Program;
import tz.sys.reflect.loader.ReflectLoader;
import tz.sys.vui.VUI;
import tz.sys.vui.VUIPanel;
import tz.sys.vui.input.VUISelect;

public class Main {

	public static void main(String[] args) {
		if (!ReflectLoader.isStartJar()) {
			Sys.verbose(false);
			Sys.log("Detect eclipse start");
			String path = ReflectLoader.defaultURL().getPath();
			String[] parts = path.substring(1).split("/");
			
			path = "";
			for (String part : parts) {
				if (part.equals("NextSys")) break;
				path += part + "/";
			}
			Sys.log("Detect root: " + path);
			File root = new File(path);
			for (File file : root.listFiles()) {
				if (file.isDirectory() && !file.getName().startsWith(".")) {
					ReflectLoader.addLoaderSource(new File(file.getAbsolutePath() + "/bin"));
					Sys.log("ADD LOAD: " + file.getAbsolutePath() + "/bin");
				}
			}
			Sys.verbose(true);
		}
		
		VUI vui = VUI.get();
		vui.open();
		
		VUIPanel cp = vui.frame().cp();
		
		ReflectUtil util = ReflectUtil.annotation(Program.class);
		List<String> options = new ArrayList<String>();
		for (Reflect r : util.reflects()) {
			Program p = r.annotation(Program.class);
			options.add(p.name());
		}
		if (options.size() == 1) {
			Reflect reflect = util.reflects().get(0);
			Program program = reflect.annotation(Program.class);
			reflect.call(program.function());
		} else if (options.size() > 1) {
			VUISelect select = cp.write(new VUISelect("Choose a program to start:", options), true);
			cp.write("Start program '" + select.input() + "'");
			for (Reflect r : util.reflects()) {
				Program p = r.annotation(Program.class);
				if (p.name().equals(select.input())) {
					r.call(p.function());
					break;
				}
			}
		} else {
			cp.write("No program to load!");
		}
	}
	
}
