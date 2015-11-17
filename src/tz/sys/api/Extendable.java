package tz.sys.api;

/**
 * 
 * @author terrazero
 * @created May 19, 2015
 * 
 * @file Extendable.java
 * @project TZCore
 * @identifier bases
 *
 */
public interface Extendable<type> {

	@SuppressWarnings("unchecked")
	public default<child extends type> child child() {
		return (child)this;
	}
	
}