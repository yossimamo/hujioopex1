package oop.ex5.filemanager;

import java.util.Iterator;

import oop.ex5.common.CommLayer;
import oop.ex5.common.NameServer;

public class InitFileManagerScenario extends Scenario {

	public InitFileManagerScenario(AbstractDataBase dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		sendMsgToAllNameServers(NO_MESSAGE);
	}

}
