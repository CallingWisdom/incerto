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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.antlr.runtime.RecognitionException;

import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;
import pt.uc.dei.cms.incerto.firstorderlogic.parser.FolParser;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;


/**
 * Markov Logic Network (MLN). <br>
 * 
 * @author Pedro Oliveira
 *
 */
public class MLN implements MLNElement, Cloneable{
	
	private HashSet<Predicate> declarations;
	private ArrayList<Formula> formulas;
	private ArrayList<Formula> evidences;
	private ArrayList<Formula> rules;
	private ArrayList<Formula> folrules;
	private ArrayList<String> axioms;
	private int invalid;
	private String name;
	
	public MLN() {
		this.declarations = new HashSet<Predicate>();
		this.formulas = new ArrayList<Formula>();
		this.evidences = new ArrayList<Formula>();
		this.rules = new ArrayList<Formula>();
		this.folrules = new ArrayList<Formula>();
		this.axioms = new ArrayList<String>();
		this.invalid = 0;
		this.name ="";
	}

	public HashSet<Predicate> getDeclarations() {
		return declarations;
	}

	public ArrayList<Formula> getFormulas() {
		return formulas;
	}
	
	public ArrayList<Formula> getEvidences() {
		return evidences;
	}
	
	public ArrayList<Formula> getRules() {
		return rules;
	}
	
	public ArrayList<Formula> getFOLRules() {
		return folrules;
	}
	
	public ArrayList<String> getAxioms() {
		return axioms;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isInvalid()
	{
		if(invalid>0)
			return true;
		else
			return false;
	}
	
	public void setAsInvalid()
	{
		invalid++;
	}
	
	public int getInvalid()
	{
		return invalid;
	}
	
	public void setInvalid(int invalid)
	{
		this.invalid = invalid;
	}

	public void addDeclaration(Predicate p)
	{
		declarations.add(p);
	}
	
	public void addFormula(Formula f)
	{
		formulas.add(f);
	}
	
	public void addEvidence(Formula f)
	{
		evidences.add(f);
	}
	
	public void addAxiom(String a)
	{
		axioms.add(a);
	}
	
	public void addRule(Formula f)
	{
		rules.add(f);
	}
	
	public void addFOLRule(Formula f)
	{
		folrules.add(f);
	}
	
	/**
	 * Parse FOL rules from file.
	 * @param extraFOLRulesLocation
	 * @param mln
	 * @return
	 * @throws IncertoException
	 */
	public static MLN parseFOLRules(String extraFOLRulesLocation, MLN mln) throws IncertoException
	{
		try {
			return FolParser.parse(extraFOLRulesLocation, mln);
		} catch (RecognitionException e) {
			LoggerUtils.addError(e);
			throw new IncertoException("Problem parsing FOL Rules file ("+extraFOLRulesLocation+")",e);
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new IncertoException("Problem reading FOL Rules file ("+extraFOLRulesLocation+")",e);
		}
	}
	
	public String toStringAxioms()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("//AXIOMS\n");
		for(String axiom: axioms)
		{
			sb.append(axiom);
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public String toStringEvidences()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("//EVIDENCES\n");
		for(Formula formula: evidences)
		{
			sb.append(formula);
			sb.deleteCharAt(sb.length()-1);
			sb.append('\n');
		}		
		return sb.toString();
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("//DECLARATIONS\n");
		for(Predicate predicate: declarations)
		{
			sb.append(predicate);
			sb.append('\n');
		}
		
		sb.append("\n//STATEMENTS\n");
		for(Formula formula: formulas)
		{
			sb.append(formula);
			sb.append('\n');
		}
		
		sb.append("\n//RULES\n");
		for(Formula formula: rules)
		{
			sb.append(formula);
			sb.append('\n');
		}
		
		sb.append("\n//FOL RULES\n");
		for(Formula formula: folrules)
		{
			sb.append(formula);
			sb.append('\n');
		}
		return sb.toString();
	}
	
	
	public void accept(MLNVisitor visitor) {
		visitor.visit(this);		
	}
	
	
	public Object clone()
	{
		MLN mln = new MLN();
		mln.setName(this.name);
		mln.setInvalid(this.invalid);
		
		for(Predicate p: this.declarations)
			mln.addDeclaration((Predicate)p.clone());
		for(Formula f: this.formulas)
			mln.addFormula((Formula)f.clone());
		for(Formula f: this.evidences)
			mln.addEvidence((Formula)f.clone());
		for(Formula f: this.rules)
			mln.addRule((Formula)f.clone());
		for(Formula f: this.folrules)
			mln.addFOLRule((Formula)f.clone());
		for(String s: this.axioms)
			mln.addAxiom(s);
		return mln;
	}
}
