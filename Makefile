
.PHONY: parser clean

ODIR = src/klx/parser
SCANNER = ${ODIR}/Scanner.java

parser: ${SCANNER} ;

${ODIR}/Scanner.java : klx.flex
	jflex -d ${@D} ${<} --nobak -q

clean:
	@rm -f ${SCANNER}
