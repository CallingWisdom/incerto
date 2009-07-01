package pt.uc.dei.cms.incerto.experiments;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;

import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.AlchemyVisitor;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.StringUtils;

public class DiseasesExperiment {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new DiseasesExperiment().doMain();
	}

	public void doMain2() throws OntologyProcessorException, IOException
	{
		MLN mln = new parserOWLAPI().onto2MLN("C:/Documents and Settings/Pedro/Ambiente de trabalho/Incerto/Final Thesis/Experiments/hydrocarbons.owl");
		InOutUtils.writeToFile("C:/Documents and Settings/Pedro/Ambiente de trabalho/Incerto/Final Thesis/Experiments/hydrocarbons.mln", new AlchemyVisitor().MLNELementToString(mln, true));
		InOutUtils.writeToFile("C:/Documents and Settings/Pedro/Ambiente de trabalho/Incerto/Final Thesis/Experiments/hydrocarbons.db", new AlchemyVisitor().MLNELementToString(new Evidence(mln.getEvidences()), true));
	}
	
	public void doMain() throws Exception
	{

		String path = "C:/Documents and Settings/Pedro/Ambiente de trabalho/Incerto/Final Thesis/Experiments/diseases/";
		
		List<String> google = InOutUtils.readListFromFile(path+"google.txt");
		List<String> yahoo = InOutUtils.readListFromFile(path+"yahoo.txt");
		List<String> diseases = InOutUtils.readListFromFile(path+"diseases.txt");

		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> googleGood = new LinkedHashMap<NounPhrase, HashSet<NounPhrase>>();
		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> yahooGood = new LinkedHashMap<NounPhrase, HashSet<NounPhrase>>();
		
		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> googleBad = new LinkedHashMap<NounPhrase, HashSet<NounPhrase>>();
		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> yahooBad = new LinkedHashMap<NounPhrase, HashSet<NounPhrase>>();
		
		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> totalGood, totalBad;
		int unmatchedGoogle, unmatchedYahoo;
		
		NounPhrase np;
		for(String disease: diseases)
		{
			if(!disease.trim().isEmpty())
			{
				np = new NounPhrase(disease);
				googleGood.put(np, new HashSet<NounPhrase>());
				yahooGood.put(np, new HashSet<NounPhrase>());
			}
		}
		
		unmatchedGoogle = toHash(google, googleGood, googleBad);	
		unmatchedYahoo = toHash(yahoo, yahooGood, yahooBad);
		
		System.out.println("Google:");
		getStats(googleGood, googleBad);
		System.out.println("\nYahoo:");
		getStats(yahooGood, yahooBad);
		
		totalGood = joinHashes(googleGood, yahooGood);
		totalBad = joinHashes(googleBad, yahooBad);
		
		System.out.println("\nTotal:");
		getStats(totalGood, totalBad);
		
		System.out.println("UnmatchedGoogle: "+unmatchedGoogle+"\tUnmatchedYahoo: "+unmatchedYahoo);
		
		//saveToOntology(totalGood);
	}


	public int toHash(List<String> lines, LinkedHashMap<NounPhrase, HashSet<NounPhrase>> good, LinkedHashMap<NounPhrase, HashSet<NounPhrase>> bad)
	{
		String[] elems;
		NounPhrase disease;
		HashSet<NounPhrase> symptoms;
		boolean isGood;
		int unmatchedDiseases=0;

		for(String line: lines)
		{
			if(!line.trim().isEmpty())
			{
				elems = line.split("[;]");
				disease = new NounPhrase(elems[1]);
				isGood = StringUtils.parseBoolean(elems[3]);
				if(isGood)
				{
					symptoms = good.get(disease);
					if(symptoms==null)	//if the disease don't match the ones in the ontology, ignore it
					{
						unmatchedDiseases++;
						continue;
					}
					symptoms.add(new NounPhrase(elems[0]));
					good.put(disease, symptoms);					
				}
				else
				{
					symptoms = bad.get(disease);
					if(symptoms==null)
						symptoms = new HashSet<NounPhrase>();
					symptoms.add(new NounPhrase(elems[0]));
					bad.put(disease, symptoms);
				}
			}
		}
		return unmatchedDiseases;
	}
	
	public void getStats(LinkedHashMap<NounPhrase, HashSet<NounPhrase>> good, LinkedHashMap<NounPhrase, HashSet<NounPhrase>> bad)
	{
		HashSet<NounPhrase> symptoms;
		HashSet<NounPhrase> allSymptoms = new HashSet<NounPhrase>();
		int countGood = 0, countBad = 0, goodSymptoms=0, badSymptoms=0, foundDiseases = 0;
		
		for(NounPhrase np: good.keySet())
		{
			symptoms = good.get(np);
			allSymptoms.addAll(symptoms);
			countGood+=symptoms.size();
			if(symptoms.size()>0)
				foundDiseases++;
		}
		goodSymptoms = allSymptoms.size();
		
		allSymptoms = new HashSet<NounPhrase>();
		for(NounPhrase np: bad.keySet())
		{
			symptoms = bad.get(np);
			allSymptoms.addAll(symptoms);
			countBad+=symptoms.size();
		}
		badSymptoms = allSymptoms.size();
		
		System.out.println("Good: "+countGood+"\tBad: "+countBad+"\tPrecision: "+(((double)countGood)/(countGood+countBad))+"\tTotalDistinctGoodSymptoms: "+goodSymptoms);
		System.out.println("Found Diseases: "+foundDiseases+"\tTotalDiseases: "+good.keySet().size()+"\tPerc: "+((double)foundDiseases/good.keySet().size()));
	
	}

	public LinkedHashMap<NounPhrase, HashSet<NounPhrase>> joinHashes(LinkedHashMap<NounPhrase, HashSet<NounPhrase>> one, LinkedHashMap<NounPhrase, HashSet<NounPhrase>> two)
	{
		LinkedHashMap<NounPhrase, HashSet<NounPhrase>> res = new LinkedHashMap<NounPhrase, HashSet<NounPhrase>>();
		
		res.putAll(one);
		HashSet<NounPhrase> symptoms;
		
		for(NounPhrase np: two.keySet())
		{
			symptoms = res.get(np);
			if(symptoms == null)
				res.put(np, two.get(np));
			else
			{
				symptoms.addAll(two.get(np));
				res.put(np, symptoms);
			}
		}
		
		return res;
	}
	
	public void saveToOntology(LinkedHashMap<NounPhrase, HashSet<NounPhrase>> individuals) throws Exception
	{
		String ontolocation = "d:/diseases.owl", baseURI = "http://code.google.com/p/incerto/diseases.owl";
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = URI.create("file:/"+ontolocation);
		OWLOntology ontology = null;
		try {
			//manager.createOntology(physicalURI);
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			return;
		}
		
		//Create DataFactory
		OWLDataFactory dataFactory = manager.getOWLDataFactory();		
		
		OWLDescription dsympt = dataFactory.getOWLClass(URI.create(baseURI+"#Symptom"));
		OWLDescription ddise = dataFactory.getOWLClass(URI.create(baseURI+"#Disease"));
		
		
		for(NounPhrase disease: individuals.keySet())
		{
			String newInd = StringUtils.removeWS(disease.getCompletePhrase().getLemmatizedName());
			
			OWLIndividual p = dataFactory.getOWLIndividual(URI.create(baseURI+"#"+newInd));		
			
			//Aux vars
			OWLObjectProperty prop;
			OWLIndividual v;
			OWLObjectPropertyAssertionAxiom assertion;
			
			for(NounPhrase symptom: individuals.get(disease))
			{
				String symp = StringUtils.removeWS(symptom.getCompletePhrase().getLemmatizedName());
				
				prop = dataFactory.getOWLObjectProperty(URI.create(baseURI + "#symptomOf"));
				v = dataFactory.getOWLIndividual(URI.create(baseURI + "#"+symp));
				assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(v, prop, p);
				manager.applyChange(new AddAxiom(ontology, assertion));
				
				manager.applyChange(new AddAxiom(ontology, dataFactory.getOWLClassAssertionAxiom(v, dsympt)));
				manager.applyChange(new AddAxiom(ontology, dataFactory.getOWLClassAssertionAxiom(p, ddise)));
				
			}
		}
		
		manager.saveOntology(ontology, URI.create("file:/"+ontolocation));
	}
}
