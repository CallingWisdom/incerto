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

import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.visitors.MLNVisitor;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;

public interface MarkovLogicEngine {
	
	/**
	 * Performs inference in MLN <code>mln</code> using Evidence data <code>evidence</code> and a Query <code>query</code>
	 * @param mln
	 * @param evidence
	 * @param query
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	public ReasoningResults inference(MLN mln,Evidence evidence, Query query) throws MarkovLogicEngineException;
	/**
	 * Performs weight learning in MLN <code>mln</code> with Evidence data <code>evidence</code>
	 * @param mln
	 * @param evidence
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	public MLN weightlearning(MLN mln, Evidence evidence) throws MarkovLogicEngineException;
	/**
	 * Performs weight learning with Evidence data <code>learnEvidence</code>, followed by inference for the Query <code>query</code> with Evidence data <code>inferEvidence</code> in MLN <code>mln</code>.
	 * @param mln
	 * @param learnEvidence
	 * @param inferEvidence
	 * @param query
	 * @return
	 * @throws MarkovLogicEngineException
	 */
	public ReasoningResults inferencewithweightlearning(MLN mln, Evidence learnEvidence,Evidence inferEvidence, Query query) throws MarkovLogicEngineException;
	/**
	 * Returns the specific <code>MLNVisitor</code> for this engine.
	 * @return
	 */
	public MLNVisitor getVisitor();
}
