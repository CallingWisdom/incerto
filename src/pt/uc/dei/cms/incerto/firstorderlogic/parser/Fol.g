grammar Fol;
options {
language=Java;
}

tokens
{
	LEFTPAR	=	'(';
	RIGHTPAR=	')';
	NEGATION=	'!';
	EQUALS	=	'=';
	VIRGULE	=	',';
	STOP = 		'.';	
}

@header {
package firstorderlogic.parser;
import java.io.IOException;
import java.util.ArrayList;
import firstorderlogic.model.Connective;
import firstorderlogic.model.Constant;
import firstorderlogic.model.Formula;
import firstorderlogic.model.Predicate;
import firstorderlogic.model.PredicateEquals;
import firstorderlogic.model.Quantifier;
import firstorderlogic.model.Term;
import firstorderlogic.model.Variable;
import model.MLN;
import utils.LoggerUtils;

}

@lexer::header{
package firstorderlogic.parser;
}

@members {

MLN mln;
Formula f;

	private boolean isVariable(String x)
	{
		if(x.length()>= 1 &&  x.length()<= 2 && Character.isLowerCase(x.charAt(0)))
			return true;
		else
			return false;
	}

	public static MLN parse(String st) throws RecognitionException, IOException {
		return parse(st,new MLN());
	}
    	
	public static MLN parse(String st, MLN mln) throws RecognitionException, IOException {
		parseNames = parseNames;
		FolLexer lex = new FolLexer(new ANTLRFileStream(st));
		CommonTokenStream tokens = new CommonTokenStream(lex);	
		FolParser parser = new FolParser(tokens);	
		return parser.expr(mln);
	}
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/		

expr[MLN inp] returns [MLN value]
@init {
	mln = inp;
	LoggerUtils.addInfo("Parsing...");
}
@after {
	LoggerUtils.addInfo("Done.");
}
	:
	{f = new Formula();} w1=REAL? sentence (STOP)? {
									//TODO: Ignore non-weighted formulas when parsing result files, because they are predicate declaration (or had grammar rule to catch predicate declarations)
									if($w1.text!=null)
										f.setWeight(Double.valueOf($w1.text));	
									mln.addFOLRule(f);
									} 
	(WS* {f = new Formula();} w1=REAL? sentence (STOP)? {
									if($w1.text!=null)
										f.setWeight(Double.valueOf($w1.text));	
									mln.addFOLRule(f);
									})*
	WS? EOF {$value=mln;};
		
sentence:	(
		atomicsentence 
		| QUANTIFIER pn+=STRING (VIRGULE pn+=STRING)* {
								if($QUANTIFIER.text.compareTo("?forall") == 0)
									for(Object t: $pn)					
										f.addUniversal(new Variable(((Token)t).getText()));
								else
								{
									Quantifier existential = new Quantifier(Connective.QUANTIFIER_EXISTENTIAL);
									for(Object t: $pn)					
										existential.addVariable(new Variable(((Token)t).getText()));
									f.addElement(existential);
								}
								} sentence 
		| NEGATION {f.addNot();} sentence 
		| LEFTPAR {f.openBrackets();} sentence RIGHTPAR {f.closeBrackets();}
		) 
		(CONNECTIVE {
				if($CONNECTIVE.text.compareTo("=>") == 0)
					f.addConditional();
				else if ($CONNECTIVE.text.compareTo("^") == 0)
					f.addAnd();
				else if ($CONNECTIVE.text.compareTo("v") == 0)
					f.addOr();
				else
					f.addBiconditional();
		            } sentence)*
		;
		

atomicsentence
	:	pname=STRING LEFTPAR pn+=STRING (VIRGULE pn+=STRING)*  RIGHTPAR {
											ArrayList<Term> terms = new ArrayList<Term>();
											for(Object t: $pn)
											{
												if(isVariable(((Token)t).getText()))
													terms.add(new Variable(((Token)t).getText()));
												else
													terms.add(new Constant(((Token)t).getText()));
											}
											Predicate p = new Predicate($pname.text, terms);
											mln.addDeclaration(p.createDeclaration());
											f.addElement(p);
										}
		| p1=STRING EQUALS p2=STRING {
						Term t1, t2;
						if(isVariable($p1.text))
							t1 = new Variable($p1.text);
						else
							t1 = new Constant($p1.text);
						
						if(isVariable($p2.text))
							t2 = new Variable($p2.text);
						else
							t2 = new Constant($p2.text);
						
						f.addElement(new PredicateEquals(t1,t2));
					     } 
		;
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
 
CONNECTIVE
	:	'=>' | '^' | 'v' | '<=>';

QUANTIFIER
	:	'?forall' | '?exist';

STRING	:	('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | '-')+ ;

REAL	:	'-'? ('0'..'9')* '.' ('0'..'9')+;

//NEWLINE
    //: '\r'? '\n';

ML_COMMENT : '/*' .* '*/' {skip();} ;

LINE_COMMENT
    :   '//' ~('\n'|'\r')*  ('\r\n' | '\r' | '\n') {skip();}
    ;  
WS : (' ' |'\t' |'\n' |'\r' )+ {skip();} ;
