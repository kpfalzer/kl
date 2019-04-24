%{
package klx.parser ;
%}

%token Token

%%
input:
	%empty
|	input line
;

line:
	'a'
;
%%

//epilogue
