package tz.sys.cui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tz.sys.cui.api.ResolveString;

public class CUIUtil {

	public static List<ResolveString> startsWith(String[] options, String startsWith) {
		List<ResolveString> list = new ArrayList<ResolveString>();
		for (String s : options) {
			if (startsWith == null || s.startsWith(startsWith)) list.add(new ResolveString(s));
		}
		return list;
	}
	
	public static List<ResolveString> startsWith(Collection<String> options, String startsWith) {
		List<ResolveString> list = new ArrayList<ResolveString>();
		for (String s : options) {
			if (startsWith == null || s.startsWith(startsWith)) list.add(new ResolveString(s));
		}
		return list;
	}
	
	public static int paramsLength(String[] param, String resolve) {
		return param.length + (resolve == null ? 1 : 0);
	}
	
}
