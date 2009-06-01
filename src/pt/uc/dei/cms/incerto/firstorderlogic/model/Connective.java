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
 * FOL connectives (i.e., logical connectives, quantifiers, identity, and delimiters)
 * @author Pedro Oliveira
 *
 */
public class Connective extends FormulaElement implements MLNElement, Cloneable{

	public static final Connective LOGICAL_CONNECTIVE_NOT = new Connective("!");
	public static final Connective LOGICAL_CONNECTIVE_AND = new Connective(" ^ ");
	public static final Connective LOGICAL_CONNECTIVE_OR = new Connective(" v ");
	public static final Connective LOGICAL_CONNECTIVE_CONDITIONAL = new Connective(" => ");
	public static final Connective LOGICAL_CONNECTIVE_BICONDITIONAL = new Connective(" <=> ");
	public static final Connective LOGICAL_CONNECTIVE_DISJUNCTION = new Connective(" => !");
	public static final Connective QUANTIFIER_UNIVERSAL = new Connective("Forall ");
	public static final Connective QUANTIFIER_EXISTENTIAL = new Connective("Exist ");
	public static final Connective IDENTITY = new Connective(" = ");
	public static final Connective DELIMITER_LEFT_BRACKET = new Connective("( ");
	public static final Connective DELIMITER_RIGHT_BRACKET = new Connective(" )");
	
	private String value;
	public Connective(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public String toString()
	{
		return value;
	}

	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		return new Connective(value);
	}
}
