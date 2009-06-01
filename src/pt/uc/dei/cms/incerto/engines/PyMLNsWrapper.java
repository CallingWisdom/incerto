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
import pt.uc.dei.cms.incerto.utils.settings.PyMLNsSettings;


/**
 * Wrapper for PyMLNs' executable.
 * @author Pedro Oliveira
 *
 */
public class PyMLNsWrapper {

	private Runtime run;
	private String pymlnslocation;
	
	public PyMLNsWrapper() {
		run = Runtime.getRuntime();
		pymlnslocation = PyMLNsSettings.getInstance().LOCATION;
	}
	
	private int runPyMLNs(String parameters) throws Exception
	{
		final Process pymlns = run.exec("python "+pymlnslocation+"Incerto.py "+parameters);	
		run.addShutdownHook(new Thread(new Runnable() {  
			public void run() {  
				pymlns.destroy();  
			}  
		}));
		new StreamProcessHandler(pymlns.getErrorStream(),true).start();
		new StreamProcessHandler(pymlns.getInputStream(),false).start();
		return pymlns.waitFor();
	}
	
	public int infer(String parameters) throws Exception
	{
		return runPyMLNs("infer "+parameters);
	}
	
	public int learnwts(String parameters) throws Exception
	{
		return runPyMLNs("learnwts "+parameters);
	}
	
}
