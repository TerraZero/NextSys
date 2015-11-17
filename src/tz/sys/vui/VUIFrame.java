package tz.sys.vui;

import javax.swing.JFrame;

public class VUIFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private VUIPanel cp;
	
	public VUIFrame(String title) {
		this.setTitle(title);
		this.cp = new VUIPanel();
		this.setContentPane(this.cp);
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setFocusTraversalKeysEnabled(false);
	}
	
	public VUIPanel cp() {
		return this.cp;
	}

}
