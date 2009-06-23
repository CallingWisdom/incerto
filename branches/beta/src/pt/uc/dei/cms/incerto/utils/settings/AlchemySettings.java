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

import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;



/**
 * Read Alchemy settings from properties file
 * @author Pedro Oliveira
 *
 */
public class AlchemySettings {
	
	private static AlchemySettings settings;
	private PropertiesReader properties;
	public String settingsFile = "alchemy.properties";
	
	public static AlchemySettings getInstance()
	{
		if(settings==null)
			try {
				settings = new AlchemySettings();
			} catch (IncertoException e) {
				//TODO: Solve this problem...
				LoggerUtils.addError(e);
				throw new Error(e);
			}
		return settings;
	}
	
	public AlchemySettings() throws IncertoException
	{
		try {
			properties = new PropertiesReader(settingsFile);
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new IncertoException("Problem loading property file "+settingsFile,e);
		}
		
		LOCATION = InOutUtils.normalizeFolderPath(properties.getString("Location"));
		LoggerUtils.addConfig("Alchemy location: "+LOCATION);
		DUMP_LOCATION = InOutUtils.normalizeFolderPath(properties.getString("DumpLocation"));
		LoggerUtils.addConfig("Alchemy dump location: "+DUMP_LOCATION);
		if(!InOutUtils.createFolder(DUMP_LOCATION))
			throw new IncertoException("Problem on acessing dump file location "+DUMP_LOCATION+" (cannot create folder)!");
		INFERENCE_EXTRA = properties.getString("InferenceExtra");
		LoggerUtils.addConfig("Alchemy inference extra tags: "+INFERENCE_EXTRA);
		LEARN_EXTRA = properties.getString("LearnExtra");
		LoggerUtils.addConfig("Alchemy learn extra tags: "+LEARN_EXTRA);
		INFERENCE_COND = properties.getString("InferenceCond");
		LoggerUtils.addConfig("Alchemy conditional inference tag: "+INFERENCE_COND);
		INFERENCE_MLA = properties.getString("InferenceMLA");
		LoggerUtils.addConfig("Alchemy most likely assignment inference tag: "+INFERENCE_MLA);
	}
	
	public static void setAlchemySettings(AlchemySettings settings)
	{
		AlchemySettings.settings = settings;
	}
	
	public String LOCATION;
	public String DUMP_LOCATION;
	public String INFERENCE_EXTRA;
	public String LEARN_EXTRA;
	public String INFERENCE_COND;
	public String INFERENCE_MLA;
}
