package tz.sys;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tz.sys.reflect.annot.Program;
import tz.sys.vui.VUI;

@Program(name="Adopt Test", tags={"vui"})
public class AdoptTest {

	public static void program() {
		JFrame frame = new JFrame();
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setFocusTraversalKeysEnabled(false);
		JPanel cp = new JPanel();
		cp.setLayout(null);
		cp.setBackground(Color.GRAY);
		frame.setContentPane(cp);
		frame.setVisible(true);
		VUI.adopt(frame);
		VUI.get().close();
		VUI.run("CUI");
	}
	
}
