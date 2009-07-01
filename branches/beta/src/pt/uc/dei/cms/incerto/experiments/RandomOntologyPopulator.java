package pt.uc.dei.cms.incerto.experiments;

import java.net.URI;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntologyStorageException;
import org.semanticweb.owl.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owl.model.UnknownOWLOntologyException;

import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class RandomOntologyPopulator {

	/**
	 * @param args
	 * @throws OntologyProcessorException 
	 */
	public static void main(String[] args) throws OntologyProcessorException {
		
		String path = "d:/scalability/advogato.owl";
		
		System.out.println(getQueries(path));
		
		/*randomPopulation(path, 1,3);
		randomPopulation(path, 10,3);
		randomPopulation(path, 100,3);
		randomPopulation(path, 1000,3);
		randomPopulation(path, 10000,3);*/
	}
	
	public static String getQueries(String ontologyLocation) throws OntologyProcessorException
	{
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = InOutUtils.uriFromFile(ontologyLocation);
		OWLOntology ontology = null;
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
		
		try {
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
		} catch (OWLOntologyCreationException e) {
			LoggerUtils.addError(e);
			throw new OntologyProcessorException("Problem loading ontology "+ontologyLocation,e);
		}	
		
		StringBuilder sb = new StringBuilder();
		
		for(OWLClass cl: ontology.getReferencedClasses())
			sb.append(cl.toString()+",");
		for(OWLObjectProperty op: ontology.getReferencedObjectProperties())
			sb.append(op.toString()+",");
		
		sb.deleteCharAt(sb.lastIndexOf(","));
		
		return sb.toString();
	}
	
	public static OWLOntology randomPopulation(String ontologyLocation, int nIndividuals, int maxAssertions) throws OntologyProcessorException
	{	
		//1. Load Ontology
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		URI physicalURI = InOutUtils.uriFromFile(ontologyLocation);
		OWLOntology ontology = null;
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
		
		try {
			ontology = manager.loadOntologyFromPhysicalURI(physicalURI);
		} catch (OWLOntologyCreationException e) {
			LoggerUtils.addError(e);
			throw new OntologyProcessorException("Problem loading ontology "+ontologyLocation,e);
		}		
		String baseURI = ontology.getURI().toString();


		//2.Create the individuals
		ArrayList<OWLIndividual> individuals = new ArrayList<OWLIndividual>();		
		for(int i=0; i<nIndividuals; i++)
			individuals.add(dataFactory.getOWLIndividual(URI.create(baseURI+"#"+"Ind"+i)));
		
		
		//2.1. Create randomizers
		Random rand = new Random();
		int min=1, max=maxAssertions, nassertions, currind;
		
		
		//2.2. BitSet to mark the individuals
		BitSet marked = new BitSet(nIndividuals);
		
		//3. Class Assertions
		OWLIndividual v;
		OWLClassAssertionAxiom assertion;
		for(OWLClass cl: ontology.getReferencedClasses())
		{
			nassertions = rand.nextInt(max-min+1)+min; 		
			for(int i=0; i<nassertions; i++)
			{
				currind = rand.nextInt(nIndividuals);
				v = individuals.get(currind);
				assertion = dataFactory.getOWLClassAssertionAxiom(v, cl);
				try {
					manager.applyChange(new AddAxiom(ontology, assertion));
				} catch (OWLOntologyChangeException e) {
					LoggerUtils.addError(e);
					throw new OntologyProcessorException("Problem writing to ontology "+ontologyLocation,e);
				}
				marked.set(currind);
			}
		}
		
		//4. Property Assertions
		OWLObjectPropertyAssertionAxiom passertion;
		OWLIndividual v2;
		for(OWLObjectProperty op: ontology.getReferencedObjectProperties())
		{
			nassertions = rand.nextInt(max-min+1)+min; 	
			for(int i=0; i<nassertions; i++)
			{
				//Ind1
				currind = rand.nextInt(nIndividuals);
				v = individuals.get(currind);
				marked.set(currind);
				
				//Ind2
				currind = rand.nextInt(nIndividuals);
				v2 = individuals.get(currind);
				marked.set(currind);
				
				passertion = dataFactory.getOWLObjectPropertyAssertionAxiom(v, op, v2);	
				try {
					manager.applyChange(new AddAxiom(ontology, passertion));
				} catch (OWLOntologyChangeException e) {
					LoggerUtils.addError(e);
					throw new OntologyProcessorException("Problem writing to ontology "+ontologyLocation,e);
				}
			}
		}
		
		//5. See if there is any individual that doesn't contain assertions
		ArrayList<OWLClass> classes = new ArrayList<OWLClass>(ontology.getReferencedClasses());
		for(int i=0; i<nIndividuals; i++)
		{
			if(!marked.get(i))
			{
				currind = rand.nextInt(classes.size());
				OWLClass cl = classes.get(currind);
				v = individuals.get(i);
				assertion = dataFactory.getOWLClassAssertionAxiom(v, cl);
				try {
					manager.applyChange(new AddAxiom(ontology, assertion));
				} catch (OWLOntologyChangeException e) {
					LoggerUtils.addError(e);
					throw new OntologyProcessorException("Problem writing to ontology "+ontologyLocation,e);
				}
			}
		}
		
		
		//6. Save ontology
		try {
			manager.saveOntology(ontology, URI.create("file:/"+ontologyLocation+nIndividuals));
		} catch (OWLException e) {
			LoggerUtils.addError(e);
			throw new OntologyProcessorException("Problem saving ontology "+ontologyLocation+nIndividuals,e);	
		}
		
		return ontology;
		
		//4. Clean manager
		//manager.removeOntology(ontology.getURI());	
	}
}
