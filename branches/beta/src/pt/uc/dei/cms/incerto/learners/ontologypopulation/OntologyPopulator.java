package pt.uc.dei.cms.incerto.learners.ontologypopulation;

import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;

public interface OntologyPopulator {

	public OntologyToPopulate populateOntology(OntologyToPopulate ontology, OntologyPopulationSource source) throws Exception;
}
