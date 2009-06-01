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
 * FOL formula. Composed by a set of FormulaElements and a weight.
 * @author Pedro Oliveira
 *
 */
public class Formula implements MLNElement, Cloneable{
	
	private ArrayList<FormulaElement> elements;
	private Double weight;
	private int currentVariable;
	private int increaseVariable;
	
	public Formula() 
	{	
		this.elements = new ArrayList<FormulaElement>();
		this.weight = null;
		this.currentVariable = 0;
		this.increaseVariable = 1;
	}

	public Formula(Double weight) 
	{
		this.elements = new ArrayList<FormulaElement>();
		this.weight = weight;
		this.currentVariable = 0;
		this.increaseVariable = 1;
	}
	
	public Formula(Double weight, FormulaElement element) 
	{
		this.elements = new ArrayList<FormulaElement>();
		this.weight = weight;
		this.currentVariable = 0;
		this.increaseVariable = 1;
		elements.add(element);
	}
	
	public Formula(FormulaElement element) 
	{
		this.elements = new ArrayList<FormulaElement>();
		this.weight = null;
		this.currentVariable = 0;
		this.increaseVariable = 1;
		elements.add(element);
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public ArrayList<FormulaElement> getElements() {
		return elements;
	}
	
	public int size()
	{
		return elements.size();
	}
	
	public void addElement(FormulaElement element)
	{
		elements.add(element);
	}
	
	public void openBrackets()
	{
		elements.add(Connective.DELIMITER_LEFT_BRACKET);
	}
	
	public void closeBrackets()
	{
		elements.add(Connective.DELIMITER_RIGHT_BRACKET);
	}
	
	public void addAnd()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_AND);
	}
	
	public void addOr()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_OR);
	}
	
	public void addNot()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_NOT);
	}
	
	public void addBiconditional()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_BICONDITIONAL);
	}
	
	public void addConditional()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_CONDITIONAL);
	}
	
	public void addDisjunction()
	{
		elements.add(Connective.LOGICAL_CONNECTIVE_DISJUNCTION);
	}
	
	public void addIdentity()
	{
		elements.add(Connective.IDENTITY);
	}
	
	public void addUniversal(Variable v)
	{
		//Universal quantifiers are declared in the beginning of the formula.
		elements.add(0,new Quantifier(Connective.QUANTIFIER_UNIVERSAL,v));
	}
	
	public void removeLastElement()
	{
		elements.remove(elements.size()-1);
	}
	
	public boolean isGrounded()
	{
		for(FormulaElement element: elements)
		{
			if(element.isPredicate())
				if(!element.asPredicate().isGrounded())
					return false;
		}
		return true;
	}
	
	public boolean isHard()
	{
		if(weight == null)
			return true;
		return false;
	}
	
	public void setAsHard()
	{
		weight = null;
	}
	
	public Variable getCurrentVariable()
	{
		return Variable.getVariable(currentVariable);
	}
	
	public Variable getNextVariable()
	{
		return Variable.getVariable(currentVariable+increaseVariable);
	}
	
	public Variable getPreviousVariable()
	{
		return Variable.getVariable(currentVariable-increaseVariable);
	}
	
	public Variable getVariable(int index)
	{
		return Variable.getVariable(index);
	}
	
	public void increaseVariable()
	{
		currentVariable+=increaseVariable;
	}
	
	public void decreaseVariable()
	{
		currentVariable-=increaseVariable;
	}
	
	public void setVariable(int index)
	{
		currentVariable = index;
	}
	
	public void revertIncreaseVariable()
	{
		increaseVariable*=-1;
	}
	
	public void setIncreaseVariable(int increase)
	{
		increaseVariable = increase;
	}
	
	public void resetIncreaseVariable()
	{
		increaseVariable = 1;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		if(!isHard())
		{
			sb.append(weight);
			sb.append(' ');
		}
		
		for(FormulaElement element: elements)
			sb.append(element);
		
		if(isHard())
			sb.append('.');
		return sb.toString();
	}
	
	//@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Object clone()
	{
		Formula f = new Formula();
		f.setWeight(this.weight);
		f.setIncreaseVariable(this.increaseVariable);
		f.setVariable(this.currentVariable);
		for(FormulaElement fe: this.elements)
			f.addElement((FormulaElement)fe.clone());
		return f;
	}
}
