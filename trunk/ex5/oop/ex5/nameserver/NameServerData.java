package oop.ex5.nameserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import oop.ex5.common.NameServer;

public class NameServerData {
	
	private LinkedList<NameServer> _nameServers = new LinkedList<NameServer>();
	private HashMap<String, HashSet<FileManager>> _filesMapping = new HashMap<String, HashSet<FileManager>>();
	private HashMap<FileManager, HashSet<String>> _fileManagersMapping = new HashMap<FileManager, HashSet<String>>();

	public NameServerData() {
		
	}
	
	public boolean hasFileManager(String ip, int port) {
		FileManager fm = new FileManager(ip, port);
		return _fileManagersMapping.containsKey(fm);
	}
	
	public synchronized FileManager addFileManager(String ip, int port) {
		FileManager fm = new FileManager(ip, port);
		_fileManagersMapping.put(fm, new HashSet<String>());
		return fm;
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
	
	public synchronized void addNameServer(String ip, int port) {
		NameServer ns = new NameServer(ip, port);
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

	public synchronized Iterator<FileManager> getFileManagers(String fileName) {
		if (_filesMapping.containsKey(fileName)) {
			return _filesMapping.get(fileName).iterator();
		} else {
			return null;
		}
	}

	public synchronized Iterator<NameServer> getNameServers() {
		return _nameServers.iterator();
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
}
