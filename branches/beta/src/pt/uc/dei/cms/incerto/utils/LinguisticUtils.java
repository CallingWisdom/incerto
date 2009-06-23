package pt.uc.dei.cms.incerto.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LinguisticUtils{

	public static String normalizeOntologyEntity(String entity)
	{
		//Composite Tokenization
		String res = entity.replaceAll("(?=\\p{Upper})"," ");
		
		//Case Normalization
		res = res.toLowerCase(Locale.ENGLISH);		
		
		//Punctuation Elimination
		res = res.replaceAll("\\p{Punct}+", " ");
		
		//Diacritics Supression
		res = Normalizer.normalize(res, Normalizer.Form.NFD);
		res = res.replaceAll("[^\\p{ASCII}]","");
		
		//Blank Normalization
		res = res.replaceAll("\\b\\s{2,}\\b", " ");
		//res = res.replaceAll("\\s{2,}", " ");		
		
		//Trim
		res = res.trim();
		return res;
	}
	
	public static String normalizeTextualEntity(String entity)
	{
		//Case Normalization
		String res = entity.toLowerCase(Locale.ENGLISH);		
		
		//Punctuation Elimination
		res = res.replaceAll("\\p{Punct}+", " ");
		
		//Diacritics Supression
		res = Normalizer.normalize(res, Normalizer.Form.NFD);
		res = res.replaceAll("[^\\p{ASCII}]","");
		
		//Blank Normalization
		//res = res.replaceAll("\\b\\s{2,}\\b", " ");
		res = res.replaceAll("\\s{2,}", " ");
		
		//Trim
		res = res.trim();
		return res;
	}
	
	public static List<String> generatePossiblePlurals(String word)
	{
		List<String> plurals = new ArrayList<String>();
		plurals.add(word+"s");	//Singular+"s"
		if(!word.endsWith("e"))	
			plurals.add(word+"es");	//Singular+"es"
		if(word.endsWith("y"))
			plurals.add(word+"ies");	//Singular+"ies"
		return plurals;
	}
	
	public static List<String> generatePossibleLemmas(String word)
	{
		List<String> lemmas = new ArrayList<String>();
		if(word.endsWith("s"))
		{
			word = word.substring(0,word.length()-1);
			lemmas.add(word);
		}
		if(word.endsWith("e"))
		{
			word = word.substring(0,word.length()-1);
			lemmas.add(word);
		}
		if(word.endsWith("i"))
		{
			word = word.substring(0,word.length()-1);
			lemmas.add(word+"y");
		}
		return lemmas;
	}
	
	public static boolean isPossiblePlural(String word)
	{
		if(word.length()<=1)
			return false;
		if(!word.endsWith("s"))	//If don't end with "s", it's not plural
			return false;
		if(word.charAt(word.length()-2)=='s')	//If end with "ss", it's not plural (e.g. boss)
			return false;
		return true;
	}

}
