package tz.sys.vui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tz.sys.Sys;
import tz.sys.reflect.Reflect;
import tz.sys.reflect.ReflectUtil;
import tz.sys.reflect.api.Program;

public class VUI {
	
	private static VUI singleton;
	
	static {
		ReflectUtil.trigger("VUI");
	}

	public static VUI get() {
		if (VUI.singleton == null) {
			VUI.singleton = new VUI();
		}
		return VUI.singleton;
	}
	
	public static void debug(boolean debug) {
		VUI.get().debug = debug;
	}
	
	public static boolean isDebug() {
		return VUI.get().debug;
	}
	
	public static void run(String name) {
		ReflectUtil util = ReflectUtil.annotation(Program.class);
		for (Reflect r : util.reflects()) {
			Program p = r.annotation(Program.class);
			if (p.name().equals(name) && Sys.isIntern(p.tags(), "vui")) {
				r.call(p.function());
			}
		}
	}
	
	private VUIFrame frame;
	private JFrame relFrame;
	private boolean debug;
	
	private VUI() {
		this.frame = new VUIFrame("VUI");
		this.relFrame = this.frame;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public VUIFrame frame() {
		return this.frame;
	}
	
	public JFrame relFrame() {
		return this.relFrame;
	}
	
	public void open() {
		this.frame.setVisible(true);
	}
	
	public void close() {
		this.frame.setVisible(false);
	}
	
	public static void clear() {
		VUI.get().frame().cp().clear();
	}
	
	public static VUIString write(String text) {
		return VUI.write(text, true);
	}
	
	public static VUIString write(String text, boolean newLine) {
		return VUI.write(new VUIString(text), newLine);
	}
	
	public static <type extends VUIString> type write(type text) {
		return VUI.write(text, true);
	}
	
	public static <type extends VUIString> type write(type text, boolean newLine) {
		return VUI.get().frame().cp().write(text, newLine);
	}
	
	public static void remove(VUIString string) {
		VUI.get().frame().cp().remove(string);
	}
	
	public static void adopt(JFrame frame) {
		VUI.singleton.relFrame = frame;
		JPanel cp = VUI.get().frame().cp();
		cp.setBounds(0, 0, frame.getContentPane().getWidth(), 350);
		frame.add(cp);
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				cp.setBounds(0, 0, frame.getContentPane().getWidth(), 350);
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				
			}
			
		});
	}
	
}
