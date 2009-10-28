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

package pt.uc.dei.cms.incerto.learners.ontologypopulation;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.DocumentContent;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;
import gate.util.InvalidOffsetException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyProperty;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.TextualEntity;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;
import pt.uc.dei.cms.incerto.learners.sources.SimpleStrings;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;
import pt.uc.dei.cms.incerto.utils.SystemUtils;
import pt.uc.dei.cms.incerto.utils.settings.GATESettings;


public class GATEOntologyPopulator implements OntologyPopulator{


	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		GATEOntologyPopulator gop = new GATEOntologyPopulator();

		OntologyToPopulate ontology = new OntologyToPopulate();
		ontology.addOntologyClass(new OntologyClass("animal"));
		//ontology.addOntologyClass(new OntologyClass("small animal"));
		//ontology.addOntologyProperty(new OntologyProperty("son of"));
		
		SimpleStrings source = new SimpleStrings();
		source.addString("Bob is the son of Jon");
		/*
		DiskFiles source = new DiskFiles();
		source.addFile("d:/GATE/yahoo2.txt");
		*/
		
		//SearchEngine source = new SearchEngine(ontology,new YahooBossAPI(8));
		gop.populateOntology(ontology, source);
		gop.populateOntology(ontology, source);
	}
	
	public GATEOntologyPopulator() throws Exception
	{
			gateLoader();
	}
	
	
	private void gateLoader() throws Exception
	{
		String location = GATESettings.getInstance().LOCATION;
		System.setProperty("gate.home",location);
		File gateHome = new File(location);
		File libsHome = new File(gateHome,"lib");
		File pluginsHome = new File(gateHome, "plugins");
		
		
		SystemUtils.addJarToClassPath(new File(libsHome,"jdom.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"xercesImpl.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"nekohtml-1.9.8+2039483.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"gate-asm.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"ontotext.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"log4j-1.2.14.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"jasper-compiler-jdt.jar"));
		SystemUtils.addJarToClassPath(new File(libsHome,"commons-lang-2.4.jar"));	
		SystemUtils.addJarToClassPath(new File(libsHome,"PDFBox-0.7.2.jar"));
		//OR		
		//SystemUtils.addFolderToClassPath(libsHome, true);
			
		Gate.init();		
		//gateHome = Gate.getGateHome();

		//Load necessary plugins
		Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURI().toURL());
		Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "Tools").toURI().toURL());
	}
	
	//@Override
	public OntologyToPopulate populateOntology(OntologyToPopulate ontology, OntologyPopulationSource source) throws Exception
	{
		Corpus corpus = Factory.newCorpus("IncertoCorpus");
		
		if(source.containsFiles())
		{
			for(File f: source.getFiles())
				corpus.add(Factory.newDocument(f.toURI().toURL()));
		}
		if(source.containsStrings())
		{
			for(String st: source.getStrings())
			{
				if(!st.isEmpty())
					corpus.add(Factory.newDocument(st));
			}
		}

		if(corpus.size() == 0)
		{
			LoggerUtils.addInfo("Empty corpus in GATEOntologyPopulator!");
			return ontology;
		}
		
		String[] processingResources = {"gate.creole.tokeniser.DefaultTokeniser","gate.creole.splitter.SentenceSplitter","gate.creole.POSTagger","gate.creole.morph.Morph"};
		SerialAnalyserController pipeline = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");

		for(String st: processingResources)
			pipeline.add((gate.LanguageAnalyser)Factory.createResource(st));

		FeatureMap fm;
		if(ontology.containsProperties())
		{
			InOutUtils.writeToFile("relations.jape", createJAPEGrammarForProperties(ontology.getProperties()));
			fm = Factory.newFeatureMap();
			fm.put("grammarURL", new File("relations.jape").toURI().toURL());
			pipeline.add((LanguageAnalyser)Factory.createResource("gate.creole.Transducer",fm));
		}


		fm= Factory.newFeatureMap();
		fm.put("grammarURL", new File("main.jape").toURI().toURL());
		pipeline.add((LanguageAnalyser)Factory.createResource("gate.creole.Transducer",fm));

		pipeline.setCorpus(corpus);
		pipeline.execute();

		if(ontology.containsClasses())
			parseClassAssertionAnnotations(corpus, ontology);
		if(ontology.containsProperties())
			parsePropertyAssertionAnnotations(corpus, ontology);

		//Delete used resources
		/*for(Object o: corpus)
		{
			Document doc = (Document)o;
			Factory.deleteResource(doc);
		}*/
		
		Factory.deleteResource(corpus);
		Factory.deleteResource(pipeline);
		
		return ontology;
	}

	//Parse all the ClassAssertion Annotations on the corpus
	private void parseClassAssertionAnnotations(Corpus corpus, OntologyToPopulate ontology) throws InvalidOffsetException
	{
		for(Object o: corpus)
		{
			Document doc = (Document)o;		
			AnnotationSet all = doc.getAnnotations();
			DocumentContent content = doc.getContent();
			//System.out.println(all);

			//Get all InstanceOf annotations and sort them			
			ArrayList<Annotation> instanceOf = new ArrayList<Annotation>(all.get("ClassAssertion"));
			Collections.sort(instanceOf, new gate.util.OffsetComparator());

			//ArrayList<InstanceOf> results = new ArrayList<InstanceOf>();
			Annotation current, next;
			//Iterate over all the InstanceOf annotations, ignoring the ones that are subsumed by others
			for(int i=0; i<instanceOf.size(); i++)
			{
				current = instanceOf.get(i);
				if(i+1<instanceOf.size())
					next = instanceOf.get(i+1);
				else
					next = null;

				//If the next InstanceOf overlaps the current one, ignore the current
				if( next !=null && next.overlaps(current))
					continue;
				else
					parseClassAssertionAnnotation(current, content, all, ontology, doc.getName());
			}
		}
	}

	//Parse all the PropertyAssertion Annotation on the corpus
	private void parsePropertyAssertionAnnotations(Corpus corpus, OntologyToPopulate ontology) throws InvalidOffsetException
	{
		for(Object o: corpus)
		{
			Document doc = (Document)o;		
			AnnotationSet all = doc.getAnnotations();
			DocumentContent content = doc.getContent();
			//System.out.println(all);

			//Get all InstanceOf annotations and sort them			
			ArrayList<Annotation> instanceOf = new ArrayList<Annotation>(all.get("PropertyAssertion"));
			Collections.sort(instanceOf, new gate.util.OffsetComparator());

			//ArrayList<InstanceOf> results = new ArrayList<InstanceOf>();
			Annotation current, next;
			//Iterate over all the InstanceOf annotations, ignoring the ones that are subsumed by others
			for(int i=0; i<instanceOf.size(); i++)
			{
				current = instanceOf.get(i);
				if(i+1<instanceOf.size())
					next = instanceOf.get(i+1);
				else
					next = null;

				//If the next InstanceOf overlaps the current one, ignore the current
				if( next !=null && next.overlaps(current))
					continue;
				else
					parsePropertyAssertionAnnotation(current, content, all, ontology, doc.getName());
			}
		}
	}

	private void parseClassAssertionAnnotation(Annotation annotation, DocumentContent content, AnnotationSet allAnnotations, OntologyToPopulate ontology, String filename) throws InvalidOffsetException
	{
		String concept,conceptHead,conceptModifiers,sentence;
		NounPhrase conceptnp;

		AnnotationSet concepts = allAnnotations.get("Class", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());
		AnnotationSet instances = allAnnotations.get("Instance", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());

		Annotation cp = concepts.iterator().next();	// Even if there are more than one Class annotation in the same InstanceOf, they are all equal
		Annotation chead = allAnnotations.get("Head", cp.getStartNode().getOffset(), cp.getEndNode().getOffset()).iterator().next();	//Get the head of the class
		AnnotationSet cmod = allAnnotations.get("Modifiers", cp.getStartNode().getOffset(), cp.getEndNode().getOffset());	//Get the Modifiers of the NP

		//concept = normalizeEntity(getTextFromAnnotation(cp, content));
		conceptHead = getTextFromAnnotation(chead, content);
		if(conceptHead.length()==0)	//If the NPHead of the Class is the empty String, this class is invalid...
			return;

		//Create NP for the Class
		if(cmod.size()>0)
			conceptnp = new NounPhrase(conceptHead,getTextFromAnnotation(cmod.iterator().next(), content));
		else
			conceptnp = new NounPhrase(conceptHead);

		//System.out.println("Concept: "+conceptnp.toString());
		List<OntologyClass> ontologyclasses = ontology.getOntologyClasses(conceptnp);
		//If the ontology doesn't contain any classes that match this concept, this InstanceOf annotation is discarded
		if(ontologyclasses.isEmpty())
			return;

		sentence = getTextFromAnnotation(annotation, content);
		//Get all the instances that belong to this InstanceOf and sort them
		ArrayList<Annotation> insts = new ArrayList<Annotation>(instances);
		Collections.sort(insts, new gate.util.OffsetComparator());

		Annotation current, next;

		//Iterate over all the instances, ignoring the ones that are subsumed by others (usually one subsumes all the others)
		for(int i=0; i<insts.size(); i++)
		{
			current = insts.get(i);
			if(i+1<insts.size())
				next = insts.get(i+1);
			else
				next = null;

			//If the next instance overlaps the current one, ignore the current
			if( next !=null && next.overlaps(current))
				continue;
			else
				createAndAddClassAssertionsToOntology(current, content, allAnnotations, ontology, ontologyclasses,sentence,filename);
		}
	}

	private void parsePropertyAssertionAnnotation(Annotation annotation, DocumentContent content, AnnotationSet allAnnotations, OntologyToPopulate ontology, String filename) throws InvalidOffsetException
	{
		String concept,pHead, tiHead, conceptModifiers,sentence;
		NounPhrase targetindividualnp;
		TextualEntity propertytext;

		AnnotationSet properties = allAnnotations.get("Property", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());
		AnnotationSet sourceIndividuals = allAnnotations.get("SourceInstance", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());
		AnnotationSet targetIndividuals = allAnnotations.get("TargetInstance", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());

		//Parse Property
		Annotation property = properties.iterator().next();	// Even if there are more than one Property annotation in the same ClassAssertion, they are all equal
		propertytext = new TextualEntity(getTextFromAnnotation(property, content),getTextFromAnnotation(property, content));

		//System.out.println("Property: "+propertytext.toString());

		//Parse target Individual
		Annotation targetIndividual = targetIndividuals.iterator().next();	// Even if there are more than one target individual PropertyAssertion, they are all equal
		Annotation targetIndividualHead = allAnnotations.get("Head", targetIndividual.getStartNode().getOffset(), targetIndividual.getEndNode().getOffset()).iterator().next();	//Get the head of the class
		AnnotationSet targetIndividualModifiers = allAnnotations.get("Modifiers", targetIndividual.getStartNode().getOffset(), targetIndividual.getEndNode().getOffset());	//Get the Modifiers of the NP

		tiHead = getTextFromAnnotation(targetIndividualHead, content);
		if(tiHead.length()==0)	//If the NPHead of the Individual is the empty String, this PropertyAssertion is invalid...
			return;

		//Create NP for the Target Individual
		if(targetIndividualModifiers.size()>0)
			targetindividualnp = new NounPhrase(tiHead,getTextFromAnnotation(targetIndividualModifiers.iterator().next(), content));
		else
			targetindividualnp = new NounPhrase(tiHead);

		//System.out.println("TargetIndividual: "+targetindividualnp.toString());

		List<OntologyProperty> ontologyproperties = ontology.getOntologyProperties(propertytext);
		//If the ontology doesn't contain any property that match this property, this PropertyAssertion annotation is discarded
		if(ontologyproperties.isEmpty())
			return;

		sentence = getTextFromAnnotation(annotation, content);
		//Get all the source individuals that belong to this PropertyAssertion and sort them
		ArrayList<Annotation> insts = new ArrayList<Annotation>(sourceIndividuals);
		Collections.sort(insts, new gate.util.OffsetComparator());

		Annotation current, next;

		//Iterate over all the instances, ignoring the ones that are subsumed by others (usually one subsumes all the others)
		for(int i=0; i<insts.size(); i++)
		{
			current = insts.get(i);
			if(i+1<insts.size())
				next = insts.get(i+1);
			else
				next = null;

			//If the next instance overlaps the current one, ignore the current
			if( next !=null && next.overlaps(current))
				continue;
			else
				createAndAddPropertyAssertionsToOntology(current, content, allAnnotations, ontology, ontologyproperties, targetindividualnp,sentence,filename);
		}
	}

	private void createAndAddClassAssertionsToOntology(Annotation annotation, DocumentContent content, AnnotationSet allAnnotations, OntologyToPopulate ontology, List<OntologyClass> ontologyclasses, String sentence, String filename) throws InvalidOffsetException
	{
		String instance,instanceModifiers;
		TextualEntity instanceHead;
		Annotation ihead;
		AnnotationSet aset;
		NounPhrase instancenp;


		//System.out.println("OntologyClasses: "+ontologyclasses);
		//ExtractedRule extractedRule = new ExtractedRule(sentence, annotation.getFeatures().get("rule").toString(), filename);
		//System.out.println("Sentence: "+sentence);
		//Iterate over all the NPs in the instance and create an InstanceOf object for each one
		for(Annotation a: allAnnotations.get("NounPhrase", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset()))	//Get all the NPs in the Annotation
		{
			ihead = allAnnotations.get("Head", a.getStartNode().getOffset(), a.getEndNode().getOffset()).iterator().next();	//Get the Head of the NP
			aset = allAnnotations.get("Modifiers", a.getStartNode().getOffset(), a.getEndNode().getOffset());	//Get the Modifiers of the NP
			instanceHead = new TextualEntity(getTextFromAnnotation(ihead, content), getLemmatizedNameFromAnnotation(ihead,allAnnotations));
			if(instanceHead.getOriginalName().length()==0)	//If the NPHead of the Instance is the empty String, this instance is invalid...
				continue;

			if(aset.size()>0)
				instancenp = new NounPhrase(instanceHead,getTextFromAnnotation(aset.iterator().next(), content));
			else
				instancenp = new NounPhrase(instanceHead);
			//System.out.println("Instance: "+instancenp.toString());
			instance = getTextFromAnnotation(a, content);
			for(OntologyClass oc: ontologyclasses)
				ontology.addClassAssertion(oc, instancenp);
		}
	}

	private void createAndAddPropertyAssertionsToOntology(Annotation annotation, DocumentContent content, AnnotationSet allAnnotations, OntologyToPopulate ontology, List<OntologyProperty> ontologyproperties, NounPhrase targetIndividual, String sentence, String filename) throws InvalidOffsetException
	{
		String instance,instanceModifiers;
		TextualEntity instanceHead;
		Annotation ihead;
		AnnotationSet aset;
		NounPhrase instancenp;


		//System.out.println("OntologyProperties: "+ontologyproperties);
		//ExtractedRule extractedRule = new ExtractedRule(sentence, annotation.getFeatures().get("rule").toString(), filename);

		//Iterate over all the NPs in the instance and create an InstanceOf object for each one
		for(Annotation a: allAnnotations.get("NounPhrase", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset()))	//Get all the NPs in the Annotation
		{
			ihead = allAnnotations.get("Head", a.getStartNode().getOffset(), a.getEndNode().getOffset()).iterator().next();	//Get the Head of the NP
			aset = allAnnotations.get("Modifiers", a.getStartNode().getOffset(), a.getEndNode().getOffset());	//Get the Modifiers of the NP
			instanceHead = new TextualEntity(getTextFromAnnotation(ihead, content), getLemmatizedNameFromAnnotation(ihead,allAnnotations));
			if(instanceHead.getOriginalName().length()==0)	//If the NPHead of the Instance is the empty String, this instance is invalid...
				continue;

			if(aset.size()>0)
				instancenp = new NounPhrase(instanceHead,getTextFromAnnotation(aset.iterator().next(), content));
			else
				instancenp = new NounPhrase(instanceHead);
			//System.out.println("Instance: "+instancenp.toString());
			instance = getTextFromAnnotation(a, content);
			for(OntologyProperty op: ontologyproperties)
				ontology.addPropertyAssertion(op, instancenp, targetIndividual);
		}
	}

	private String getTextFromAnnotation(Annotation annotation, DocumentContent content) throws InvalidOffsetException
	{
		return content.getContent(annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset()).toString();
	}

	//Creates a String with the lemmas of all the Tokens in the annotation (i.e., the full lemmatization of the annotation)
	private String getLemmatizedNameFromAnnotation(Annotation annotation, AnnotationSet allAnnotations)
	{
		AnnotationSet tokenset = allAnnotations.get("Token", annotation.getStartNode().getOffset(), annotation.getEndNode().getOffset());
		ArrayList<Annotation> tokens = new ArrayList<Annotation>(tokenset);
		Collections.sort(tokens, new gate.util.OffsetComparator());

		StringBuilder sb = new StringBuilder();
		for(Annotation a: tokens)
		{
			sb.append(a.getFeatures().get("root").toString());
			sb.append(' ');
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	private String createJAPEGrammarForProperties(List<OntologyProperty> properties)
	{
		String[] tokens;
		StringBuilder sb = new StringBuilder();
		sb.append("Phase: PropertyIdentifier\n");
		sb.append("Input: Relation SpaceToken Token\n\n");
		sb.append("rule: Relation\n");
		sb.append("(");
		for(OntologyProperty oproperty: properties)
		{
			for(String property: oproperty.generatePossibleQueries())
			{
				tokens = property.split(" ");
				sb.append("\n\t(");
				for(int i=0; i< tokens.length; i++)
				{
					sb.append("{Token.string == \"");
					sb.append(tokens[i]);
					sb.append("\"}");
					if(i!=tokens.length-1)
						sb.append("{SpaceToken.kind == space}");
				}
				sb.append(")\n|");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");

		sb.append(":relation -->\n");
		sb.append(":relation.Relation = {rule = \"Relation\"}\n");
		return sb.toString();
	}
}