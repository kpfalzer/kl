%{
package klx.parser ;
import klx.Token ;
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
