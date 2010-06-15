package oop.ex5.filemanager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

import oop.ex5.common.FileManager;
import oop.ex5.common.NameServer;
import oop.ex5.common.ShutdownSignal;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FileManagerData implements ShutdownSignal {
	
	private LinkedList<NameServer> _servers = new LinkedList<NameServer>();
	private TreeMap<String, SynchronizedFile> _files = new TreeMap<String, SynchronizedFile>();
	private String _directoryPath;
	private FileManager _self;
	

	public FileManagerData(String serverListFile, String directory, int port)
		throws UnknownHostException {
		_directoryPath = directory;
		initServers(serverListFile);
		initFiles(directory);		
		_self = new FileManager(InetAddress.getLocalHost().getHostAddress(), port);
	}
	
	private void initFiles(String directory) {
		_files = new TreeMap<String, SynchronizedFile>();
		java.io.File dir = new java.io.File(directory);
		String[] files = dir.list();
		for (int i = 0 ; i < files.length ; i++) {
			addFile(files[i]);
		}	
	}

	private void initServers(String serverListFile) {
		_servers = new LinkedList<NameServer>();
		java.io.File serversFile = new java.io.File(serverListFile);
		//TODO
		Scanner sc = null;
		try {
			sc = new Scanner (serversFile);
		} catch (FileNotFoundException e) {
			//TODO we assume input is legal so shouldn't happen.
		}
		while (sc.hasNextLine()) {
			processLine(sc.nextLine());
		}
		sc.close();
	}

	private void processLine(String nextLine) {
		Scanner sc = new Scanner(nextLine);
		String IP = sc.next();
		int port = sc.nextInt();
		_servers.add(new NameServer(IP, port));
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
	
	public boolean containsNameServer(NameServer nameServer) {
		return _servers.contains(nameServer);
	}
	
	public Iterator<NameServer> nameServersIterator() {
		return _servers.iterator();
	}
	
	public synchronized String[] getFiles() {
		if (_files.isEmpty()) {
			return null;
		} 
		return  _files.keySet().toArray(new String[0]);
	}
	
	public synchronized SynchronizedFile getFileObject(String filesName) {
		return _files.get(filesName);
	}
	
	public synchronized boolean containsFile(String fileName) {
		return _files.containsKey(fileName);
	}
	
	public synchronized SynchronizedFile removeFile(String fileName) {
		return _files.remove(fileName);
	}
	
	public synchronized void addFile(String fileName) {
		_files.put(fileName, new SynchronizedFile(_directoryPath + java.io.File.pathSeparator + fileName));
	}

	@Override
	public boolean getShutdownSignal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setShutdownSignal(boolean shouldShutdown) {
		// TODO Auto-generated method stub
		
	}

	public FileManager getSelfFileManager() {
		return _self;
	}
	
}
