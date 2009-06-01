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

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;


/**
 * Utilities related to logging
 * @author Pedro Oliveira
 *
 */
public class LoggerUtils {
	private static Logger logger = null;
	
	public static Logger getInstance()
	{
		if(logger == null)
		{			
			try
			{					
				logger = Logger.getLogger("incerto");
				logger.setLevel(Level.ALL);
				
				ConsoleHandler console = new ConsoleHandler(); 
				console.setFormatter(new SimpleFormatter()
					{
						@Override
						public String format(LogRecord record) {
							StringBuilder sb = new StringBuilder();
							sb.append(record.getLevel().getName());
							sb.append(": ");
							sb.append(formatMessage(record));
							sb.append('\n');
							return sb.toString();
						}
					}		
				);
				
				logger.addHandler(console);
				if(IncertoSettings.getInstance().DEBUG)
					console.setLevel(Level.ALL);
				else
					console.setLevel(Level.SEVERE);
				
				if(IncertoSettings.getInstance().LOG)
				{
					FileHandler handler = new FileHandler(IncertoSettings.getInstance().LOGFILE);
					handler.setLevel(Level.ALL);
					logger.addHandler(handler);
				}
				logger.setUseParentHandlers(false);
			}
			catch(Exception e) {
				logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
				logger.log(Level.SEVERE, "Problem loading system logger", e);
			}
		}		
		return logger;
	}
	
	public static void addInfo(String msg)
	{
		LoggerUtils.getInstance().log(Level.INFO, msg);
	}
	
	public static void addWarning(String msg)
	{
		LoggerUtils.getInstance().log(Level.WARNING, msg);
	}
	
	public static void addWarning(String msg, Throwable t)
	{
		LoggerUtils.getInstance().log(Level.WARNING, msg, t);
	}
	
	public static void addError(String msg, Throwable t)
	{
		LoggerUtils.getInstance().log(Level.SEVERE, msg, t);
	}
	
	public static void addError(String msg)
	{
		LoggerUtils.getInstance().log(Level.SEVERE, msg);
	}
	
	public static void addError(Throwable t)
	{
		LoggerUtils.getInstance().log(Level.SEVERE, t.getLocalizedMessage(), t);
	}
	
	public static void addConfig(String msg)
	{
		LoggerUtils.getInstance().log(Level.CONFIG, msg);
	}
	
	public static void addFine(String msg)
	{
		LoggerUtils.getInstance().log(Level.FINE, msg);
	}
}
