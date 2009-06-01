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

package pt.uc.dei.cms.incerto.utils;

import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLConstant;
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
import org.semanticweb.owl.model.OWLDataPropertyExpression;
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

public class OWLStatisticsVisitor implements OWLObjectVisitor {

	private OWLStatistics statistics;

	public OWLStatistics getOWLStatistics(OWLOntology ontology, String filename)
	{
		statistics = new OWLStatistics(ontology,filename);
		for(OWLAxiom axiom: ontology.getAxioms())
			axiom.accept(this);
		
		statistics.setAnnotations(ontology.getAnnotationAxioms().size());
		statistics.setClasses(ontology.getReferencedClasses().size());
		statistics.setDataProperties(ontology.getReferencedDataProperties().size());
		statistics.setEntities(ontology.getReferencedEntities().size());
		statistics.setIndividuals(ontology.getReferencedIndividuals().size());
		statistics.setObjectProperties(ontology.getReferencedObjectProperties().size());
		
		return statistics;
	}

	
	public void visit(OWLOntology ontology) {
		statistics.increaseOntologyAxiomCount();
	}

	
	public void visit(OWLSubClassAxiom axiom) {
		statistics.increaseSubClassAxiomCount();
		axiom.getSubClass().accept(this);
		axiom.getSuperClass().accept(this);
	}

	
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		statistics.increaseNegativeObjectPropertyAssertionAxiomCount();
		axiom.getObject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
		statistics.increaseAntiSymmetricObjectPropertyAxiomCountCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		statistics.increaseReflexiveObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLDisjointClassesAxiom axiom) {
		statistics.increaseDisjointClassesAxiomCount();
		for(OWLDescription d: axiom.getDescriptions())
			d.accept(this);
	}

	
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		statistics.increaseDataPropertyDomainAxiomCount();
		axiom.getDomain().accept(this);
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLImportsDeclaration axiom) {
		statistics.increaseImportsDeclarationCount();
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLAxiomAnnotationAxiom axiom) {
		statistics.increaseAxiomAnnotationAxiomCount();
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		statistics.increaseObjectPropertyDomainAxiomCount();
		axiom.getDomain().accept(this);
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		statistics.increaseEquivalentObjectPropertiesAxiomCount();
		for(OWLObjectPropertyExpression d: axiom.getProperties())
			d.accept(this);
	}

	
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		statistics.increaseNegativeDataPropertyAssertionAxiomCount();
		axiom.getObject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLDifferentIndividualsAxiom axiom) {
		statistics.increaseDifferentIndividualsAxiomCount();
		for(OWLIndividual d: axiom.getIndividuals())
			d.accept(this);
	}

	
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		statistics.increaseDisjointDataPropertiesAxiomCount();
		for(OWLDataPropertyExpression d: axiom.getProperties())
			d.accept(this);
	}

	
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		statistics.increaseDisjointObjectPropertiesAxiomCount();
		for(OWLObjectPropertyExpression d: axiom.getProperties())
			d.accept(this);
	}

	
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		statistics.increaseObjectPropertyRangeAxiomCount();
		axiom.getProperty().accept(this);
		axiom.getRange().accept(this);
	}

	
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		statistics.increaseObjectPropertyAssertionAxiomCount();
		axiom.getObject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		statistics.increaseFunctionalObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLObjectSubPropertyAxiom axiom) {
		statistics.increaseObjectSubPropertyAxiomCount();
		axiom.getSubProperty().accept(this);
		axiom.getSuperProperty().accept(this);
	}

	
	public void visit(OWLDisjointUnionAxiom axiom) {
		statistics.increaseDisjointUnionAxiomCount();
		for(OWLDescription d: axiom.getDescriptions())
			d.accept(this);
	}

	
	public void visit(OWLDeclarationAxiom axiom) {
		statistics.increaseDeclarationAxiomCount();
		axiom.getEntity().accept(this);
	}

	
	public void visit(OWLEntityAnnotationAxiom axiom) {
		statistics.increaseEntityAnnotationAxiomCount();
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLOntologyAnnotationAxiom axiom) {
		statistics.increaseOntologyAnnotationAxiomCount();
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		statistics.increaseSymmetricObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		statistics.increaseDataPropertyRangeAxiomCount();
		axiom.getProperty().accept(this);
		axiom.getRange().accept(this);
	}

	
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		statistics.increaseFunctionalDataPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		statistics.increaseEquivalentDataPropertiesAxiomCount();
		for(OWLDataPropertyExpression d: axiom.getProperties())
			d.accept(this);
	}

	
	public void visit(OWLClassAssertionAxiom axiom) {
		statistics.increaseClassAssertionAxiomCount();
		axiom.getDescription().accept(this);
		axiom.getIndividual().accept(this);
	}

	
	public void visit(OWLEquivalentClassesAxiom axiom) {
		statistics.increaseEquivalentClassesAxiomCount();
		for(OWLDescription d: axiom.getDescriptions())
			d.accept(this);
	}

	
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		statistics.increaseDataPropertyAssertionAxiomCount();
		axiom.getObject().accept(this);
		axiom.getProperty().accept(this);
		axiom.getSubject().accept(this);
	}

	
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		statistics.increaseTransitiveObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		statistics.increaseIrreflexiveObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLDataSubPropertyAxiom axiom) {
		statistics.increaseDataSubPropertyAxiomCount();
		axiom.getSubProperty().accept(this);
		axiom.getSuperProperty().accept(this);
	}

	
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		statistics.increaseInverseFunctionalObjectPropertyAxiomCount();
		axiom.getProperty().accept(this);
	}

	
	public void visit(OWLSameIndividualsAxiom axiom) {
		statistics.increaseSameIndividualsAxiomCount();
		for(OWLIndividual d: axiom.getIndividuals())
			d.accept(this);
	}

	
	public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
		statistics.increaseObjectPropertyChainSubPropertyAxiomCount();
		axiom.getSuperProperty().accept(this);
		for(OWLObjectPropertyExpression d: axiom.getPropertyChain())
			d.accept(this);
	}

	
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		statistics.increaseInverseObjectPropertiesAxiomCount();
		axiom.getFirstProperty().accept(this);
		axiom.getSecondProperty().accept(this);
	}

	
	public void visit(SWRLRule rule) {
		// TODO Auto-generated method stub

	}

	
	public void visit(OWLClass arg0) {
		statistics.increaseClassAxiomCount();
	}

	
	public void visit(OWLObjectIntersectionOf desc) {
		statistics.increaseObjectIntersectionOfCount();
		for(OWLDescription d: desc.getOperands())
			d.accept(this);
	}

	
	public void visit(OWLObjectUnionOf desc) {
		statistics.increaseObjectUnionOfCount();
		for(OWLDescription d: desc.getOperands())
			d.accept(this);

	}

	
	public void visit(OWLObjectComplementOf desc) {
		statistics.increaseObjectComplementOfCount();
		desc.getOperand().accept(this);
	}

	
	public void visit(OWLObjectSomeRestriction desc) {
		statistics.increaseObjectSomeRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectAllRestriction desc) {
		statistics.increaseObjectAllRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectValueRestriction desc) {
		statistics.increaseObjectValueRestrictionCount();
		desc.getProperty().accept(this);
		desc.getValue().accept(this);
	}

	
	public void visit(OWLObjectMinCardinalityRestriction desc) {
		statistics.increaseObjectMinCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectExactCardinalityRestriction desc) {
		statistics.increaseObjectExactCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectMaxCardinalityRestriction desc) {
		statistics.increaseObjectMaxCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectSelfRestriction desc) {
		statistics.increaseObjectSelfRestrictionCount();
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLObjectOneOf desc) {
		statistics.increaseObjectOneOfCount();
		for(OWLIndividual d: desc.getIndividuals())
			d.accept(this);
	}

	
	public void visit(OWLDataSomeRestriction desc) {
		statistics.increaseDataSomeRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLDataAllRestriction desc) {
		statistics.increaseDataAllRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLDataValueRestriction desc) {
		statistics.increaseDataValueRestrictionCount();
		desc.getProperty().accept(this);
		desc.getValue().accept(this);
	}

	
	public void visit(OWLDataMinCardinalityRestriction desc) {
		statistics.increaseDataMinCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLDataExactCardinalityRestriction desc) {
		statistics.increaseDataExactCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLDataMaxCardinalityRestriction desc) {
		statistics.increaseDataMaxCardinalityRestrictionCount();
		desc.getFiller().accept(this);
		desc.getProperty().accept(this);
	}

	
	public void visit(OWLDataType node) {
		statistics.increaseDataTypeCount();
	}

	
	public void visit(OWLDataComplementOf node) {
		statistics.increaseDataComplementOfCount();
		node.getDataRange().accept(this);
	}

	
	public void visit(OWLDataOneOf node) {
		statistics.increaseDataOneOfCount();
		for(OWLConstant d: node.getValues())
			d.accept(this);
	}

	
	public void visit(OWLDataRangeRestriction node) {
		statistics.increaseDataRangeRestrictionCount();
		node.getDataRange().accept(this);
		for(OWLDataRangeFacetRestriction d: node.getFacetRestrictions())
			d.accept(this);
	}

	
	public void visit(OWLTypedConstant node) {
		statistics.increaseTypedConstantCount();
		node.getDataType().accept(this);
	}

	
	public void visit(OWLUntypedConstant node) {
		statistics.increaseUntypedConstantCount();
	}

	
	public void visit(OWLDataRangeFacetRestriction node) {
		statistics.increaseDataRangeFacetRestrictionCount();
		node.getFacetValue().accept(this);
	}

	
	public void visit(OWLObjectProperty property) {
		statistics.increaseObjectPropertyCount();
	}

	
	public void visit(OWLObjectPropertyInverse property) {
		statistics.increaseObjectPropertyInverseCount();
	}

	
	public void visit(OWLDataProperty property) {
		statistics.increaseDataPropertyCount();
	}

	
	public void visit(OWLIndividual individual) {
		statistics.increaseIndividualCount();
	}

	
	public void visit(OWLObjectAnnotation annotation) {
		statistics.increaseObjectAnnotationCount();
		annotation.getAnnotationValue().accept(this);
	}

	
	public void visit(OWLConstantAnnotation annotation) {
		statistics.increaseConstantAnnotationCount();
		annotation.getAnnotationValue().accept(this);
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
