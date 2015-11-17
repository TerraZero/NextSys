package tz.sys.cui.vui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CUIInputKeys implements KeyListener {
	
	private CUIInput input;
	
	public CUIInputKeys(CUIInput input) {
		this.input = input;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String s = this.input.input();
		int p = this.input.pos();
		switch (e.getKeyCode()) {
			case 0 : // any unknown key
			case 16 : // shift
			case 17 : // strg
			case 18 : // alt
			case 19 : // break
			case 20 : // caps lock
			case 27 : // esc
			case 33 : // screen up
			case 34 : // screen down
			case 38 : // up arrow
			case 40 : // down arrow
			case 144 : // numpad
			case 145 : // scroll
			case 155 : // paste
			case 524 : // windows
			case 525 : // context
				return;
			case 8 : // backspace
				if (s.length() > 0 && p > 0) {
					String prefix = s.substring(0, p - 1);
					String suffix = s.substring(p);
					this.input.setInput(prefix + suffix);
					this.input.setPos(p - 1);
					this.input.refresh();
				}
				break;
			case 9 : // tabulator
				this.input.resolve();
				return; // do not let typed call
			case 10 : // return
				this.input.enter();
				break;
			case 35 : // end
				this.input.pos(s.length());
				break;
			case 36 : // pos1
				this.input.pos(0);
				break;
			case 37 : // left
				p--;
				if (p < 0) p = 0;
				this.input.pos(p);
				break;
			case 39 : // right
				p++;
				if (p > s.length()) p = s.length();
				this.input.pos(p);
				break;
			case 127 : // remove
				if (s.length() > 0) {
					if (p == s.length()) {
						String prefix = s.substring(0, p - 1);
						String suffix = s.substring(p);
						this.input.setInput(prefix + suffix);
						this.input.setPos(p - 1);
						this.input.refresh();
					} else {
						String prefix = s.substring(0, p);
						String suffix = s.substring(p + 1);
						this.input.setInput(prefix + suffix);
						this.input.refresh();
					}
				}
				break;
			default :
				String prefix = s.substring(0, p);
				String suffix = s.substring(p);
				this.input.setInput(prefix + e.getKeyChar() + suffix);
				this.input.pos(p + 1);
				break;
		}
		this.input.typed();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
