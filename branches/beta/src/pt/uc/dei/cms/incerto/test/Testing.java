package pt.uc.dei.cms.incerto.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.AlchemyVisitor;
import pt.uc.dei.cms.incerto.learners.GATEEvidenceLearner;
import pt.uc.dei.cms.incerto.learners.GateIndividualRelationsLearner;
import pt.uc.dei.cms.incerto.learners.GateTaxonomyLearner;
import pt.uc.dei.cms.incerto.learners.LearnerMetrics;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.GATEOntologyPopulator;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyProperty;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.Pair;
import pt.uc.dei.cms.incerto.learners.sources.SearchEngine;
import pt.uc.dei.cms.incerto.learners.sources.SimpleStrings;
import pt.uc.dei.cms.incerto.learners.sources.searchengines.GoogleSearchAPI;
import pt.uc.dei.cms.incerto.learners.sources.searchengines.YahooBossAPI;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.MathUtils;
import pt.uc.dei.cms.incerto.utils.SemanticSimilarity;

public class Testing {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
/*
		MLN mln = new parserOWLAPI().onto2MLN("d:/dragonballz.owl");
		AlchemyVisitor vis = new AlchemyVisitor();
		
		System.out.println(vis.MLNELementToString(mln, true));
		System.out.println(mln.getEvidences());
		
		/*
		MLN mln = new parserOWLAPI().onto2MLN("d:/labels.owl");
		GATEEvidenceLearner gate = new GATEEvidenceLearner();
		Evidence evidence = gate.learn(mln, source);		
		System.out.println(evidence);

		SimpleStrings source = new SimpleStrings();
		source.addString("infections, such as pneumonia, and ehrlichiosis");
		source.addString("such infections as Lyme disease, ehrlichiosis, and babesiosis");
		source.addString("pneumonia, such as babesiosis, and ehrlichiosis");

		MLN mln = new MLN();
		mln.addDeclaration(new Predicate("Diseases",Constant.Individual));
		//mln.addDeclaration(new Predicate("fight",Constant.Individual,Constant.Individual));
		System.out.println(mln);
		SearchEngine source = new SearchEngine(new GoogleSearchAPI(8));
		GATEEvidenceLearner gate = new GATEEvidenceLearner();
		Evidence e = gate.learn(mln, source);
		System.out.println(e);
				*/
		//*/
		
		
		SearchEngine source = new SearchEngine(new YahooBossAPI(8));
		
		/*OntologyToPopulate ontology = new OntologyToPopulate();
		OntologyClass cl = new OntologyClass("Diseases");
		ontology.addOntologyClass(cl);
		ontology.addOntologyProperty(new OntologyProperty("is a symptom of"));
		ontology.addOntologyProperty(new OntologyProperty("is a sign of"));
		ontology.addClassAssertion(cl, new NounPhrase("malaria"));
		ontology.addClassAssertion(cl, new NounPhrase("aids"));
		*/
		

		//<Symptom,{Disease,Count}>
		HashMap<NounPhrase, List<Pair<NounPhrase,Integer>>> symptoms = new HashMap<NounPhrase, List<Pair<NounPhrase,Integer>>>();
		
		
		List<String> diseases = InOutUtils.readListFromFile("C:/Documents and Settings/Pedro/Ambiente de trabalho/Incerto/Final Thesis/Experiments/diseases.txt");
		
