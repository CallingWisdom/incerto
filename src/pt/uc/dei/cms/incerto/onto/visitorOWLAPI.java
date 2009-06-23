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

import java.net.URI;
import java.util.ArrayList;

import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLConstantAnnotation;
import org.semanticweb.owl.model.OWLDataAllRestriction;
import org.semanticweb.owl.model.OWLDataComplementOf;
import org.semanticweb.owl.model.OWLDataExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLDataOneOf;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLDataRangeFacetRestriction;
import org.semanticweb.owl.model.OWLDataRangeRestriction;
import org.semanticweb.owl.model.OWLDataSomeRestriction;
import org.semanticweb.owl.model.OWLDataSubPropertyAxiom;
import org.semanticweb.owl.model.OWLDataType;
import org.semanticweb.owl.model.OWLDataValueRestriction;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owl.model.OWLDisjointClassesAxiom;
import org.semanticweb.owl.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointUnionAxiom;
import org.semanticweb.owl.model.OWLEntityAnnotationAxiom;
import org.semanticweb.owl.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owl.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owl.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLImportsDeclaration;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLLogicalAxiom;
import org.semanticweb.owl.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectAllRestriction;
import org.semanticweb.owl.model.OWLObjectAnnotation;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectOneOf;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyChainSubPropertyAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLObjectPropertyInverse;
import org.semanticweb.owl.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLObjectSelfRestriction;
import org.semanticweb.owl.model.OWLObjectSomeRestriction;
import org.semanticweb.owl.model.OWLObjectSubPropertyAxiom;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.model.OWLObjectValueRestriction;
import org.semanticweb.owl.model.OWLObjectVisitor;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;
import org.semanticweb.owl.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLSameIndividualsAxiom;
import org.semanticweb.owl.model.OWLSubClassAxiom;
import org.semanticweb.owl.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLTypedConstant;
import org.semanticweb.owl.model.OWLUntypedConstant;
import org.semanticweb.owl.model.SWRLAtomConstantObject;
import org.semanticweb.owl.model.SWRLAtomDVariable;
import org.semanticweb.owl.model.SWRLAtomIVariable;
import org.semanticweb.owl.model.SWRLAtomIndividualObject;
import org.semanticweb.owl.model.SWRLBuiltInAtom;
import org.semanticweb.owl.model.SWRLClassAtom;
import org.semanticweb.owl.model.SWRLDataRangeAtom;
import org.semanticweb.owl.model.SWRLDataValuedPropertyAtom;
import org.semanticweb.owl.model.SWRLDifferentFromAtom;
import org.semanticweb.owl.model.SWRLObjectPropertyAtom;
import org.semanticweb.owl.model.SWRLRule;
import org.semanticweb.owl.model.SWRLSameAsAtom;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Connective;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.PredicateEquals;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Quantifier;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;
import pt.uc.dei.cms.incerto.utils.MarkovLogicUtils;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;



/**
 * OWLAPI Visitor for Formulas (i.e., axioms).<br>
 * Transforms logical axioms in FOL formulas.
 * @author Pedro Oliveira
 *
 */
public class visitorOWLAPI implements OWLObjectVisitor {

	private OWLOntology ontology;
	private MLN kb;
	private URI probAnnotationURI;
	private URI weightAnnotationURI;
	private Formula f;
	private boolean valid;
	private boolean typedVariables;

	public visitorOWLAPI(OWLOntology ontology) {
		this.ontology = ontology;
		this.kb = null;
		this.probAnnotationURI = URI.create(IncertoSettings.getInstance().PROB_URI);
		this.weightAnnotationURI = URI.create(IncertoSettings.getInstance().WEIGHT_URI);
		this.valid = false;
		this.typedVariables = IncertoSettings.getInstance().TYPED_VARIABLES;
	}

	/**
	 * Create MLN from Ontologies logical axioms.
	 * @return
	 */
	public MLN addLogicalAxiomsToMLN(MLN kb) {
		this.kb = kb;
		for(OWLLogicalAxiom axiom: ontology.getLogicalAxioms())
			axiom.accept(this);
		return kb;
	}

