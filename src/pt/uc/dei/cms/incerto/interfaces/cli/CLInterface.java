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


import java.io.IOException;

import pt.uc.dei.cms.incerto.exceptions.IncertoException;
import pt.uc.dei.cms.incerto.interfaces.api.APInterface;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.utils.InOutUtils;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

/**
 * Command Line Interface.<br>
 * Type "--help" for the available commands.
 * @author Pedro Oliveira
 *
 */
public class CLInterface {

	public static void main(String[] args) {
		CLInterface cli = new CLInterface();
		cli.doMain(args);		
	}
	
	private void doMain(String[] args)
	{
		try {
			CLIOptions cli = CliFactory.parseArguments(CLIOptions.class, args);
			logCLIOptions(cli);

			if(cli.getHelp())
				printHelp();
			else
			{
				ReasoningResults results = APInterface.Incerto(cli.getOntologyLocation(), cli.isLearnIndividualsLocation()? cli.getLearnIndividualsLocation():null, cli.isInferenceIndividualsLocation()? cli.getInferenceIndividualsLocation(): null, cli.getQuery(), cli.isExtraFOLRulesLocation()? cli.getExtraFOLRulesLocation():null, cli.getDisableWeightLearning(), !cli.getMostLikelyAssignment());
				if(cli.isResultLocation())
					InOutUtils.writeToFile(cli.getResultLocation(), results.toString());
				else
					System.out.println(results.toString());
			}
			
		} catch (ArgumentValidationException e) {
			LoggerUtils.addError(e);
			e.printStackTrace();
			printHelp();
		} catch (IncertoException e) {
			e.printStackTrace();
			printHelp();
		} catch (IOException e) {
			LoggerUtils.addError(e);
			e.printStackTrace();
			printHelp();
		}

	}
	
	private void printHelp()
	{
		System.out.println("INCERTO HELP"); 
		System.out.println("Required <>\tOptional []");
		System.out.println("<-s <string>>\tSource ontology location.");
		System.out.println("[-l <string>]\tOntology with individuals to learn the weights. If omitted, the individuals of the source ontology are used for this task.");
		System.out.println("[-i <string>]\tOntology with individuals to be used in the inference process. If omitted, the individuals of the source ontology are used for this task.");
		System.out.println("[-r <string>]\tResults file location. If omitted, results are written to the console.");
		System.out.println("<-q <string>>\tQuery predicates. They can be composed by the name of a class or property(e.g., Person;isFatherOf) with (optional) additional information about individuals (e.g., Person(Pedro);isFatherOf(x,Pedro)). Multiple query predicates can be added, separated by a semicolon (e.g., Person;Person(Pedro);isFatherOf;isFatherOf(x,Pedro) is a valid query). Free variables must be represented as lowercase digits.");
		System.out.println("[-e <string>]\tExtra first-order logic rules file location.");
		System.out.println("[--disablewl]\tDisable weight learning.");
		System.out.println("[--mla]\tIf set, instead of the probabilities, the most likely assignments to all query atoms are returned.");
		System.out.println("[--help]\tThis help!");
	}
	
	private void logCLIOptions(CLIOptions cli)
	{
		LoggerUtils.addConfig("CLIOption <-s>: "+cli.getOntologyLocation());
		if(cli.isLearnIndividualsLocation())
			LoggerUtils.addConfig("CLIOption [-l]: "+cli.isLearnIndividualsLocation()+"("+cli.getLearnIndividualsLocation()+")");
		else
			LoggerUtils.addConfig("CLIOption [-l]: "+cli.isLearnIndividualsLocation());
		if(cli.isInferenceIndividualsLocation())
			LoggerUtils.addConfig("CLIOption [-i]: "+cli.isInferenceIndividualsLocation()+"("+cli.getInferenceIndividualsLocation()+")");
		else
			LoggerUtils.addConfig("CLIOption [-i]: "+cli.isInferenceIndividualsLocation());	
		if(cli.isResultLocation())
			LoggerUtils.addConfig("CLIOption [-r]: "+cli.isResultLocation()+"("+cli.getResultLocation()+")");
		else
			LoggerUtils.addConfig("CLIOption [-r]: "+cli.isResultLocation());
		LoggerUtils.addConfig("CLIOption <-q>: "+cli.getQuery());
		if(cli.isExtraFOLRulesLocation())
			LoggerUtils.addConfig("CLIOption [-e]: "+cli.isExtraFOLRulesLocation()+"("+cli.getExtraFOLRulesLocation()+")");
		else
			LoggerUtils.addConfig("CLIOption [-e]: "+cli.isExtraFOLRulesLocation());
		LoggerUtils.addConfig("CLIOption [--disablewl]: "+cli.getDisableWeightLearning());
		LoggerUtils.addConfig("CLIOption [--mla]: "+cli.getMostLikelyAssignment());
		LoggerUtils.addConfig("CLIOption [--help]: "+cli.getHelp());	
	}
	
}
