package oop.ex5.nameserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import oop.ex5.common.FileManager;
import oop.ex5.common.NameServer;
import oop.ex5.common.ShutdownSignal;

public class NameServerData implements ShutdownSignal {
	
	private LinkedList<NameServer> _nameServers = new LinkedList<NameServer>();
	private HashMap<String, HashSet<FileManager>> _filesMapping = new HashMap<String, HashSet<FileManager>>();
	private HashMap<FileManager, HashSet<String>> _fileManagersMapping = new HashMap<FileManager, HashSet<String>>();
	private boolean _shutdownSignal = false;

	public NameServerData() {
		
	}
	
	public boolean hasFileManager(FileManager fm) {
		return _fileManagersMapping.containsKey(fm);
	}
	
	public synchronized void addFileManager(FileManager fm) {
		if (!_fileManagersMapping.containsKey(fm)) {
			_fileManagersMapping.put(fm, new HashSet<String>());
		}
	}
	
	public synchronized void addFile(FileManager fm, String fileName) {
		if (_filesMapping.containsKey(fileName)) {
			_filesMapping.get(fileName).add(fm);
		} else {
			HashSet<FileManager> set = new HashSet<FileManager>();
			set.add(fm);
			_filesMapping.put(fileName, set);
		}
		if (_fileManagersMapping.containsKey(fm)) {
			_fileManagersMapping.get(fm).add(fileName);
		} else {
			HashSet<String> set = new HashSet<String>();
			set.add(fileName);
			_fileManagersMapping.put(fm, set);
		}
	}
	
	public synchronized void addNameServer(NameServer ns) {
		if (!_nameServers.contains(ns)) {
			_nameServers.addLast(ns);
		}
	}

	public synchronized void removeFile(FileManager fm, String fileName) {
		if (_filesMapping.containsKey(fileName)) {
			_filesMapping.get(fileName).remove(fm);
		}
		if (_fileManagersMapping.containsKey(fm)) {
			_fileManagersMapping.get(fm).remove(fileName);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized Iterator<FileManager> getFileManagers(String fileName) {
		if (_filesMapping.containsKey(fileName)) {
			HashSet<FileManager> set = (HashSet<FileManager>)_filesMapping.get(fileName).clone();
			return set.iterator();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized Iterator<NameServer> getNameServers() {
		LinkedList<NameServer> list = (LinkedList<NameServer>)_nameServers.clone();
		return list.iterator();
	}

	public synchronized void clearFileManager(FileManager fm) {
		if (_fileManagersMapping.containsKey(fm)) {
			for (String fileName : _fileManagersMapping.get(fm)) {
				if (_filesMapping.containsKey(fileName)) {
					_filesMapping.get(fileName).remove(fm);
				}
			}
			_fileManagersMapping.remove(fm);
		}
	}

	// TODO Not sure this synchronization is good enough
	@Override
	public synchronized boolean getShutdownSignal() {
		return _shutdownSignal;
	}

	@Override
	public synchronized void setShutdownSignal(boolean shouldShutdown) {
		_shutdownSignal = shouldShutdown;		
	}
}
