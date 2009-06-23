package pt.uc.dei.cms.incerto.learners.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyProperty;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.sources.searchengines.SearchEngineAPI;
import pt.uc.dei.cms.incerto.learners.sources.searchengines.SearchEngineUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class SearchEngine implements OntologyPopulationSource {

	private List<String> results;
	private SearchEngineAPI api;

	public SearchEngine(SearchEngineAPI api)
	{
		this.api = api;
		this.results = new ArrayList<String>();
	}

	public void addEntitiesToSearch(OntologyToPopulate ontology) throws LearnerException
	{
		List<String> queries = new ArrayList<String>();

		if(ontology.containsClasses())
		{
			List<String> entities = new ArrayList<String>();
			for(OntologyClass oclass: ontology.getClasses())
				entities.add(SearchEngineUtils.createORs(oclass.generatePossibleQueries()));
			queries.addAll(SearchEngineUtils.getQueriesFromPatterns(entities, SearchEngineUtils.CLASS_ASSERTION_PATTERNS));

		}
		if(ontology.containsProperties())
		{
			List<String> entities = new ArrayList<String>();
			for(OntologyProperty oprop: ontology.getProperties())
				entities.add(SearchEngineUtils.createORs(oprop.generatePossibleQueries()));
			queries.addAll(SearchEngineUtils.getQueriesFromPatterns(entities, SearchEngineUtils.PROPERTY_ASSERTION_PATTERNS));
		}
		performQueries(queries);
	}
	
	//Add the ontology classes to search
	public void addClassesToSearch(OntologyToPopulate ontology) throws LearnerException
	{
		if(ontology.containsClasses())
		{
			List<String> entities = new ArrayList<String>();
			for(OntologyClass oclass: ontology.getClasses())
				entities.add(SearchEngineUtils.createORs(oclass.generatePossibleQueries()));
			addEntitiesToSearch(entities, false);
		}
	}
	
	public void addPropertiesToSearchWithClassIndividuals(OntologyToPopulate ontology, int type) throws LearnerException
	{
		if(ontology.containsProperties())
		{
			List<String> properties = new ArrayList<String>();
			List<String> individuals = new ArrayList<String>();
			for(OntologyProperty oprop: ontology.getProperties())
				properties.add(oprop.getOriginalName().getOriginalName());	//TODO: We are ignoring all the possible queries...
			for(OntologyClass ocl: ontology.getClasses())	//Get all the individuals in the ontology
			{
				List<NounPhrase> inds = ontology.getIndividuals(ocl);
				for(NounPhrase ind: inds)
					individuals.add(ind.getCompletePhrase().getOriginalName());	//TODO: we are ignoring all the possible queries
			}
			if(type==2)
				performQueries(SearchEngineUtils.getQueriesFromPatterns2(properties, individuals, SearchEngineUtils.PROPERTY_ASSERTION_PATTERNS));
			else
				performQueries(SearchEngineUtils.getQueriesFromPatterns1(properties, individuals, SearchEngineUtils.PROPERTY_ASSERTION_PATTERNS));
			
		}
	}
	
	public void addEntitiesToSearch(List<String> entities, boolean property) throws LearnerException
	{
		if(property)
			performQueries(SearchEngineUtils.getQueriesFromPatterns(entities, SearchEngineUtils.PROPERTY_ASSERTION_PATTERNS));
		else
			performQueries(SearchEngineUtils.getQueriesFromPatterns(entities, SearchEngineUtils.CLASS_ASSERTION_PATTERNS));
	}
	
	public void addEntityToSearch(String entity, boolean property) throws LearnerException
	{
		if(property)
			performQueries(SearchEngineUtils.getQueriesFromPatterns(Arrays.asList(entity), SearchEngineUtils.PROPERTY_ASSERTION_PATTERNS));
		else
			performQueries(SearchEngineUtils.getQueriesFromPatterns(Arrays.asList(entity), SearchEngineUtils.CLASS_ASSERTION_PATTERNS));
	}
	
	public void resetResults()
	{
		this.results.clear();
	}
	
	private void performQueries(List<String> queries) throws LearnerException
	{
		try
		{
			StringBuilder sb;
			int count = 0;
			for(String query: queries)
			{
				System.out.println(query);
				sb = new StringBuilder();
				for(String result: api.getResults(query))
					sb.append(result+"\n");
				results.add(sb.toString());		
				if(count++==50)
				{
					Thread.sleep(2000);
					System.out.println("Sleeping");
					count = 0;
				}
			}	
		}catch(Exception e)
		{
			LoggerUtils.addError(e);
			throw new LearnerException("Problem on queriying search engine!",e);
		}
	}

	//@Override
	public boolean containsFiles() {
		return false;
	}

	//@Override
	public boolean containsStrings() {
		return true;
	}

	//@Override
	public List<File> getFiles() {
		return null;
	}

	//@Override
	public List<String> getStrings() {
		return results;
	}

}
