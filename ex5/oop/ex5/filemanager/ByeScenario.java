package oop.ex5.filemanager;

import oop.ex5.messages.Message;

public class ByeScenario extends Scenario {

	public ByeScenario(FileManagerData dataBase) {
		super(dataBase);
	}

	public void executeScenario() {
		sendMsgToAllNameServers(Message.BYE_MSG);
	}

}
