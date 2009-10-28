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
import java.util.HashSet;
import java.util.List;

import pt.uc.dei.cms.incerto.exceptions.LearnerException;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.GATEOntologyPopulator;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.NounPhrase;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyClass;
import pt.uc.dei.cms.incerto.learners.ontologypopulation.model.OntologyToPopulate;
import pt.uc.dei.cms.incerto.learners.sources.OntologyPopulationSource;
import pt.uc.dei.cms.incerto.learners.sources.SearchEngine;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class GateTaxonomyLearner {

	public static int DEPTH = 2;
	public static int EXPAND_NUMBER = 3;

	
	public OntologyToPopulate learnTaxonomy(OntologyClass root, OntologyPopulationSource source) throws LearnerException
	{
		return learnTaxonomy(root, source,DEPTH,EXPAND_NUMBER);
	}
	
	public OntologyToPopulate learnTaxonomy(OntologyClass root, OntologyPopulationSource source, int depth, int expand_number) throws LearnerException
	{
		OntologyToPopulate current, res= new OntologyToPopulate(), next = new OntologyToPopulate();
		
		HashSet<OntologyClass> exploredClasses = new HashSet<OntologyClass>();
		
		next.addOntologyClass(root);
		GATEOntologyPopulator gate;
		try {
			gate = new GATEOntologyPopulator();
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new LearnerException("Problem on loading GATE!",e);
		}
		for(int i=0; i<depth; i++)
		{
			current = next;		
			try {
				
				if(source.getClass().equals(SearchEngine.class))	//If the source is a search engine, we must had the ontology entities to the search
				{
					SearchEngine s = ((SearchEngine)source);
					s.resetResults();
					s.addEntitiesToSearch(current);
				}
				current = gate.populateOntology(current, source);
			} catch (Exception e) {
				LoggerUtils.addError(e);
				throw new LearnerException("Problem on generating MLN evidences!",e);
			}
			
			res.join(current);	//TODO: O join tb devia de ser so as melhores (no entanto, assim tb nao é mau, so expande as melhores, mas mantem as outras)...
			
			for(OntologyClass cl: next.getClasses())
				exploredClasses.add(cl);

			next = new OntologyToPopulate();	
			/*
			for(OntologyClass cl: current.getClasses())
			{
				for(NounPhrase ind: current.getIndividuals(cl))
					next.addOntologyClass(new OntologyClass(ind.getHead()));
			}
			*/
			int explorecount, indsize;
			for(OntologyClass cl: current.getClasses())
			{
				indsize = current.getIndividuals(cl).size();
				if((explorecount=expand_number)>indsize)	//Select the number of classes to expand in the next iteration
					explorecount=indsize;
				for(NounPhrase ind: LearnerMetrics.getNBest(current.getIndividualsWithCounts(cl), explorecount))
				{	
					OntologyClass ocl = new OntologyClass(ind.getHead());
					if(!exploredClasses.contains(ocl))
						next.addOntologyClass(ocl);
				}
			}
		}
		return res;
	}

}
