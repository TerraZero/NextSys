package tz.sys.cui.api;

import java.util.List;

public interface CUICommand {
	
	public String name();

	public default List<ResolveString> resolve(String[] param, String resolve) {
		return null;
	}
	
	public CUIState command(CommandString command);
	
}
