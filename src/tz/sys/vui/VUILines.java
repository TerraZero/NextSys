package tz.sys.vui;

import java.util.ArrayList;
import java.util.List;

public class VUILines {

	private List<VUIString> lines;
	private int buffer;
	
	public VUILines() {
		this.buffer = 10000;
		this.lines = new ArrayList<VUIString>(this.buffer);
	}
	
	public synchronized List<VUIString> lines() {
		return new ArrayList<VUIString>(this.lines);
	}
	
	public synchronized void add(VUIString line) {
		if (this.lines.size() == this.buffer) {
			this.lines.remove(0);
		}
		this.lines.add(line);
	}
	
	public void remove(VUIString string) {
		this.lines.remove(string);
	}
	
	public int buffer() {
		return this.buffer;
	}
	
	public void buffer(int buffer) {
		this.buffer = buffer;
	}
	
	public void clear() {
		this.lines.clear();
	}
	
}
