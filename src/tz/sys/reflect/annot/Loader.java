package tz.sys.reflect.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Loader {

	public String[] triggers();
	
	public String function() default "init";
	
	public int weight() default 0;
	
}
