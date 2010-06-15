package oop.ex5.filemanager;

import java.util.Iterator;

import oop.ex5.common.NameServer;

public abstract class AbstractDataBase {

	public abstract boolean addNameServer(NameServer nameServer);

	
	public abstract Iterator<NameServer> nameServersIterator();

	
	public abstract void addFile(String fileName);

	
	public abstract boolean containsFile(String fileName);

	
	public abstract String[] getFiles();

	
	public abstract File getFileObject(String fileName);

	
	public abstract File removeFile(String fileName);
	
	public abstract int getPort();
	
	public abstract String getIP();
	
}
