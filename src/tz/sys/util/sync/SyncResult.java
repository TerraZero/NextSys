package tz.sys.util.sync;

public class SyncResult {

	private Sync sync;
	private InterruptedException e;
	
	public SyncResult(Sync sync) {
		this.sync = sync;
	}
	
	public SyncResult(Sync sync, InterruptedException e) {
		this.sync = sync;
		this.e = e;
	}
	
	public Sync sync() {
		return this.sync;
	}
	
	public boolean isOk() {
		return this.e == null;
	}
	
	public InterruptedException exception() {
		return this.e;
	}
	
}
