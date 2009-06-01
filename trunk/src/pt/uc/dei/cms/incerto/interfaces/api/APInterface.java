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

package pt.uc.dei.cms.incerto.interfaces.api;

import pt.uc.dei.cms.incerto.engines.MarkovLogicEngine;
import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;


/**
 * Simple Application Programming Interface (API) with similar functionality with the CLI.
 * @author Pedro Oliveira
 *
 */
public class APInterface {
	
	/**
	 * 
	 * @param ontologyLocation File location of the base ontology
	 * @param learnIndividualsLocation File location of the ontology containing the training (i.e., learning) individuals. null if it is the same of the ontologyLocation parameter. 
	 * @param inferIndividualsLocation File location of the ontology containing the test (i.e., inference) individuals. null if it is the same of the ontologyLocation parameter. 
	 * @param query Query 
	 * @param extraFOLRulesLocation File with extra FOL rules. null if none.
	 * @param disableWeightLearning If true, weight learning is disabled (i.e., only inference is performed).
	 * @param conditional If true, conditional probabilities are queried. Otherwise, most likely assignment query is performed.
	 * @return
	 * @throws IncertoException
	 */
	public static ReasoningResults Incerto(String ontologyLocation, String learnIndividualsLocation, String inferIndividualsLocation, String query, String extraFOLRulesLocation, boolean disableWeightLearning, boolean conditional) throws IncertoException
	{
		MarkovLogicEngine engine = IncertoSettings.getInstance().ML_ENGINE;	
		Evidence learn, infer;
		
		MLN mln = new parserOWLAPI().onto2MLN(ontologyLocation);
		if(extraFOLRulesLocation!=null)
			mln = MLN.parseFOLRules(extraFOLRulesLocation, mln);
		
		if(learnIndividualsLocation!=null)
			learn = new Evidence(new parserOWLAPI().onto2MLN(learnIndividualsLocation).getEvidences());
		else
			learn = new Evidence(mln.getEvidences());
		
		if(inferIndividualsLocation!=null)
			infer = new Evidence(new parserOWLAPI().onto2MLN(inferIndividualsLocation).getEvidences());
		else
			infer = new Evidence(mln.getEvidences());
		
		Query q = Query.parseQuery(query, conditional);
		if(disableWeightLearning)
			return engine.inference(mln, infer, q);
		else
			return engine.inferencewithweightlearning(mln, learn, infer, q);		
	}
	

}
