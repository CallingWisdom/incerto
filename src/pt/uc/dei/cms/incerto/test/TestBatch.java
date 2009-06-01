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

import java.io.File;

import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;

public class TestBatch {


	public static void main(String[] args) throws OntologyProcessorException {
		String name = null;
		MLN kb;
		//System.out.println(kb);
		//System.out.println(kb.toStringEvidences());

		//System.out.println("Form\tInval\tClasses\tInst\tNot\tAnd\tOr\tEq\tDisj\tNome");
		//System.out.println("Formulas: "+kb.getFormulas().size()+" Classes: "+kb.getDeclarations().size());

		File folder = new File("d:/ontologias/rules");

		for(File fi: folder.listFiles())
		{
			if(fi.isFile())
			{
				name = fi.getName();
				//name = "earthrealm.owl";
				System.out.print(name+" | ");
				kb = new parserOWLAPI().onto2MLN("d:/ontologias/rules/"+name);
				if(kb==null)
				{
					System.out.println("Error");
					continue;
				}
				/*
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
			*/
				System.out.println(kb.getRules().size());
			}
		}



		//System.out.println("not: "+not+"\nand: "+and+"\nor: "+or+"\neq: "+eq+"\ndisj: "+disj);
		//System.out.println("Invalid: "+kb.getInvalid());

		/*BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(name+".mln"));
			out.write(kb.toString());
			out.close();

			out = new BufferedWriter(new FileWriter(name+".db"));
			out.write(kb.toStringEvidences());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}

}
