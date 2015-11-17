package tz.sys.cui.api;

public class ResolveString {
	
	private String string;
	private String[] splits;

	public ResolveString(String string) {
		this.string = string;
		this.splits = this.string.split(":");
	}
	
	public String string() {
		return this.string;
	}
	
	public String label() {
		if (this.isOptional()) {
			return "<" + this.splits[0] + ">";
		}
		return this.splits[0];
	}
	
	public String resolve() {
		if (this.splits.length > 1) return this.splits[1];
		return this.splits[0];
	}
	
	public boolean isOptional() {
		return this.splits.length != 1;
	}
	
}
