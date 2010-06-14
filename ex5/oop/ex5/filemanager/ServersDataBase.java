package oop.ex5.filemanager;

import java.util.Iterator;

import oop.ex5.common.NameServer;

public interface ServersDataBase {
	public boolean addNameServer(NameServer nameServer);
	public Iterator<NameServer> nameServersIterator();

}
