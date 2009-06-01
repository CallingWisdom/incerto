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

package pt.uc.dei.cms.incerto.engines;


import pt.uc.dei.cms.incerto.utils.StreamProcessHandler;
import pt.uc.dei.cms.incerto.utils.settings.AlchemySettings;


/**
 * Wrapper for Alchemy's executable.
 * @author Pedro Oliveira
 *
 */
public class AlchemyWrapper {

	private Runtime run;
	private String alchemylocation;

	public AlchemyWrapper() {
		run = Runtime.getRuntime();
		alchemylocation = AlchemySettings.getInstance().LOCATION;
	}

	private int runAlchemy(String parameters) throws Exception
	{
		final Process alchemy = run.exec(alchemylocation+parameters);	
		run.addShutdownHook(new Thread(new Runnable() {  
			public void run() {  
				alchemy.destroy();  
			}  
		})); 
		
		new StreamProcessHandler(alchemy.getErrorStream(),true).start();
		new StreamProcessHandler(alchemy.getInputStream(),false).start();
		return alchemy.waitFor();
	}

	public int infer(String parameters) throws Exception
	{
		return runAlchemy("infer "+parameters);
	}

	public int learnwts(String parameters) throws Exception
	{
		return runAlchemy("learnwts "+parameters);
	}


}
