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

package pt.uc.dei.cms.incerto.engines;

import java.util.HashMap;

import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.utils.StringUtils;


/**
 * General utilities for Markov logic engines.
 * @author Pedro Oliveira
 *
 */
public class MarkovLogicEngineUtils {

	/**
	 * Update the weights of a MLN (original) with a weighted one (weighted) <br>
	 * The weighted MLN was generated through weight learning processes, and contains normalized names
	 * @param original
	 * @param weighted
	 * @return
	 * @throws MarkovLogicEngineException 
	 */
	public static MLN updateMLNWeights(MLN original, MLN weighted, MLNVisitor visitor) throws MarkovLogicEngineException
	{
		HashMap<String, Double> weights = new HashMap<String, Double>();

		String form;
		for(Formula f: weighted.getFOLRules())
		{
			form = visitor.MLNELementToString(f, false);
			form = form.substring(form.indexOf(' ')+1);	//Remove weight
			weights.put(StringUtils.removeWS(form), f.getWeight());
		}
		
		for(Formula f: original.getFormulas())
			updateWeight(f, weights, visitor);
		
		for(Formula f: original.getRules())
			updateWeight(f, weights, visitor);
		
		for(Formula f: original.getFOLRules())
			updateWeight(f, weights, visitor);
	
		return original;
	}
	
	/**
	 * Update a particular formula's weight <br>
	 * If the formula is not found in the weighted MLN, it is chosen the most similar formula
	 * @param f
	 * @param weights
	 * @param visitor
	 * @throws MarkovLogicEngineException
	 */
	public static void updateWeight(Formula f, HashMap<String, Double> weights, MLNVisitor visitor) throws MarkovLogicEngineException
	{
		String form =  visitor.MLNELementToString(f, true);
		if(f.isHard())
			form = form.substring(0, form.length()-1);	//Remove point (.)
		else
			form = form.substring(form.indexOf(' ')+1);	//Remove weight
		
		form = StringUtils.removeWS(form);
		if(weights.containsKey(form))
			f.setWeight(weights.get(form));
		else	//The formula is different from the one provided (i.e., it may contains extra-characters like brackets). In this case, we see which formula is the most similar.
		{
			Double closer = null;
			int best = Integer.MAX_VALUE, aux;
			for(String formula: weights.keySet())
			{
				aux = StringUtils.levenshteinDistance(form, formula);
				if(aux<best)
				{
					closer = weights.get(formula);
					best = aux;
				}
				else if(aux==best)	//There are more than one most similar formulas...
					throw new MarkovLogicEngineException("Problem on parsing PyMLNs result MLN (ambiguity on parsing resulting formulas)");
			}
			f.setWeight(closer);
		}
	}
	
	
	
	/**
	 * Parse query to the given MLN, i.e., interpret the query predicates given the predicates of the MLN.
	 * @param query
	 * @param mln
	 * @param visitor
	 * @param separator The separator of query predicates (commas in Alchemy, semicolons in PyMLNs) 
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	public static String parseQuery(Query query, MLN mln, MLNVisitor visitor, String separator) throws MarkovLogicEngineException
	{
		StringBuilder sb = new StringBuilder();
		for(Predicate p: query.getQueryPredicates())
		{
			if(!mln.getDeclarations().contains(p))
				throw new MarkovLogicEngineException("Query Predicate "+p+ " doesn't exist in MLN "+mln.getName());
			if(p.getArity()==0)
			{
				for(Predicate pr: mln.getDeclarations())	//Get the correspondent predicate
					if(pr.equals(p))
					{
						for(int i=0; i< pr.getTerms().size(); i++)	//Add the same number of terms to the predicate
							p.addTerm(Variable.getVariable(i));
						break;
					}
			}
			
			sb.append(visitor.MLNELementToString(p, true));
			sb.append(separator);	//Queries are separated by semicolons (different from Alchemy)
		}
		sb.deleteCharAt(sb.length()-1);
		return StringUtils.removeWS(sb.toString());		
	}

}
