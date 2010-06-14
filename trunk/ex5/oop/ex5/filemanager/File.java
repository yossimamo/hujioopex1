package oop.ex5.filemanager;

public class File {
	private final int NO_OCCURANCE_IN_STRING = -1;
	private final int LOCKED = 1;
	private final int UNLOCKED = 0;
	
	private String _shortName;
	private String _localPath;
	private int _lock = UNLOCKED;
	
	public File(String path) {
		_localPath = path;
		int shortNameIndex = path.lastIndexOf(java.io.File.pathSeparator);
		if (shortNameIndex == NO_OCCURANCE_IN_STRING) {
			shortNameIndex = 0;
		}
		_shortName = path.substring(shortNameIndex);
	}
	
	public synchronized void lock() {
		_lock = LOCKED;
	}
	
	public synchronized void unlock() {
		_lock = UNLOCKED;
	}
	
	public synchronized boolean isLocked() {
		return _lock == LOCKED;
	}

}
