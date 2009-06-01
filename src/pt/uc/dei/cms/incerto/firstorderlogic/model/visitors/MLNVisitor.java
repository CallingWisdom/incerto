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

/**
 * Visitor for MLNElements. Used to print those elements.
 * @author Pedro Oliveira
 *
 */
public interface MLNVisitor {

	public String MLNELementToString(MLNElement element, boolean normalizeNames);
	public void visit(MLN mln);
	public void visit(Evidence e);
	public void visit(Formula f);
	public void visit(FormulaElement fe);
	public void visit(Predicate p);
	public void visit(PredicateEquals p);
	public void visit(PredicateNotEquals p);
	public void visit(Connective c);
	public void visit(Quantifier q);
	public void visit(Term t);
	public void visit(Constant c);
	public void visit(Variable v);	
}
