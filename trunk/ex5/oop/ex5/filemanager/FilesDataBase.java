package oop.ex5.filemanager;

import java.util.Set;

public interface FilesDataBase {
	public Set<String> getFiles();
	public String getFilesFullPath(String filesName);
	public boolean containsKey(String key);
	public String removeFile(String filesName);

}
