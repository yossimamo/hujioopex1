package oop.ex5.filemanager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;
import oop.ex5.filemanager.scenarios.ByeScenario;
import oop.ex5.filemanager.scenarios.DelScenario;
import oop.ex5.filemanager.scenarios.GetScenario;
import oop.ex5.filemanager.scenarios.KillScenario;
import oop.ex5.filemanager.scenarios.Scenario;

public class MyFileManager {
	
	enum Command {
		LIST,
		LISTSERVERS,
		GET,
		DEL,
		KILL,
		BYE,
		OTHER
	}

	private static final int NUM_OF_ARGS = 3;
	
	private FileManagerData _data;
	private ListeningThread _listeningThread;
	
	public MyFileManager(String serverList, String myDirectory, int port)
		throws IOException {
		_data = new FileManagerData(serverList, myDirectory, port);
		Scenario init = new InitFileManagerScenario(_data);
		init.executeScenario();
		_listeningThread = new ListeningThread(_data.getSelfFileManager().getPort(), new FileManagerClientThreadFactory(_data), _data);
		_listeningThread.start();
	}
	
	public static void main(String[] args) {
		if (NUM_OF_ARGS != args.length) {
			System.err.println("ERROR: incorrect arguments");
			return;
		}
		try {
			MyFileManager myFileManager = new MyFileManager(args[0], args[1], Integer.valueOf(args[2]));
			myFileManager.StartClientUserInteraction();
		} catch (UnknownHostException e) {
			e.printStackTrace(); //TODO remove
			System.err.println("Error: failed to retrieve self hostname");
			return;
		} catch (IOException e) {
			e.printStackTrace(); //TODO remove
			System.err.println("Error: IO exception");
			return;
		}
	}

	public void StartClientUserInteraction() {
		Command nextCommand;
		Scanner sc = new Scanner(System.in);
		do {
			// assumes all commands are valid
			String nextCommandLine = sc.nextLine();
			Scanner commandLineScanner = new Scanner(nextCommandLine);
			try {
				nextCommand = Command.valueOf(commandLineScanner.next());
			} catch (IllegalArgumentException e) {
				e.printStackTrace(); //TODO remove
				nextCommand = Command.OTHER;
			}
			switch (nextCommand) {
			case LIST :
				printFiles();
				break;
			case LISTSERVERS :
				printServers();
				break;
			case GET :
				Scenario getScenario = new GetScenario(_data, commandLineScanner.next());
				getScenario.executeScenario();
				break;
			case DEL:
				Scenario delScenario = new DelScenario(_data, commandLineScanner.next());
				delScenario.executeScenario();
				break;
			case KILL :
				Scenario killScenario = new KillScenario(_data);
				killScenario.executeScenario();
				break;
			case BYE :
				Scenario byeScenario = new ByeScenario(_data, _listeningThread);
				byeScenario.executeScenario();
				break;
			case OTHER :
				System.out.println("unknown command please try again!");
			}
			commandLineScanner.close();
		} while (nextCommand != Command.BYE);
		sc.close();
	}

	private void printServers() {
		Iterator<NameServer> it = _data.nameServersIterator();
		while (it.hasNext()) {
			NameServer nameServer = it.next();
			System.out.println(nameServer.getIP() + " " + nameServer.getPort());
		}
	}

	private void printFiles() {
		String[] files = _data.getFiles();
		for (int i=0 ; i<files.length ; i++) {
			System.out.println(files[i]);
		}
	}
	
}
