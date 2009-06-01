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

import org.semanticweb.owl.model.OWLOntology;


public class OWLStatistics {
	
	private int OntologyAxiom;
	private int subClassAxiom;
	private int AntiSymmetricObjectPropertyAxiom;
	private int NegativeObjectPropertyAssertionAxiom;
	private int ReflexiveObjectPropertyAxiom;
	private int DisjointClassesAxiom;
	private int DataPropertyDomainAxiom;
	private int ImportsDeclaration;
	private int AxiomAnnotationAxiom;
	private int ObjectPropertyDomainAxiom;
	private int EquivalentObjectPropertiesAxiom;
	private int NegativeDataPropertyAssertionAxiom;
	private int DifferentIndividualsAxiom;
	private int DisjointDataPropertiesAxiom;
	private int DisjointObjectPropertiesAxiom;
	private int ObjectPropertyRangeAxiom;
	private int ObjectPropertyAssertionAxiom;
	private int FunctionalObjectPropertyAxiom;
	private int ObjectSubPropertyAxiom;
	private int DisjointUnionAxiom;
	private int DeclarationAxiom;
	private int EntityAnnotationAxiom;
	private int OntologyAnnotationAxiom;
	private int SymmetricObjectPropertyAxiom;
	private int DataPropertyRangeAxiom;
	private int FunctionalDataPropertyAxiom;
	private int EquivalentDataPropertiesAxiom;
	private int ClassAssertionAxiom;
	private int EquivalentClassesAxiom;
	private int DataPropertyAssertionAxiom;
	private int TransitiveObjectPropertyAxiom;
	private int IrreflexiveObjectPropertyAxiom;
	private int DataSubPropertyAxiom;
	private int InverseFunctionalObjectPropertyAxiom;
	private int SameIndividualsAxiom;
	private int ObjectPropertyChainSubPropertyAxiom;
	private int InverseObjectPropertiesAxiom;
	private int Class;
	private int ObjectIntersectionOf;
	private int ObjectUnionOf;
	private int ObjectComplementOf;
	private int ObjectSomeRestriction;
	private int ObjectAllRestriction;
	private int ObjectValueRestriction;
	private int ObjectMinCardinalityRestriction;
	private int ObjectExactCardinalityRestriction;
	private int ObjectMaxCardinalityRestriction;
	private int ObjectSelfRestriction;
	private int ObjectOneOf;
	private int DataSomeRestriction;
	private int DataAllRestriction;
	private int DataValueRestriction;
	private int DataMinCardinalityRestriction;
	private int DataExactCardinalityRestriction;
	private int DataMaxCardinalityRestriction;
	private int DataType;
	private int DataComplementOf;
	private int DataOneOf;
	private int DataRangeRestriction;
	private int TypedConstant;
	private int UntypedConstant;
	private int DataRangeFacetRestriction;
	private int ObjectProperty;
	private int ObjectPropertyInverse;
	private int DataProperty;
	private int Individual;
	private int ObjectAnnotation;
	private int ConstantAnnotation;
	
	
	private int Individuals;
	private int ObjectProperties;
	private int DataProperties;
	private int Classes;
	private int Annotations;
	private int Entities;
	
	private OWLOntology ontology;
	private String filename;
	
	public OWLStatistics(OWLOntology ontology, String filename) {
		this.ontology = ontology;
		this.filename = filename;
	}

	/*
	 * INCREASERS 
	 */
	
	public void increaseSubClassAxiomCount() {
		subClassAxiom++;
	}

	public void increaseOntologyAxiomCount() {
		OntologyAxiom++;
	}

	public void increaseAntiSymmetricObjectPropertyAxiomCountCount() {
		AntiSymmetricObjectPropertyAxiom++;
	}

	public void increaseNegativeObjectPropertyAssertionAxiomCount() {
		NegativeObjectPropertyAssertionAxiom++;
	}

	public void increaseReflexiveObjectPropertyAxiomCount() {
		ReflexiveObjectPropertyAxiom++;
	}

	public void increaseDisjointClassesAxiomCount() {
		DisjointClassesAxiom++;
	}

	public void increaseDataPropertyDomainAxiomCount() {
		DataPropertyDomainAxiom++;
	}

	public void increaseImportsDeclarationCount() {
		ImportsDeclaration++;
	}

