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


/**
 * Reasoning results of the Markov logic engine.
 * @author Pedro Oliveira
 *
 */
public class ReasoningResults implements Cloneable{

	private ArrayList<Formula> formulas;

	public ReasoningResults() {
		this.formulas = new ArrayList<Formula>();
	}

	public ReasoningResults(ArrayList<Formula> formulas) {
		this.formulas = formulas;
	}

	public ArrayList<Formula> getFormulas() {
		return formulas;
	}
	
	public void addFormula(Formula f)
	{
		formulas.add(f);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(Formula f: formulas)
		{
			sb.append(f.toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	
	@Override
	public Object clone()
	{
		ReasoningResults r = new ReasoningResults();
		for(Formula f: this.formulas)
			r.addFormula((Formula)f.clone());
		return r;
	}
}
