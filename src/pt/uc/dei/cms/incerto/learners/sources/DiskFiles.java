package pt.uc.dei.cms.incerto.learners.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiskFiles implements OntologyPopulationSource {

	private List<File> files;
	
	public DiskFiles()
	{
		files = new ArrayList<File>();
	}
	
	public void addFile(String filename)
	{
		files.add(new File(filename));
	}
	
	public void addFolder(String path, boolean recursive)
	{
		files = parseFolder(files, path, recursive);
	}
	
	private List<File> parseFolder(List<File> files, String path, boolean recursive)
	{
		File folder = new File(path);
		if(folder.isDirectory())
		{
			for(File fi: folder.listFiles())
			{
				if(fi.isHidden())	//Ignore hidden files/folders
					continue;
				if(fi.isFile())
					files.add(fi);
				else if(fi.isDirectory() && recursive)
					files = parseFolder(files, fi.getAbsolutePath(), recursive);
			}
		}
		return files;
	}
	
	//@Override
	public boolean containsFiles() {
		return true;
	}

	//@Override
	public boolean containsStrings() {
		return false;
	}

	//@Override
	public List<File> getFiles() {
		return files;
	}

	//@Override
	public List<String> getStrings() {
		return null;
	}

}
