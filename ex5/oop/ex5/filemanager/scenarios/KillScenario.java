package oop.ex5.filemanager.scenarios;

import oop.ex5.filemanager.FileManagerData;
import oop.ex5.messages.Message;

public class KillScenario extends Scenario {

	public KillScenario(FileManagerData dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		sendMsgToAllNameServers(Message.KILL_MSG);
	}

}
