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

package pt.uc.dei.cms.incerto.exceptions;


/**
 * Exception generated by Markov Logic engines.
 * @author Pedro Oliveira
 *
 */
public class MarkovLogicEngineException extends IncertoException {
	private static final long serialVersionUID = 5458875762283299215L;

	public MarkovLogicEngineException(String message, Throwable cause)
	{
		super(message,cause);
	}
	
	public MarkovLogicEngineException(String message)
	{
		super(message);
	}
}