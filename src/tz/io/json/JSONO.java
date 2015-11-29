package tz.io.json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONO {
	
	private JSONO parent;
	private JSONObject data;
	private String key;
	
	// cache
	private Type type;
	private Object object;
	private Map<String, JSONO> cache;
	
	public JSONO(String json) {
		this(new JSONObject(json));
	}
	
	public JSONO(JSONObject object) {
		this.data = new JSONObject();
		this.data.put("genric-root", object);
		this.key = "genric-root";
		this.init();
	}
	
	public JSONO(JSONObject data, String key) {
		this.data = data;
		this.key = key;
		this.init();
	}
	
	public JSONO(JSONObject data, String key, JSONO parent) {
		this.data = data;
		this.key = key;
		this.parent = parent;
		this.init();
	}
	
	protected void init() {
		this.cache = new HashMap<String, JSONO>();
	}
	
	public JSONO get(String key) {
		if (!this.cache.containsKey(key)) {
			JSONObject that = this.data.getJSONObject(this.key);
			if (that.has(key)) {
				this.cache.put(key, new JSONO(that, key, this));
			} else {
				this.cache.put(key, new JSONO(null, null, this));
			}
		}
		return this.cache.get(key);
	}
	
	public Type type() {
		if (this.type == null) {
			if (this.key == null) {
				this.type = Type.UNDEFINED;
			} else {
				Object object = this.get();
				if (object instanceof Integer) {
					this.type = Type.INT;
				} else if (object instanceof Double) {
					this.type = Type.DOUBLE;
				} else if (object instanceof String) {
					this.type = Type.STRING;
				} else if (object instanceof JSONArray) {
					this.type = Type.ARRAY;
				} else if (object instanceof JSONObject) {
					this.type = Type.OBJECT;
				}
			}
		}
		return this.type;
	}
	
	public Object get() {
		if (this.object == null) {
			this.object = this.data.get(this.key);
		}
		return this.object;
	}
	
	public JSONO parent() {
		return this.parent;
	}
	
	public enum Type {
		
		STRING,
		INT,
		DOUBLE,
		ARRAY,
		OBJECT,
		UNDEFINED,
		
	}
	
}
