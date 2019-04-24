%{
package klx.parser ;
%}

%define api.parser.class {Parser}
%define api.value.type {Token}

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
