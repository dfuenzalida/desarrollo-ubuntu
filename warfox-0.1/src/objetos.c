#include <math.h>
#include <stdio.h>
#include <GL/glut.h>

#include "globales.h"
#include "objetos.h"

/* FUNCION ASTEROIDE */
int asteroide(float x,float y,float z,float rad)
{
GLint i;

	glPushMatrix();
	glTranslatef(-x,-y,z);
	glutSolidSphere(rad,6,6);// auxSolidSphere(rad); // DENIS 2003
	glPopMatrix();
	if

(((posx-x)*(posx-x)+(posy-y)*(posy-y)+(altura+5-z)*(altura+5-z))
<((rad+1)*(rad+1)))
		explotando=1;

for (i=0;i<6;i++)
	if (disparos[i].activo==1)
		if
		(((-disparos[i].orix-x)*(-disparos[i].orix-x)+
		(-disparos[i].oriy-y)*(-disparos[i].oriy-y)+
		(disparos[i].oriz-z)*(disparos[i].oriz-z))
		< ((rad)*(rad)))
			{
			disparos[i].impacto=1;
			return(1);
			}

return(0);
}

/* FUNCION CAJA */

int caja(float x,float y,float z,float ancho,float
largo,float alto)
{
GLint i;

	glPushMatrix();
	glTranslatef(-x,-y,z);
	// auxSolidBox(ancho,largo,alto); // DENIS 2003
	glScalef(ancho,largo,alto);
	glutSolidCube(1.0);
	glPopMatrix();

if ((posx>(x-(ancho/2)-1))&&(posx<(x+(ancho/2)+1))&&
(posy>(y-(largo/2)-1))&&(posy<(y+(largo/2)+1))&&
(altura>(z-(alto/2)-1-5))&&(altura<(z+(alto/2)+1-5)))
		explotando=1;

for (i=0;i<6;i++)
	if (disparos[i].activo==1)
		if
		((-disparos[i].orix>(x-(ancho/2)))&&
		(-disparos[i].orix<(x+(ancho/2)))&&
		(-disparos[i].oriy>(y-(largo/2)))&&
		(-disparos[i].oriy<(y+(largo/2)))&&
		(disparos[i].oriz>(z-(alto/2)))&&
		(disparos[i].oriz<(z+(alto/2))))
			{
			disparos[i].impacto=1;
			return(1);
			}
return(0);
}

/* FUNCION PIRAMIDE */

void piramide(float x,float y,float z,float rad)
{
	glPushMatrix();
	glTranslatef(-x,-y,z);
	glutSolidTetrahedron(); // auxSolidTetrahedron(rad); // DENIS 2003
	glPopMatrix();
	if
	(((posx-x)*(posx-x)+(posy-y)*(posy-y)+
	(altura+5-z)*(altura+5-z)) < ((rad)*(rad)))
		explotando=1;

}

/* CILINDRO */
void cilindro(GLfloat tx,GLfloat ty,GLfloat tz)
{
GLint a,ang,x,y,z,cont=0,rad=10,dis=6;
GLfloat anc;

for (a=0;a<=200;a++) 
    {

    glBegin(GL_QUAD_STRIP);

    if (cont==4)
    	{
    	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas1);
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas2);
        }
    else
    	{
    	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2);
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1);
    	}
    cont=(cont+1)%5;


    for (ang = 45; ang <= 405; ang += 90) 
	{
	x = rad * cose[ang];
	z = rad * seno[ang]+dis*cose[((a+1)*10+360)%360];
	y = a+1;
	glVertex3f(x, y, z);
	
	x = rad * cose[ang];
	z = rad * seno[ang]+dis*cose[(a*10+360)%360];
	y = a;
	glVertex3f(x, y, z);
    	}
    glEnd();

anc=rad/sqrt(2);

if (
   ( (posx<(tx-anc+1)) || (posx>(tx+anc-1)) ||
(altura<(dis*cose[(a*10+360)%360]-anc+2-5+tz)) ||
(altura>(dis*cose[(a*10+360)%360]+anc-2-5+tz)) )
&&((posy>(-a+ty))&&(posy<(-a+1+ty)))
   )
		explotando=1;

    }
}

int Mario(GLfloat x,GLfloat y,GLfloat z)
{
int aux=0;

GLfloat piel1[]	= {1.0,0.8,0.8,1.0};
GLfloat piel2[]	= {1.0,0.5,0.5,1.0};

GLfloat pelo1[]	= {0.2,0.2,0.0,1.0};
GLfloat pelo2[]	= {0.1,0.1,0.0,1.0};

GLfloat ojos1[]	= {1.0,1.0,1.0,1.0};
GLfloat ojos2[]	= {0.8,0.8,0.8,1.0};

GLfloat pupi1[]	= {0.0,0.0,1.0,1.0};
GLfloat pupi2[]	= {0.0,0.0,0.0,1.0};

GLfloat gorr1[]	= {1.0,0.0,0.0,1.0};
GLfloat gorr2[]	= {0.9,0.0,0.0,1.0};


glMaterialfv(GL_FRONT, GL_DIFFUSE, piel1);
glMaterialfv(GL_FRONT, GL_AMBIENT, piel2);
aux+=asteroide(x,-30.0+y,5.0+z,10);
aux+=asteroide(x,-20.0+y,5.0+z,4);

glMaterialfv(GL_FRONT, GL_DIFFUSE, ojos1);
glMaterialfv(GL_FRONT, GL_AMBIENT, ojos2);
aux+=asteroide(3.0+x,-23.0+y,9.0+z,3);
aux+=asteroide(-3.0+x,-23.0+y,9.0+z,3);

aux+=caja(x,-25.5+y,16.0+z,5.0,2.0,3.5);

glMaterialfv(GL_FRONT, GL_DIFFUSE, pupi1);
glMaterialfv(GL_FRONT, GL_AMBIENT, pupi2);
aux+=asteroide(2.5+x,-20.5+y,9.0+z,1);
aux+=asteroide(-2.5+x,-20.5+y,9.0+z,1);

glMaterialfv(GL_FRONT, GL_DIFFUSE, pelo1);
glMaterialfv(GL_FRONT, GL_AMBIENT, pelo2);
aux+=caja(x,-21.0+y,1.0+z,10.0,2.0,3.0);

aux+=caja(3.0+x,-23.0+y,12.0+z,5.0,2.0,1.0);
aux+=caja(-3.0+x,-23.0+y,12.0+z,5.0,2.0,1.0);

glPushMatrix();
glMaterialfv(GL_FRONT, GL_DIFFUSE, gorr1);
glMaterialfv(GL_FRONT, GL_AMBIENT, gorr2);
aux+=caja(x,-23.0+y,14.0+z,10.0,10.0,1.0);
glTranslatef(-x,33.0-y,15.0+z);
glRotatef(-30,1.0,0.0,0.0);
glutSolidCone(10,0, 6,6);// auxSolidCone(10,0); // DENIS 2003
glRotatef(180,1.0,0.0,0.0);
glutSolidCone(10,14,6,6);// auxSolidCone(10,14); // DENIS 2003
glPopMatrix();

return(aux);
}
