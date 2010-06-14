package oop.ex5.nameserver;

import java.util.HashMap;
import java.util.LinkedList;

import oop.ex5.common.NameServer;

public class NameServerData {
	
	private LinkedList<FileManager> _fileManagers;
	private LinkedList<NameServer> _nameServers;
	private HashMap<String, LinkedList<FileManager>> _filesMapping;

	public NameServerData() {
		
	}
}
