package tz.sys.reflect;

public class ReflectParams {
	
	private Object[] params;
	private Reflect[] reflects;

	public ReflectParams(Object... params) {
		this.params = params;
		this.reflects = new Reflect[params.length];
	}
	
	public int length() {
		return this.params.length;
	}
	
	public Object[] params() {
		return this.params;
	}
	
	public Object param(int index) {
		return this.params[index];
	}
	
	public Reflect reflect(int index) {
		if (this.reflects[index] == null) {
			this.reflects[index] = new Reflect(this.param(index));
		}
		return this.reflects[index];
	}
	
	public Reflect[] reflects() {
		for (int i = 0; i < this.length(); i++) {
			if (this.reflects[i] == null) {
				this.reflects[i] = new Reflect(this.param(i));
			}
		}
		return this.reflects;
	}
	
	public<type> type get(int index) {
		return this.reflect(index).getReflect();
	}
	
	public boolean is(int index, Class<?> type) {
		return this.reflect(index).is(type);
	}
	
}