	public void increaseAxiomAnnotationAxiomCount() {
		AxiomAnnotationAxiom++;
	}

	public void increaseObjectPropertyDomainAxiomCount() {
		ObjectPropertyDomainAxiom++;
	}

	public void increaseEquivalentObjectPropertiesAxiomCount() {
		EquivalentObjectPropertiesAxiom++;
	}

	public void increaseNegativeDataPropertyAssertionAxiomCount() {
		NegativeDataPropertyAssertionAxiom++;
	}

	public void increaseDifferentIndividualsAxiomCount() {
		DifferentIndividualsAxiom++;
	}

	public void increaseDisjointDataPropertiesAxiomCount() {
		DisjointDataPropertiesAxiom++;
	}

	public void increaseDisjointObjectPropertiesAxiomCount() {
		DisjointObjectPropertiesAxiom++;
	}

	public void increaseObjectPropertyRangeAxiomCount() {
		ObjectPropertyRangeAxiom++;
	}

	public void increaseObjectPropertyAssertionAxiomCount() {
		ObjectPropertyAssertionAxiom++;
	}

	public void increaseFunctionalObjectPropertyAxiomCount() {
		FunctionalObjectPropertyAxiom++;
	}

	public void increaseObjectSubPropertyAxiomCount() {
		ObjectSubPropertyAxiom++;
	}

	public void increaseDisjointUnionAxiomCount() {
		DisjointUnionAxiom++;
	}

	public void increaseDeclarationAxiomCount() {
		DeclarationAxiom++;
	}

	public void increaseEntityAnnotationAxiomCount() {
		EntityAnnotationAxiom++;
	}

	public void increaseOntologyAnnotationAxiomCount() {
		OntologyAnnotationAxiom++;
	}

	public void increaseSymmetricObjectPropertyAxiomCount() {
		SymmetricObjectPropertyAxiom++;
	}

	public void increaseDataPropertyRangeAxiomCount() {
		DataPropertyRangeAxiom++;
	}

	public void increaseFunctionalDataPropertyAxiomCount() {
		FunctionalDataPropertyAxiom++;
	}

	public void increaseEquivalentDataPropertiesAxiomCount() {
		EquivalentDataPropertiesAxiom++;
	}

	public void increaseClassAssertionAxiomCount() {
		ClassAssertionAxiom++;
	}

	public void increaseEquivalentClassesAxiomCount() {
		EquivalentClassesAxiom++;
	}

	public void increaseDataPropertyAssertionAxiomCount() {
		DataPropertyAssertionAxiom++;
	}

	public void increaseTransitiveObjectPropertyAxiomCount() {
		TransitiveObjectPropertyAxiom++;
	}

	public void increaseIrreflexiveObjectPropertyAxiomCount() {
		IrreflexiveObjectPropertyAxiom++;
	}

	public void increaseDataSubPropertyAxiomCount() {
		DataSubPropertyAxiom++;
	}

	public void increaseInverseFunctionalObjectPropertyAxiomCount() {
		InverseFunctionalObjectPropertyAxiom++;
	}

	public void increaseSameIndividualsAxiomCount() {
		SameIndividualsAxiom++;
	}

	public void increaseObjectPropertyChainSubPropertyAxiomCount() {
		ObjectPropertyChainSubPropertyAxiom++;
	}

	public void increaseInverseObjectPropertiesAxiomCount() {
		InverseObjectPropertiesAxiom++;
	}

	public void increaseClassAxiomCount() {
		Class++;
	}

	public void increaseObjectIntersectionOfCount() {
		ObjectIntersectionOf++;
	}

	public void increaseObjectUnionOfCount() {
		ObjectUnionOf++;
	}

	public void increaseObjectComplementOfCount() {
		ObjectComplementOf++;
	}

	public void increaseObjectSomeRestrictionCount() {
		ObjectSomeRestriction++;
	}

	public void increaseObjectAllRestrictionCount() {
		ObjectAllRestriction++;
	}

	public void increaseObjectValueRestrictionCount() {
		ObjectValueRestriction++;
	}

	public void increaseObjectMinCardinalityRestrictionCount() {
		ObjectMinCardinalityRestriction++;
	}

	public void increaseObjectExactCardinalityRestrictionCount() {
		ObjectExactCardinalityRestriction++;
	}

