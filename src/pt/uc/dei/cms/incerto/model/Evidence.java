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

package pt.uc.dei.cms.incerto.model;

import java.util.ArrayList;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;


/**
 * Evidence data (i.e., a set of ground atoms).
 * @author Pedro Oliveira
 *
 */
public class Evidence implements MLNElement, Cloneable{
	private ArrayList<Formula> evidences;

	public Evidence() {
		this.evidences = new ArrayList<Formula>();
	}

	public Evidence(ArrayList<Formula> evidences) {
		this.evidences = evidences;
	}

	public ArrayList<Formula> getEvidences() {
		return evidences;
	}
	
	public void addEvidence(Formula f)
	{
		evidences.add(f);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(Formula f: evidences)
		{
			sb.append(f.toString());
			sb.deleteCharAt(sb.length()-1);
			sb.append('\n');
		}
		return sb.toString();
	}
	
	
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	
	public Object clone()
	{
		Evidence e = new Evidence();
		for(Formula f: this.evidences)
			e.addEvidence((Formula)f.clone());
		return e;
	}
}
