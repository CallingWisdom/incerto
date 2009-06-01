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

import org.antlr.runtime.RecognitionException;

import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.PyMLNsVisitor;
import pt.uc.dei.cms.incerto.firstorderlogic.parser.FolParser;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.model.Query.QueryType;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;
import pt.uc.dei.cms.incerto.utils.settings.PyMLNsSettings;



/**
 * PyMLNs Markov Logic engine interface.
 * @author Pedro Oliveira
 *
 */
public class PyMLNs implements MarkovLogicEngine {
	
	private String dumplocation = PyMLNsSettings.getInstance().DUMP_LOCATION;
	private PyMLNsWrapper wrapper = new PyMLNsWrapper();
	private PyMLNsVisitor visitor = new PyMLNsVisitor();

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

		ReasoningResults res = pinference(i, name, evidence, query,mln);
		
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
			learned = FolParser.parse(o);
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on reading PyMLNs's result file",e);
		} catch (RecognitionException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on parsing PyMLNs's result file",e);
		}
		//System.out.println(res);
		MLN res = (MLN)mln.clone();
		MarkovLogicEngineUtils.updateMLNWeights(res, learned, visitor);
		
		//Delete dump files
		InOutUtils.deleteFileOnExit(i);
		InOutUtils.deleteFileOnExit(o);
		InOutUtils.deleteFileOnExit(dumplocation+name+"-out.db");
		
		return res;
	}
	

	//@Override
	public ReasoningResults inferencewithweightlearning(MLN mln, Evidence learnEvidence,Evidence inferEvidence, Query query) throws MarkovLogicEngineException {
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
		String inftype, stquery, debug="";
		int result;
		
		if(IncertoSettings.getInstance().DEBUG)
			debug = "debug";
		
		try {
			InOutUtils.writeToFile(e, visitor.MLNELementToString(evidence, true));	
		} catch (IOException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on writing dump files",e1);
		}

		if(query.getQueryType().compareTo(QueryType.MLA)!=0)
			inftype= PyMLNsSettings.getInstance().INFERENCE;
		else
			throw new MarkovLogicEngineException("PyMLNs doesn't support Most Likely Assignment (MLA) inference! (TIP: Use Alchemy)");

		stquery = MarkovLogicEngineUtils.parseQuery(query, kb, visitor, ";");
		
		try {
			if((result = wrapper.infer(" "+mln+" "+e+" "+r+" "+stquery+" "+inftype+" "+debug)) == 0)
				//return new ReasoningResults(FolParser.parse(InOutUtils.readStringFromFile(r)).getFOLRules());
				return parseResults(InOutUtils.readListFromFile(r));
			else
				throw new MarkovLogicEngineException("Problem on using PyMLNs's inference (Returned "+result+")");
		} catch (IOException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on reading PyMLNs's results",e1);
		} catch (RecognitionException e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on parsing PyMLNs's results",e1);
		} catch (Exception e1) {
			LoggerUtils.addError(e1);
			throw new MarkovLogicEngineException("Problem on acessing PyMLNs's inference",e1);	
		}
	}
	
	/**
	 * Internal method for weight learning.
	 * @param mln
	 * @param filename
	 * @param evidence
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	private String pweightlearning(String mln, String filename, Evidence evidence) throws MarkovLogicEngineException {
		String o = dumplocation+filename+"-out.mln";
		String t = dumplocation+filename+"-out.db";
		String mode = PyMLNsSettings.getInstance().WEIGHT_LEARNING;
		int result;		
		
		try {
			InOutUtils.writeToFile(t, visitor.MLNELementToString(evidence, true));
		} catch (IOException e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on writing dump files",e);
		}
		
			
		try {
			if((result = wrapper.learnwts(" "+mln+" "+t+" "+o+" "+mode)) != 0)
				throw new MarkovLogicEngineException("Problem on using PyMLNs's weight learning (Returned "+result+")");
		} catch (Exception e) {
			LoggerUtils.addError(e);
			throw new MarkovLogicEngineException("Problem on acessing PyMLNs's weight learning",e);
		}

		return o;
	}

	/**
	 * Parse reasoning results file <br>
	 * E.g.: <br>
	 * 0.323  P(Ind)  <br>
	 * 0.9  Q(Ind)  <br>
	 * @param results
	 * @return
	 */
	private ReasoningResults parseResults(ArrayList<String> results)
	{
		ReasoningResults res = new ReasoningResults();
		for(String st: results)
			res.addFormula(parseResultPredicate(st));	
		return res;
	}
	
	/**
	 * Parse PyMLNs result predicate. E.g.: <br>
	 * 0.34  P(Ind)  
	 * @param st
	 * @return
	 */
	private Formula parseResultPredicate(String st)
	{
		Formula f = new Formula();		
		String[] elems = st.split("\\s+");	
		
		//Predicate
		int lpar,rpar;	
		lpar = elems[1].indexOf('(');
		rpar = elems[1].indexOf(')');
		ArrayList<Term> pterms = new ArrayList<Term>();
		String terms = elems[1].substring(lpar+1, rpar);
		for(String term: terms.split(","))
			pterms.add(new Constant(term.substring(2,term.length())));		//Remove attachment
		f.addElement(new Predicate(elems[1].substring(2, lpar),pterms)); //Remove attachment
		
		//Weight
		f.setWeight(Double.parseDouble(elems[0]));
		return f;
	}


	//@Override
	public MLNVisitor getVisitor() {
		return visitor;
	}

}
