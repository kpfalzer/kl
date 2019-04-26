%{
package klx.parser ;
import klx.Token ;
%}

%define api.parser.class {Parser}
%define api.value.type {Token}

%token K_AND
%token K_BOOL
%token K_CLASS
%token K_CONST
%token K_DEF
%token K_ELIF
%token K_ELSE
%token K_EXTENDS
%token K_FALSE
%token K_FLOAT
%token K_FOR
%token K_IMPLEMENTS
%token K_IF
%token K_INT
%token K_INTERFACE
%token K_NIL
%token K_NOT
%token K_OR
%token K_PRIVATE
%token K_PROTECTED
%token K_PUBLIC
%token K_STATIC
%token K_TRUE
%token K_UNLESS
%token K_VAR
%token K_WHILE
%token IDENT
%token LPAREN
%token RPAREN
%token LBRACE
%token RBRACE
%token LBRACK
%token RBRACK
%token SEMICOLON
%token COMMA
%token DOT
%token EQ
%token GT
%token LT
%token NOT
%token COMP
%token QUESTION
%token COLON
%token EQEQ
%token LTEQ
%token GTEQ
%token NOTEQ
%token ANDAND
%token OROR
%token PLUSPLUS
%token MINUSMINUS
%token PLUS
%token MINUS
%token MULT
%token DIV
%token AND
%token OR
%token XOR
%token MOD
%token LSHIFT
%token RSHIFT
%token URSHIFT
%token PLUSEQ
%token MINUSEQ
%token MULTEQ
%token DIVEQ
%token ANDEQ
%token OREQ
%token XOREQ
%token MODEQ
%token LSHIFTEQ
%token RSHIFTEQ
%token STRING_LITERAL
%token CHARACTER_LITERAL
%%

input:
	%empty
|	IDENT PLUS IDENT
;

%%

//epilogue
