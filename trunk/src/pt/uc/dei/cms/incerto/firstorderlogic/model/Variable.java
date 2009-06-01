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
 * FOL Variable.
 * @author Pedro Oliveira
 *
 */
public class Variable extends Term implements MLNElement, Cloneable{
	
	private static final Variable X = new Variable("a1");
	private static final Variable Y = new Variable("a2");
	private static final Variable Z = new Variable("a3");
	private static final Variable K = new Variable("a4");
	private static final Variable L = new Variable("a5");
	private static final Variable M = new Variable("a6");
	private static final Variable N = new Variable("a7");
	private static final Variable O = new Variable("a8");
	private static final Variable P = new Variable("a9");
	private static final Variable Q = new Variable("a10");
	private static final Variable R = new Variable("a11");
	private static final Variable S = new Variable("a12");
	private static final Variable T = new Variable("a13");
	
	private static final Variable[] stack = new Variable[]{X,Y,Z,K,L,M,N,O,P,Q,R,S,T};
	
	public Variable(String name)
	{
		this.name = name;
	}
	
	public static Variable getVariable(int i)
	{
		if(i>= stack.length)
			return new Variable("a"+i);
		else
			return stack[i];
	}
	
	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		return new Variable(this.name);
	}
}
