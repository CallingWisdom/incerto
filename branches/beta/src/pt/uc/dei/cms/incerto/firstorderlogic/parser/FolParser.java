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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import pt.uc.dei.cms.incerto.firstorderlogic.model.Connective;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Constant;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Predicate;
import pt.uc.dei.cms.incerto.firstorderlogic.model.PredicateEquals;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Quantifier;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Term;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Variable;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;

public class FolParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LEFTPAR", "RIGHTPAR", "NEGATION", "EQUALS", "VIRGULE", "STOP", "REAL", "WS", "QUANTIFIER", "STRING", "CONNECTIVE", "ML_COMMENT", "LINE_COMMENT"
    };
    public static final int ML_COMMENT=15;
    public static final int REAL=10;
    public static final int VIRGULE=8;
    public static final int WS=11;
    public static final int NEGATION=6;
    public static final int CONNECTIVE=14;
    public static final int LINE_COMMENT=16;
    public static final int RIGHTPAR=5;
    public static final int LEFTPAR=4;
    public static final int STOP=9;
    public static final int EQUALS=7;
    public static final int QUANTIFIER=12;
    public static final int EOF=-1;
    public static final int STRING=13;

    // delegates
    // delegators


        public FolParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public FolParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return FolParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g"; }



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
    		FolLexer lex = new FolLexer(new ANTLRFileStream(st));
    		CommonTokenStream tokens = new CommonTokenStream(lex);	
    		FolParser parser = new FolParser(tokens);	
    		return parser.expr(mln);
    	}



    // $ANTLR start "expr"
    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:64:1: expr[MLN inp] returns [MLN value] : (w1= REAL )? sentence ( STOP )? ( ( WS )* (w1= REAL )? sentence ( STOP )? )* ( WS )? EOF ;
    public final MLN expr(MLN inp) throws RecognitionException {
        MLN value = null;
        
        Token w1=null;


        	mln = inp;
        	LoggerUtils.addInfo("Parsing...");

        try {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:72:2: ( (w1= REAL )? sentence ( STOP )? ( ( WS )* (w1= REAL )? sentence ( STOP )? )* ( WS )? EOF )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:73:2: (w1= REAL )? sentence ( STOP )? ( ( WS )* (w1= REAL )? sentence ( STOP )? )* ( WS )? EOF
            {
            f = new Formula();
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:73:25: (w1= REAL )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==REAL) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:73:25: w1= REAL
                    {
                    w1=(Token)match(input,REAL,FOLLOW_REAL_in_expr119); 

                    }
                    break;

            }

            pushFollow(FOLLOW_sentence_in_expr122);
            sentence();

            state._fsp--;

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:73:41: ( STOP )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==STOP) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:73:42: STOP
                    {
                    match(input,STOP,FOLLOW_STOP_in_expr125); 

                    }
                    break;

            }


            									if((w1!=null?w1.getText():null)!=null)            			
            										f.setWeight(Double.valueOf((w1!=null?w1.getText():null)));
            									mln.addFOLRule(f);
            									 
            						     
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:2: ( ( WS )* (w1= REAL )? sentence ( STOP )? )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==WS) ) {
                    int LA6_1 = input.LA(2);

                    if ( (LA6_1==LEFTPAR||LA6_1==NEGATION||(LA6_1>=REAL && LA6_1<=STRING)) ) {
                        alt6=1;
                    }


                }
                else if ( (LA6_0==LEFTPAR||LA6_0==NEGATION||LA6_0==REAL||(LA6_0>=QUANTIFIER && LA6_0<=STRING)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:3: ( WS )* (w1= REAL )? sentence ( STOP )?
            	    {
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:3: ( WS )*
            	    loop3:
            	    do {
            	        int alt3=2;
            	        int LA3_0 = input.LA(1);

            	        if ( (LA3_0==WS) ) {
            	            alt3=1;
            	        }


            	        switch (alt3) {
            	    	case 1 :
            	    	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:3: WS
            	    	    {
            	    	    match(input,WS,FOLLOW_WS_in_expr134); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop3;
            	        }
            	    } while (true);

            	    f = new Formula();
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:30: (w1= REAL )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==REAL) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:30: w1= REAL
            	            {
            	            w1=(Token)match(input,REAL,FOLLOW_REAL_in_expr141); 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_sentence_in_expr144);
            	    sentence();

            	    state._fsp--;

            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:46: ( STOP )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==STOP) ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:78:47: STOP
            	            {
            	            match(input,STOP,FOLLOW_STOP_in_expr147); 

            	            }
            	            break;

            	    }

            	    										if((w1!=null?w1.getText():null)!=null)
                        										f.setWeight(Double.valueOf((w1!=null?w1.getText():null)));
                        									mln.addFOLRule(f);
                        									
            	    										
            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:83:2: ( WS )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==WS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:83:2: WS
                    {
                    match(input,WS,FOLLOW_WS_in_expr156); 

                    }
                    break;

            }

            match(input,EOF,FOLLOW_EOF_in_expr159); 
            value =mln;

            }


            	LoggerUtils.addInfo("Done.");

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "expr"


    // $ANTLR start "sentence"
    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:85:1: sentence : ( atomicsentence | QUANTIFIER pn+= STRING ( VIRGULE pn+= STRING )* sentence | NEGATION sentence | LEFTPAR sentence RIGHTPAR ) ( CONNECTIVE sentence )* ;
    public final void sentence() throws RecognitionException {
        Token QUANTIFIER1=null;
        Token CONNECTIVE2=null;
        Token pn=null;
        List list_pn=null;

        try {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:85:9: ( ( atomicsentence | QUANTIFIER pn+= STRING ( VIRGULE pn+= STRING )* sentence | NEGATION sentence | LEFTPAR sentence RIGHTPAR ) ( CONNECTIVE sentence )* )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:85:11: ( atomicsentence | QUANTIFIER pn+= STRING ( VIRGULE pn+= STRING )* sentence | NEGATION sentence | LEFTPAR sentence RIGHTPAR ) ( CONNECTIVE sentence )*
            {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:85:11: ( atomicsentence | QUANTIFIER pn+= STRING ( VIRGULE pn+= STRING )* sentence | NEGATION sentence | LEFTPAR sentence RIGHTPAR )
            int alt9=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt9=1;
                }
                break;
            case QUANTIFIER:
                {
                alt9=2;
                }
                break;
            case NEGATION:
                {
                alt9=3;
                }
                break;
            case LEFTPAR:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:86:3: atomicsentence
                    {
                    pushFollow(FOLLOW_atomicsentence_in_sentence174);
                    atomicsentence();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:87:5: QUANTIFIER pn+= STRING ( VIRGULE pn+= STRING )* sentence
                    {
                    QUANTIFIER1=(Token)match(input,QUANTIFIER,FOLLOW_QUANTIFIER_in_sentence181); 
                    pn=(Token)match(input,STRING,FOLLOW_STRING_in_sentence185); 
                    if (list_pn==null) list_pn=new ArrayList();
                    list_pn.add(pn);

                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:87:27: ( VIRGULE pn+= STRING )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==VIRGULE) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:87:28: VIRGULE pn+= STRING
                    	    {
                    	    match(input,VIRGULE,FOLLOW_VIRGULE_in_sentence188); 
                    	    pn=(Token)match(input,STRING,FOLLOW_STRING_in_sentence192); 
                    	    if (list_pn==null) list_pn=new ArrayList();
                    	    list_pn.add(pn);


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    								if((QUANTIFIER1!=null?QUANTIFIER1.getText():null).compareTo("?forall") == 0)
                    									for(Object t: list_pn)					
                    										f.addUniversal(new Variable(((Token)t).getText()));
                    								else
                    								{
                    									Quantifier existential = new Quantifier(Connective.QUANTIFIER_EXISTENTIAL);                
                    									for(Object t: list_pn)					
                    										existential.addVariable(new Variable(((Token)t).getText()));
                    									f.addElement(existential);
                    								}
                    								
                    pushFollow(FOLLOW_sentence_in_sentence198);
                    sentence();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:95:5: NEGATION sentence
                    {
                    match(input,NEGATION,FOLLOW_NEGATION_in_sentence205); 
                    f.addNot();
                    pushFollow(FOLLOW_sentence_in_sentence209);
                    sentence();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:96:5: LEFTPAR sentence RIGHTPAR
                    {
                    match(input,LEFTPAR,FOLLOW_LEFTPAR_in_sentence216); 
                    f.openBrackets();
                    pushFollow(FOLLOW_sentence_in_sentence220);
                    sentence();

                    state._fsp--;

                    match(input,RIGHTPAR,FOLLOW_RIGHTPAR_in_sentence222); 
                    f.closeBrackets();

                    }
                    break;

            }

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:98:3: ( CONNECTIVE sentence )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==CONNECTIVE) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:98:4: CONNECTIVE sentence
            	    {
            	    CONNECTIVE2=(Token)match(input,CONNECTIVE,FOLLOW_CONNECTIVE_in_sentence234); 

            	    				if((CONNECTIVE2!=null?CONNECTIVE2.getText():null).compareTo("=>") == 0)
            	    					f.addConditional();
            	    				else if ((CONNECTIVE2!=null?CONNECTIVE2.getText():null).compareTo("^") == 0)
            	    					f.addAnd();
            	    				else if ((CONNECTIVE2!=null?CONNECTIVE2.getText():null).compareTo("v") == 0)
            	    					f.addOr();
            	    				else
            	    					f.addBiconditional();
            	    		            
            	    pushFollow(FOLLOW_sentence_in_sentence238);
            	    sentence();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "sentence"


    // $ANTLR start "atomicsentence"
    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:111:1: atomicsentence : (pname= STRING LEFTPAR pn+= STRING ( VIRGULE pn+= STRING )* RIGHTPAR | p1= STRING EQUALS p2= STRING );
    public final void atomicsentence() throws RecognitionException {
        Token pname=null;
        Token p1=null;
        Token p2=null;
        Token pn=null;
        List list_pn=null;

        try {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:112:2: (pname= STRING LEFTPAR pn+= STRING ( VIRGULE pn+= STRING )* RIGHTPAR | p1= STRING EQUALS p2= STRING )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==STRING) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==LEFTPAR) ) {
                    alt12=1;
                }
                else if ( (LA12_1==EQUALS) ) {
                    alt12=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:112:4: pname= STRING LEFTPAR pn+= STRING ( VIRGULE pn+= STRING )* RIGHTPAR
                    {
                    pname=(Token)match(input,STRING,FOLLOW_STRING_in_atomicsentence257); 
                    match(input,LEFTPAR,FOLLOW_LEFTPAR_in_atomicsentence259); 
                    pn=(Token)match(input,STRING,FOLLOW_STRING_in_atomicsentence263); 
                    if (list_pn==null) list_pn=new ArrayList();
                    list_pn.add(pn);

                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:112:36: ( VIRGULE pn+= STRING )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0==VIRGULE) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:112:37: VIRGULE pn+= STRING
                    	    {
                    	    match(input,VIRGULE,FOLLOW_VIRGULE_in_atomicsentence266); 
                    	    pn=(Token)match(input,STRING,FOLLOW_STRING_in_atomicsentence270); 
                    	    if (list_pn==null) list_pn=new ArrayList();
                    	    list_pn.add(pn);


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    match(input,RIGHTPAR,FOLLOW_RIGHTPAR_in_atomicsentence275); 

                    											ArrayList<Term> terms = new ArrayList<Term>();
                    											for(Object t: list_pn)
                    											{
                    												if(isVariable(((Token)t).getText()))
                    													terms.add(new Variable(((Token)t).getText()));
                    												else
                    													terms.add(new Constant(((Token)t).getText()));
                    											}
                    											Predicate p = new Predicate((pname!=null?pname.getText():null), terms);
                    											mln.addDeclaration(p.createDeclaration());
                    											f.addElement(p);
                    										

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:125:5: p1= STRING EQUALS p2= STRING
                    {
                    p1=(Token)match(input,STRING,FOLLOW_STRING_in_atomicsentence285); 
                    match(input,EQUALS,FOLLOW_EQUALS_in_atomicsentence287); 
                    p2=(Token)match(input,STRING,FOLLOW_STRING_in_atomicsentence291); 

                    						Term t1, t2;
                    						if(isVariable((p1!=null?p1.getText():null)))
                    							t1 = new Variable((p1!=null?p1.getText():null));
                    						else
                    							t1 = new Constant((p1!=null?p1.getText():null));
                    						
                    						if(isVariable((p2!=null?p2.getText():null)))
                    							t2 = new Variable((p2!=null?p2.getText():null));
                    						else
                    							t2 = new Constant((p2!=null?p2.getText():null));
                    						
                    						f.addElement(new PredicateEquals(t1,t2));
                    					     

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "atomicsentence"

    // Delegated rules


 

    public static final BitSet FOLLOW_REAL_in_expr119 = new BitSet(new long[]{0x0000000000003050L});
    public static final BitSet FOLLOW_sentence_in_expr122 = new BitSet(new long[]{0x0000000000003E50L});
    public static final BitSet FOLLOW_STOP_in_expr125 = new BitSet(new long[]{0x0000000000003C50L});
    public static final BitSet FOLLOW_WS_in_expr134 = new BitSet(new long[]{0x0000000000003C50L});
    public static final BitSet FOLLOW_REAL_in_expr141 = new BitSet(new long[]{0x0000000000003050L});
    public static final BitSet FOLLOW_sentence_in_expr144 = new BitSet(new long[]{0x0000000000003E50L});
    public static final BitSet FOLLOW_STOP_in_expr147 = new BitSet(new long[]{0x0000000000003C50L});
    public static final BitSet FOLLOW_WS_in_expr156 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expr159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicsentence_in_sentence174 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_QUANTIFIER_in_sentence181 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_sentence185 = new BitSet(new long[]{0x0000000000003150L});
    public static final BitSet FOLLOW_VIRGULE_in_sentence188 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_sentence192 = new BitSet(new long[]{0x0000000000003150L});
    public static final BitSet FOLLOW_sentence_in_sentence198 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_NEGATION_in_sentence205 = new BitSet(new long[]{0x0000000000003050L});
    public static final BitSet FOLLOW_sentence_in_sentence209 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_LEFTPAR_in_sentence216 = new BitSet(new long[]{0x0000000000003050L});
    public static final BitSet FOLLOW_sentence_in_sentence220 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RIGHTPAR_in_sentence222 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_CONNECTIVE_in_sentence234 = new BitSet(new long[]{0x0000000000003050L});
    public static final BitSet FOLLOW_sentence_in_sentence238 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_STRING_in_atomicsentence257 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_LEFTPAR_in_atomicsentence259 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_atomicsentence263 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_VIRGULE_in_atomicsentence266 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_atomicsentence270 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_RIGHTPAR_in_atomicsentence275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atomicsentence285 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_EQUALS_in_atomicsentence287 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_STRING_in_atomicsentence291 = new BitSet(new long[]{0x0000000000000002L});

}