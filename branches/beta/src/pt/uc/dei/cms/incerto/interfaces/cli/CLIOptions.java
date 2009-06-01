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

package pt.uc.dei.cms.incerto.interfaces.cli;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface CLIOptions {
	
	@Option(shortName="s")
	String getOntologyLocation();
	
	@Option(shortName="l")
	String getLearnIndividualsLocation();
	boolean isLearnIndividualsLocation();
	
	@Option(shortName="i")
	String getInferenceIndividualsLocation();
	boolean isInferenceIndividualsLocation();
	
	@Option(shortName="r")
	String getResultLocation();	
	boolean isResultLocation();

	@Option(shortName="q")
	String getQuery();
	
	@Option(shortName="e")
	String getExtraFOLRulesLocation();	
	boolean isExtraFOLRulesLocation();
	
	@Option(longName="disablewl")
	boolean getDisableWeightLearning();
	
	@Option(longName="mla")
	boolean getMostLikelyAssignment();
	
	//@Option(helpRequest=true)
	@Option(longName="help")
	boolean getHelp();
}
