package tz.sys.vui.render;

import java.awt.Color;
import java.awt.Graphics;

import tz.sys.vui.VUIPanel;

public class VUIGraphics {

	public Graphics g;
	public int x;
	public int y;
	public int w;
	public int h;
	public int size;
	public Color fg;
	public Color bg;
	public VUIPanel panel;
	public int lineStart = 10;
	
	public VUIGraphics(Graphics g) {
		this.g = g;
	}
	
	public VUIGraphics(Graphics g, int x, int y) {
		this.g = g;
		this.x = x;
		this.y = y;
	}
	
	public void resetColor() {
		this.g.setColor(this.fg);
	}
	
	public void reset() {
		this.x = this.lineStart;
		this.w = this.panel.getWidth() - this.x;
	}
	
}
