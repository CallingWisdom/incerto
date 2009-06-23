package pt.uc.dei.cms.incerto.learners;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.GATEOntologyPopulator;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyProperty;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.Pair;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;
import pt.uc.dei.cms.incerto.learners.sources.SearchEngine;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class GATEEvidenceLearner implements EvidenceLearner {

	public Evidence learn(MLN mln, OntologyPopulationSource source) throws LearnerException
	{	
		OntologyToPopulate ontology = MLN2OntologyToPopulate(mln);
		boolean isSearchEngine = source.getClass().equals(SearchEngine.class);
		
		if(isSearchEngine)	//If the source is a search engine, we must had the ontology classes to the search
				((SearchEngine)source).addClassesToSearch(ontology);
		
		GATEOntologyPopulator gate;
		try {
			gate = new GATEOntologyPopulator();
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new LearnerException("Problem on loading GATE!",e);
		}
		try {
			ontology = gate.populateOntology(ontology, source);
			if(isSearchEngine && ontology.containsProperties())		//If it is a search engine and the ontology contains properties, we must now search for the properties assertions given the previously found individuals
			{
				SearchEngine s = (SearchEngine)source;
				s.resetResults();
				s.addPropertiesToSearchWithClassIndividuals(ontology,2);
				ontology = gate.populateOntology(ontology, source);
			}
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new LearnerException("Problem on generating MLN evidences!",e);
		}
		System.out.println("OntologyToPopulate");
		System.out.println(ontology.toString()+"\n\n");
		return OntologyToPopulate2Evidence(ontology);
	}
	
	private OntologyToPopulate MLN2OntologyToPopulate(MLN mln) throws LearnerException
	{
		OntologyToPopulate ontology = new OntologyToPopulate();
		for(Predicate p: mln.getDeclarations())
		{
			if(p.getArity()==1)	//Classes
			{
				if(p.getLabel() != null)
					ontology.addOntologyClass(new OntologyClass(p.getName(),p.getLabel()));
				else
					ontology.addOntologyClass(new OntologyClass(p.getName()));
			}
			else if(p.getArity()==2)	//Properties
			{
				if(p.getLabel() != null)
					ontology.addOntologyProperty(new OntologyProperty(p.getName(),p.getLabel()));
				else
					ontology.addOntologyProperty(new OntologyProperty(p.getName()));
			}
			else
				throw new LearnerException("Problem on learning MLN evidences. There are predicates with more than 2 terms!");	//In the cases were there are user-defined predicates with more than 2 terms, the rules don't work...
		}
		return ontology;
	}
	
	private Evidence OntologyToPopulate2Evidence(OntologyToPopulate ontology)
	{
		Evidence evidence = new Evidence();
		Predicate p;
		for(OntologyClass oc: ontology.getClasses())	//Classes
		{
			for(NounPhrase np: ontology.getIndividuals(oc))
			{
				p = new Predicate(oc.getOriginalName().getOriginalUnnormalizedName(), new Constant(np.getHead().getLemmatizedName()));
				evidence.addEvidence(new Formula(p));
			}
		}
		
		for(OntologyProperty op: ontology.getProperties())	//Properties
		{
			for(Pair<NounPhrase,NounPhrase> pair: ontology.getIndividuals(op))
			{
				p = new Predicate(op.getOriginalName().getOriginalUnnormalizedName(), new Constant(pair.getFirst().getHead().getLemmatizedName()), new Constant(pair.getSecond().getHead().getLemmatizedName()));
				evidence.addEvidence(new Formula(p));
			}
		}		
		return evidence;
	}

}
