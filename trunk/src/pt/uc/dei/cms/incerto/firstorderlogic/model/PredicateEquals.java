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

import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;

/**
 * FOL Predicate Equals, i.e., (x=y)
 * @author Pedro Oliveira
 *
 */
public class PredicateEquals extends Predicate implements MLNElement, Cloneable{

	public PredicateEquals(Term t1, Term t2) {
		super("Equals", t1, t2);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(terms.get(0));
		sb.append(Connective.IDENTITY);
		sb.append(terms.get(1));
		return sb.toString();
	}
	
	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		return new PredicateEquals(this.terms.get(0), this.terms.get(1));
	}

}
