package oop.ex5.filemanager;

import oop.ex5.filemanager.scenarios.Scenario;


public class InitFileManagerScenario extends Scenario {

	public InitFileManagerScenario(FileManagerData dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		sendMsgToAllNameServers(NO_MESSAGE);
	}

}
