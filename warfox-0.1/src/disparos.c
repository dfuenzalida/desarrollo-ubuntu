#include <math.h>
#include <stdio.h>
#include <GL/glut.h>

#include "globales.h"
#include "disparos.h"
#include "objetos.h"
#include "monstruos.h"

void crea_disparo(int planox,int roty)
{
int a=0;
	while((disparos[a].activo!=0) && (a<6))
		a++;

	if (a<6)
		{
		disparos[a].orix=-posx;
		disparos[a].oriy=-posy;
		disparos[a].oriz=altura+5;
		disparos[a].incy=-2*cose[((-planox+360)%360)+1];
		disparos[a].incx=2*seno[(-planox+360)%360];
		disparos[a].incz=-4*seno[(roty+360)%360];
		disparos[a].dist=0;
		disparos[a].impacto=0;
		disparos[a].activo=1;
		}
}

/* FUNCION IMPACTO */
void impacto(float x,float y,float z)
{
glMaterialfv(GL_FRONT, GL_DIFFUSE, color_bolas);
glMaterialfv(GL_FRONT, GL_AMBIENT, color_motor);	
glPushMatrix();
glTranslatef(x, y, z);
glutSolidSphere(0, 3, 3); // era auxSolidSphere(3) - DENIS 2011.03.19
glPopMatrix();
}


/* DISPAROS */

void dibuja_disparos()
{

int a;
for (a=0;a<6;a++)
	if (disparos[a].activo==1)
	{
	disparos[a].dist++;
	if ((disparos[a].dist==20)||(disparos[a].impacto==1))
		{
		disparos[a].activo=0;
		if (disparos[a].impacto)
			{
			impacto(disparos[a].orix,disparos[a].oriy,
			disparos[a].oriz);
			}
		}
	else
		{
		glPushMatrix();
   		glMaterialfv(GL_FRONT, GL_DIFFUSE, color_bolas );
   		glMaterialfv(GL_FRONT, GL_AMBIENT, color_motor );

		disparos[a].orix+=disparos[a].incx;
		disparos[a].oriy+=disparos[a].incy;
		disparos[a].oriz+=disparos[a].incz;

		glTranslatef(
		disparos[a].orix,disparos[a].oriy,disparos[a].oriz);
		glutSolidTetrahedron(); // auxSolidTetrahedron(0.15);

		glPopMatrix();
		}
	}

}
