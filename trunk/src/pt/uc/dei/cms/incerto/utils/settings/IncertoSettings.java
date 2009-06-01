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

import pt.uc.dei.cms.incerto.engines.Alchemy;
import pt.uc.dei.cms.incerto.engines.MarkovLogicEngine;
import pt.uc.dei.cms.incerto.engines.PyMLNs;
import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

/**
 * Read Incerto settings from properties file
 * @author Pedro Oliveira
 *
 */
public class IncertoSettings {
	
	private static IncertoSettings settings;
	private PropertiesReader properties;
	public String settingsFile = "incerto.properties";
	
	public static IncertoSettings getInstance()
	{
		if(settings == null )
			try {
				settings = new IncertoSettings();
			} catch (IncertoException e) {
				//TODO: Solve this problem...
				LoggerUtils.addError(e);
				throw new Error(e);
			}
		return settings;
	}
	
	public IncertoSettings() throws IncertoException
	{
		try {
			properties = new PropertiesReader(settingsFile);
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new IncertoException("Problem loading property file "+settingsFile,e);
		}
		
		DEBUG = properties.getBoolean("Debug");
		LoggerUtils.addConfig("Incerto debug information: "+DEBUG);
		LOG = properties.getBoolean("Log");
		LoggerUtils.addConfig("Incerto save log: "+LOG);
		LOGFILE = properties.getString("Logfile");
		LoggerUtils.addConfig("Incerto log file: "+LOGFILE);
		PROB_URI = properties.getString("ProbURI");
		LoggerUtils.addConfig("Incerto probability annotation URI: "+PROB_URI);
		WEIGHT_URI = properties.getString("WeightURI");
		LoggerUtils.addConfig("Incerto weight annotation URI: "+WEIGHT_URI);
		TYPED_VARIABLES = properties.getBoolean("TypedVariables");
		LoggerUtils.addConfig("Incerto typed variables: "+TYPED_VARIABLES);
		
		if(properties.getString("MLEngine").compareTo("PyMLNs")==0)
			ML_ENGINE = new PyMLNs();
		else
			ML_ENGINE = new Alchemy();
	}
	
	public boolean DEBUG;
	public boolean LOG;
	public String LOGFILE;
	public String PROB_URI;
	public String WEIGHT_URI;
	public MarkovLogicEngine ML_ENGINE;
	public boolean TYPED_VARIABLES;
	
	/*INTERN SETTINGS*/
	
	public static String FILE_SEPARATOR = (System.getProperty("file.separator").compareTo("/")==0) ? "/":"\\";
	
	public static boolean IS_WINDOWS = (System.getProperty("os.name").startsWith("Windows")) ? true:false;

}
