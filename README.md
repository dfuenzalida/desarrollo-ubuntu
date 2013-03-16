Desarrollo Ubuntu
=================

Este proyecto consiste en un texto libre y en español sobre el desarrollo y empaquetamiento de programas para sistemas basados en Debian GNU/Linux (en particular, Ubuntu).

La idea es documentar el proceso de desarrollo de aplicaciones de escritorio sencillas escritas en lenguajes como Python o Java, usando Glade; y además del trabajo que se requiere para tomar el código de la aplicación y convertirlo en un paquete de software instalable desde un repositorio.

Estas tareas adicionales al desarrollo permitirán que los usuarios de una aplicación puedar obtener de forma automática las ultimas actualizaciones de nuestra aplicación.

Cómo generar la documentación en formato HTML
---------------------------------------------

1.- Debes estar seguro de tener instalados los paquetes `xsltproc`, `docbook-xsl`, `make` y `subversion`

```
$ sudo apt-get install xsltproc docbook-xsl make git-core
```

2.- Obtener el código fuente

```
$ git clone https://github.com/dfuenzalida/desarrollo-ubuntu.git
$ cd desarrollo-ubuntu/libro
```

3.- Generar el libro en formato HTML

```
 $ make html
```

El documento `libro.html` recién generado es el libro en formato HTML.

Generación de la documentación en formato PDF
---------------------------------------------

1.- Debes estar seguro de tener instalados los paquetes `xsltproc`, `docbook-xsl`, `make` y `subversion`

```
$ sudo apt-get install xsltproc docbook-xsl make git-core
```

Además de los paquetes anteriores, se requiere Java y [http://xmlgraphics.apache.org/fop/ Apache FOP].

2.- Si no tienes instalado Java, basta con:

```
$ sudo apt-get install openjdk-6-jre
```

3.- Instalar Apache FOP

```
 $ sudo apt-get install fop
```

4.- Obtener el código fuente

```
$ git clone https://github.com/dfuenzalida/desarrollo-ubuntu.git
$ cd desarrollo-ubuntu/libro
```

5.- Generar el PDF en el directorio donde se encuentran los archivos XML

```
$ make pdf
```

El documento `libro.pdf` recién generado es el libro en formato PDF.