all: pdf

clean:
	if test -f libro.html; then rm libro.html; fi
	if test -f libro.fo; then rm libro.fo; fi
	if test -f libro.pdf; then rm libro.pdf; fi

html:
	xsltproc -o libro.html /usr/share/xml/docbook/stylesheet/nwalsh/xhtml/docbook.xsl libro.xml

pdf:
	xsltproc -o libro.fo /usr/share/xml/docbook/stylesheet/nwalsh/fo/docbook.xsl libro.xml
	fop libro.fo libro.pdf
