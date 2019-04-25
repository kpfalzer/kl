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
  public Scanner(String fileName) throws FileNotFoundException {
    __fileName = fileName;
    this.zzReader = new BufferedReader(new FileReader(__fileName));
  }

  public Scanner(String text, boolean unused) {
    __fileName = null;
    this.zzReader = new BufferedReader(new StringReader(text));
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

  private Token getToken(int code) {
    return new Token(code, __fileName, yyline, yycolumn, yytext());
  }

  private Token __lastToken = null;
  private String __fileName = null;

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
  {Identifier} {return getToken(IDENT);}
}
