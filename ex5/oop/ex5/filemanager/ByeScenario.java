package oop.ex5.filemanager;

import oop.ex5.messages.ByeMessage;

public class ByeScenario extends Scenario {

	public ByeScenario(AbstractDataBase dataBase) {
		super(dataBase);
	}

	
	public void executeScenario() {
		sendMsgToAllNameServers(new ByeMessage());
	}

}
