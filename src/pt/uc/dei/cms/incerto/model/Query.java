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

package pt.uc.dei.cms.incerto.model;

import java.util.ArrayList;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.utils.StringUtils;


/**
 * Markov Logic engine query.<br>
 * It is formed by a set of Predicates and a type (Conditional or Most Likely Assignment).
 * @author Pedro Oliveira
 *
 */
public class Query implements Cloneable{

	//Query Type: Conditional (COND) or Most Likely Assignment (MLA)
	public enum QueryType{COND, MLA};
	
	private ArrayList<Predicate>  queryPredicates;
	private QueryType queryType;
	
	public Query()
	{
		this.queryPredicates = new ArrayList<Predicate>();
		this.queryType = QueryType.COND;
	}
	
	public Query(QueryType queryType) {
		this.queryPredicates = new ArrayList<Predicate>();
		this.queryType = queryType;
	}

	public Query(ArrayList<Predicate> queryPredicates, QueryType queryType) {
		this.queryPredicates = queryPredicates;
		this.queryType = queryType;
	}
	
	public Query(Predicate queryPredicate, QueryType queryType)
	{
		this.queryPredicates = new ArrayList<Predicate>();
		this.queryPredicates.add(queryPredicate);
		this.queryType = queryType;
	}

	public ArrayList<Predicate> getQueryPredicates() {
		return queryPredicates;
	}

	public void setFormulas(ArrayList<Predicate> queryPredicates) {
		this.queryPredicates = queryPredicates;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	
	public void addQueryPredicate(Predicate p)
	{
		queryPredicates.add(p);
	}
	
	public void setQueryTypeCOND()
	{
		this.queryType = QueryType.COND;
	}
	
	public void setQueryTypeMLA()
	{
		this.queryType = QueryType.MLA;
	}
	
	/**
	 * Create query from String. Predicates are separated with semicolons. Free variables must be represented as lowercase digits. <br>
	 * E.g.: <br>
	 * Class;Property;Property(x,y);Class(A)
	 * @param query
	 * @param cond If true, query is Conditional. Otherwise, its Most Likely Assignment (MLA)
	 * @return
	 */
	public static Query parseQuery(String query, boolean cond)
	{
		String[] elems = StringUtils.removeWS(query).split(";");
		ArrayList<Predicate> predicates = new ArrayList<Predicate>();
		int lpar,rpar;
		String pname;
		Predicate p;
		for(String st: elems)
		{
			lpar = st.indexOf('(');
			rpar = st.indexOf(')');
			if(lpar!=-1 && rpar !=-1)
			{
				pname = st.substring(0, lpar);

				ArrayList<Term> pterms = new ArrayList<Term>();
				String terms = st.substring(lpar+1, rpar);
				for(String term: terms.split(","))
				{
					if(term.length()>=1 && term.length()<=2 && Character.isLowerCase(term.charAt(0)))
						pterms.add(new Variable(term));
					else
						pterms.add(new Constant(term));
				}
				p = new Predicate(pname,pterms);
			}
			else
				p = new Predicate(st);

			predicates.add(p);
	
		}
		if(cond)
			return new Query(predicates,QueryType.COND);
		else
			return new Query(predicates,QueryType.MLA);
	}
	
	
	/**
	 * Create query from String. Predicates are separated with semicolons. Free variables must be represented as lowercase digits. <br>
	 * E.g.: <br>
	 * Class;Property;Property(x,y);Class(A)
	 * @param query
	 * @return
	 */
	public static Query parseQuery(String query)
	{
		return parseQuery(query, true);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<queryPredicates.size(); i++)
		{
			sb.append(queryPredicates.get(i).toString());
			if(i!=queryPredicates.size()-1)
				sb.append(',');
		}
		return sb.toString();
	}
	
	@Override
	public Object clone()
	{
		Query q = new Query();
		q.setQueryType(this.queryType);
		for(Predicate p: this.queryPredicates)
			q.addQueryPredicate((Predicate)p.clone());
		return q;
	}
}
