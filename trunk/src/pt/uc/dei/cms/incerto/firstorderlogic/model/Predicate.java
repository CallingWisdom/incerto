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
import java.util.List;

import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;


/**
 * FOL predicate. It is composed by a name and a set of terms.
 * @author Pedro Oliveira
 *
 */
public class Predicate extends FormulaElement implements MLNElement, Cloneable{

	protected String name;
	protected ArrayList<Term> terms;


	public Predicate(String name) {
		this.name = name;
		this.terms = new ArrayList<Term>();
	}	

	public Predicate(String name, List<Term> terms) {
		this.name = name;
		this.terms = new ArrayList<Term>(terms);
	}

	public Predicate(String name, Term t1) {
		this.name = name;
		this.terms = new ArrayList<Term>(1);
		this.terms.add(t1);
	}

	public Predicate(String name, Term t1, Term t2) {
		this.name = name;
		this.terms = new ArrayList<Term>(2);
		this.terms.add(t1);
		this.terms.add(t2);
	}

	public String getName() {
		return name;
	}

	public ArrayList<Term> getTerms() {
		return terms;
	}

	public void addTerm(Term term) {
		terms.add(term);
	}

	public boolean isGrounded()
	{
		for(Term t: terms)
			if(!t.isGrounded())
				return false;
		return true;
	}

	public int getArity()
	{
		return terms.size();
	}

	/**
	 * Create predicate declaration. E.g.:<br>
	 * P(x,y) is transformed in P(Individual,Individual).
	 * @return
	 */
	public Predicate createDeclaration()
	{
		ArrayList<Term> dterms = new ArrayList<Term>(terms.size());
		for(int i=0; i<terms.size(); i++)
			dterms.add(Constant.Individual);
		return new Predicate(this.name,dterms);		
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if(getArity()>0)
		{
			sb.append("( ");
			for(Term t: terms)
			{
				sb.append(t);
				sb.append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(" )");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof Predicate))
			return false;
		Predicate p = (Predicate)o;
		if(this.name.compareTo(p.getName())==0)
			return true;
		return false;
	}

	@Override 
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}

	@Override
	public Object clone()
	{
		Predicate p = new Predicate(this.name);
		for(Term t: this.terms)
			p.addTerm((Term)t.clone());
		return p;

	}
}
