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

import java.util.ArrayList;
import java.util.Collections;

import pt.uc.dei.cms.incerto.learners.sources.searchengines.GoogleSearchAPI;
import pt.uc.dei.cms.incerto.utils.MathUtils;
import pt.uc.dei.cms.incerto.utils.SemanticSimilarity;

public class SemanticValidationExperiment1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new SemanticValidationExperiment1().doMain();
	}
	
	public void doMain() throws Exception
	{
		//Validate semantic relations	
		SemanticSimilarity sm = new SemanticSimilarity(new GoogleSearchAPI(1),SemanticSimilarity.GOOGLE_SIZE);

		ArrayList<Double> results = new ArrayList<Double>();
		results.add(sm.getSimilarityWithHearstPatterns("rabbits", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("dogs", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("horses", "animals"));
		results.add(sm.getSimilarityWithHearstPatterns("lions", "animals"));
		
		results.add(sm.getSimilarityWithHearstPatterns("house rabbits", "rabbits"));
		results.add(sm.getSimilarityWithHearstPatterns("cats", "rabbits"));
		results.add(sm.getSimilarityWithHearstPatterns("cats", "dogs"));
		results.add(sm.getSimilarityWithHearstPatterns("arthritis", "dogs"));
		results.add(sm.getSimilarityWithHearstPatterns("Lusitano", "horses"));
		
		Double max = Collections.max(results);
		double[] fres = new double[results.size()];
		int i = 0;
		System.out.println("MAX: "+max);
		for(Double d: results)
		{
			System.out.println(+d+"\t"+(d/max));
			fres[i++] = d;
		}
			
		System.out.println("\t"+MathUtils.getPearsonCorrelation(new double[]{1,1,1,1,1,0,0,0,1}, fres ));
		
	}

}
