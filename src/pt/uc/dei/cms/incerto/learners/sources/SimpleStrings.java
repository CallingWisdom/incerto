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

package pt.uc.dei.cms.incerto.learners.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SimpleStrings implements OntologyPopulationSource {
	
	private List<String> strings;
	
	public SimpleStrings() {
		this.strings = new ArrayList<String>();
	}
	
	public void addString(String st)
	{
		strings.add(st);
	}

	//@Override
	public boolean containsFiles() {
		return false;
	}

	//@Override
	public boolean containsStrings() {
		return true;
	}

	//@Override
	public List<File> getFiles() {
		return null;
	}

	//@Override
	public List<String> getStrings() {
		return strings;
	}

}
