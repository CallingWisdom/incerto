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


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class FolLexer extends Lexer {
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

    public FolLexer() {;} 
    public FolLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public FolLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g"; }

    // $ANTLR start "LEFTPAR"
    public final void mLEFTPAR() throws RecognitionException {
        try {
            int _type = LEFTPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:11:9: ( '(' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:11:11: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTPAR"

    // $ANTLR start "RIGHTPAR"
    public final void mRIGHTPAR() throws RecognitionException {
        try {
            int _type = RIGHTPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:12:10: ( ')' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:12:12: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTPAR"

    // $ANTLR start "NEGATION"
    public final void mNEGATION() throws RecognitionException {
        try {
            int _type = NEGATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:13:10: ( '!' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:13:12: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEGATION"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:14:8: ( '=' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:14:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "VIRGULE"
    public final void mVIRGULE() throws RecognitionException {
        try {
            int _type = VIRGULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:15:9: ( ',' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:15:11: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VIRGULE"

    // $ANTLR start "STOP"
    public final void mSTOP() throws RecognitionException {
        try {
            int _type = STOP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:16:6: ( '.' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:16:8: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STOP"

    // $ANTLR start "CONNECTIVE"
    public final void mCONNECTIVE() throws RecognitionException {
        try {
            int _type = CONNECTIVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:145:2: ( '=>' | '^' | 'v' | '<=>' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case '=':
                {
                alt1=1;
                }
                break;
            case '^':
                {
                alt1=2;
                }
                break;
            case 'v':
                {
                alt1=3;
                }
                break;
            case '<':
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:145:4: '=>'
                    {
                    match("=>"); 


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:145:11: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:145:17: 'v'
                    {
                    match('v'); 

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:145:23: '<=>'
                    {
                    match("<=>"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONNECTIVE"

    // $ANTLR start "QUANTIFIER"
    public final void mQUANTIFIER() throws RecognitionException {
        try {
            int _type = QUANTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:148:2: ( '?forall' | '?exist' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='?') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='f') ) {
                    alt2=1;
                }
                else if ( (LA2_1=='e') ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:148:4: '?forall'
                    {
                    match("?forall"); 


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:148:16: '?exist'
                    {
                    match("?exist"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUANTIFIER"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:150:8: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )+ )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:150:10: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )+
            {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:150:10: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='-'||(LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:
            	    {
            	    if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:6: ( ( '-' )? ( '0' .. '9' )* '.' ( '0' .. '9' )+ )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:8: ( '-' )? ( '0' .. '9' )* '.' ( '0' .. '9' )+
            {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:8: ( '-' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='-') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:8: '-'
                    {
                    match('-'); 

                    }
                    break;

            }

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:13: ( '0' .. '9' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:14: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match('.'); 
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:29: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:152:30: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REAL"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:157:12: ( '/*' ( . )* '*/' )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:157:14: '/*' ( . )* '*/'
            {
            match("/*"); 

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:157:19: ( . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='/') ) {
                        alt7=2;
                    }
                    else if ( ((LA7_1>='\u0000' && LA7_1<='.')||(LA7_1>='0' && LA7_1<='\uFFFF')) ) {
                        alt7=1;
                    }


                }
                else if ( ((LA7_0>='\u0000' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:157:19: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match("*/"); 

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' ) )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' )
            {
            match("//"); 

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:14: (~ ( '\\n' | '\\r' ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='\t')||(LA8_0>='\u000B' && LA8_0<='\f')||(LA8_0>='\u000E' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:14: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:29: ( '\\r\\n' | '\\r' | '\\n' )
            int alt9=3;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\r') ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1=='\n') ) {
                    alt9=1;
                }
                else {
                    alt9=2;}
            }
            else if ( (LA9_0=='\n') ) {
                alt9=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:30: '\\r\\n'
                    {
                    match("\r\n"); 


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:39: '\\r'
                    {
                    match('\r'); 

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:160:46: '\\n'
                    {
                    match('\n'); 

                    }
                    break;

            }

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:162:4: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:162:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:162:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\t' && LA10_0<='\n')||LA10_0=='\r'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:8: ( LEFTPAR | RIGHTPAR | NEGATION | EQUALS | VIRGULE | STOP | CONNECTIVE | QUANTIFIER | STRING | REAL | ML_COMMENT | LINE_COMMENT | WS )
        int alt11=13;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:10: LEFTPAR
                {
                mLEFTPAR(); 

                }
                break;
            case 2 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:18: RIGHTPAR
                {
                mRIGHTPAR(); 

                }
                break;
            case 3 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:27: NEGATION
                {
                mNEGATION(); 

                }
                break;
            case 4 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:36: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 5 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:43: VIRGULE
                {
                mVIRGULE(); 

                }
                break;
            case 6 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:51: STOP
                {
                mSTOP(); 

                }
                break;
            case 7 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:56: CONNECTIVE
                {
                mCONNECTIVE(); 

                }
                break;
            case 8 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:67: QUANTIFIER
                {
                mQUANTIFIER(); 

                }
                break;
            case 9 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:78: STRING
                {
                mSTRING(); 

                }
                break;
            case 10 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:85: REAL
                {
                mREAL(); 

                }
                break;
            case 11 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:90: ML_COMMENT
                {
                mML_COMMENT(); 

                }
                break;
            case 12 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:101: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 13 :
                // C:\\Documents and Settings\\Pedro\\Ambiente de trabalho\\Incerto\\grammar\\Fol.g:1:114: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\4\uffff\1\17\1\uffff\1\21\1\uffff\1\7\1\uffff\2\14\10\uffff";
    static final String DFA11_eofS =
        "\24\uffff";
    static final String DFA11_minS =
        "\1\11\3\uffff\1\76\1\uffff\1\60\1\uffff\1\55\1\uffff\2\56\1\uffff"+
        "\1\52\6\uffff";
    static final String DFA11_maxS =
        "\1\172\3\uffff\1\76\1\uffff\1\71\1\uffff\1\172\1\uffff\2\71\1\uffff"+
        "\1\57\6\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\5\1\uffff\1\7\1\uffff\1\10\2\uffff"+
        "\1\11\1\uffff\1\15\1\4\1\12\1\6\1\13\1\14";
    static final String DFA11_specialS =
        "\24\uffff}>";
    static final String[] DFA11_transitionS = {
            "\2\16\2\uffff\1\16\22\uffff\1\16\1\3\6\uffff\1\1\1\2\2\uffff"+
            "\1\5\1\12\1\6\1\15\12\13\2\uffff\1\7\1\4\1\uffff\1\11\1\uffff"+
            "\32\14\3\uffff\1\7\1\14\1\uffff\25\14\1\10\4\14",
            "",
            "",
            "",
            "\1\7",
            "",
            "\12\20",
            "",
            "\1\14\2\uffff\12\14\7\uffff\32\14\4\uffff\1\14\1\uffff\32"+
            "\14",
            "",
            "\1\20\1\uffff\12\13",
            "\1\20\1\uffff\12\13",
            "",
            "\1\22\4\uffff\1\23",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( LEFTPAR | RIGHTPAR | NEGATION | EQUALS | VIRGULE | STOP | CONNECTIVE | QUANTIFIER | STRING | REAL | ML_COMMENT | LINE_COMMENT | WS );";
        }
    }
 

}