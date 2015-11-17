package tz.sys.vui.util;

import java.util.HashMap;
import java.util.Map;

public class VUIUOpts {
	
	private String parse;
	
	private Map<String, VUIUOpt> opts;

	public VUIUOpts(String parse) {
		this.parse = parse;
		this.opts = new HashMap<String, VUIUOpt>();
		this.parsing();
	}
	
	public void parsing() {
		String[] s = this.parse.split(";");
		for (int i = 0; i < s.length; i++) {
			String[] p = s[i].split("-");
			if (p.length < 2) {
				System.out.println(p[i]);
			}
			this.opts.put(p[0], new VUIUOpt(p[1]));
		}
	}
	
	public VUIUOpt opt(String key) {
		return this.opt(key, false);
	}
	
	public VUIUOpt opt(String key, boolean fallback) {
		if (fallback && !this.has(key)) return new VUIUOpt(null);
		return this.opts.get(key);
	}
	
	public boolean has(String key) {
		return this.opts.containsKey(key);
	}
	
}
