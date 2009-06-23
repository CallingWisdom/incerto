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
