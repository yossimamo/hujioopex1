package oop.ex5.filemanager.scenarios;

import java.io.IOException;
import java.util.Iterator;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;
import oop.ex5.filemanager.FileManagerData;
import oop.ex5.filemanager.InvalidMessageContextException;
import oop.ex5.messages.InvalidMessageFormatException;
import oop.ex5.messages.InvalidMessageNameException;


public class InitFileManagerScenario extends Scenario {

	public InitFileManagerScenario(FileManagerData dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			try {
				_comm = new CommLayer(nameServer.getIP(), nameServer.getPort());
				introduce();
				endSession();
			} catch (IOException e) {
				continue;
			} catch (InvalidMessageFormatException e) {
				continue;
			} catch (InvalidMessageNameException e) {
				continue;
			} catch (InvalidMessageContextException e) {
				continue;
			}
		}
	}

}
