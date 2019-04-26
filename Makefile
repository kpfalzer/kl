
.PHONY: parser clean

ODIR = src/klx/parser
SCANNER = ${ODIR}/Scanner.java
JFLEX   = jflex

parser: ${SCANNER} ;

${SCANNER} : klx.flex
	${JFLEX} -d ${@D} ${<} --nobak -q

clean:
	@rm -f ${SCANNER}
