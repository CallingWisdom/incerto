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

package pt.uc.dei.cms.incerto.utils.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.utils.StringUtils;


/**
 * Utility to read property files
 * @author Pedro Oliveira
 *
 */
public class PropertiesReader {
	
	private Properties properties;
	private String filename;
	
	public PropertiesReader(String filename) throws FileNotFoundException, IOException
	{
		properties = new Properties();
		properties.load(new FileInputStream(filename));
		this.filename = filename;
	}
	
	public String getString(String propertyName) throws IncertoException {
		if(properties.containsKey(propertyName))
			return properties.getProperty(propertyName);
		else
			throw new IncertoException("Problem reading property "+propertyName+" from file "+filename);
	}

	public double getDouble(String propertyName) throws IncertoException {
		try{
			if(properties.containsKey(propertyName))
				return Double.parseDouble(properties.getProperty(propertyName));
			else
				throw new IncertoException("Problem reading property "+propertyName+" from file "+filename);
		}catch (NumberFormatException e) {
			throw new IncertoException("Problem parsing property "+propertyName+" from file "+filename,e);
		}
	}
	
	public int getInt(String propertyName) throws IncertoException {
		try{
			if(properties.containsKey(propertyName))
				return Integer.parseInt(properties.getProperty(propertyName));
			else
				throw new IncertoException("Problem reading property "+propertyName+" from file "+filename);
		}catch (NumberFormatException e) {
			throw new IncertoException("Problem parsing property "+propertyName+" from file "+filename,e);
		}	
	}
	
	public boolean getBoolean(String propertyName) throws IncertoException {
		if(properties.containsKey(propertyName))
			return StringUtils.parseBoolean(properties.getProperty(propertyName));
		else
			throw new IncertoException("Problem reading property "+propertyName+" from file "+filename);
	}
	
}
