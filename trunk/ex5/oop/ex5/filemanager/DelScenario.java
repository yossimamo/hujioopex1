package oop.ex5.filemanager;

import java.io.File;

import oop.ex5.messages.DontHaveFileMessage;

public class DelScenario extends Scenario {
	
	private String _fileName;

	public DelScenario(FileManagerData data, String fileName) {
		super(data);
		_fileName = fileName;
	}

	public void executeScenario() {
		SynchronizedFile file = _data.getFileObject(_fileName);
		file.prepareFileForDeletion();
		File realFile = new File(file.getLocalPath());
		realFile.delete();
		_data.removeFile(_fileName);
		sendMsgToAllNameServers(new DontHaveFileMessage(_fileName));
	}

}