	public void increaseObjectMaxCardinalityRestrictionCount() {
		ObjectMaxCardinalityRestriction++;
	}

	public void increaseObjectSelfRestrictionCount() {
		ObjectSelfRestriction++;
	}

	public void increaseObjectOneOfCount() {
		ObjectOneOf++;
	}

	public void increaseDataSomeRestrictionCount() {
		DataSomeRestriction++;
	}

	public void increaseDataAllRestrictionCount() {
		DataAllRestriction++;
	}

	public void increaseDataValueRestrictionCount() {
		DataValueRestriction++;
	}

	public void increaseDataMinCardinalityRestrictionCount() {
		DataMinCardinalityRestriction++;
	}

	public void increaseDataExactCardinalityRestrictionCount() {
		DataExactCardinalityRestriction++;
	}

	public void increaseDataMaxCardinalityRestrictionCount() {
		DataMaxCardinalityRestriction++;
	}

	public void increaseDataTypeCount() {
		DataType++;
	}

	public void increaseDataComplementOfCount() {
		DataComplementOf++;
	}

	public void increaseDataOneOfCount() {
		DataOneOf++;
	}

	public void increaseDataRangeRestrictionCount() {
		DataRangeRestriction++;
	}

	public void increaseTypedConstantCount() {
		TypedConstant++;
	}

	public void increaseUntypedConstantCount() {
		UntypedConstant++;
	}

	public void increaseDataRangeFacetRestrictionCount() {
		DataRangeFacetRestriction++;
	}

	public void increaseObjectPropertyCount() {
		ObjectProperty++;
	}

	public void increaseObjectPropertyInverseCount() {
		ObjectPropertyInverse++;
	}

	public void increaseDataPropertyCount() {
		DataProperty++;
	}

	public void increaseIndividualCount() {
		Individual++;
	}

	public void increaseObjectAnnotationCount() {
		ObjectAnnotation++;
	}

	public void increaseConstantAnnotationCount() {
		ConstantAnnotation++;
	}

	public void increaseIndividualsCount() {
		Individuals++;
	}

	public void increaseObjectPropertiesCount() {
		ObjectProperties++;
	}

	public void increaseDataPropertiesCount() {
		DataProperties++;
	}

	public void increaseClassesCount() {
		Classes++;
	}

	public void increaseAnnotationsCount() {
		Annotations++;
	}

	public void increaseEntitiesCount() {
		Entities++;
	}

	
	/*
	 * GETTERS 
	 */	

	public int getSubClassAxiomCount() {
		return subClassAxiom;
	}

	public int getOntologyAxiom() {
		return OntologyAxiom;
	}

	public int getAntiSymmetricObjectPropertyAxiomCount() {
		return AntiSymmetricObjectPropertyAxiom;
	}

	public int getNegativeObjectPropertyAssertionAxiomCount() {
		return NegativeObjectPropertyAssertionAxiom;
	}

	public int getReflexiveObjectPropertyAxiomCount() {
		return ReflexiveObjectPropertyAxiom;
	}

	public int getDisjointClassesAxiomCount() {
		return DisjointClassesAxiom;
	}

	public int getDataPropertyDomainAxiomCount() {
		return DataPropertyDomainAxiom;
	}

	public int getImportsDeclarationCount() {
		return ImportsDeclaration;
	}

	public int getAxiomAnnotationAxiomCount() {
		return AxiomAnnotationAxiom;
	}

	public int getObjectPropertyDomainAxiomCount() {
		return ObjectPropertyDomainAxiom;
	}

	public int getEquivalentObjectPropertiesAxiomCount() {
		return EquivalentObjectPropertiesAxiom;
	}

	public int getNegativeDataPropertyAssertionAxiomCount() {
		return NegativeDataPropertyAssertionAxiom;
	}

	public int getDifferentIndividualsAxiomCount() {
		return DifferentIndividualsAxiom;
	}

	public int getDisjointDataPropertiesAxiomCount() {
		return DisjointDataPropertiesAxiom;
	}

	public int getDisjointObjectPropertiesAxiomCount() {
		return DisjointObjectPropertiesAxiom;
	}

	public int getObjectPropertyRangeAxiomCount() {
		return ObjectPropertyRangeAxiom;
	}

	public int getObjectPropertyAssertionAxiomCount() {
		return ObjectPropertyAssertionAxiom;
	}

