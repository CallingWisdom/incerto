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

package pt.uc.dei.cms.incerto.test;

import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Connective;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.FormulaElement;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;

public class TesteWrite {


	public static void main(String[] args) throws OntologyProcessorException {
		String name = "Ontology1238592737531";
		MLN kb;


		
		kb = new parserOWLAPI().onto2MLN("d:/"+name+".owl");
		System.out.println(kb);
		System.out.println(kb.toStringEvidences());


		System.out.println("Form\tInval\tClasses\tInst\tNot\tAnd\tOr\tEq\tDisj\tNome");
		
		if(kb==null)
		{
			System.out.println("\t\t\t\t\t\t\t\t\t"+name);
			return;
		}
		
		int not=0, and=0, or=0, eq=0, disj=0;
		for(Formula f: kb.getFormulas())
		{
			for(FormulaElement fe: f.getElements())
			{
				if(fe.equals(Connective.LOGICAL_CONNECTIVE_AND))
					and++;
				else if(fe.equals(Connective.LOGICAL_CONNECTIVE_OR))
					or++;
				else if(fe.equals(Connective.LOGICAL_CONNECTIVE_NOT))
					not++;
				else if(fe.equals(Connective.LOGICAL_CONNECTIVE_BICONDITIONAL))
					eq++;
				else if(fe.equals(Connective.LOGICAL_CONNECTIVE_DISJUNCTION))
					disj++;
			}
		}
		System.out.println(kb.getFormulas().size()+"\t"+kb.getInvalid()+"\t"+kb.getDeclarations().size()+"\t"+kb.getEvidences().size()+"\t"+not+"\t"+and+"\t"+or+"\t"+eq+"\t"+disj+"\t"+name);
/*

		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("d:/ontologias/"+name+".mln"));
			out.write(kb.toString());
			out.close();

			out = new BufferedWriter(new FileWriter("d:/ontologias/"+name+".db"));
			out.write(kb.toStringEvidences());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
