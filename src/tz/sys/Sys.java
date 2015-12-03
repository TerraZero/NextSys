package tz.sys;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.api.Loader;
import tz.sys.vui.VUI;
import tz.sys.vui.util.VUIUtil;

@Loader(triggers={"VUI"}, weight=-1000)
public class Sys {
	
	private static boolean vuiloaded;
	private static boolean verbose;
	
	private static List<String> logbuffer;
	private static List<String> warnbuffer;
	private static List<String> errorbuffer;
	private static List<Exception> ebuffer;
	
	static {
		Sys.verbose = true;
		
		Sys.logbuffer = new ArrayList<String>();
		Sys.warnbuffer = new ArrayList<String>();
		Sys.errorbuffer = new ArrayList<String>();
		Sys.ebuffer = new ArrayList<Exception>();
	}
	
	public static void init(String trigger) {
		if (trigger.equals("VUI")) {
			Sys.vuiloaded = true;
			
			System.setOut(new PrintStream(System.out) {
					
				@Override
				public void println(String s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(Object s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(boolean s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(char s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(double s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(float s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(int s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(char[] s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + Arrays.toString(s) + VUIUtil.colorReset());
					super.println(s);
				}
				
				@Override
				public void println(long s) {
					VUI.write(VUIUtil.color(Color.WHITE) + "{end: [ SYSOUT ]}" + s + VUIUtil.colorReset());
					super.println(s);
				}
				
			});
			
			Sys.flushLog();
			Sys.flushWarn();
			Sys.flushError();
			Sys.flushException();
		}
	}
	
	public static void verbose(boolean verbose) {
		Sys.verbose = verbose;
	}
	
	public static void log(String log, String... placeholders) {
		log = Sys.placeholder(log, placeholders);
		if (Sys.vuiloaded) {
			VUI.get().frame().cp().write(VUIUtil.color(Color.CYAN) + log + "{end: [ LOG ]}" + VUIUtil.colorReset());
		} else {
			Sys.logbuffer.add(log);
			if (Sys.verbose) {
				System.out.println("[ LOG ] " + log);
			}
		}
	}
	
	public static void warn(String warn, String... placeholders) {
		warn = Sys.placeholder(warn, placeholders);
		if (Sys.vuiloaded) {
			VUI.get().frame().cp().write(VUIUtil.color(Color.YELLOW) + warn + "{end: [ WARN ]}" + VUIUtil.colorReset());
		} else {
			if (Sys.verbose) {
				System.out.println("[ WARN ] " + warn);
			}
		}
	}

	public static void error(String error, String... placeholders) {
		error = Sys.placeholder(error, placeholders);
		if (Sys.vuiloaded) {
			VUI.get().frame().cp().write(VUIUtil.color(Color.RED) + error + "{end: [ ERROR ]}" + VUIUtil.colorReset());
		} else {
			if (Sys.verbose) {
				System.out.println("[ ERROR ] " + error);
			}
		}
	}
	
	public static void exception(Exception e) {
		if (Sys.vuiloaded) {
			try {
				if (VUI.isDebug()) {
					Sys.error("Exception: " + e.toString());
					VUI.write(VUIUtil.color(Color.RED) + "{util:x-10}Message: " + e.getMessage() + VUIUtil.colorReset());
					VUI.write(VUIUtil.color(Color.RED) + "{util:x-10}Stack:" + VUIUtil.colorReset());
					StackTraceElement[] ee = e.getStackTrace();
					for (int i = 0; i < ee.length; i++) {
						VUI.write(VUIUtil.color(Color.RED) + "{util:x-20}" + (i + 1) + ":{util:x-70;a-1}" + ee[i].toString() + VUIUtil.colorReset());
					}
				} else {
					VUI.write(VUIUtil.color(Color.RED) + e.toString() + VUIUtil.colorReset());
				}	
			} catch (Exception exc) {
				System.out.println("VUI Exception by print '" + e.toString() + "'");
				System.out.println(exc);
			}
		} else {
			if (Sys.verbose) {
				System.out.println(e);
			}
		}
	}
	


	public static<type> boolean isIntern(type[] array, type in) {
		for (type item : array) {
			if (item.equals(in)) return true;
		}
		return false;
	}
	
	public static<type> boolean isIntern(type[] array, type[] intern) {
		for (type item : intern) {
			if (!Sys.isIntern(array, item)) return false;
		}
		return true;
	}
	
	public static void flushLog() {
		if (Sys.vuiloaded) {
			for (String log : Sys.logbuffer) {
				Sys.log(log);
			}
		}
	}
	
	public static void flushWarn() {
		if (Sys.vuiloaded) {
			for (String warn : Sys.warnbuffer) {
				Sys.warn(warn);
			}
		}
	}
	
	public static void flushError() {
		if (Sys.vuiloaded) {
			for (String error : Sys.errorbuffer) {
				Sys.error(error);
			}
		}
	}
	
	public static void flushException() {
		if (Sys.vuiloaded) {
			for (Exception e : Sys.ebuffer) {
				Sys.exception(e);
			}
		}
	}
	
	public static String placeholder(String message, String... placeholders) {
		if (placeholders != null) {
			for (int i = 0; i < placeholders.length; i++) {
				message = message.replaceAll("\\[" + i + "\\]", "'" + placeholders[i] + "'");
			}
		}
		return message;
	}
	
	public static void exit() {
		Sys.exit(0);
	}
	
	public static void exit(int code) {
		ReflectUtil.trigger("sysexit");
		System.exit(code);
	}
	
}
