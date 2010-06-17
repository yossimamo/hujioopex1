package oop.ex5.filemanager;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

import oop.ex5.common.FileManager;
import oop.ex5.common.NameServer;
import oop.ex5.common.ShutdownSignal;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FileManagerData implements ShutdownSignal {
	
	private LinkedList<NameServer> _servers = new LinkedList<NameServer>();
	private TreeMap<String, SynchronizedFile> _files = new TreeMap<String, SynchronizedFile>();
	private String _directoryPath;
	private FileManager _self;
	private volatile boolean _shutdownSignal;
	

	public FileManagerData(String serverListFile, String directory, int port)
		throws UnknownHostException, FileNotFoundException {
		_directoryPath = directory;
		initServers(serverListFile);
		initFiles(directory);		
		_self = new FileManager(InetAddress.getLocalHost().getHostAddress(), port);
	}
	
	private void initFiles(String directory) {
		_files = new TreeMap<String, SynchronizedFile>();
		File dir = new File(directory);
		String[] files = dir.list();
		for (int i = 0 ; i < files.length ; i++) {
			addFile(files[i]);
		}	
	}

	private void initServers(String serverListFile) throws FileNotFoundException, UnknownHostException {
		_servers = new LinkedList<NameServer>();
		File serversFile = new File(serverListFile);
		Scanner sc = new Scanner (serversFile);
		while (sc.hasNextLine()) {
			processLine(sc.nextLine());
		}
		sc.close();
	}

	private void processLine(String nextLine) throws UnknownHostException {
		Scanner sc = new Scanner(nextLine);
		String IP = sc.next();
		int port = sc.nextInt();
		if (IP.equals("localhost")) {
			IP = InetAddress.getLocalHost().getHostAddress();
		}
		_servers.add(new NameServer(IP, port));
		sc.close();
	}

	public boolean addAllNameServers(Collection<NameServer> nameServers) {
		return _servers.addAll(nameServers);
	}
	
	public boolean containsNameServer(NameServer nameServer) {
		return _servers.contains(nameServer);
	}
	
	public Iterator<NameServer> nameServersIterator() {
		return _servers.iterator();
	}
	
	public synchronized String[] getFiles() {
		String[] files = new String[0];
		if (!_files.isEmpty()) {
			files = _files.keySet().toArray(files);
		} 
		return files;
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
		_files.put(fileName, new SynchronizedFile(generateLocalPath(fileName)));
	}

	public synchronized boolean getShutdownSignal() {
		return _shutdownSignal;
	}

	public synchronized void setShutdownSignal() {
		_shutdownSignal = true;		
	}

	public FileManager getSelfFileManager() {
		return _self;
	}

	public String generateLocalPath(String fileName) {
		return _directoryPath + File.separatorChar + fileName;
	}
	
}
