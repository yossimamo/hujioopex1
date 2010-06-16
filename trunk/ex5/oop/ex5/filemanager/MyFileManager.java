package oop.ex5.filemanager;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import oop.ex5.common.ListeningThread;
import oop.ex5.common.NameServer;

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
			case DEL:
				// TODO put everything in the scenarios + packages to the Scenarios
				fileName = commandLineScanner.next();
				if (!_data.containsFile(fileName)) {
					System.out.println("File is not in the database");
				}
				else {
					SynchronizedFile syncFile = _data.getFileObject(fileName);
					syncFile.prepareFileForDeletion();
					File file = new File(syncFile.getLocalPath());
					System.out.println(syncFile.getLocalPath());
					file.delete(); // TODO check that file was successfully deleted
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
				while (_listeningThread.isAlive()) {
					try {
						_listeningThread.join();
					} catch (InterruptedException e) {
						e.printStackTrace(); //TODO remove
						// TODO
					}
				}
				
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
