package tz.sys.vui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.JPanel;

import tz.sys.vui.render.VUIGraphics;
import tz.sys.vui.util.VUIUtil;

public class VUIPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private VUILines lines;
	private VUIString line;
	private int showline;
	
	public VUIPanel() {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.GREEN);
		
		this.lines = new VUILines();
		this.showline = 0;
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				VUIPanel.this.showline(VUIPanel.this.showline - e.getWheelRotation());
			}
			
		});
	}

	public VUIString write(String text) {
		return this.write(text, true);
	}
	
	public VUIString write(String text, boolean newLine) {
		return this.write(new VUIString(text), newLine);
	}
	
	public <type extends VUIString> type write(type text, boolean newLine) {
		text.setPanel(this);
		this.line = text.adding(this, this.lines, this.line, newLine);
		return text;
	}
	
	public void remove(VUIString string) {
		this.lines.remove(string);
		this.refresh();
	}
	
	public void refresh() {
		this.repaint();
	}
	
	public void showline(int showline) {
		int sl = this.getHeight() / VUIRendering.getLineHeight(this.getGraphics());
		this.showline = showline;
		if (this.showline >= this.lines.lines().size() - sl) this.showline = this.lines.lines().size() - sl;
		if (this.showline < 0) this.showline = 0;
		this.refresh();
	}
	
	public void clear() {
		this.lines.clear();
		this.showline = 0;
		this.refresh();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(this.getForeground());
		
		VUIGraphics v = new VUIGraphics(g);
		v.h = VUIRendering.getLineHeight(g);
		v.fg = this.getForeground();
		v.bg = this.getBackground();
		v.size = VUIRendering.getFontSize(v.g);
		v.panel = this;
		
		List<VUIString> lines = this.lines.lines();
		int sl = this.getHeight() / v.h;
		int size = lines.size();
		
		int index = 0;
		for (index = this.showline; index < sl + this.showline && index < size; index++) {
			if (lines.get(size - index - 1).empty()) {
				sl++;
			}
		}
		sl = index;

		for (int i = this.showline; i < sl + this.showline; i++) {
			VUIString s = lines.get(size - (sl + this.showline) + i);
			v.reset();
			v.y += v.h;
			s.print(v);
		}
		if (line != null) {
			this.line.print(v);
		}
	}
	
	public VUIString writeColor(Color color) {
		return this.write(new VUIString(VUIUtil.color(color)), true);
	}
	
	public VUIString error(String error) {
		return this.write("{#:ff0000}" + error);
	}
	
	public VUIString error(Exception e) {
		return this.error(e.toString());
	}

}
