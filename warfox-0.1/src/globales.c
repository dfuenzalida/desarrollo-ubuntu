#include <math.h>
#include <stdio.h>
#include <GL/glut.h>

#include "globales.h"

GLfloat lightOnePosition[]  ={ L1_POS };   /*Posicion de light1*/

GLfloat color_motor[]	={ 0.7,0.0,0.0 };
GLfloat color_tronco[]	={ 0.6,0.6,0.6 };
GLfloat color_alas1[]	={ 0.0,0.0,1.0 };
GLfloat color_alas2[]	={ 0.0,1.0,0.0 };
GLfloat color_edifi[]	={ 0.7,0.7,0.7 };

GLfloat floorcolor[]	={ 1.0,0.5,0.0 };
GLfloat color_bolas[]	={ 1.0,1.0,0.0 };

GLfloat color_negro[]	={ 0.0,0.0,0.0 };


/* LLENA TABLA DE SENOS Y COSENOS */

void iniciar_senos()
{
GLint i;
for(i=0;i<=720;i++)
	{
	seno[i]=sin(i*3.1415926/180);
	cose[i]=cos(i*3.1415926/180);
	}
}

/* VALOR ABSOLUTO */

float absf(float x)
{
   if (x>=0.0) return(x);
      else return(-x);
}
