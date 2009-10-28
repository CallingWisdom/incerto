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

package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.uc.dei.cms.incerto.learners.LearnerMetrics;

public class OntologyToPopulate {

	private HashMap<OntologyClass, HashMap<NounPhrase, Integer>> classes;	//Each class contains its individuals, plus the number of times that these individuals occurred with the respective class
	private HashMap<OntologyProperty, HashMap<Pair<NounPhrase, NounPhrase>,Integer>> properties;	//The same for properties
	
	public OntologyToPopulate()
	{
		classes = new HashMap<OntologyClass, HashMap<NounPhrase,Integer>>();
		properties = new HashMap<OntologyProperty, HashMap<Pair<NounPhrase,NounPhrase>,Integer>>();
	}
	
	public List<OntologyClass> getOntologyClasses(String description)
	{
		List<OntologyClass> getclasses = new ArrayList<OntologyClass>();
		for(OntologyClass o: classes.keySet())
		{
			if(o.compareTo(description)==0)
				getclasses.add(o);
		}
		return getclasses;
	}
	
	public List<OntologyClass> getOntologyClasses(NounPhrase classnp)
	{
		ArrayList<OntologyClass> getclasses = new ArrayList<OntologyClass>();
		for(OntologyClass o: classes.keySet())
		{
			if(o.getOriginalName().compareTo(classnp.getHead()) == 0 || o.getNormalizedName().compareTo(classnp.getHead()) == 0 || o.getLabel().compareTo(classnp.getHead()) == 0)
				getclasses.add(o);
			else if(o.getOriginalName().compareTo(classnp.getCompletePhrase()) == 0 || o.getNormalizedName().compareTo(classnp.getCompletePhrase()) == 0 || o.getLabel().compareTo(classnp.getCompletePhrase()) == 0)
				getclasses.add(o);
		}
		return getclasses;
	}
	
	public List<OntologyProperty> getOntologyProperties(TextualEntity property)
	{
		ArrayList<OntologyProperty> getproperties = new ArrayList<OntologyProperty>();
		for(OntologyProperty o: properties.keySet())
		{
			if(o.getOriginalName().compareTo(property) == 0 || o.getNormalizedName().compareTo(property) == 0 || o.getLabel().compareTo(property) == 0)
				getproperties.add(o);
		}
		return getproperties;
	}
	
	public void addOntologyClass(OntologyClass oclass)
	{
		if(!classes.containsKey(oclass))
			classes.put(oclass, new HashMap<NounPhrase, Integer>());
	}
	
	public void addOntologyProperty(OntologyProperty oproperty)
	{
		if(!properties.containsKey(oproperty))
			properties.put(oproperty, new HashMap<Pair<NounPhrase,NounPhrase>, Integer>());
	}
	
	public void addClassAssertion(OntologyClass oclass, NounPhrase individual)
	{
		HashMap<NounPhrase, Integer> individuals = classes.get(oclass);
		if(individuals!=null)
		{
			Integer currentcount = individuals.get(individual);
			if(currentcount!=null)	//If this class is already associated with this individual
				individuals.put(individual, ++currentcount);	//Increase the counting
			else	//otherwise
				individuals.put(individual, 1);	//Start the counting
		}
	}
	
	public void addPropertyAssertion(OntologyProperty oproperty, NounPhrase sourceIndividual, NounPhrase targetIndividual)
	{
		HashMap<Pair<NounPhrase, NounPhrase>, Integer> assertions = properties.get(oproperty);
		if(assertions!=null)
		{
			Pair<NounPhrase, NounPhrase> pair = new Pair<NounPhrase, NounPhrase>(sourceIndividual,targetIndividual);
			Integer currentcount = assertions.get(pair);
			if(currentcount!=null)	//If this property is already associated with this individuals
				assertions.put(pair, ++currentcount);	//Increase the counting
			else	//otherwise
				assertions.put(pair, 1);	//Start the counting
		}
	}
	
	public boolean containsClasses()
	{
		return classes.keySet().size()>0 ? true:false;
	}
	
	public boolean containsProperties()
	{
		return properties.keySet().size()>0 ? true:false;
	}
	
	public List<OntologyClass> getClasses()
	{
		return new ArrayList<OntologyClass>(classes.keySet());
	}
	
	public List<OntologyProperty> getProperties()
	{
		return new ArrayList<OntologyProperty>(properties.keySet());
	}
	
	public List<NounPhrase> getIndividuals(OntologyClass oc)
	{
		return new ArrayList<NounPhrase>(classes.get(oc).keySet());
	}
	
	public HashMap<NounPhrase, Integer> getIndividualsWithCounts(OntologyClass oc)
	{
		return classes.get(oc);
	}
	
	public HashMap<Pair<NounPhrase,NounPhrase>,Integer> getIndividualsWithCounts(OntologyProperty op)
	{
		return properties.get(op);
	}
	
	public List<Pair<NounPhrase,NounPhrase>> getIndividuals(OntologyProperty op)
	{
		return new ArrayList<Pair<NounPhrase,NounPhrase>>(properties.get(op).keySet());
	}
	
	//TODO: Properties (Classes only, for now)
	public void join(OntologyToPopulate toJoin)
	{
		for(OntologyClass oc: toJoin.getClasses())
			classes.put(oc, toJoin.getIndividualsWithCounts(oc));
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Classes\n");
		List<Pair<NounPhrase, Integer>> individuals;
		for(OntologyClass oc: classes.keySet())
		{
			individuals = LearnerMetrics.sort(classes.get(oc));	//
			sb.append(oc.getOriginalName()+"\t[");
			for(Pair<NounPhrase, Integer> np: individuals)
				sb.append(np.getFirst().getCompletePhrase().getLemmatizedName()+"{"+np.getSecond()+"}"+", ");
			sb.append("]\n");
		}
		
		sb.append("Properties\n");
		HashMap<Pair<NounPhrase, NounPhrase>, Integer> assertions;
		for(OntologyProperty op: properties.keySet())
		{
			assertions = properties.get(op);
			sb.append(op.getOriginalName()+"\t[");
			for(Pair<NounPhrase,NounPhrase> pair: assertions.keySet())
				sb.append("("+pair.getFirst().getCompletePhrase()+","+pair.getSecond().getCompletePhrase()+")"+"{"+assertions.get(pair)+"}"+" , ");
			sb.append("]\n");
		}
		return sb.toString();
	}
}
