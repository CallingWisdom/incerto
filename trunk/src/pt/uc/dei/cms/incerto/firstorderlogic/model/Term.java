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
 * FOL Predicate Term (a constant or a variable).
 * @author Pedro Oliveira
 *
 */
public abstract class Term implements MLNElement, Cloneable{
	
	protected String name;
	
	public String getName() {
		return name;
	}

	public boolean isConstant()
	{
		if(this.getClass().equals(Constant.class))
			return true;
		return false;
	}
	
	public boolean isVariable()
	{
		if(this.getClass().equals(Variable.class))
			return true;
		return false;
	}
	
	public boolean isGrounded()
	{
		return isConstant();
	}
	
	public Constant asConstant()
	{
		return (Constant)this;
	}
	
	public Variable asVariable()
	{
		return (Variable)this;
	}
	
	public String toString()
	{
		return name;
	}
	
	//@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		if(isConstant())
			return asConstant().clone();
		else if(isVariable())
			return asVariable().clone();
		else
			return null;
	}
}
