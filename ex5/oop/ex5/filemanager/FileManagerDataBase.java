package oop.ex5.filemanager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import oop.ex5.common.IDataBase;
import oop.ex5.common.NameServer;

public class FileManagerDataBase implements FilesDataBase, ServersDataBase, IDataBase {
	private LinkedList<NameServer> _servers = new LinkedList<NameServer>();
	private TreeMap<String, String> _files = new TreeMap<String, String>();
	

	public FileManagerDataBase(String serverListFile, String directory) {
		
	}
	
	public boolean addNameServer(NameServer nameServer) {
		Iterator<NameServer> it = _servers.iterator();
		while (it.hasNext()) {
			if (it.next().equals(nameServer)) {
				return false;
			}
		}
		_servers.add(nameServer);
		return true;
	}
	
	public Iterator<NameServer> nameServersIterator() {
		return _servers.iterator();
	}
	
	public synchronized Set<String> getFiles() {
		return _files.keySet();
	}
	
	public synchronized String getFilesFullPath(String filesName) {
		return _files.get(filesName);
	}
	
	public synchronized boolean containsKey(String key) {
		return _files.containsKey(key);
	}
	
	public synchronized String removeFile(String filesName) {
		return _files.remove(filesName);
	}
	
	
	
	

}
