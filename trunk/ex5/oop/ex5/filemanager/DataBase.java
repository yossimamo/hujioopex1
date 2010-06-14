package oop.ex5.filemanager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import oop.ex5.common.IDataBase;
import oop.ex5.common.NameServer;

public class DataBase implements IDataBase{
	private LinkedList<NameServer> _servers = new LinkedList<NameServer>();
	private TreeMap<String, File> _files = new TreeMap<String, File>();
	

	public DataBase() {
		
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
	
	

}
