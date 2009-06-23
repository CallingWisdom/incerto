package pt.uc.dei.cms.incerto.learners.ontologypopulation.model;

import java.util.ArrayList;
import java.util.List;

import pt.uc.dei.cms.incerto.utils.LinguisticUtils;

public class TextualEntity implements Comparable<String>{

	private String originalUnnormalizedName;
	private String originalName;
	private String lemmatizedName;
	private List<String> possibleLemmatizedNames;
	
	public TextualEntity(String originalName) {
		this.originalUnnormalizedName = originalName;
		this.originalName = LinguisticUtils.normalizeTextualEntity(originalName);
		this.lemmatizedName = this.originalName; //TODO: Criar funcao que utiliza GATE para lematizar nomes simples
		this.possibleLemmatizedNames = getOriginalNamePossibleLemmas();
	}
	
	public TextualEntity(String originalName, String lemmatizedName)
	{
		this.originalUnnormalizedName = originalName;
		this.originalName = LinguisticUtils.normalizeTextualEntity(originalName);
		this.lemmatizedName = LinguisticUtils.normalizeTextualEntity(lemmatizedName);
		this.possibleLemmatizedNames = getOriginalNamePossibleLemmas();
	}
	
	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getLemmatizedName() {
		return lemmatizedName;
	}

	public void setLemmatizedName(String lemmatizedName) {
		this.lemmatizedName = lemmatizedName;
	}

	public List<String> getPossibleLemmatizedNames() {
		return possibleLemmatizedNames;
	}

	public void setPossibleLemmatizedNames(List<String> possibleLemmatizedNames) {
		this.possibleLemmatizedNames = possibleLemmatizedNames;
	}

	public String getOriginalUnnormalizedName() {
		return originalUnnormalizedName;
	}

	public void setOriginalUnnormalizedName(String originalUnnormalizedName) {
		this.originalUnnormalizedName = originalUnnormalizedName;
	}

	//Create possible lemmas for the original name. Only the last token is lemmatized
	//E.g.: monogastric animals -> monogastric animal
	private List<String> getOriginalNamePossibleLemmas()
	{
		List<String> possibleLemas = new ArrayList<String>();
		List<String> aux;
		String[] tokens = originalName.split(" ");
		String lastToken = tokens[tokens.length-1];
		StringBuilder newLemmatizedName = new StringBuilder();
		if(LinguisticUtils.isPossiblePlural(lastToken))	//Lemmatize last token only
		{
			for(int i=0; i<tokens.length-1; i++)	//Build new name
			{
				newLemmatizedName.append(tokens[i]);
				newLemmatizedName.append(' ');
			}
			aux = LinguisticUtils.generatePossibleLemmas(lastToken);
			for(String lemma: aux)
				possibleLemas.add(newLemmatizedName.toString()+lemma); //Create a new name for each possible lemma
		}
		return possibleLemas;
	}
	
	//Generate all the possible queries, for the search engines, from this entity, using the pluralization of its original name
	//E.g.: monogastric animal -> {monogastric animal, monogastric animals,monogastric animales}
	public List<String> generatePossibleQueries()
	{
		List<String> possibleQueries = new ArrayList<String>();
		List<String> aux;
		possibleQueries.add(originalName);
		String[] tokens = originalName.split(" ");
		String lastToken = tokens[tokens.length-1];
		StringBuilder newPluralizedName = new StringBuilder();
		if(!LinguisticUtils.isPossiblePlural(lastToken))
		{
			for(int i=0; i<tokens.length-1; i++)	//Build new name
			{
				newPluralizedName.append(tokens[i]);
				newPluralizedName.append(' ');
			}
			aux = LinguisticUtils.generatePossiblePlurals(lastToken);
			for(String plural: aux)
				possibleQueries.add(newPluralizedName.toString()+plural); //Create a new query, with the last word pluralized
	
		}
		return possibleQueries;
	}

	//@Override
	public int compareTo(String o) {
		if(originalName.compareTo(o) == 0 || lemmatizedName.compareTo(o) == 0)
			return 0;
		for(String lemma:possibleLemmatizedNames)
		{
			if(lemma.compareTo(o)==0)
				return 0;
		}
		return -1;
	}
	
	public int compareTo(TextualEntity o)
	{
		if(o.compareTo(originalName) == 0 || o.compareTo(lemmatizedName)==0)
			return 0;
		for(String l: possibleLemmatizedNames)
		{
			if(o.compareTo(l)==0)
				return 0;
		}
		return -1;
	}
	
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(other==this)
			return true;
		if (!(other instanceof TextualEntity))
			return false;
		TextualEntity o = (TextualEntity)other;
		if(this.compareTo(o)==0)
			return true;
		return false;
	}
	
	//TODO: Improve HashCode!
	public int hashCode() {
		return this.getLemmatizedName().hashCode();
	}

	public String toString()
	{
		return lemmatizedName;
	}
}
