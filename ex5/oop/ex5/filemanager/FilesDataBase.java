package oop.ex5.filemanager;

import java.util.Set;

public interface FilesDataBase {
	public String[] getFiles();
	public String getFileObject(File filesName);
	public boolean containsFile(String key);
	public File removeFile(String filesName);
	public void addFile(String fileName);
}
