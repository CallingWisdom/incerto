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


import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.*;

import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;



/**
 * OWLAPI Parser. Transforms an ontology into an MLN.
 * @author Pedro Oliveira
 *
 */
public class parserOWLAPI implements Onto2MLN {

	
	public MLN onto2MLN(String location) throws OntologyProcessorException {

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = InOutUtils.uriFromFile(location);
		OWLOntology ontology = null;

		try {
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
		} catch (OWLOntologyCreationException e) {
			LoggerUtils.addError(e);
			throw new OntologyProcessorException("Problem loading ontology "+location,e);
		}

		//Add statements
		visitorOWLAPI visitor = new visitorOWLAPI(ontology);
		MLN kb = new MLN();
		

		
		kb.setName(InOutUtils.getFilename(location));

		//Add axioms
		for(OWLAxiom a: ontology.getLogicalAxioms())
			kb.addAxiom(a.toString());

		//if(!kb.isInvalid())
		//{
			//Add declarations
			for(OWLClass cl: ontology.getReferencedClasses())
				kb.addDeclaration(new Predicate(cl.toString(),Constant.Individual));
			for(OWLObjectProperty op: ontology.getReferencedObjectProperties())
				kb.addDeclaration(new Predicate(op.toString(),Constant.Individual,Constant.Individual));
	
			//Add evidences
			EvidencesOWLAPIVisitor ivisitor = new EvidencesOWLAPIVisitor(ontology);
			kb = ivisitor.addEvidencesToMLN(kb);
			
			//Add rules
			RulesOWLAPIVisitor rvisitor = new RulesOWLAPIVisitor(ontology);
			kb = rvisitor.addRulesToMLN(kb);

		//}

		visitor.addLogicalAxiomsToMLN(kb);
		
		manager.removeOntology(ontology.getURI());

		return kb;
	}
}