	public int getFunctionalObjectPropertyAxiomCount() {
		return FunctionalObjectPropertyAxiom;
	}

	public int getObjectSubPropertyAxiomCount() {
		return ObjectSubPropertyAxiom;
	}

	public int getDisjointUnionAxiomCount() {
		return DisjointUnionAxiom;
	}

	public int getDeclarationAxiomCount() {
		return DeclarationAxiom;
	}

	public int getEntityAnnotationAxiomCount() {
		return EntityAnnotationAxiom;
	}

	public int getOntologyAnnotationAxiomCount() {
		return OntologyAnnotationAxiom;
	}

	public int getSymmetricObjectPropertyAxiomCount() {
		return SymmetricObjectPropertyAxiom;
	}

	public int getDataPropertyRangeAxiomCount() {
		return DataPropertyRangeAxiom;
	}

	public int getFunctionalDataPropertyAxiomCount() {
		return FunctionalDataPropertyAxiom;
	}

	public int getEquivalentDataPropertiesAxiomCount() {
		return EquivalentDataPropertiesAxiom;
	}

	public int getClassAssertionAxiomCount() {
		return ClassAssertionAxiom;
	}

	public int getEquivalentClassesAxiomCount() {
		return EquivalentClassesAxiom;
	}

	public int getDataPropertyAssertionAxiomCount() {
		return DataPropertyAssertionAxiom;
	}

	public int getTransitiveObjectPropertyAxiomCount() {
		return TransitiveObjectPropertyAxiom;
	}

	public int getIrreflexiveObjectPropertyAxiomCount() {
		return IrreflexiveObjectPropertyAxiom;
	}

	public int getDataSubPropertyAxiomCount() {
		return DataSubPropertyAxiom;
	}

	public int getInverseFunctionalObjectPropertyAxiomCount() {
		return InverseFunctionalObjectPropertyAxiom;
	}

	public int getSameIndividualsAxiomCount() {
		return SameIndividualsAxiom;
	}

	public int getObjectPropertyChainSubPropertyAxiomCount() {
		return ObjectPropertyChainSubPropertyAxiom;
	}

	public int getInverseObjectPropertiesAxiomCount() {
		return InverseObjectPropertiesAxiom;
	}

	public int getClassAxiomCount() {
		return Class;
	}

	public int getObjectIntersectionOfCount() {
		return ObjectIntersectionOf;
	}

	public int getObjectUnionOfCount() {
		return ObjectUnionOf;
	}

	public int getObjectComplementOfCount() {
		return ObjectComplementOf;
	}

	public int getObjectSomeRestrictionCount() {
		return ObjectSomeRestriction;
	}

	public int getObjectAllRestrictionCount() {
		return ObjectAllRestriction;
	}

	public int getObjectValueRestrictionCount() {
		return ObjectValueRestriction;
	}

	public int getObjectMinCardinalityRestrictionCount() {
		return ObjectMinCardinalityRestriction;
	}

	public int getObjectExactCardinalityRestrictionCount() {
		return ObjectExactCardinalityRestriction;
	}

	public int getObjectMaxCardinalityRestrictionCount() {
		return ObjectMaxCardinalityRestriction;
	}

	public int getObjectSelfRestrictionCount() {
		return ObjectSelfRestriction;
	}

	public int getObjectOneOfCount() {
		return ObjectOneOf;
	}

	public int getDataSomeRestrictionCount() {
		return DataSomeRestriction;
	}

	public int getDataAllRestrictionCount() {
		return DataAllRestriction;
	}

	public int getDataValueRestrictionCount() {
		return DataValueRestriction;
	}

	public int getDataMinCardinalityRestrictionCount() {
		return DataMinCardinalityRestriction;
	}

	public int getDataExactCardinalityRestrictionCount() {
		return DataExactCardinalityRestriction;
	}

	public int getDataMaxCardinalityRestrictionCount() {
		return DataMaxCardinalityRestriction;
	}

	public int getDataTypeCount() {
		return DataType;
	}

	public int getDataComplementOfCount() {
		return DataComplementOf;
	}

	public int getDataOneOfCount() {
		return DataOneOf;
	}

	public int getDataRangeRestrictionCount() {
		return DataRangeRestriction;
	}

	public int getTypedConstantCount() {
		return TypedConstant;
	}