		int SIZE = 10;
		for(int i=0; i<diseases.size(); i+=SIZE)
		{
			System.out.println("RANGE: ["+i+","+(i+SIZE)+"]\n");
			OntologyToPopulate ontology = new OntologyToPopulate();
			OntologyClass cl = new OntologyClass("Diseases");
			ontology.addOntologyClass(cl);
			ontology.addOntologyProperty(new OntologyProperty("is a symptom of"));
			ontology.addOntologyProperty(new OntologyProperty("is a sign of"));
			
			for(int j=0; j<SIZE && (i+j)<diseases.size();j++)
			{
				String ind = diseases.get(i+j);
				if(!ind.trim().isEmpty())
					ontology.addClassAssertion(cl, new NounPhrase(ind));
			}
			
			ontology = new GateIndividualRelationsLearner().learnRelationsBetweenIndividuals(ontology, source, 1);
			for(OntologyProperty p: ontology.getProperties())
			{
				HashMap<Pair<NounPhrase,NounPhrase>,Integer> inds = ontology.getIndividualsWithCounts(p);
				for(Pair<NounPhrase,NounPhrase> assertion: inds.keySet())
				{
					List<Pair<NounPhrase,Integer>> symptom = symptoms.get(assertion.getFirst());
					if(symptom == null)
						symptom = new ArrayList<Pair<NounPhrase,Integer>>();
					symptom.add(new Pair<NounPhrase, Integer>(assertion.getSecond(),inds.get(assertion)));
					symptoms.put(assertion.getFirst(), symptom);
				}			
			}	
		}	
	
		ArrayList<String> results = new ArrayList<String>();
		for(NounPhrase symptom: symptoms.keySet())
		{
			for(Pair<NounPhrase,Integer> pairs: symptoms.get(symptom))
				results.add(symptom.getCompletePhrase().getLemmatizedName()+"\t"+pairs.getFirst().getHead().getLemmatizedName()+"\t"+pairs.getSecond());
		}
		InOutUtils.writeToFile("diseasesResultsYahoo.txt", results);
		
/*
 * Learn Individuals of class, and pretty print them
		SearchEngine source = new SearchEngine(new GoogleSearchAPI(64));
		GateTaxonomyLearner gate = new GateTaxonomyLearner();	
		OntologyToPopulate o = gate.learnTaxonomy(new OntologyClass("Diseases"), source, 1, 0);
		System.out.println(o);
		for(OntologyClass cl: o.getClasses())
		{
			List<Pair<NounPhrase, Integer>> individuals = LearnerMetrics.sort(o.getIndividualsWithCounts(cl));;
			for(Pair<NounPhrase, Integer> np: individuals)
				System.out.println(np.getFirst().getCompletePhrase()+"\t"+np.getSecond());
		}
	*/	
		/*
		//Learn Taxonomies
		SearchEngine source = new SearchEngine(new GoogleSearchAPI(8));
		GateTaxonomyLearner t = new GateTaxonomyLearner();
		OntologyToPopulate o = t.learnTaxonomy(new OntologyClass("infections"), source,3,4);
		System.out.println(o);
		LearnerMetrics.Str_INorm(o);
		LearnerMetrics.Str_CNorm(o);
		LearnerMetrics.Str_INorm_Tresh(o);
		*/
		
		
		
		/*	
		//Validate semantic relations	
		SemanticSimilarity sm = new SemanticSimilarity(new GoogleSearchAPI(1),SemanticSimilarity.GOOGLE_SIZE);
		//sm.getSimilarity("dog","is a","pet");
		
		ArrayList<Double> results = new ArrayList<Double>();
		results.add(sm.getSimilarityWithHearstPatterns("rabbits", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("dogs", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("horses", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("lions", "animals"));
		
		results.add(sm.getSimilarityWithHearstPatterns("house rabbits", "rabbits"));
		results.add(sm.getSimilarityWithHearstPatterns("cats", "rabbits"));
		results.add(sm.getSimilarityWithHearstPatterns("cats", "dogs"));
		results.add(sm.getSimilarityWithHearstPatterns("arthritis", "dogs"));
		results.add(sm.getSimilarityWithHearstPatterns("Lusitano", "horses"));
		
		Double max = Collections.max(results);
		double[] fres = new double[results.size()];
		int i = 0;
		System.out.println("MAX: "+max);
		for(Double d: results)
		{
			System.out.println(+d+"\t"+(d/max));
			fres[i++] = d;
		}
			
		System.out.println("\t"+MathUtils.getPearsonCorrelation(new double[]{1,1,1,1,1,0,0,0,1}, fres ));
		
		*/
		
		//System.out.println(MathUtils.getPearsonCorrelation(new double[]{1,1,1,1,1,0,0,0,1}, new double[]{1,1,1,1,1,0,0,0,1}));
	}

}
