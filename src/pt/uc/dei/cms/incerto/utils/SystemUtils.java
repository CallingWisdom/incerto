package pt.uc.dei.cms.incerto.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class SystemUtils {

	public static void addFolderToClassPath(File folder, boolean recursive) throws Exception
	{
		if(folder.isDirectory())
		{
			for(File fi: folder.listFiles())
			{
				if(fi.isHidden())	//Ignore hidden files/folders
					continue;
				if(fi.isFile())
					addJarToClassPath(fi.getAbsolutePath());
				else if(fi.isDirectory() && recursive)
					addFolderToClassPath(fi, recursive);
			}
		}
	}
	
	public static void addFolderToClassPath(String path, boolean recursive) throws Exception
	{
		File folder = new File(path);
		addFolderToClassPath(folder, recursive);
	}
	
	
	public static void addJarToClassPath(String filename) throws Exception 
	{  
		if(InOutUtils.isFromFileType(filename, "jar"))
		{
			URL u = InOutUtils.uriFromFile(filename).toURL();  
			URLClassLoader urlClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();  
			Class<URLClassLoader> urlClass = URLClassLoader.class;  
			Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});  
			method.setAccessible(true);  
			method.invoke(urlClassLoader, new Object[]{u});  
		}
	}
	
	//TODO: Optimize...
	public static void addJarToClassPath(File file) throws Exception
	{
		addJarToClassPath(file.getAbsolutePath());
	}
}
