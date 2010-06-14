package oop.ex5.common;

public class NameServer {
	
	private String _ip;
	private int _port;
	
	public NameServer(String ip, int port) {
		_ip = ip;
		_port = port;
	}
	
	public String getIP() {
		return _ip;
	}
	
	public int getPort() {
		return _port;
	}
	
	public boolean equals(NameServer other) {
		if (_port == other._port &&  _ip.equals(other._ip)) {
			return true;
		}
		return false;
	}

}
