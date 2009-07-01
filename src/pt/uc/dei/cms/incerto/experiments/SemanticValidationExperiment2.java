package pt.uc.dei.cms.incerto.experiments;

import java.util.ArrayList;
import java.util.Collections;

import pt.uc.dei.cms.incerto.learners.sources.searchengines.GoogleSearchAPI;
import pt.uc.dei.cms.incerto.utils.MathUtils;
import pt.uc.dei.cms.incerto.utils.SemanticSimilarity;

public class SemanticValidationExperiment2 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		new SemanticValidationExperiment2().doMain();
	}
	
	public void doMain() throws Exception
	{
		//Validate semantic relations	
		SemanticSimilarity sm = new SemanticSimilarity(new GoogleSearchAPI(1),SemanticSimilarity.GOOGLE_SIZE);

		ArrayList<Double> results = new ArrayList<Double>();
		results.add(sm.getSimilarityWithHearstPatterns("Alcohol", "Substance"));
		results.add(sm.getSimilarityWithHearstPatterns("Preservative", "Substance"));
		results.add(sm.getSimilarityWithHearstPatterns("Albumen", "Substance"));
		results.add(sm.getSimilarityWithHearstPatterns("Antioxidant", "Substance"));
		
		
		Double max = Collections.max(results);
		double[] fres = new double[results.size()];
		int i = 0;
		System.out.println("MAX: "+max);
		for(Double d: results)
		{
			System.out.println(+d+"\t"+(d/max));
			fres[i++] = d;
		}
			
		System.out.println("\t"+MathUtils.getPearsonCorrelation(new double[]{1,1,1,1}, fres ));
		
	}

}
