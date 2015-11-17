package tz.sys.vui;

import java.awt.Font;
import java.awt.Graphics;

/**
 * 
 * @author terrazero
 * @created Dec 17, 2014
 * 
 * @file TextRendering.java
 * @project G7C
 * @identifier TZ.G7.Rendering
 *
 */
public class VUIRendering {
	
	public static int getLineHeight(Graphics g) {
		return g.getFontMetrics(g.getFont()).getHeight();
	}
	
	public static int getLineHeight(Graphics g, Font font) {
		return g.getFontMetrics(font).getHeight();
	}
	
	public static int getTextWidth(Graphics g, String text) {
		return g.getFontMetrics(g.getFont()).stringWidth(text);
	}
	
	public static int getTextWidth(Graphics g, Font font, String text) {
		return g.getFontMetrics(font).stringWidth(text);
	}
	
	public static int getMiddleWidthText(Graphics g, String text, int width) {
		return (width - VUIRendering.getTextWidth(g, text)) / 2;
	}
	
	public static int getRightWidthText(Graphics g, String text, int width) {
		return width - VUIRendering.getTextWidth(g, text);
	}
	
	public static int getMiddleHeightText(Graphics g, int height) {
		return (height + VUIRendering.getLineHeight(g) / 2) / 2;
	}
	
	public static Graphics setFontSize(Graphics g, float size) {
		g.setFont(g.getFont().deriveFont(size));
		return g;
	}
	
	public static int getFontSize(Graphics g) {
		return g.getFont().getSize();
	}
	
}
