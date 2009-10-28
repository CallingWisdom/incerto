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

package pt.uc.dei.cms.incerto.learners.sources.searchengines;


import java.util.ArrayList;
import java.util.List;

public class SearchEngineUtils {

	public static String[] CLASS_ASSERTION_PATTERNS = 
	{
		"$ such as *",
		"such $ as *",
		"* (and OR or) other $",
		"* (and OR or) (all OR every) other $",
		"$ (including OR especially OR specially) *",
		"$ like *",
		"* (is OR are) $",
		"* (is OR are) (a OR an OR the) $"
	};

	public static String[] PROPERTY_ASSERTION_PATTERNS = 
	{
		//"* (is OR are) $ *",
		//"* (is OR are) (a OR an OR the) $ *",
		"*, $ *"
	};

	/**
	 * Create a set of search engine queries, given the desired patterns and the entities to substitute in the patterns.
	 * @param entities
	 * @param patterns
	 * @return
	 */
	public static ArrayList<String> getQueriesFromPatterns(List<String> entities, String[] patterns)
	{
		ArrayList<String> queries = new ArrayList<String>(entities.size()*patterns.length);

		String qpattern;
		for(String c: entities)
		{
			for(String pattern: patterns)
			{
				qpattern = pattern.replaceFirst("[$]", c);
				qpattern = "\""+qpattern+"\"";
				queries.add(qpattern);
			}
		}
		return queries;
	}

	//Test if two individuals are connected by property X
	//e.g."Fever is a symptom of Malaria"
	public static ArrayList<String> getQueriesFromPatterns2(List<String> properties, List<String> individuals, String[] patterns)
	{
		ArrayList<String> queries = new ArrayList<String>(properties.size()*(individuals.size()*2)*patterns.length);

		String qpattern;
		for(String p: properties)
		{
			for(String individual1: individuals)
			{
				for(String individual2: individuals)
				{
					for(String pattern: patterns)	//Generate all the possible combinations between individuals for each property
					{
						qpattern = pattern.replaceFirst("[$]", p);
						qpattern = qpattern.replaceFirst("[*]", individual1);
						qpattern = qpattern.replaceFirst("[*]", individual2);
						qpattern = "\""+qpattern+"\"";
						queries.add(qpattern);
					}
				}
			}
		}
		return queries;
	}

	//Get all the individuals that are connected to an individual through property X
	//e.g.: "* is a symptom of Malaria"
	public static ArrayList<String> getQueriesFromPatterns1(List<String> properties, List<String> individuals, String[] patterns)
	{
		ArrayList<String> queries = new ArrayList<String>(properties.size()*(individuals.size()*2)*patterns.length);

		String qpattern;
		for(String p: properties)
		{
			for(String individual: individuals)
			{
				for(String pattern: patterns)
				{
					qpattern = pattern.replaceFirst("[$]", p);
					qpattern = qpattern.replaceFirst("[*]$", individual);	//Replace the last *
					qpattern = "\""+qpattern+"\"";
					queries.add(qpattern);
				}
			}
		}
		return queries;
	}

	/**
	 * Clean the search engine results. Removes html and character entities tags, normalize blanks, and trims the string.
	 * @param result
	 * @return
	 */
	public static String cleanResult(String result)
	{
		result = result.replaceAll("(<[^>]+>)", "");	//Remove html tags
		result = result.replaceAll("(&[^;]+;)", "");		//Remove character entities such as &quot;
		result = result.replaceAll("\\s{2,}", " ");	//Blanks normalization
		result = result.trim(); 	//Remove leading and trailing whitespaces
		return result;
	}

	/**
	 * Create list of ORs from elements<br>
	 * E.g.: {dog,cat,bird} => (dog OR cat OR bird)
	 * @param elements
	 * @return
	 */
	public static String createORs(List<String> elements)
	{
		if(elements.size()>1)
		{
			StringBuilder sb = new StringBuilder();
			sb.append('(');
			for(int i=0; i<elements.size(); i++)
			{
				sb.append(elements.get(i));
				if(i!=elements.size()-1)
					sb.append(" OR ");
			}
			sb.append(')');
			return sb.toString();
		}
		else if(elements.size()==1)
			return elements.get(0);
		else
			return "";
	}


	/**
	 * Surround String with quotation marks, if the String is composed by more than one word
	 * @param s
	 * @return
	 */
	public static String surroundWithQuotationMarks(String s)
	{
		if(s.indexOf(' ')!=-1)
			return "\""+s+"\"";
		return s;
	}

}
