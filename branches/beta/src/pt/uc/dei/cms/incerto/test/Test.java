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

package pt.uc.dei.cms.incerto.test;

import pt.uc.dei.cms.incerto.engines.MarkovLogicEngine;
import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.AlchemyVisitor;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;

public class Test {

	/**
	 * @param args
	 * @throws OntologyProcessorException 
	 */
	public static void main(String[] args) throws OntologyProcessorException {
		String onto = "goldDLP.owl";

		
		MLN kb = new parserOWLAPI().onto2MLN("d:/ontologies/"+onto);
		
		System.out.println(new AlchemyVisitor().MLNELementToString(kb, true));
		
		MarkovLogicEngine alchemy = IncertoSettings.getInstance().ML_ENGINE;
		Query q = Query.parseQuery("ProblemLoan", true);
		System.out.println(q);
		try {
			//System.out.println(alchemy.inference(kb, q));
			MLN mln = alchemy.weightlearning(kb, new Evidence(kb.getEvidences()));
			System.out.println("MLN:\n"+mln);
			ReasoningResults res = alchemy.inference(mln, new Evidence(kb.getEvidences()), q);
			System.out.println(res);
			//System.out.println(alchemy.inferencewithweightlearning(kb, new Evidence(kb.getEvidences()), q));
		} catch (MarkovLogicEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
