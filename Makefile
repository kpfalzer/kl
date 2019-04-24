
.PHONY: parser clean

ODIR = src/klx/parser
SCANNER = ${ODIR}/Scanner.java
PARSER  = ${ODIR}/Parser.java
BISON   = /usr/local/share/bin/bison
JFLEX   = jflex

parser: ${SCANNER} ${PARSER} ;

${SCANNER} : klx.flex
	${JFLEX} -d ${@D} ${<} --nobak -q

${PARSER} : klx.bison
	${BISON} -L java -o ${PARSER} ${<}

clean:
	@rm -f ${SCANNER}
	@rm -f ${PARSER}
