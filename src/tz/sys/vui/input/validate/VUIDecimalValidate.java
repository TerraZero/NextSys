package tz.sys.vui.input.validate;

import tz.sys.vui.input.api.VUIInputValidate;
import tz.sys.vui.util.VUIUtil;

public class VUIDecimalValidate implements VUIInputValidate {

	@Override
	public boolean validate(String input) {
		VUIUtil util = VUIUtil.decimal(input, null);
		return util.isOk();
	}

	@Override
	public String messege() {
		return "The input is not a decimal!{end:[ WARNING ]}";
	}

	@Override
	public String name() {
		return "decimal";
	}

}