	public int getUntypedConstantCount() {
		return UntypedConstant;
	}

	public int getDataRangeFacetRestrictionCount() {
		return DataRangeFacetRestriction;
	}

	public int getObjectPropertyCount() {
		return ObjectProperty;
	}

	public int getObjectPropertyInverseCount() {
		return ObjectPropertyInverse;
	}

	public int getDataPropertyCount() {
		return DataProperty;
	}

	public int getIndividualCount() {
		return Individual;
	}

	public int getObjectAnnotationCount() {
		return ObjectAnnotation;
	}

	public int getConstantAnnotationCount() {
		return ConstantAnnotation;
	}

	public int getIndividualsCount() {
		return Individuals;
	}

	public int getObjectPropertiesCount() {
		return ObjectProperties;
	}

	public int getDataPropertiesCount() {
		return DataProperties;
	}

	public int getClassesCount() {
		return Classes;
	}

	public int getAnnotationsCount() {
		return Annotations;
	}

	public int getEntitiesCount() {
		return Entities;
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	
	/*
	 * SETTERS
	 */
	

	public void setIndividuals(int individuals) {
		Individuals = individuals;
	}

	public void setObjectProperties(int objectProperties) {
		ObjectProperties = objectProperties;
	}

	public void setDataProperties(int dataProperties) {
		DataProperties = dataProperties;
	}

	public void setClasses(int classes) {
		Classes = classes;
	}

	public void setAnnotations(int annotations) {
		Annotations = annotations;
	}

	public void setEntities(int entities) {
		Entities = entities;
	}
	
	
	public static String header()
	{
		return	
			"FileName"+";"+
			"URI"+";"+
			"Individuals"+";"+
			"ObjectProperties"+";"+
			"DataProperties"+";"+
			"Classes"+";"+
			"Annotations"+";"+
			"Entities"+";"+	
			"OntologyAxiom"+";"+
			"subClassAxiom"+";"+
			"AntiSymmetricObjectPropertyAxiom"+""+";"+
			"NegativeObjectPropertyAssertionAxiom"+";"+
			"ReflexiveObjectPropertyAxiom"+";"+
			"DisjointClassesAxiom"+";"+
			"DataPropertyDomainAxiom"+";"+
			"ImportsDeclaration"+";"+
			"AxiomAnnotationAxiom"+";"+
			"ObjectPropertyDomainAxiom"+";"+
			"EquivalentObjectPropertiesAxiom"+";"+
			"NegativeDataPropertyAssertionAxiom"+";"+
			"DifferentIndividualsAxiom"+";"+
			"DisjointDataPropertiesAxiom"+";"+
			"DisjointObjectPropertiesAxiom"+";"+
			"ObjectPropertyRangeAxiom"+";"+
			"ObjectPropertyAssertionAxiom"+";"+
			"FunctionalObjectPropertyAxiom"+";"+
			"ObjectSubPropertyAxiom"+";"+
			"DisjointUnionAxiom"+";"+
			"DeclarationAxiom"+";"+
			"EntityAnnotationAxiom"+";"+
			"OntologyAnnotationAxiom"+";"+
			"SymmetricObjectPropertyAxiom"+";"+
			"DataPropertyRangeAxiom"+";"+
			"FunctionalDataPropertyAxiom"+";"+
			"EquivalentDataPropertiesAxiom"+";"+
			"ClassAssertionAxiom"+";"+
			"EquivalentClassesAxiom"+";"+
			"DataPropertyAssertionAxiom"+";"+
			"TransitiveObjectPropertyAxiom"+";"+
			"IrreflexiveObjectPropertyAxiom"+";"+
			"DataSubPropertyAxiom"+";"+
			"InverseFunctionalObjectPropertyAxiom"+";"+
			"SameIndividualsAxiom"+";"+
			"ObjectPropertyChainSubPropertyAxiom"+";"+
			"InverseObjectPropertiesAxiom"+";"+
			"Class"+";"+
			"ObjectIntersectionOf"+";"+
			"ObjectUnionOf"+";"+
			"ObjectComplementOf"+";"+
			"ObjectSomeRestriction"+";"+
			"ObjectAllRestriction"+";"+
			"ObjectValueRestriction"+";"+
			"ObjectMinCardinalityRestriction"+";"+
			"ObjectExactCardinalityRestriction"+";"+
			"ObjectMaxCardinalityRestriction"+";"+
			"ObjectSelfRestriction"+";"+
			"ObjectOneOf"+";"+
			"DataSomeRestriction"+";"+
			"DataAllRestriction"+";"+
			"DataValueRestriction"+";"+
			"DataMinCardinalityRestriction"+";"+
			"DataExactCardinalityRestriction"+";"+
			"DataMaxCardinalityRestriction"+";"+
			"DataType"+";"+
			"DataComplementOf"+";"+
			"DataOneOf"+";"+
			"DataRangeRestriction"+";"+
			"TypedConstant"+";"+
			"UntypedConstant"+";"+
			"DataRangeFacetRestriction"+";"+
			"ObjectProperty"+";"+
			"ObjectPropertyInverse"+";"+
			"DataProperty"+";"+
			"Individual"+";"+
			"ObjectAnnotation"+";"+
			"ConstantAnnotation";
	}
	public String toString()
	{
		return
		filename+";"+
		ontology.getURI()+";"+
		Individuals+";"+
		ObjectProperties+";"+
		DataProperties+";"+
		Classes+";"+
		Annotations+";"+
		Entities+";"+		
		OntologyAxiom+";"+
		subClassAxiom+";"+
		AntiSymmetricObjectPropertyAxiom+";"+
		NegativeObjectPropertyAssertionAxiom+";"+
		ReflexiveObjectPropertyAxiom+";"+
		DisjointClassesAxiom+";"+
		DataPropertyDomainAxiom+";"+
		ImportsDeclaration+";"+
		AxiomAnnotationAxiom+";"+
		ObjectPropertyDomainAxiom+";"+
		EquivalentObjectPropertiesAxiom+";"+
		NegativeDataPropertyAssertionAxiom+";"+
		DifferentIndividualsAxiom+";"+
		DisjointDataPropertiesAxiom+";"+
		DisjointObjectPropertiesAxiom+";"+
		ObjectPropertyRangeAxiom+";"+
		ObjectPropertyAssertionAxiom+";"+
		FunctionalObjectPropertyAxiom+";"+
		ObjectSubPropertyAxiom+";"+
		DisjointUnionAxiom+";"+
		DeclarationAxiom+";"+
		EntityAnnotationAxiom+";"+
		OntologyAnnotationAxiom+";"+
		SymmetricObjectPropertyAxiom+";"+
		DataPropertyRangeAxiom+";"+
		FunctionalDataPropertyAxiom+";"+
		EquivalentDataPropertiesAxiom+";"+
		ClassAssertionAxiom+";"+
		EquivalentClassesAxiom+";"+
		DataPropertyAssertionAxiom+";"+
		TransitiveObjectPropertyAxiom+";"+
		IrreflexiveObjectPropertyAxiom+";"+
		DataSubPropertyAxiom+";"+
		InverseFunctionalObjectPropertyAxiom+";"+
		SameIndividualsAxiom+";"+
		ObjectPropertyChainSubPropertyAxiom+";"+
		InverseObjectPropertiesAxiom+";"+
		Class+";"+
		ObjectIntersectionOf+";"+
		ObjectUnionOf+";"+
		ObjectComplementOf+";"+
		ObjectSomeRestriction+";"+
		ObjectAllRestriction+";"+
		ObjectValueRestriction+";"+
		ObjectMinCardinalityRestriction+";"+
		ObjectExactCardinalityRestriction+";"+
		ObjectMaxCardinalityRestriction+";"+
		ObjectSelfRestriction+";"+
		ObjectOneOf+";"+
		DataSomeRestriction+";"+
		DataAllRestriction+";"+
		DataValueRestriction+";"+
		DataMinCardinalityRestriction+";"+
		DataExactCardinalityRestriction+";"+
		DataMaxCardinalityRestriction+";"+
		DataType+";"+
		DataComplementOf+";"+
		DataOneOf+";"+
		DataRangeRestriction+";"+
		TypedConstant+";"+
		UntypedConstant+";"+
		DataRangeFacetRestriction+";"+
		ObjectProperty+";"+
		ObjectPropertyInverse+";"+
		DataProperty+";"+
		Individual+";"+
		ObjectAnnotation+";"+
		ConstantAnnotation;	
	}
}
