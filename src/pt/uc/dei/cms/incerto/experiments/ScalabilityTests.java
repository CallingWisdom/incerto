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

package pt.uc.dei.cms.incerto.experiments;

import java.io.FileWriter;
import java.io.IOException;

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

public class ScalabilityTests {

	/**
	 * @param args
	 * @throws OntologyProcessorException 
	 */
	public static void main(String[] args) throws Exception {
		new ScalabilityTests().doMain();

	}

	public void doMain() throws Exception
	{
		MarkovLogicEngine alchemy = IncertoSettings.getInstance().ML_ENGINE;

		String[] ontos = new String[]{"animals.owl1","animals.owl100"};
		String query = "Dog;Lusitano;Cat;Lion;House_Rabbit;Arthritis;Horse;Rabbit;Animal";

		long start = System.currentTimeMillis(), current;
		
		FileWriter fw;
		try {
			fw = new FileWriter("d:/scalability/res.txt",true);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		Query q = Query.parseQuery(query, true);
		for(String onto: ontos)
		{		
			fw.write(onto+"\t");
			fw.flush();
			MLN kb = new parserOWLAPI().onto2MLN("d:/scalability/"+onto);
			current = System.currentTimeMillis();
			fw.write((current-start)+"\t");
			fw.flush();
			
			start = current;
			try {
				Evidence evidence = new Evidence(kb.getEvidences());
				
				MLN mln = alchemy.weightlearning(kb, evidence);
				current = System.currentTimeMillis();
				fw.write((current-start)+"\t");
				fw.flush();
				start = current;
				
				alchemy.inference(mln, evidence, q);
				current = System.currentTimeMillis();
				fw.write((current-start)+"\t");
				fw.flush();
				start = current;
			} catch (MarkovLogicEngineException e) {
				e.printStackTrace();
			}
			fw.write("\n");
			fw.flush();
		}
		fw.close();
	}

}
