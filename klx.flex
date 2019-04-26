package klx.parser;
import java.io.FileReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import klx.Token;

%%

%public
%class Scanner
%function __yylex
%implements Parser.Lexer
%type Token
%line
%column

%{
  StringBuilder __string = new StringBuilder();

  public Scanner(String fileName) throws FileNotFoundException {
    __fileName = fileName;
    this.zzReader = new BufferedReader(new FileReader(__fileName));
  }

  public Scanner(String text, boolean unused) {
    __fileName = null;
    this.zzReader = new BufferedReader(new StringReader(text));
  }

  public static Scanner getStringScanner(String text) {
        return new Scanner(text, true);
  }



  @Override
  public Token getLVal() {
    return __lastToken;
  }

  @Override
  public int yylex() throws java.io.IOException {
		__lastToken = __yylex();
		return __lastToken.type;
	}

  @Override
  public void yyerror(String msg) {
    //todo
  }

  private Token getToken(int code, char c) {
    return getToken(code, Character.toString(c));
  }
  private Token getToken(int code, String str) {
    return new Token(code, __fileName, yyline+1, yycolumn+1, str);
  }
  private Token getToken(int code) {
    return getToken(code, yytext());
  }

  private Token __lastToken = null;
  private String __fileName = null;

%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = [ \t\f]

/* comments */
Comment = {BlockComment} | {LineComment}

BlockComment = "/*" ~"*/"
LineComment  = "//" {InputCharacter}* {LineTerminator}?

/* identifiers */
Identifier = [_a-zA-Z][_a-zA-Z0-9]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

/* floating point literals */        
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? 

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

OctDigit      = [0-7]
BinaryLiteral = {BasedWidth}? \' [bB] [01][01_]*
OctalLiteral  = {BasedWidth}? \' [oO] {OctDigit} (_|{OctDigit})*
HexLiteral    = {BasedWidth}? \' [hH] {HexDigit} (_|{HexDigit})*
DecLiteral    = {BasedWidth}? \' [dD] [0-9][0-9_]*
HexDigit      = [0-9a-fA-F]
BasedWidth    = [1-9][0-9]*

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state STRING, CHARLITERAL

%%

<YYINITIAL> {

  "and" 		{return getToken(K_AND);}
  "bool" 		{return getToken(K_BOOL);}
  "class" 		{return getToken(K_CLASS);}
  "const"		{return getToken(K_CONST);}
  "def"			{return getToken(K_DEF);}
  "elif"		{return getToken(K_ELIF);}
  "else"		{return getToken(K_ELSE);}
  "extends"		{return getToken(K_EXTENDS);}
  "false" 		{return getToken(K_FALSE);}
  "float"		{return getToken(K_FLOAT);}
  "for"			{return getToken(K_FOR);}
  "implements"	{return getToken(K_IMPLEMENTS);}
  "if"			{return getToken(K_IF);}
  "int"			{return getToken(K_INT);}
  "interface"	{return getToken(K_INTERFACE);}
  "nil"			{return getToken(K_NIL);}
  "not"			{return getToken(K_NOT);}
  "or"			{return getToken(K_OR);}
  "private"		{return getToken(K_PRIVATE);}
  "protected"	{return getToken(K_PROTECTED);}
  "public"		{return getToken(K_PUBLIC);}
  "static" 		{return getToken(K_STATIC);}
  "true" 		{return getToken(K_TRUE);}
  "unless" 		{return getToken(K_UNLESS);}
  "var"			{return getToken(K_VAR);}
  "while"		{return getToken(K_WHILE);}
  
  {Comment} {/*todo: accumulate*/}

  {WhiteSpace} {/*nothing*/}

  {Identifier} {return getToken(IDENT);}

  "("                            { return getToken(LPAREN); }
  ")"                            { return getToken(RPAREN); }
  "{"                            { return getToken(LBRACE); }
  "}"                            { return getToken(RBRACE); }
  "["                            { return getToken(LBRACK); }
  "]"                            { return getToken(RBRACK); }
  ";"                            { return getToken(SEMICOLON); }
  ","                            { return getToken(COMMA); }
  "."                            { return getToken(DOT); }
  ".."                           { return getToken(DOTDOT); }
  
  "="                            { return getToken(EQ); }
  ">"                            { return getToken(GT); }
  "<"                            { return getToken(LT); }
  "!"                            { return getToken(NOT); }
  "~"                            { return getToken(COMP); }
  "?"                            { return getToken(QUESTION); }
  ":"                            { return getToken(COLON); }
  "=="                           { return getToken(EQEQ); }
  "<="                           { return getToken(LTEQ); }
  ">="                           { return getToken(GTEQ); }
  "!="                           { return getToken(NOTEQ); }
  "&&"                           { return getToken(ANDAND); }
  "||"                           { return getToken(OROR); }
  "++"                           { return getToken(PLUSPLUS); }
  "--"                           { return getToken(MINUSMINUS); }
  "+"                            { return getToken(PLUS); }
  "-"                            { return getToken(MINUS); }
  "*"                            { return getToken(MULT); }
  "/"                            { return getToken(DIV); }
  "&"                            { return getToken(AND); }
  "|"                            { return getToken(OR); }
  "^"                            { return getToken(XOR); }
  "%"                            { return getToken(MOD); }
  "<<"                           { return getToken(LSHIFT); }
  ">>"                           { return getToken(RSHIFT); }
  ">>>"                          { return getToken(URSHIFT); }
  "+="                           { return getToken(PLUSEQ); }
  "-="                           { return getToken(MINUSEQ); }
  "*="                           { return getToken(MULTEQ); }
  "/="                           { return getToken(DIVEQ); }
  "&="                           { return getToken(ANDEQ); }
  "|="                           { return getToken(OREQ); }
  "^="                           { return getToken(XOREQ); }
  "%="                           { return getToken(MODEQ); }
  "<<="                          { return getToken(LSHIFTEQ); }
  ">>="                          { return getToken(RSHIFTEQ); }

  \"                             { yybegin(STRING); __string.setLength(0); }
  \'                             { yybegin(CHARLITERAL); }
}

<STRING> {
  \"	{ 	
  			yybegin(YYINITIAL); 
  			return getToken(STRING_LITERAL, __string.toString()); 
		}
  
  {StringCharacter}+             { __string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { __string.append( '\b' ); }
  "\\t"                          { __string.append( '\t' ); }
  "\\n"                          { __string.append( '\n' ); }
  "\\f"                          { __string.append( '\f' ); }
  "\\r"                          { __string.append( '\r' ); }
  "\\\""                         { __string.append( '\"' ); }
  "\\'"                          { __string.append( '\'' ); }
  "\\\\"                         { __string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { 
  		char val = (char) Integer.parseInt(yytext().substring(1),8);
        __string.append( val ); 
	}
  
  /* error cases */
  \\.	{ 
  	throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); 
	}
  {LineTerminator} { 
  		throw new RuntimeException("Unterminated string at end of line"); 
	}
}

<CHARLITERAL> {
  {SingleCharacter}\'  { 
  		yybegin(YYINITIAL); 
		return getToken(CHARACTER_LITERAL, yytext().charAt(0)); 
	}
  
  /* escape sequences */
  "\\b"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\b');}
  "\\t"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\t');}
  "\\n"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\n');}
  "\\f"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\f');}
  "\\r"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\r');}
  "\\\""\'     { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\"');}
  "\\'"\'      { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\'');}
  "\\\\"\'     { yybegin(YYINITIAL); return getToken(CHARACTER_LITERAL, '\\'); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { 
  		yybegin(YYINITIAL); 
	    int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
	    return getToken(CHARACTER_LITERAL, (char)val); 
	}
  
  /* error cases */
  \\. { 
  		throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); 
	}
  {LineTerminator}  { 
  	throw new RuntimeException("Unterminated character literal at end of line");
  }
}

/* error fallback */
[^]      { throw new RuntimeException("Illegal character \""+yytext()+
           "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>  { return getToken(EOF); }
