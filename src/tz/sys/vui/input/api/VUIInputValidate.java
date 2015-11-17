package tz.sys.vui.input.api;

public interface VUIInputValidate {
	
	public String name();

	public boolean validate(String input);
	
	public String messege();
	
}
