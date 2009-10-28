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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.Pair;

public class LearnerMetrics {
	
	
	public void Strength(OntologyToPopulate ontology)
	{
		
	}
	
	public static void Str_INorm(OntologyToPopulate ontology)
	{
		HashMap<NounPhrase, Integer> individualCount = countNumberOfIndividuals(ontology);
		HashMap<NounPhrase, Integer> aux;
		
		//Compute the metric
		for(OntologyClass oc: ontology.getClasses())
		{
			aux = ontology.getIndividualsWithCounts(oc);
			for(NounPhrase ind: aux.keySet())
			{
				Integer indCount = individualCount.get(ind);
				Integer currCount = aux.get(ind);
				Double metric = currCount.doubleValue()/indCount.doubleValue();
				System.out.println("("+ind.getHead()+", is_a ,"+oc.getOriginalName()+") "+metric);
			}
		}
	}
	
	
	public static void Str_INorm_Tresh(OntologyToPopulate ontology)
	{
		HashMap<NounPhrase, Integer> individualCount = countNumberOfIndividuals(ontology);
		HashMap<NounPhrase, Integer> aux;
		
		Integer percentilevalue = getPercentile(individualCount, 25);
		
		//Compute the metric
		for(OntologyClass oc: ontology.getClasses())
		{
			aux = ontology.getIndividualsWithCounts(oc);
			for(NounPhrase ind: aux.keySet())
			{
				Integer indCount = individualCount.get(ind);
				Integer currCount = aux.get(ind);
				Double metric = currCount.doubleValue()/Math.max(indCount.doubleValue(),percentilevalue);
				System.out.println("("+ind.getHead()+", is_a ,"+oc.getOriginalName()+") "+metric);
			}
		}
	}
	
	private static HashMap<NounPhrase, Integer> countNumberOfIndividuals(OntologyToPopulate ontology)
	{
		HashMap<NounPhrase, Integer> individualCount = new HashMap<NounPhrase, Integer>();
		HashMap<NounPhrase, Integer> aux;
		
		//Count the number of times that each individual appears 
		for(OntologyClass oc: ontology.getClasses())
		{
			aux = ontology.getIndividualsWithCounts(oc);
			for(NounPhrase ind: aux.keySet())
			{
				Integer currentCount = individualCount.get(ind);
				if(currentCount!=null)
					individualCount.put(ind, currentCount+aux.get(ind));
				else
					individualCount.put(ind, aux.get(ind));
			}
		}
		return individualCount;
	}
	
	public static void Str_CNorm(OntologyToPopulate ontology)
	{
		HashMap<OntologyClass, Integer> classCount = new HashMap<OntologyClass, Integer>();
		HashMap<NounPhrase, Integer> aux;
		
		//Count the number of individuals per class
		for(OntologyClass oc: ontology.getClasses())
		{
			aux = ontology.getIndividualsWithCounts(oc);
			Integer currentCount = 0;
			
			for(NounPhrase ind: aux.keySet())
				currentCount+=aux.get(ind);
			classCount.put(oc, currentCount);
		}
		
		//Compute the metric
		for(OntologyClass oc: ontology.getClasses())
		{
			aux = ontology.getIndividualsWithCounts(oc);
			Integer clCount = classCount.get(oc);
			for(NounPhrase ind: aux.keySet())
			{
				Integer currCount = aux.get(ind);
				Double metric = currCount.doubleValue()/clCount.doubleValue();
				System.out.println("("+ind.getHead()+", is_a ,"+oc.getOriginalName()+") "+metric);
			}
		}
	}

	public static <T> List<T> getNBest(HashMap<T, Integer> map, int n)
	{
		ArrayList<Entry<T, Integer>> entries = new ArrayList<Map.Entry<T, Integer>>(map.entrySet());	
		Collections.sort(entries, new Comparator<Map.Entry<T, Integer>>(){
				public int compare(Entry<T, Integer> o1, Entry<T, Integer> o2) {
					return (o1.getValue().equals(o2.getValue()) ? 0 : (o1.getValue() > o2.getValue() ? -1 : 1));
				}
	     });
		
		List<T> res = new ArrayList<T>();	
		for(int i=0; i<entries.size() && i<n;i++)
			res.add(entries.get(i).getKey());
		return res;
	}
	
	public static <T> List<Pair<T, Integer>> sort(HashMap<T, Integer> map)
	{
		ArrayList<Entry<T, Integer>> entries = new ArrayList<Map.Entry<T, Integer>>(map.entrySet());	
		Collections.sort(entries, new Comparator<Map.Entry<T, Integer>>(){
				public int compare(Entry<T, Integer> o1, Entry<T, Integer> o2) {
					return (o1.getValue().equals(o2.getValue()) ? 0 : (o1.getValue() > o2.getValue() ? -1 : 1));
				}
	     });
		
		List<Pair<T, Integer>> res = new ArrayList<Pair<T, Integer>>(map.size());	
		for(Entry<T, Integer> entry: entries)
			res.add(new Pair<T, Integer>(entry.getKey(),entry.getValue()));
		return res;
	}
	
	public static <T> Integer getPercentile(HashMap<T, Integer> map, int percentile)
	{
		ArrayList<Entry<T, Integer>> entries = new ArrayList<Map.Entry<T, Integer>>(map.entrySet());	
		Collections.sort(entries, new Comparator<Map.Entry<T, Integer>>(){
				public int compare(Entry<T, Integer> o1, Entry<T, Integer> o2) {
					return (o1.getValue().equals(o2.getValue()) ? 0 : (o1.getValue() > o2.getValue() ? 1 : -1));
				}
	     });
		Double n = (map.keySet().size()/100.0)*percentile;
		return entries.get(n.intValue()).getValue();
	}
}
