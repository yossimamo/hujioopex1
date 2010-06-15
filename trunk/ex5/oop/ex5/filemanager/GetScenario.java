package oop.ex5.filemanager;

import java.util.Iterator;
import java.util.LinkedList;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;

public class GetScenario extends Scenario {
	
	private String _fileName;

	public GetScenario(AbstractDataBase dataBase, String fileName) {
		super(dataBase);
		_fileName = fileName;
	}

	public void executeScenario() {
		Iterator<NameServer> serversIterator = _dataBase.nameServersIterator();
		while (serversIterator.hasNext()) {
			NameServer nameServer = serversIterator.next();
			CommLayer comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
			LinkedList fileManagers = getFileManagersHoldingFile();
			Iterator<NameServer> serversIterator = _dataBase.nameServersIterator();
		}
	}

}
