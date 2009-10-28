/*
 * Copyright 2009 Pedro Oliveira
 * This file is part of Incerto.
 * 
 * Incerto is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Incerto is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Incerto.  If not, see <http://www.gnu.org/licenses/>.
 */

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
