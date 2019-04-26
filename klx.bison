%{
package klx.parser ;
import klx.Token ;
%}

%define api.parser.class {Parser}
%define api.value.type {Token}

%token K_AND 		"and"
%token K_BOOL		"bool"
%token K_CLASS		"class"
%token K_CONST		"const"
%token K_DEF		"def"
%token K_ELIF		"elif"
%token K_ELSE		"else"
%token K_EXTENDS	"extends"
%token K_FALSE		"false"
%token K_FLOAT		"float"
%token K_FOR		"for"
%token K_IMPLEMENTS "implements"
%token K_IF			"if"
%token K_INT		"int"
%token K_INTERFACE	"interface"
%token K_NIL		"nil"
%token K_NOT		"not"
%token K_OR			"or"
%token K_PRIVATE	"private"
%token K_PROTECTED	"protected"
%token K_PUBLIC		"public"
%token K_STATIC		"static"
%token K_TRUE		"true"
%token K_UNLESS		"unless"
%token K_VAR		"var"
%token K_WHILE		"while"
%token IDENT		"<ident>"
%token LPAREN		"("
%token RPAREN		")"
%token LBRACE		"{"
%token RBRACE		"}"
%token LBRACK		"["
%token RBRACK		"]"
%token SEMICOLON	";"
%token COMMA		","
%token DOT			"."
%token DOTDOT		".."
%token EQ			"="
%token GT			">"
%token LT			"<"
%token NOT			"!"
%token COMP			"~"
%token QUESTION		"?"
%token COLON		":"
%token EQEQ			"=="
%token LTEQ			"<="
%token GTEQ			">="
%token NOTEQ		"!="
%token ANDAND		"&&"
%token OROR			"||"
%token PLUSPLUS		"++"
%token MINUSMINUS	"--"
%token PLUS			"+"
%token MINUS		"-"
%token MULT			"*"
%token DIV			"/"
%token AND			"&"
%token OR			"|"
%token XOR			"^"
%token MOD			"%"
%token LSHIFT		"<<"
%token RSHIFT		">>"
%token URSHIFT		">>>"
%token PLUSEQ		"+="
%token MINUSEQ		"-="
%token MULTEQ		"*="
%token DIVEQ		"/="
%token ANDEQ		"&="
%token OREQ			"|="
%token XOREQ		"^="
%token MODEQ		"%="
%token LSHIFTEQ		"<<="
%token RSHIFTEQ		">>="
%token STRING_LITERAL		"<string>"
%token CHARACTER_LITERAL	"<char>"		

%%

input:
	%empty
|	IDENT PLUS IDENT
;

%%

//epilogue
