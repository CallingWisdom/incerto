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
 * FOL constant.
 * @author Pedro Oliveira
 *
 */
public class Constant extends Term implements MLNElement, Cloneable{
	
	public static final Constant Individual = new Constant("Individual");

	public Constant(String name)
	{
		this.name = name;
	}
	
	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		return new Constant(this.name);
	}
}
