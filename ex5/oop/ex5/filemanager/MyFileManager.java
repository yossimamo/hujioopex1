package oop.ex5.filemanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;

public class MyFileManager {
	
	private FileManagerData _data;
	
	enum Command {
		LIST,
		LISTSERVERS,
		GET,
		DEL,
		KILL,
		BYE,
		OTHER
	};
	
	public MyFileManager(String serverList, String myDirectory, int port)
		throws UnknownHostException, FileNotFoundException {
		_data = new FileManagerData(serverList, myDirectory, port);
		Scenario init = new InitFileManagerScenario(_data);
		init.executeScenario();
	}
	
	public static void main(String[] args) {
		try {
			MyFileManager myFileManager = new MyFileManager(args[0], args[1], Integer.valueOf(args[2]));
			//myFileManager.createListeningThread();
			myFileManager.StartClientUserInteraction();
		} catch (UnknownHostException e) {
			System.err.println("Error: failed to retrieve self hostname");
			return;
		} catch (IOException e) {
			System.err.println("Error: IO exception");
			return;
		}
	}
	
	public void createListeningThread() throws IOException {
		Thread listeningThread = new ListeningThread(_data.getSelfFileManager().getPort(), new FileManagerClientThreadFactory(_data), _data);
		listeningThread.run();
	}
	
	public void StartClientUserInteraction() {
		Command nextCommand;
		Scanner sc = new Scanner(System.in);
		do {
			// assumes all commands are valid
			String nextCommandLine = sc.next();
			Scanner commandLineScanner = new Scanner(nextCommandLine);
			try {
				nextCommand = Command.valueOf(commandLineScanner.next());
			} catch (IllegalArgumentException e) {
				nextCommand = Command.OTHER;
			}
			String fileName;
			switch (nextCommand) {
			case LIST :
				printFiles();
				break;
			case LISTSERVERS :
				printServers();
				break;
			case GET :
				fileName = commandLineScanner.next();
				if (_data.containsFile(fileName)) {
					//TODO write in STDIN
					System.out.println("File is already in the database");
				}
				else {
					Scenario getScenario = new GetScenario(_data, fileName);
					getScenario.executeScenario();
				}
				break;
			case DEL :
				fileName = commandLineScanner.next();
				if (!_data.containsFile(fileName)) {
					System.out.println("File is not in the database");
				}
				else {
					Scenario delScenario = new DelScenario(_data, fileName);
					delScenario.executeScenario();
					System.out.println("Deletion OK");
				}
				break;
			case KILL :
				Scenario killScenario = new KillScenario(_data);
				killScenario.executeScenario();
				break;
			case BYE :
				_data.setShutdownSignal(true);
				Scenario byeScenario = new ByeScenario(_data);
				byeScenario.executeScenario();
				System.out.println("Good Bye!");
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
			System.out.println("ip: " + nameServer.getIP() + " port: " + nameServer.getPort());
		}
	}

	private void printFiles() {
		String[] files = _data.getFiles();
		for (int i=0 ; i<files.length ; i++) {
			System.out.println(files[i]);
		}
	}
	
}
