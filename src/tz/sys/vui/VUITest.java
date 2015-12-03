package tz.sys.vui;

import java.awt.Color;

import tz.sys.reflect.api.Program;
import tz.sys.vui.input.VUIInput;
import tz.sys.vui.input.VUISelect;
import tz.sys.vui.util.VUIUtil;

@Program(name="VUI Test", tags={"vui"})
public class VUITest {

	public static void program() {
		VUI.clear();
		VUI.write("Start VUI Test");
		VUI.write("Write string");
		VUI.write(VUIUtil.colorRevert() + "Revert string");
		VUI.write(VUIUtil.colorReset() + "Reset string");
		VUIInput input = VUI.write(new VUIInput("Decimal: ", "decimal"));
		VUI.write("Input: " + input.input());
		VUIInput string = VUI.write(new VUIInput("String: "));
		VUI.write("Input: " + string.input());
		VUI.write(VUIUtil.color(Color.CYAN));
		VUISelect select = VUI.write(new VUISelect("Select: ", "Option 1", "Option 2", "Option 3"));
		VUI.write(VUIUtil.colorReset());
		VUI.write("Select input: " + select.input());
		VUI.write("end");
	}
	
}
