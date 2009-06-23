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

package pt.uc.dei.cms.incerto.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.runtime.RecognitionException;

import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.AlchemyVisitor;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;
import pt.uc.dei.cms.incerto.firstorderlogic.parser.FolParser;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.model.Query.QueryType;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;
import pt.uc.dei.cms.incerto.utils.settings.AlchemySettings;



/**
 * Alchemy Markov Logic engine interface.
 * @author Pedro Oliveira
 *
 */
public class Alchemy implements MarkovLogicEngine {
	
	private String dumplocation = AlchemySettings.getInstance().DUMP_LOCATION;
	private AlchemyWrapper wrapper = new AlchemyWrapper();
	private MLNVisitor visitor = new AlchemyVisitor();

	//@Override
	public ReasoningResults inference(MLN mln, Evidence evidence, Query query) throws MarkovLogicEngineException {
		String name = mln.getName()+System.currentTimeMillis();
		String i = dumplocation+name+".mln";

		try {
			InOutUtils.writeToFile(i, visitor.MLNELementToString(mln, true));
		} catch (IOException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on writing dump files",e1);
		}

		ReasoningResults res = pinference(i, name, evidence, query, mln);
		
		//Delete dump files
		InOutUtils.deleteFileOnExit(i);
		InOutUtils.deleteFileOnExit(dumplocation+name+".result");
		InOutUtils.deleteFileOnExit(dumplocation+name+".db");

		return res;
	}
	
	//@Override
	public MLN weightlearning(MLN mln, Evidence evidence) throws MarkovLogicEngineException {
		String name = mln.getName()+System.currentTimeMillis();
		String o, i = dumplocation+name+".mln";

		try {
			InOutUtils.writeToFile(i, visitor.MLNELementToString(mln, true));
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on writing dump files",e);
		}
		
		o = pweightlearning(i, name, evidence);
		
		MLN learned;
		try {
			parseResultMLN(o);
			learned = FolParser.parse(o);
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on reading Alchemy's result MLN file",e);
		} catch (RecognitionException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on parsing Alchemy's result MLN file",e);
		}

		MLN res = (MLN)mln.clone();
		MarkovLogicEngineUtils.updateMLNWeights(res, learned, visitor);
		
		
		//Delete dump files
		InOutUtils.deleteFileOnExit(i);
		InOutUtils.deleteFileOnExit(o);
		InOutUtils.deleteFileOnExit(dumplocation+name+"-out.db");
		
		return res;
	}
	

	//@Override
	public ReasoningResults inferencewithweightlearning(MLN mln, Evidence learnEvidence,Evidence inferEvidence, Query query) throws MarkovLogicEngineException{
		String name = mln.getName()+System.currentTimeMillis();
		String o, i = dumplocation+name+".mln";
		
		try {
			InOutUtils.writeToFile(i, visitor.MLNELementToString(mln, true));
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on writing dump files",e);
		}
		
		o = pweightlearning(i, name, learnEvidence);
		
		return pinference(o, name, inferEvidence, query, mln);
	}
	
	/**
	 * Internal method for inference.
	 * @param mln
	 * @param filename
	 * @param evidence
	 * @param query
	 * @param kb
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	private ReasoningResults pinference(String mln, String filename, Evidence evidence, Query query, MLN kb) throws MarkovLogicEngineException {
		String r = dumplocation+filename+".result";
		String e = dumplocation+filename+".db";
		String inftype, stquery, extra = AlchemySettings.getInstance().INFERENCE_EXTRA;
		int result;
		
		try {
			InOutUtils.writeToFile(e, visitor.MLNELementToString(evidence, true));	
		} catch (IOException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on writing dump files",e1);
		}

		if(query.getQueryType().compareTo(QueryType.MLA)==0)
			inftype= AlchemySettings.getInstance().INFERENCE_MLA;
		else
			inftype= AlchemySettings.getInstance().INFERENCE_COND;

		stquery = MarkovLogicEngineUtils.parseQuery(query, kb, visitor, ",");
		
		try {
			if((result = wrapper.infer(" -i "+mln+" -r "+r+" -e "+e+" "+inftype+" "+extra+" -q "+stquery+"")) == 0)
				//return new ReasoningResults(FolParser.parse(InOutUtils.readStringFromFile(r)).getFOLRules());
				return parseResults(InOutUtils.readListFromFile(r));
			else
				throw new MarkovLogicEngineException("Problem on using Alchemy's inference (Returned "+result+")");
		} catch (IOException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on reading Alchemy's results",e1);
		} catch (RecognitionException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on parsing Alchemy's results",e1);
		} catch (Exception e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on acessing Alchemy's inference",e1);	
		}
	}
	
	/**
	 * Internal method for weight learning
	 * @param mln
	 * @param filename
	 * @param evidence
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	private String pweightlearning(String mln, String filename, Evidence evidence) throws MarkovLogicEngineException {
		String o = dumplocation+filename+"-out.mln";
		String t = dumplocation+filename+"-out.db";
		String extra = AlchemySettings.getInstance().LEARN_EXTRA;
		int result;		
		
		try {
			InOutUtils.writeToFile(t, visitor.MLNELementToString(evidence, true));
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on writing dump files",e);
		}
		
			
		try {
			if((result = wrapper.learnwts(" -i "+mln+" -o "+o+" -t "+t+" "+extra)) != 0)
				throw new MarkovLogicEngineException("Problem on using Alchemy's weight learning (Returned "+result+")");
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on acessing Alchemy's weight learning",e);
		}

		return o;
	}

	
	/**
	 * Parse reasoning results file <br>
	 * E.g.: <br>
	 * P(Ind) 0.323 <br>
	 * Q(Ind) 0.9 <br>
	 * @param results
	 * @return
	 */
	private ReasoningResults parseResults(List<String> results)
	{
		ReasoningResults res = new ReasoningResults();
		for(String st: results)
			res.addFormula(parseResultPredicate(st));	
		return res;
	}
	
	/**
	 * Parse Alchemy's result predicate. E.g.: <br>
	 * P(Ind) 0.34
	 * @param st
	 * @return
	 */
	private Formula parseResultPredicate(String st)
	{
		Formula f = new Formula();
		int lpar,rpar;
		
		lpar = st.indexOf('(');
		rpar = st.indexOf(')');
		ArrayList<Term> pterms = new ArrayList<Term>();
		String terms = st.substring(lpar+1, rpar);
		for(String term: terms.split(","))
			pterms.add(new Constant(term.substring(2,term.length())));	//Remove attachment
		
		f.addElement(new Predicate(st.substring(2, lpar),pterms));	//Remove attachment
		if(st.length()>rpar+1)
			f.setWeight(Double.parseDouble(st.substring(rpar+2)));	
		return f;
	}
	
	/**
	 * Parses Alchemy's results MLN for the untransformed formulas (i.e., the formulas without being in CNF)<br>
	 * These formulas are contained in the comments below the transformed formulas<br>
	 * E.g.:<br>
	 *	// 0.3 P(x) => Q(x) <br>
	 *  Q(x) v !P(x) 
	 * @param o
	 * @throws IOException
	 */
	private void parseResultMLN(String o) throws IOException
	{
		List<String> mln = InOutUtils.readListFromFile(o);
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile("// [\\d-].*");	
		Matcher m;
		for(String st: mln)
		{
			m = p.matcher(st);
			if(m.matches())
				sb.append(st.substring(3, st.length()-1)+"\n");	//Remove comment operators(//)
		}
		InOutUtils.writeToFile(o, sb.toString());
	}

	//@Override
	public MLNVisitor getVisitor() {
		return visitor;
	}

}
