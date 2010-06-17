package oop.ex5.filemanager.scenarios;

import java.io.File;

import oop.ex5.filemanager.FileManagerData;
import oop.ex5.filemanager.SynchronizedFile;
import oop.ex5.messages.DontHaveFileMessage;

public class DelScenario extends Scenario {
	
	private String _fileName;

	public DelScenario(FileManagerData data, String fileName) {
		super(data);
		_fileName = fileName;
	}

	public void executeScenario() {
		if (!_data.containsFile(_fileName)) {
			System.out.println("File is not in the database");
		}
		else {
			SynchronizedFile syncFile = _data.getFileObject(_fileName);
			syncFile.prepareFileForDeletion();
			File file = new File(syncFile.getLocalPath());
			if (file.delete()) {
				System.out.println("Deletion OK");
				_data.removeFile(_fileName);
				sendMsgToAllNameServers(new DontHaveFileMessage(_fileName));
			}
			else {
				System.out.println("Error : unable to delete file");
			}
		}
	}

}
