package tz.sys.vui.util;

public class VUIUOpt {

	private String parse;
	
	private VUIUOpt[] multiple;
	private String string;
	private int number;
	private double decimal;
	
	private VUIOptType type;
	
	public VUIUOpt(String parse) {
		this.parse = parse;
		this.parsing();
	}
	
	public void parsing() {
		if (this.parse == null) {
			this.type = VUIOptType.NULL;
			this.string = "";
		} else {
			String[] s = this.parse.split(",");
			if (s.length > 1) {
				this.type = VUIOptType.MULTI;
				this.multiple = new VUIUOpt[s.length];
				for (int i = 0; i < this.multiple.length; i++) {
					System.out.println(s[i]);
					this.multiple[i] = new VUIUOpt(s[i]);
				}
			} else {
				if (s[0].startsWith("'") && s[0].endsWith("'")) {
					this.type = VUIOptType.STRING;
					this.string = s[0].substring(1, s[0].length() - 1);
				} else if (s[0].indexOf('.') != -1) {
					this.type = VUIOptType.DECIMAL;
					VUIUtil util = VUIUtil.decimal(s[0], null);
					if (util.isOk()) {
						this.decimal = util.decimal();
					}
				} else {
					this.type = VUIOptType.NUMBER;
					VUIUtil util = VUIUtil.number(s[0], null);
					if (util.isOk()) {
						this.number = util.number();
					}
				}
			}
		}
	}
	
	public VUIOptType type() {
		return this.type;
	}
	
	public VUIUOpt[] multi() {
		return this.multiple;
	}
	
	public String string() {
		return this.string;
	}
	
	public int number() {
		return this.number;
	}
	
	public double decimal() {
		return this.decimal;
	}
	
	public String parse() {
		return this.parse;
	}
	
}
