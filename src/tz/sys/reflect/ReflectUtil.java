package tz.sys.reflect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tz.sys.Sys;
import tz.sys.reflect.annot.Loader;
import tz.sys.reflect.loader.ReflectFile;
import tz.sys.reflect.loader.ReflectLoader;

public class ReflectUtil {
	
	private static Map<Class<? extends Annotation>, ReflectUtil> annotCache;
	private static Map<Class<?>, ReflectUtil> interCache;
	private static List<String> loaded;
	
	static {
		ReflectUtil.annotCache = new HashMap<Class<? extends Annotation>, ReflectUtil>();
		ReflectUtil.interCache = new HashMap<Class<?>, ReflectUtil>();
		ReflectUtil.loaded = new ArrayList<String>();
	}

	public static ReflectUtil annotation(Class<? extends Annotation> annotation) {
		ReflectUtil util = ReflectUtil.annotCache.get(annotation);
		
		if (util == null) {
			util = new ReflectUtil();
			ReflectUtil.annotCache.put(annotation, util);
			
			for (ReflectFile file : ReflectLoader.sysloader().load()) {
				if (file.reflect().hasAnnotation(annotation)) {
					util.add(file.reflect());
				}
			}
		}
		return util;
	}
	
	public static ReflectUtil implement(Class<?> implement) {
		ReflectUtil util = ReflectUtil.interCache.get(implement);
		
		if (util == null) {
			util = new ReflectUtil();
			ReflectUtil.interCache.put(implement, util);
			
			for (ReflectFile file : ReflectLoader.sysloader().load()) {
				if (file.reflect().implement(implement)) {
					util.add(file.reflect());
				}
			}
		}
		return util;
	}
	
	public static void trigger(String trigger) {
		if (!ReflectUtil.loaded.contains(trigger)) {
			ReflectUtil.loaded.add(trigger);
			ReflectUtil util = ReflectUtil.annotation(Loader.class);
			List<Reflect> reflects = util.reflects();
			Collections.sort(reflects, new Comparator<Reflect>() {

				public int compare(Reflect o1, Reflect o2) {
					return o1.annotation(Loader.class).weight() - o2.annotation(Loader.class).weight();
				}
				
			});
			for (Reflect r : util.reflects()) {
				Loader info = r.annotation(Loader.class);
				if (Sys.isIntern(info.triggers(), trigger)) {
					r.call(info.function(), trigger);
				}
			}
			Sys.log("LOAD TRIGGER '" + trigger + "'");
		}
	}
	
	public static boolean isLoaded(String trigger) {
		return ReflectUtil.loaded.contains(trigger);
	}
	
	public static List<String> loaded() {
		return ReflectUtil.loaded;
	}
	
	private List<Reflect> reflects;
	
	public ReflectUtil() {
		this.reflects = new ArrayList<Reflect>();
	}
	
	public ReflectUtil(List<Reflect> reflects) {
		this.reflects = reflects;
	}
	
	public ReflectUtil add(Reflect reflect) {
		this.reflects.add(reflect);
		return this;
	}
	
	public List<Reflect> reflects() {
		return this.reflects;
	}
	
	public<type> List<type> create(Object... param) {
		List<type> creates = new ArrayList<type>();
		for (Reflect r : this.reflects) {
			creates.add(r.instantiate(param).getReflect());
		}
		return creates;
	}
	
}
