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

import java.io.IOException;

import pt.uc.dei.cms.incerto.engines.MarkovLogicEngine;
import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;

public class GeneralTests {

	/**
	 * @param args
	 * @throws IncertoException 
	 * @throws IOException 
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws IncertoException, IOException {

		//Create learning and inference MLNs
		MLN learn = new parserOWLAPI().onto2MLN("d:/ontologies/advogato.owl");
		MLN infer = new parserOWLAPI().onto2MLN("d:/ontologies/advogato_subset.owl");
		//Parse FOL rules
		learn = MLN.parseFOLRules("d:/ontologies/advogatoknowsrules.fol", learn);
		//Get the default Markov Logic engine
		MarkovLogicEngine engine = IncertoSettings.getInstance().ML_ENGINE;
		//Create query 
		Query q = Query.parseQuery("knows");

		System.out.println(learn);
		ReasoningResults res = engine.inference(learn, new Evidence(infer.getEvidences()), q);
		//Print results
		System.out.println(res);
	}

}