	public Double getUncertaintyAnnotation(OWLAxiom axiom)
	{
		for(OWLAxiomAnnotationAxiom annotation: axiom.getAnnotationAxioms(ontology))
		{
			if(annotation.getAnnotation().isAnnotationByConstant())
			{		
				if((annotation.getAnnotation().getAnnotationURI().compareTo(weightAnnotationURI) == 0))
				{
					try
					{
						Double weight = Double.valueOf(annotation.getAnnotation().getAnnotationValueAsConstant().getLiteral());
						return weight;
					}catch(NumberFormatException nfe)
					{
						LoggerUtils.addWarning("Invalid Weight "+annotation.getAnnotation().getAnnotationValueAsConstant().getLiteral(),nfe);
						break;
					}
				}
				else if((annotation.getAnnotation().getAnnotationURI().compareTo(probAnnotationURI) == 0))
				{
					try
					{
						Double prob = Double.valueOf(annotation.getAnnotation().getAnnotationValueAsConstant().getLiteral());
						return prob;
					}catch(NumberFormatException nfe)
					{
						LoggerUtils.addWarning("Invalid Probability "+annotation.getAnnotation().getAnnotationValueAsConstant().getLiteral(),nfe);
						break;
					}
				}

			}
		}
		return null;
	}

	
	public void visit(OWLOntology arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLSubClassAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getSubClass().accept(this);
		f.addConditional();
		arg0.getSuperClass().accept(this);
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid();
	}

	
	public void visit(OWLNegativeObjectPropertyAssertionAxiom arg0) {
		//Evidence
	}

	
	public void visit(OWLAntiSymmetricObjectPropertyAxiom arg0) {

		valid = true;
		f = new Formula();
		arg0.getProperty().accept(this);
		f.addBiconditional();
		f.addNot();
		f.increaseVariable();
		f.revertIncreaseVariable();
		arg0.getProperty().accept(this);
		f.revertIncreaseVariable();
		f.decreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 
	}

	
	public void visit(OWLReflexiveObjectPropertyAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getProperty().accept(this);
		f.addConditional();
		f.setIncreaseVariable(0);
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLDisjointClassesAxiom arg0) {
		ArrayList<OWLDescription> descriptions = new ArrayList<OWLDescription>(arg0.getDescriptions());
		ArrayList<Formula> formulas = new ArrayList<Formula>(descriptions.size()*2);
		Double prob = getUncertaintyAnnotation(arg0);
		valid = true;
		for(int i=0; i< descriptions.size()-1 && valid; i++)
		{
			for(int j=i+1; j< descriptions.size()&& valid; j++)
			{
				valid = true;
				f = new Formula();
				descriptions.get(i).accept(this);
				f.addDisjunction();
				descriptions.get(j).accept(this);
				f.setWeight(prob);
				if(valid)
					formulas.add(f);
			}
		}
		if(valid)
		{
			for(Formula f: formulas)
				kb.addFormula(f);
		}
		else
			kb.setAsInvalid();
	}

	
	public void visit(OWLDataPropertyDomainAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLImportsDeclaration arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLAxiomAnnotationAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectPropertyDomainAxiom arg0) {

		if(!typedVariables)
		{
			valid = true;
			f = new Formula();
			arg0.getProperty().accept(this);
			f.addConditional();
			arg0.getDomain().accept(this);
			f.setWeight(getUncertaintyAnnotation(arg0));
			if(valid)
				kb.addFormula(f);
			else
				kb.setAsInvalid(); 
		}
		else
		{
			Predicate propertyname = new Predicate(arg0.getProperty().toString());
			Predicate domainname = new Predicate(arg0.getDomain().toString());
			//TODO: Improve performance
			for(Predicate p: kb.getDeclarations())
			{
				if(p.equals(propertyname) || p.equals(domainname))
					p.getTerms().set(0, new Constant(domainname.getName()));
			}
		}

	}

	
	public void visit(OWLEquivalentObjectPropertiesAxiom arg0) {
		ArrayList<OWLObjectPropertyExpression> properties = new ArrayList<OWLObjectPropertyExpression>(arg0.getProperties());
		ArrayList<Formula> formulas = new ArrayList<Formula>(properties.size());
		Double prob = getUncertaintyAnnotation(arg0);
		valid = true;
		for(int i=0; i< properties.size()-1 && valid; i++)
		{
			valid = true;
			f = new Formula();
			properties.get(i).accept(this);
			f.addBiconditional();
			properties.get(i+1).accept(this);
			f.setWeight(prob);
			if(valid)
				formulas.add(f);
		}
		if(valid)
		{
			for(Formula f: formulas)
				kb.addFormula(f);
		}
		else
			kb.setAsInvalid();

	}

	
	public void visit(OWLNegativeDataPropertyAssertionAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDifferentIndividualsAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointDataPropertiesAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointObjectPropertiesAxiom arg0) {
		ArrayList<OWLObjectPropertyExpression> properties = new ArrayList<OWLObjectPropertyExpression>(arg0.getProperties());
		ArrayList<Formula> formulas = new ArrayList<Formula>(properties.size()*2);
		Double prob = getUncertaintyAnnotation(arg0);
		valid = true;
		for(int i=0; i< properties.size()-1 && valid; i++)
		{
			for(int j=i+1; j< properties.size()&& valid; j++)
			{
				valid = true;
				f = new Formula();
				properties.get(i).accept(this);
				f.addDisjunction();
				properties.get(j).accept(this);
				f.setWeight(prob);
				if(valid)
					formulas.add(f);
			}
		}
		if(valid)
		{
			for(Formula f: formulas)
				kb.addFormula(f);
		}
		else
			kb.setAsInvalid();

	}

	
	public void visit(OWLObjectPropertyRangeAxiom arg0) {
		if(!typedVariables)
		{
			valid = true;
			f = new Formula();
			arg0.getProperty().accept(this);
			f.addConditional();
			f.increaseVariable();
			arg0.getRange().accept(this);
			f.decreaseVariable();
			f.setWeight(getUncertaintyAnnotation(arg0));
			if(valid)
				kb.addFormula(f);
			else
				kb.setAsInvalid(); 
		}
		else
		{
			Predicate propertyname = new Predicate(arg0.getProperty().toString());
			Predicate domainname = new Predicate(arg0.getRange().toString());
			//TODO: Improve performance
			for(Predicate p: kb.getDeclarations())
			{
				if(p.equals(propertyname))
					p.getTerms().set(1, new Constant(domainname.getName()));
				else if (p.equals(domainname))
					p.getTerms().set(0, new Constant(domainname.getName()));
			}
		}
	}

	
	public void visit(OWLObjectPropertyAssertionAxiom arg0) {
		// Evidence

	}

	
	public void visit(OWLFunctionalObjectPropertyAxiom arg0) {
		valid = true;
		Variable v1, v2;
		f = new Formula();
		v1 = f.getNextVariable();
		arg0.getProperty().accept(this);
		f.addAnd();
		f.setIncreaseVariable(2);
		v2 = f.getNextVariable();
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.addConditional();
		f.openBrackets();
		f.addElement(new PredicateEquals(v1,v2));
		f.closeBrackets();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLObjectSubPropertyAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getSubProperty().accept(this);
		f.addConditional();
		arg0.getSuperProperty().accept(this);
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLDisjointUnionAxiom arg0) {


	}

	
	public void visit(OWLDeclarationAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLEntityAnnotationAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLOntologyAnnotationAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLSymmetricObjectPropertyAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getProperty().accept(this);
		f.addBiconditional();
		f.increaseVariable();
		f.revertIncreaseVariable();
		arg0.getProperty().accept(this);
		f.revertIncreaseVariable();
		f.decreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLDataPropertyRangeAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLFunctionalDataPropertyAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLEquivalentDataPropertiesAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLClassAssertionAxiom arg0) {
		// Evidence

	}

	
	public void visit(OWLEquivalentClassesAxiom arg0) {
		ArrayList<OWLDescription> descriptions = new ArrayList<OWLDescription>(arg0.getDescriptions());
		ArrayList<Formula> formulas = new ArrayList<Formula>(descriptions.size());
		Double prob = getUncertaintyAnnotation(arg0);
		valid = true;
		for(int i=0; i< descriptions.size()-1 && valid; i++)
		{
			valid = true;
			f = new Formula();
			descriptions.get(i).accept(this);
			f.addBiconditional();
			descriptions.get(i+1).accept(this);
			f.setWeight(prob);
			if(valid)
				formulas.add(f);
		}
		if(valid)
		{
			for(Formula f: formulas)
				kb.addFormula(f);
		}
		else
			kb.setAsInvalid();
	}

	
	public void visit(OWLDataPropertyAssertionAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLTransitiveObjectPropertyAxiom arg0) {	
		valid = true;
		f = new Formula();
		arg0.getProperty().accept(this);
		f.addAnd();
		f.increaseVariable();
		arg0.getProperty().accept(this);
		f.addConditional();
		f.decreaseVariable();
		f.setIncreaseVariable(2);
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 


	}

	
	public void visit(OWLIrreflexiveObjectPropertyAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getProperty().accept(this);
		f.addConditional();
		f.addNot();
		f.setIncreaseVariable(0);
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLDataSubPropertyAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLInverseFunctionalObjectPropertyAxiom arg0) {
		valid = true;
		Variable v1, v2;
		f = new Formula();
		v1 = f.getNextVariable();
		f.increaseVariable();
		f.revertIncreaseVariable();
		arg0.getProperty().accept(this);
		f.addAnd();
		f.revertIncreaseVariable();
		v2 = f.getNextVariable();
		f.increaseVariable();
		f.setIncreaseVariable(-2);
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.decreaseVariable();
		f.decreaseVariable();
		f.addConditional();
		f.openBrackets();
		f.addElement(new PredicateEquals(v1,v2));
		f.closeBrackets();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 

	}

	
	public void visit(OWLSameIndividualsAxiom arg0) {
		/*
		ArrayList<OWLIndividual> individuals = new ArrayList<OWLIndividual>(arg0.getIndividuals());
		ArrayList<Formula> formulas = new ArrayList<Formula>(individuals.size());
		Double prob = getProbabilityAnnotation(arg0);
		valid = true;
		for(int i=0; i< individuals.size()-1 && valid; i++)
		{
			valid = true;
			f = new Formula();
			//TODO: 
			individuals.get(i).toString();
			f.addBiconditional();
			individuals.get(i+1).toString();
			f.setWeight(prob);
			if(valid)
				formulas.add(f);
		}
		if(valid)
		{
			for(Formula f: formulas)
				kb.addFormula(f);
		}
		else
			kb.setAsInvalid();
		 */
	}

	
	public void visit(OWLObjectPropertyChainSubPropertyAxiom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLInverseObjectPropertiesAxiom arg0) {
		valid = true;
		f = new Formula();
		arg0.getFirstProperty().accept(this);
		f.addBiconditional();
		f.increaseVariable();
		f.revertIncreaseVariable();
		arg0.getSecondProperty().accept(this);
		f.revertIncreaseVariable();
		f.decreaseVariable();
		f.setWeight(getUncertaintyAnnotation(arg0));
		if(valid)
			kb.addFormula(f);
		else
			kb.setAsInvalid(); 
	}

	
	public void visit(SWRLRule arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLClass arg0) {
		f.addElement(new Predicate(arg0.toString(),f.getCurrentVariable()));
	}

	
	public void visit(OWLObjectIntersectionOf arg0) {
		f.openBrackets();
		for(OWLDescription desc: arg0.getOperands())
		{
			desc.accept(this);
			f.addAnd();
		}
		f.removeLastElement();
		f.closeBrackets();
	}

	
	public void visit(OWLObjectUnionOf arg0) {
		f.openBrackets();
		for(OWLDescription desc: arg0.getOperands())
		{
			desc.accept(this);
			f.addOr();
		}
		f.removeLastElement();
		f.closeBrackets();

	}

	
	public void visit(OWLObjectComplementOf arg0) {
		f.openBrackets();
		f.addNot();
		arg0.getOperand().accept(this);		
		f.closeBrackets();
	}

	
	public void visit(OWLObjectSomeRestriction arg0) {
		f.openBrackets();
		Quantifier existential = new Quantifier(Connective.QUANTIFIER_EXISTENTIAL);
		f.addElement(existential);
		arg0.getProperty().accept(this);
		f.addAnd();
		f.increaseVariable();
		existential.addVariable(f.getCurrentVariable());
		arg0.getFiller().accept(this);
		f.decreaseVariable();
		f.closeBrackets();
	}

	
	public void visit(OWLObjectAllRestriction arg0) {

		f.openBrackets();
		arg0.getProperty().accept(this);
		f.addConditional();
		f.increaseVariable();
		arg0.getFiller().accept(this);
		f.decreaseVariable();
		f.closeBrackets();
	}

	
	public void visit(OWLObjectValueRestriction arg0) {
		f.openBrackets();
		f.addElement(new Predicate(arg0.getProperty().toString(), f.getCurrentVariable(), new Constant(arg0.getValue().toString())));
		f.closeBrackets();
	}

	
	public void visit(OWLObjectMinCardinalityRestriction arg0) {
		ArrayList<Variable> variables = new ArrayList<Variable>(arg0.getCardinality());
		f.openBrackets();

		Quantifier existential = new Quantifier(Connective.QUANTIFIER_EXISTENTIAL);
		f.addElement(existential);

		f.openBrackets();
		for(int i = 0; i<arg0.getCardinality(); i++)
		{
			f.openBrackets();
			f.setIncreaseVariable(i+1);
			existential.addVariable(f.getNextVariable());
			variables.add(f.getNextVariable());
			arg0.getProperty().accept(this);
			f.addAnd();
			f.increaseVariable();
			arg0.getFiller().accept(this);
			f.decreaseVariable();
			f.resetIncreaseVariable();
			f.closeBrackets();
			if(i!=arg0.getCardinality()-1)
				f.addAnd();			
		}
		f.closeBrackets();
		f.addAnd();
		f.openBrackets();
		for(int i=0; i<variables.size()-1; i++)
		{
			for(int j=i+1; j<variables.size(); j++)
			{
				f.addNot();
				f.openBrackets();
				f.addElement(new PredicateEquals(variables.get(i),variables.get(j)));
				f.closeBrackets();
				if(i<variables.size()-2)
					f.addAnd();
			}

		}
		f.closeBrackets();

		f.closeBrackets();
	}

	
	public void visit(OWLObjectExactCardinalityRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLObjectMaxCardinalityRestriction arg0) {
		ArrayList<Variable> variables = new ArrayList<Variable>(arg0.getCardinality());
		f.openBrackets();

		f.openBrackets();
		for(int i = 0; i<=arg0.getCardinality(); i++)
		{
			f.openBrackets();
			f.setIncreaseVariable(i+1);
			variables.add(f.getNextVariable());
			arg0.getProperty().accept(this);
			f.addAnd();
			f.increaseVariable();
			arg0.getFiller().accept(this);
			f.decreaseVariable();
			f.resetIncreaseVariable();
			f.closeBrackets();
			if(i!=arg0.getCardinality())
				f.addAnd();			
		}
		f.closeBrackets();
		f.addConditional();
		f.openBrackets();
		for(int i=0; i<variables.size()-1; i++)
		{
			for(int j=i+1; j<variables.size(); j++)
			{
				f.openBrackets();
				f.addElement(new PredicateEquals(variables.get(i),variables.get(j)));
				f.closeBrackets();
				if(i<variables.size()-2)
					f.addAnd();
			}

		}
		f.closeBrackets();

		f.closeBrackets();
	}

	
	public void visit(OWLObjectSelfRestriction arg0) {
		f.openBrackets();
		f.setIncreaseVariable(0);
		arg0.getProperty().accept(this);
		f.resetIncreaseVariable();
		f.closeBrackets();
	}

	
	public void visit(OWLObjectOneOf arg0) {
		f.openBrackets();
		for(OWLIndividual i: arg0.getIndividuals())
		{
			f.addElement(new PredicateEquals(f.getCurrentVariable(), new Constant(i.toString())));
			f.addOr();
		}
		f.removeLastElement();
		f.closeBrackets();
	}

	
	public void visit(OWLDataSomeRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataAllRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataValueRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataMinCardinalityRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataExactCardinalityRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataMaxCardinalityRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataType arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataComplementOf arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataOneOf arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataRangeRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLTypedConstant arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLUntypedConstant arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLDataRangeFacetRestriction arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLObjectProperty arg0) {
		f.addElement(new Predicate(arg0.toString(),f.getCurrentVariable(),f.getNextVariable()));
	}

	
	public void visit(OWLObjectPropertyInverse arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataProperty arg0) {
		// TODO Auto-generated method stub
		valid = false;
	}

	
	public void visit(OWLIndividual arg0) {

	}

	
	public void visit(OWLObjectAnnotation arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLConstantAnnotation arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLClassAtom arg0) {
		// TODO Auto-generated method stub
	}

	
	public void visit(SWRLDataRangeAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLObjectPropertyAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLDataValuedPropertyAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLBuiltInAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomDVariable arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomIVariable arg0) {
		// TODO Auto-generated method stub
	}

	
	public void visit(SWRLAtomIndividualObject arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomConstantObject arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLSameAsAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLDifferentFromAtom arg0) {
		// TODO Auto-generated method stub

	}

}
