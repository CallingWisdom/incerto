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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Thread that handles with streams from external processes created with Runtime.getRuntime().exec()
 * @author Pedro Oliveira
 *
 */
public class StreamProcessHandler extends Thread {

	private InputStream in;
	private boolean error; //true if error stream, false if input stream

	public StreamProcessHandler(InputStream in, boolean error) {
		this.in = in;
		this.error = error;
	}

	public void run(){
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;

		try
		{
			while((line = br.readLine())!=null)
			{
				if(error)
					LoggerUtils.addError(line);
				else
					LoggerUtils.addInfo(line);
			}				
			br.close();
		}catch (IOException e) {
			LoggerUtils.addError("Problem on reading Process buffer!", e);
		}
	}

}
