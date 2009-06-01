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

package pt.uc.dei.cms.incerto.firstorderlogic.model;

import java.util.ArrayList;

import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;


/**
 * FOL Quantifier. It is composed by a connective (namely a quantifier) and a set of variables related to the quantifier.
 * @author Pedro Oliveira
 *
 */
public class Quantifier extends FormulaElement implements MLNElement, Cloneable{
	
	private Connective connective;
	private ArrayList<Variable> variables;
	
	
	public Quantifier(Connective connective) {
		this.connective = connective;
		this.variables = new ArrayList<Variable>();
	}
	
	public Quantifier(Connective connective, Variable variable) {
		this.connective = connective;
		this.variables = new ArrayList<Variable>(1);
		this.variables.add(variable);
	}

	public void addVariable(Variable variable)
	{
		variables.add(variable);
	}
	
	public Connective getConnective() {
		return connective;
	}
	
	public ArrayList<Variable> getVariables() {
		return variables;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(connective.toString());
		for(Variable v: variables)
		{
			sb.append(v.toString());
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(' ');
		return sb.toString();
	}
	
	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		Quantifier q = new Quantifier((Connective)this.connective.clone());
		for(Variable v: variables)
			q.addVariable((Variable)v.clone());
		return q;
	}
}
