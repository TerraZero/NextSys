package tz.sys.net.dns;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import tz.sys.SysUtil;
import tz.sys.net.lib.URLEncodedUtils;
import tz.sys.net.lib.URLEncodedUtils.NVPair;

public class DNS {
	
	private String address;
	private URI uri;
	private List<NVPair> query;

	public DNS(String address) {
		this.address = address;
		try {
			this.uri = new URI(this.address);
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		if (this.uri.getQuery() != null) {
			this.query = URLEncodedUtils.parse(uri);
		} else {
			this.query = null;
		}
	}
	
	public boolean isValid() {
		return this.uri != null;
	}
	
	public URI uri() {
		return this.uri;
	}
	
	public String address() {
		return this.address;
	}
	
	public DNS schema(String schema) {
		try {
			this.uri = new URI(schema, this.uri.getRawUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getRawPath(), this.uri.getRawQuery(), this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public String schema() {
		return this.uri.getScheme();
	}
	
	public DNS userinfo(String userinfo) {
		try {
			this.uri = new URI(this.uri.getScheme(), userinfo, this.uri.getHost(), this.uri.getPort(), this.uri.getRawPath(), this.uri.getRawQuery(), this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public String userinfo() {
		return this.uri.getUserInfo();
	}
	
	public DNS host(String host) {
		try {
			this.uri = new URI(this.uri.getScheme(), this.uri.getRawUserInfo(), host, this.uri.getPort(), this.uri.getRawPath(), this.uri.getRawQuery(), this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public String host() {
		return this.uri.getHost();
	}
	
	public DNS port(int port) {
		try {
			this.uri = new URI(this.uri.getScheme(), this.uri.getRawUserInfo(), this.uri.getHost(), port, this.uri.getRawPath(), this.uri.getRawQuery(), this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public int port() {
		return this.uri.getPort();
	}
	
	public DNS path(String path) {
		try {
			this.uri = new URI(this.uri.getScheme(), this.uri.getRawUserInfo(), this.uri.getHost(), this.uri.getPort(), path, this.uri.getRawQuery(), this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public String path() {
		return this.uri.getPath();
	}
	
	public DNS query(String query) {
		try {
			this.uri = new URI(this.uri.getScheme(), this.uri.getRawUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getRawPath(), query, this.uri.getRawFragment());
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		if (this.uri.getQuery() != null) {
			this.query = URLEncodedUtils.parse(uri);
		} else {
			this.query = null;
		}
		return this;
	}
	
	public String query() {
		return this.uri.getQuery();
	}
	
	public DNS fragment(String fragment) {
		try {
			this.uri = new URI(this.uri.getScheme(), this.uri.getRawUserInfo(), this.uri.getHost(), this.uri.getPort(), this.uri.getRawPath(), this.uri.getRawQuery(), fragment);
		} catch (URISyntaxException e) {
			SysUtil.error("Address invalid: " + e);
		}
		return this;
	}
	
	public String fragment() {
		return this.uri.getFragment();
	}
	
	public String get(String key) {
		if (this.query == null) return null;
		for (NVPair p : this.query) {
			if (p.name().equals(key)) return p.value();
		}
		return null;
	}
	
	public DNS set(String key, String value) {
		if (this.query == null) {
			this.query = new ArrayList<NVPair>();
		}
		for (NVPair p : this.query) {
			if (p.name().equals(key)) {
				if (value == null) {
					this.query.remove(p);
				} else {
					p.value(value);
				}
				this.query(this.joinQuery());
				return this;
			}
		}
		if (value != null) {
			this.query.add(new NVPair(key, value));
		}
		this.query(this.joinQuery());
		return this;
	}
	
	public String joinQuery() {
		if (this.query == null || this.query.size() == 0) return null;
		return String.join("&", this.query);
	}
	
}
