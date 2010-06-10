package oop.ex5.nameserver;

public class FileManager {
	
	private String _ip;
	private int _port;
	
	public FileManager(String ip, int port) {
		_ip = ip;
		_port = port;
	}
	
	public String getIP() {
		return _ip;
	}
	
	public int getPort() {
		return _port;
	}
}
