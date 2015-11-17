package tz.sys.util.sync;

import java.util.HashMap;
import java.util.Map;

public class Sync {

	private static Map<String, Sync> syncs;
	
	static {
		Sync.syncs = new HashMap<String, Sync>();
	}
	
	public static Sync get(String name) {
		Sync sync = Sync.syncs.get(name);
		if (sync == null) {
			sync = Sync.create();
			Sync.syncs.put(name, sync);
		}
		return sync;
	}
	
	public static Sync create() {
		return new Sync();
	}
	
	public static SyncResult stop(String name) {
		return Sync.get(name).syncStop();
	}
	
	public static SyncResult stop(String name, long timeout) {
		return Sync.get(name).syncStop(timeout);
	}
	
	public static SyncResult stop(String name, long timeout, int nanos) {
		return Sync.get(name).syncStop(timeout, nanos);
	}
	
	public static void play(String name) {
		Sync.get(name).syncPlay();
	}
	
	private Object sync;
	
	private Sync() {
		this.sync = new Object();
	}
	
	public SyncResult syncStop() {
		try {
			synchronized (this.sync) {
				this.sync.wait();
			}
		} catch (InterruptedException e) {
			return new SyncResult(this, e);
		}
		return new SyncResult(this);
	}
	
	public SyncResult syncStop(long timeout) {
		try {
			synchronized (this.sync) {
				this.sync.wait(timeout);
			}
		} catch (InterruptedException e) {
			return new SyncResult(this, e);
		}
		return new SyncResult(this);
	}
	
	public SyncResult syncStop(long timeout, int nanos) {
		try {
			synchronized (this.sync) {
				this.sync.wait(timeout, nanos);
			}
		} catch (InterruptedException e) {
			return new SyncResult(this, e);
		}
		return new SyncResult(this);
	}
	
	public void syncPlay() {
		synchronized (this.sync) {
			this.sync.notifyAll();
		}
	}
	
}
