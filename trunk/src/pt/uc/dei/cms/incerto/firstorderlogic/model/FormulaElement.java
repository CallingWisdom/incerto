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
 * FOL Formula Element (i.e., predicate, connective, or quantifier)
 * @author Pedro Oliveira
 *
 */
public abstract class FormulaElement implements MLNElement, Cloneable{

	public boolean isPredicate()
	{
		if(this.getClass().equals(Predicate.class))
			return true;
		return false;
	}
	
	public boolean isConnective()
	{
		if(this.getClass().equals(Connective.class))
			return true;
		return false;
	}
	
	public boolean isQuantifier()
	{
		if(this.getClass().equals(Quantifier.class))
			return true;
		return false;
	}
	
	public Predicate asPredicate()
	{
		return (Predicate)this;
	}
	
	public Connective asConnective()
	{
		return (Connective)this;
	}
	
	public Quantifier asQuantifier()
	{
		return (Quantifier)this;
	}
	
	//@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		if(isConnective())
			return asConnective().clone();
		else if(isPredicate())
			return asPredicate().clone();
		else if(isQuantifier())
			return asQuantifier().clone();
		else
			return null;
	}
}
