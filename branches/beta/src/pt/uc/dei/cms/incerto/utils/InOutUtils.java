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

package pt.uc.dei.cms.incerto.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;


/**
 * Input/Output utilities
 * @author Pedro Oliveira
 *
 */
public class InOutUtils {
	
	/**
	 * Write string to file
	 * @param filename
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public static int writeToFile(String filename, String text) throws IOException
	{
		BufferedWriter bw;

		bw = new BufferedWriter(new FileWriter(filename));
		bw.write(text);
		bw.close();
		return 0;
	}
	
	/**
	 * Read List of String from file (one element for each line)
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> readListFromFile(String filename) throws IOException
	{
		BufferedReader br;
		ArrayList<String> res = new ArrayList<String>();
		String line;

		br = new BufferedReader(new FileReader(filename));
		while((line=br.readLine())!=null)
			res.add(line);
		br.close();
		return res;
	}
	
	/**
	 * Read file to String
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readStringFromFile(String filename) throws IOException
	{
		BufferedReader br;
		StringBuilder res = new StringBuilder();
		String line;

		br = new BufferedReader(new FileReader(filename));
		while((line=br.readLine())!=null)
			res.append(line).append(System.getProperty("line.separator"));
		br.close();
		return res.toString();
	}
	
	/**
	 * Delete file when JVM exits
	 * @param filename
	 */
	public static void deleteFileOnExit(String filename)
	{
		File f = new File(filename);
		f.deleteOnExit();
	}
	
	/**
	 * Get filename from path <br>
	 * E.g.:<br>
	 * Return "file.ex" from "c:/folder/file.ex"
	 * @param path
	 * @return
	 */
	public static String getFilename(String path)
	{
		File f = new File(path);
		return f.getName();
	}
	
	/**
	 * Create folder with the desired path
	 * @param path
	 * @return
	 */
	public static boolean createFolder(String path)
	{
		File f = new File(path);
		if(!f.exists())
			return f.mkdirs();
		else if(!f.isDirectory())
			return false;
		else
			return true;
	}
	
	/**
	 * Transform file into URI
	 * @param path
	 * @return
	 */
	public static URI uriFromFile(String path)
	{
		File f = new File(path);
		return f.toURI();
	}
	
	/**
	 * Normalize folder path by adding a file separator to the end, if it's missing
	 * @param path
	 * @return
	 */
	public static String normalizeFolderPath(String path)
	{
		if(path != null && !path.endsWith("\\") && !path.endsWith("/"))
			return path+IncertoSettings.FILE_SEPARATOR;
		else 
			return path;
	}
}
