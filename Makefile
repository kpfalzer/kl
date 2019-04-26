
.PHONY: parser clean

ODIR = src/klx/parser
SCANNER = ${ODIR}/Scanner.java
PARSER  = ${ODIR}/Parser.java
PARSER_DEBUG = ${ODIR}/Parser.output
BISON   = /usr/local/share/bin/bison
BISON_OPTS = --report=all
JFLEX   = jflex

parser: ${SCANNER} ${PARSER} ;

${SCANNER} : klx.flex
	${JFLEX} -d ${@D} ${<} --nobak -q

${PARSER} : klx.bison
	@rm -f ${PARSER_DEBUG}
	${BISON} -L java -o ${PARSER} ${BISON_OPTS} ${<}

clean:
	@rm -f ${SCANNER}
	@rm -f ${PARSER}
	@rm -f ${PARSER_DEBUG}
