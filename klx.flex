package klx.parser;
import java.io.FileReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import klx.Token;
import klx.Token.EType;

%%

%public
%class Scanner
%function nextToken
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

  private Token getToken(EType code, char c) {
    return getToken(code, Character.toString(c));
  }
  private Token getToken(EType code, String str) {
    return new Token(code, __fileName, yyline+1, yycolumn+1, str);
  }
  private Token getToken(EType code) {
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
RegexCharacter  = [^\r\n\}\\]

%state STRING, CHARLITERAL, REGEX

%%

<YYINITIAL> {

  "and" 		{return getToken(EType.K_AND);}
  "bool" 		{return getToken(EType.K_BOOL);}
  "class" 		{return getToken(EType.K_CLASS);}
  "const"		{return getToken(EType.K_CONST);}
  "def"			{return getToken(EType.K_DEF);}
  "elif"		{return getToken(EType.K_ELIF);}
  "else"		{return getToken(EType.K_ELSE);}
  "extends"		{return getToken(EType.K_EXTENDS);}
  "false" 		{return getToken(EType.K_FALSE);}
  "float"		{return getToken(EType.K_FLOAT);}
  "for"			{return getToken(EType.K_FOR);}
  "implements"	{return getToken(EType.K_IMPLEMENTS);}
  "if"			{return getToken(EType.K_IF);}
  "import"      {return getToken(EType.K_IMPORT);}
  "int"			{return getToken(EType.K_INT);}
  "interface"	{return getToken(EType.K_INTERFACE);}
  "nil"			{return getToken(EType.K_NIL);}
  "not"			{return getToken(EType.K_NOT);}
  "or"			{return getToken(EType.K_OR);}
  "package"		{return getToken(EType.K_PACKAGE);}
  "private"		{return getToken(EType.K_PRIVATE);}
  "protected"	{return getToken(EType.K_PROTECTED);}
  "public"		{return getToken(EType.K_PUBLIC);}
  "static" 		{return getToken(EType.K_STATIC);}
  "true" 		{return getToken(EType.K_TRUE);}
  "unless" 		{return getToken(EType.K_UNLESS);}
  "var"			{return getToken(EType.K_VAR);}
  "while"		{return getToken(EType.K_WHILE);}
  
  {Comment} {/*todo: accumulate*/}

  {WhiteSpace} {/*nothing*/}

  {Identifier} {return getToken(EType.IDENT);}

  "("                            { return getToken(EType.LPAREN); }
  ")"                            { return getToken(EType.RPAREN); }
  "{"                            { return getToken(EType.LBRACE); }
  "}"                            { return getToken(EType.RBRACE); }
  "["                            { return getToken(EType.LBRACK); }
  "]"                            { return getToken(EType.RBRACK); }
  ";"                            { return getToken(EType.SEMICOLON); }
  ","                            { return getToken(EType.COMMA); }
  "."                            { return getToken(EType.DOT); }
  ".."                           { return getToken(EType.DOTDOT); }
  
  "="                            { return getToken(EType.EQ); }
  ">"                            { return getToken(EType.GT); }
  "<"                            { return getToken(EType.LT); }
  "!"                            { return getToken(EType.NOT); }
  "~"                            { return getToken(EType.COMP); }
  "?"                            { return getToken(EType.QUESTION); }
  ":"                            { return getToken(EType.COLON); }
  "=="                           { return getToken(EType.EQEQ); }
  "<="                           { return getToken(EType.LTEQ); }
  ">="                           { return getToken(EType.GTEQ); }
  "!="                           { return getToken(EType.NOTEQ); }
  "&&"                           { return getToken(EType.ANDAND); }
  "||"                           { return getToken(EType.OROR); }
  "++"                           { return getToken(EType.PLUSPLUS); }
  "--"                           { return getToken(EType.MINUSMINUS); }
  "+"                            { return getToken(EType.PLUS); }
  "-"                            { return getToken(EType.MINUS); }
  "*"                            { return getToken(EType.MULT); }
  "/"                            { return getToken(EType.DIV); }
  "&"                            { return getToken(EType.AND); }
  "|"                            { return getToken(EType.OR); }
  "^"                            { return getToken(EType.XOR); }
  "%"                            { return getToken(EType.MOD); }
  "<<"                           { return getToken(EType.LSHIFT); }
  ">>"                           { return getToken(EType.RSHIFT); }
  ">>>"                          { return getToken(EType.URSHIFT); }
  "+="                           { return getToken(EType.PLUSEQ); }
  "-="                           { return getToken(EType.MINUSEQ); }
  "*="                           { return getToken(EType.MULTEQ); }
  "/="                           { return getToken(EType.DIVEQ); }
  "&="                           { return getToken(EType.ANDEQ); }
  "|="                           { return getToken(EType.OREQ); }
  "^="                           { return getToken(EType.XOREQ); }
  "%="                           { return getToken(EType.MODEQ); }
  "<<="                          { return getToken(EType.LSHIFTEQ); }
  ">>="                          { return getToken(EType.RSHIFTEQ); }
  ">>>="                         { return getToken(EType.URSHIFTEQ); }

  "%r{"                          { yybegin(REGEX); __string.setLength(0); }
  \"                             { yybegin(STRING); __string.setLength(0); }
  \'                             { yybegin(CHARLITERAL); }
}

<REGEX> {
  "}"	{ 	
  			yybegin(YYINITIAL); 
  			return getToken(EType.REGEX_LITERAL, __string.toString()); 
		}
  
  {RegexCharacter}+             { __string.append( yytext() ); }
  
  /* escape sequences */
  "\\}"                         { __string.append( '}' ); }
  
  /* error cases */
  \\.	{ 
  	throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); 
	}
  {LineTerminator} { 
  		throw new RuntimeException("Unterminated string at end of regex"); 
	}
}

<STRING> {
  \"	{ 	
  			yybegin(YYINITIAL); 
  			return getToken(EType.STRING_LITERAL, __string.toString()); 
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
		return getToken(EType.CHARACTER_LITERAL, yytext().charAt(0)); 
	}
  
  /* escape sequences */
  "\\b"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\b');}
  "\\t"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\t');}
  "\\n"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\n');}
  "\\f"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\f');}
  "\\r"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\r');}
  "\\\""\'     { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\"');}
  "\\'"\'      { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\'');}
  "\\\\"\'     { yybegin(YYINITIAL); return getToken(EType.CHARACTER_LITERAL, '\\'); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { 
  		yybegin(YYINITIAL); 
	    int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
	    return getToken(EType.CHARACTER_LITERAL, (char)val); 
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
<<EOF>>  { return getToken(EType.EOF); }
