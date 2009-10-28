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

package pt.uc.dei.cms.incerto.learners;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.GATEOntologyPopulator;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;
import pt.uc.dei.cms.incerto.learners.sources.SearchEngine;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class GateIndividualRelationsLearner {


	//Learn relations between individuals of an ontology
	//If type=1, learn all the individuals that could be in a relation with the given individuals
	//If type=2, checks if two given individuals are in a relation
	public OntologyToPopulate learnRelationsBetweenIndividuals(OntologyToPopulate ontology, OntologyPopulationSource source, int type) throws LearnerException
	{
		boolean isSearchEngine = source.getClass().equals(SearchEngine.class);
		GATEOntologyPopulator gate;
		if(isSearchEngine && ontology.containsProperties())	//TODO: Search engines only (for now)
		{
			try {
				gate = new GATEOntologyPopulator();
			} catch (Exception e) {
				LoggerUtils.addError(e);
				throw new LearnerException("Problem on loading GATE!",e);
			}
			try {
				SearchEngine s = (SearchEngine)source;
				s.addPropertiesToSearchWithClassIndividuals(ontology,type);
				ontology = gate.populateOntology(ontology, source);
			}catch (Exception e) {
				LoggerUtils.addError(e);
				throw new LearnerException("Problem on generating MLN evidences!",e);
			}
		}
		return ontology;
	}
}
