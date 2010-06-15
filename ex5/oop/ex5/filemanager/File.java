package oop.ex5.filemanager;

public class File {
	private final int LOCKED = 1;
	private final int UNLOCKED = 0;
	private String _localPath;
	private int _lock;
	private int _uploadsCounter;
	private Thread _waitingOnDeletionThread;
	
	public File(String path) {
		_localPath = path;
		_lock = UNLOCKED;
		_uploadsCounter = 0;
		_waitingOnDeletionThread = null;
	}
	
	public synchronized void prepareFileForDeletion() {
		_lock = LOCKED;
		if (_uploadsCounter > 0) {
			_waitingOnDeletionThread = Thread.currentThread();
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
	}
	//TODO delete if not needed.
	public void unlock() {
		_lock = UNLOCKED;
	}
	
	public synchronized boolean isLocked() {
		return _lock == LOCKED;
	}
	
	public synchronized void addUpload() {
		_uploadsCounter++;
	}
	
	public synchronized void finishedUpload() {
		_uploadsCounter--;
		if ( (_uploadsCounter == 0) && (_lock == LOCKED) ) {
			_waitingOnDeletionThread.interrupt();
		}
	}
	
	public String getLocalPath() {
		return _localPath;
	}

}
