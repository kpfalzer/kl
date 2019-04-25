package klx.parser;
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
	private static Token __lastToken = null;

  @Override
  public Token getLVal() {
    return null;
  }

  @Override
  public int yylex() {
		__lastToken = __yylex();
		return __lastToken.type.ordinal();
	}

  @Override
  public void yyerror(String msg) {

  }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment = "/*" ~"*/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

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

BinaryLiteral = {BasedWidth}? \' [bB] [01][01_]*
OctalLiteral  = {BasedWidth}? \' [oO] [0-7][0-7_]*
HexLiteral    = {BasedWidth}? \' [hH] {HexDigit} (_|{HexDigit})*
DecLiteral    = {BasedWidth}? \' [dD] [0-9][0-9_]*
HexDigit      = [0-9a-fA-F]
BasedWidth    = [1-9][0-9]*

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%%

<YYINITIAL> {

  /* keywords */
  "abstract" {}
  
  /* comments */
  {Comment} {}

  /* whitespace */
  {WhiteSpace} {}

  /* identifiers */ 
  {Identifier} {}
}
