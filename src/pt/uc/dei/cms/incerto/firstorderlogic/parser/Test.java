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

package pt.uc.dei.cms.incerto.firstorderlogic.parser;


import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import pt.uc.dei.cms.incerto.model.MLN;


public class Test {
	
	public static void main(String[] args)
	{
		String input = "?forall x P(x,\nAs) => (?exist y P(x,y) v (x=y)) <=> ((P(A)))//Pedro\n/*sad*/\n9.8234 P(x,y)\n\t";
		System.out.println(input);
		FolLexer lex = new FolLexer(new ANTLRStringStream(input));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

        FolParser parser = new FolParser(tokens);
        
        try {
            System.out.println(parser.expr(new MLN()));
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }

		
	}

}
