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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_ip == null) ? 0 : _ip.hashCode());
		result = prime * result + _port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileManager other = (FileManager) obj;
		if (_ip == null) {
			if (other._ip != null)
				return false;
		} else if (!_ip.equals(other._ip))
			return false;
		if (_port != other._port)
			return false;
		return true;
	}
}
