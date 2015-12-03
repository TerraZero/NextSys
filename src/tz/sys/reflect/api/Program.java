package tz.sys.reflect.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Program {

	public String name();
	
	public String function() default "program";
	
	public String[] tags() default {};
	
}
