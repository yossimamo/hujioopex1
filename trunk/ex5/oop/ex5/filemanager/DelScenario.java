package oop.ex5.filemanager;

import oop.ex5.messages.DontHaveFileMessage;

public class DelScenario extends Scenario {
	
	private String _fileName;

	public DelScenario(AbstractDataBase dataBase, String fileName) {
		super(dataBase);
		_fileName = fileName;
	}

	public void executeScenario() {
		File file = _dataBase.getFileObject(_fileName);
		file.prepareFileForDeletion();
		java.io.File realFile = new java.io.File(file.getLocalPath());
		if (!realFile.delete()) {
			throw new //TODO
		}
		_dataBase.removeFile(_fileName);
		sendMsgToAllNameServers(new DontHaveFileMessage(_fileName));
	}

}
