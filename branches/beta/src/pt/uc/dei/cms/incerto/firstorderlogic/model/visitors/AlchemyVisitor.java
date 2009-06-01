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

package pt.uc.dei.cms.incerto.firstorderlogic.model.visitors;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Connective;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.FormulaElement;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.PredicateEquals;
import pt.uc.dei.cms.incerto.firstorderlogic.model.PredicateNotEquals;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Quantifier;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.utils.StringUtils;

/**
 * Concrete MLNVisitor for the Alchemy engine.
 * @author Pedro Oliveira
 *
 */
public class AlchemyVisitor implements MLNVisitor {

	private StringBuilder sb;
	private boolean normalizeNames;

	public String ATTACHMENT_INDIVIDUAL = "I_";
	public String ATTACHMENT_CLASS = "C_";
	public String ATTACHMENT_PROPERTY = "P_";
	public String ATTACHMENT_UNKNOWN = "U_";

	
	public String MLNELementToString(MLNElement element, boolean normalizeNames)
	{
		sb = new StringBuilder();
		this.normalizeNames = normalizeNames;
		element.accept(this);
		return sb.toString();
	}

	
	public void visit(MLN mln) {
		sb.append("//DECLARATIONS\n");
		for(Predicate predicate: mln.getDeclarations())
		{
			predicate.accept(this);
			sb.append('\n');
		}

		sb.append("\n//STATEMENTS\n");
		for(Formula formula: mln.getFormulas())
		{
			formula.accept(this);
			sb.append('\n');
		}

		sb.append("\n//RULES\n");
		for(Formula formula: mln.getRules())
		{
			formula.accept(this);
			sb.append('\n');
		}

		sb.append("\n//FOL RULES\n");
		for(Formula formula: mln.getFOLRules())
		{
			formula.accept(this);
			sb.append('\n');
		}

	}

	
	public void visit(Formula f) {
		if(!normalizeNames && f.isHard())
			return;	//TODO: This rule catches predicate declarations declared as formulas while parsing result files. Solve this problem in the grammar!
		
		if(!f.isHard())
		{
			sb.append(f.getWeight());
			sb.append(' ');
		}

		for(FormulaElement element: f.getElements())
			element.accept(this);

		if(f.isHard())
			sb.append('.');

	}

	
	public void visit(FormulaElement fe) {
		if(fe.isConnective())
			fe.asConnective().accept(this);
		else if(fe.isPredicate())
			fe.asPredicate().accept(this);
		else
			fe.asQuantifier().accept(this);
	}

	
	public void visit(Predicate p) {

		if(normalizeNames)
		{
			switch (p.getArity())
			{
			case 0: sb.append(p.getName());break;
			case 1: sb.append(ATTACHMENT_CLASS+p.getName());break;
			case 2: sb.append(ATTACHMENT_PROPERTY+p.getName());break;
			default: sb.append(ATTACHMENT_UNKNOWN+p.getName());
			}
		}
		else
			sb.append(p.getName());

		if(p.getArity()>0)
		{
			sb.append("(");
			for(Term t: p.getTerms())
			{
				t.accept(this);
				sb.append(',');
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
		}

	}

	
	public void visit(PredicateEquals p) {
		p.getTerms().get(0).accept(this);
		sb.append(Connective.IDENTITY);
		p.getTerms().get(1).accept(this);
	}

	
	public void visit(PredicateNotEquals p) {
		p.getTerms().get(0).accept(this);
		sb.append(Connective.LOGICAL_CONNECTIVE_NOT);
		sb.append(Connective.IDENTITY);
		p.getTerms().get(1).accept(this);
	}

	
	public void visit(Connective c) {
		sb.append(c.getValue());
	}

	
	public void visit(Quantifier q) {
		q.getConnective().accept(this);
		for(Variable v: q.getVariables())
		{
			v.accept(this);
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(' ');
	}

	
	public void visit(Term t) {
		if(t.isConstant())
			t.asConstant().accept(this);
		else
			t.asVariable().accept(this);
	}

	
	public void visit(Constant c) {
		if(normalizeNames)
			sb.append(ATTACHMENT_INDIVIDUAL+c.getName());
		else
			sb.append(c.getName());	
	}

	
	public void visit(Variable v) {
		if(normalizeNames)
			sb.append(StringUtils.decapitalize(v.getName()));
		else
			sb.append(v.getName());
	}

	
	public void visit(Evidence e) {
		sb.append("// EVIDENCES\n");
		for(Formula f: e.getEvidences())
		{
			f.accept(this);
			sb.setCharAt(sb.length()-1, '\n');
		}	
	}

}
