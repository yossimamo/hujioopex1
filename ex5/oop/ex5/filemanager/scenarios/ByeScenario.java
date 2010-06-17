package oop.ex5.filemanager.scenarios;

import oop.ex5.common.ListeningThread;
import oop.ex5.filemanager.FileManagerData;
import oop.ex5.messages.Message;

public class ByeScenario extends Scenario {
	
	private ListeningThread _listeningThread;

	public ByeScenario(FileManagerData dataBase, ListeningThread listeningThread) {
		super(dataBase);
		_listeningThread = listeningThread;
		
	}

	public void executeScenario() {
		_data.setShutdownSignal(true);
		while (_listeningThread.isAlive()) {
			try {
				_listeningThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace(); //TODO remove
				// TODO
			}
		}
		sendMsgToAllNameServers(Message.BYE_MSG);
		System.out.println("Good Bye!");
	}

}
