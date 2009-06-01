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

import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
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
import org.semanticweb.owl.model.OWLIndividualAxiom;
import org.semanticweb.owl.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLIrreflexiveObjectPropertyAxiom;
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
import org.semanticweb.owl.model.OWLPropertyAxiom;
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

import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.model.MLN;


/**
 * OWLAPI Visitor for Evidences (i.e., ontology individuals).<br>
 * Transforms class and property assertions in ground atoms.
 * @author Pedro Oliveira
 *
 */
public class EvidencesOWLAPIVisitor implements OWLObjectVisitor {

	private OWLOntology ontology;
	private MLN kb;
	private Formula f;
	private Constant individual;
	private Constant individual2;

	public EvidencesOWLAPIVisitor(OWLOntology ontology) {
		this.ontology = ontology;
		this.kb = null;
	}

	/**
	 * Add evidences (i.e., ontology individuals) to the MLN
	 * @param kb
	 * @return
	 */
	public MLN addEvidencesToMLN(MLN kb) {
		this.kb = kb;
		for(OWLIndividualAxiom axiom: ontology.getIndividualAxioms())
			axiom.accept(this);
		for(OWLPropertyAxiom axiom: ontology.getObjectPropertyAxioms())
			axiom.accept(this);
		return kb;
	}

	
	public void visit(OWLOntology ontology) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLSubClassAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		f = new Formula();		
		individual = new Constant(axiom.getSubject().toString());
		individual2 = new Constant(axiom.getObject().toString());
		f.addNot();
		axiom.getProperty().accept(this);
		kb.addEvidence(f);
	}

	
	public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointClassesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLImportsDeclaration axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLAxiomAnnotationAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDifferentIndividualsAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		f = new Formula();		
		individual = new Constant(axiom.getSubject().toString());
		individual2 = new Constant(axiom.getObject().toString());
		axiom.getProperty().accept(this);
		kb.addEvidence(f);
	}

	
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectSubPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDisjointUnionAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDeclarationAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLEntityAnnotationAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLOntologyAnnotationAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLClassAssertionAxiom axiom) {
		f = new Formula();
		individual = new Constant(axiom.getIndividual().toString());
		axiom.getDescription().accept(this);
		kb.addEvidence(f);		
	}

	
	public void visit(OWLEquivalentClassesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataSubPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLSameIndividualsAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLRule rule) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLClass arg0) {
		f.addElement(new Predicate(arg0.toString(),individual));
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

	
	public void visit(OWLObjectSomeRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectAllRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectValueRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectMinCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectExactCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectMaxCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectSelfRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectOneOf desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataSomeRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataAllRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataValueRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataMinCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataExactCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataMaxCardinalityRestriction desc) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataType node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataComplementOf node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataOneOf node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataRangeRestriction node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLTypedConstant node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLUntypedConstant node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataRangeFacetRestriction node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectProperty property) {	
		f.addElement(new Predicate(property.toString(),individual,individual2));
	}

	
	public void visit(OWLObjectPropertyInverse property) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLDataProperty property) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLIndividual individual) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLObjectAnnotation annotation) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLConstantAnnotation annotation) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLClassAtom arg0) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLDataRangeAtom node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLObjectPropertyAtom node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLDataValuedPropertyAtom node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLBuiltInAtom node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomDVariable node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomIVariable node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomIndividualObject node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLAtomConstantObject node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLSameAsAtom node) {
		// TODO Auto-generated method stub

	}

	
	public void visit(SWRLDifferentFromAtom node) {
		// TODO Auto-generated method stub

	}

}
