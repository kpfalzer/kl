%{
package klx.parser ;
import klx.Token ;
%}

%define api.parser.class {Parser}
%define api.value.type {Token}

%token IDENT
%token PLUS

%%

input:
	%empty
|	IDENT PLUS IDENT
;

%%

//epilogue
