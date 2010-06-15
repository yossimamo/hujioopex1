package oop.ex5.filemanager;

import oop.ex5.messages.KillMessage;

public class KillScenario extends Scenario {

	public KillScenario(AbstractDataBase dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		sendMsgToAllNameServers(new KillMessage());
	}

}
