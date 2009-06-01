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

package pt.uc.dei.cms.incerto.onto;

import java.util.ArrayList;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.SWRLAtom;
import org.semanticweb.owl.model.SWRLAtomConstantObject;
import org.semanticweb.owl.model.SWRLAtomDObject;
import org.semanticweb.owl.model.SWRLAtomDVariable;
import org.semanticweb.owl.model.SWRLAtomIVariable;
import org.semanticweb.owl.model.SWRLAtomIndividualObject;
import org.semanticweb.owl.model.SWRLBuiltInAtom;
import org.semanticweb.owl.model.SWRLClassAtom;
import org.semanticweb.owl.model.SWRLDataRangeAtom;
import org.semanticweb.owl.model.SWRLDataValuedPropertyAtom;
import org.semanticweb.owl.model.SWRLDifferentFromAtom;
import org.semanticweb.owl.model.SWRLObjectPropertyAtom;
import org.semanticweb.owl.model.SWRLObjectVisitor;
import org.semanticweb.owl.model.SWRLRule;
import org.semanticweb.owl.model.SWRLSameAsAtom;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.PredicateEquals;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.model.MLN;


/**
 * OWLAPI Visitor for SWRL rules. <br>
 * Transforms SWRL rules in FOL rules.
 * @author Pedro Oliveira
 *
 */
public class RulesOWLAPIVisitor implements SWRLObjectVisitor {

	private OWLOntology ontology;
	private MLN kb;
	private Formula f;
	private boolean valid;
	private Term term;
	private boolean sameas, differentfrom;
	
	public RulesOWLAPIVisitor(OWLOntology ontology )
	{
		this.ontology = ontology;	
	}
	
	/**
	 * Add SWRL rules to MLN.
	 * @param kb
	 * @return
	 */
	public MLN addRulesToMLN(MLN kb)
	{
		this.kb = kb;
		for(SWRLRule rule: ontology.getRules())
			rule.accept(this);
		
		if(sameas)
			addSameAs();
		if(differentfrom)
			addDifferentFrom();
		
		return kb;
	}
	
	private void addSameAs()
	{
		//sameAs(x,y) => (x=y)
		f = new Formula();
		Predicate p = new Predicate("sameAs",Variable.getVariable(0),Variable.getVariable(1));
		f.addElement(p);
		f.addConditional();
		f.openBrackets();
		f.addElement(new PredicateEquals(Variable.getVariable(0),Variable.getVariable(1)));
		f.closeBrackets();
		kb.addDeclaration(p.createDeclaration());
		kb.addRule(f);
	}
	
	private void addDifferentFrom()
	{
		//differentFrom(x,y) => (x!=y)
		f = new Formula();
		Predicate p = new Predicate("differentFrom",Variable.getVariable(0),Variable.getVariable(1));
		f.addElement(p);
		f.addDisjunction();
		f.openBrackets();
		f.addElement(new PredicateEquals(Variable.getVariable(0),Variable.getVariable(1)));
		f.closeBrackets();
		kb.addDeclaration(p.createDeclaration());
		kb.addRule(f);
	}
	
	
	public void visit(SWRLRule node) {
		System.out.println(node.toString());
		valid = true;
		f = new Formula();
		for(SWRLAtom<?> atom:node.getBody())
		{
			atom.accept(this);
			f.addAnd();
		}
		f.removeLastElement();
		f.addConditional();
		for(SWRLAtom<?> atom:node.getHead())
		{
			atom.accept(this);
			f.addAnd();
		}
		f.removeLastElement();
		System.out.println("Formula: "+f+"\tValid"+valid);
		if(valid)
			kb.addRule(f);
	}

	
	public void visit(SWRLClassAtom node) {
		//TODO: Simplificacao. Penso que atributo do predicado pode ser complexo (por enquanto penso que nao, mas no futuro pode vir a ser)
		node.getArgument().accept(this);
		System.out.println("SWRLClass"+node);
		Predicate p = new Predicate(node.getPredicate().toString(),term);
		kb.addDeclaration(p.createDeclaration());
		f.addElement(p);
	}

	
	public void visit(SWRLDataRangeAtom node) {
		System.out.println("SWRLDataRange"+node);
		valid = false;
	}

	
	public void visit(SWRLObjectPropertyAtom node) {
		//TODO: Simplificacao. Penso que atributo do predicado pode ser complexo (por enquanto penso que nao, mas no futuro pode vir a ser)

		node.getFirstArgument().accept(this);
		Term t1 = term;
		node.getSecondArgument().accept(this);
		
		Predicate p = new Predicate(node.getPredicate().toString(),t1,term);
		System.out.println("SWRLOPAtom"+node);
		kb.addDeclaration(p.createDeclaration());
		f.addElement(p);
	}

	
	public void visit(SWRLDataValuedPropertyAtom node) {
		System.out.println("SWRLDataValuedPato"+node);
		valid = false;
	}

	
	public void visit(SWRLBuiltInAtom node) {
		// TODO Builtins sao funcoes definidas pelos utilizadores. O funcionamento e assim, mas elas so aceitam datatypes e tb nao sei ate que ponto isto nao pode ser complexo (existe uma bd com builtins que provavelmente ja tem uma semantica associada). ver melhor	
		ArrayList<Term> terms = new ArrayList<Term>();
		for(SWRLAtomDObject argument:node.getArguments())
		{
			argument.accept(this);
			terms.add(term);
		}
		System.out.println("SWRLBuiltIn"+node);
		Predicate p = new Predicate(node.getPredicate().getShortName(),terms);
		kb.addDeclaration(p.createDeclaration());
		f.addElement(p);
	}

	
	public void visit(SWRLAtomDVariable node) {
		//Uma variavel (ex: x) que tem range de datatypes
		System.out.println("SWRLDVariable"+node);
		valid = false;
	}

	
	public void visit(SWRLAtomIVariable node) {
		//Uma variavel (ex: x) que tem range de individuos
		term = new Variable(node.toString().substring(1));
		System.out.println("SWRLIVariable"+node);
	}

	
	public void visit(SWRLAtomIndividualObject node) {
		//Uma constante (ex: Pedro) que e um individuo
		term = new Constant(node.toString());
		System.out.println("SWRLIndividualObjec"+node);
	}

	
	public void visit(SWRLAtomConstantObject node) {
		//Uma constante (ex: 23) que e datatype
		valid = false;
		System.out.println("SWRLConstantObj"+node);
	}

	
	public void visit(SWRLSameAsAtom node) {	
		//TODO: Simplificacao. Penso que atributo do predicado pode ser complexo (nao tem logica, visto que ja se sabe qual ele e, sameAs)	
		node.getFirstArgument().accept(this);
		Term t1 = term;
		node.getSecondArgument().accept(this);
		
		Predicate p = new Predicate(node.getPredicate().toString(), t1, term);
		f.addElement(p);
		System.out.println("SWRLSameAS"+node);

		sameas = true;
	}

	
	public void visit(SWRLDifferentFromAtom node) {
		//TODO: Simplificacao. Penso que atributo do predicado pode ser complexo (nao tem logica, visto que ja se sabe qual ele e, sameAs)
		node.getFirstArgument().accept(this);
		Term t1 = term;
		node.getSecondArgument().accept(this);

		Predicate p = new Predicate(node.getPredicate().toString(), t1, term);
		f.addElement(p);
		System.out.println("SWRLDifferentFrom"+node);
		
		differentfrom = true;
	}

}
