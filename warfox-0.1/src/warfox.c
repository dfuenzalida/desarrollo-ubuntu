#include <math.h>
#include <stdio.h>

/*
#include "gltk.h"
#include <tk.h>
*/


/* #include <gl/glaux.h> */
#include <GL/glut.h>

#include "globales.h"
#include "disparos.h"
#include "objetos.h"
#include "monstruos.h"
/*
*/


/* FUNCION DIBUJA NAVE */

static void sombra( void )
{
   glPushMatrix();
   glTranslatef(0.0,0.0,-3.99-altura);
   glScalef(0.4,0.4,0.4);

   glBegin( GL_TRIANGLES );
   glMaterialfv(GL_FRONT, GL_DIFFUSE, color_negro );
   glMaterialfv(GL_FRONT, GL_AMBIENT, color_negro );

if (Rotx<300)
   {
   glVertex3f( -3+((float)Rotx/100), 1, 0);
   glVertex3f( 0, -3, 0);
   glVertex3f( 3-((float)Rotx/100),  1, 0);
   }
else
   {
   glVertex3f( -3+absf((360-(float)Rotx)/100), 1, 0);
   glVertex3f( 0, -3, 0);
   glVertex3f( 3-absf((360-(float)Rotx)/100),  1, 0);
   }

   glEnd();
   glPopMatrix();
}


static void dibuja_nave( void )
{
glPushMatrix();

glRotatef(90,1.0,0.0,0.0);

   glBegin( GL_TRIANGLES );

/* cuerpo */
	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_motor );
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_motor );

	glVertex3f( -1, 0, -1);
	glVertex3f( 0, 1, 0);
	glVertex3f( 1, 0, -1); 

	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_tronco );
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_tronco );

	glVertex3f( -1, 0, -1);
	glVertex3f( 0, 0, 3);
        glVertex3f( 0, 1, 0);
	
        glVertex3f( 0, 1, 0);
        glVertex3f( 0, 0, 3);
        glVertex3f( 1, 0, -1);

        glVertex3f( -1, 0, -1);
        glVertex3f( 1, 0, -1);
        glVertex3f( 0, 0, 3);

/* alas izquierdas */

	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas1 );
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1 );

        glVertex3f( -1.0, 0, -1);
        glVertex3f( -0.25, 0, 2);
        glVertex3f( -3.0, -1, -1);

        glVertex3f( -1.0, 0, -1);
        glVertex3f( -3.0, -1, -1);
        glVertex3f( -0.25, 0, 2); 

	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2 );
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas2 );

        glVertex3f( -1.0, 0, -1);
        glVertex3f( -2.0, 1, -1.5);
        glVertex3f( -0.25, 0, 2);

        glVertex3f( -1.1, 0, -1);
        glVertex3f( -0.25, 0, 2);
        glVertex3f( -2.0, 1, -1.5);


/* alas derechas */
	
	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas1);
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1 );

        glVertex3f( 1, 0, -1);
        glVertex3f( 0.25, 0, 2);
        glVertex3f( 3, -1, -1);
   
        glVertex3f( 1, 0, -1);
        glVertex3f( 3, -1, -1);
        glVertex3f( 0.25, 0, 2);

    	glColor3fv(color_alas2 );	
	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2 );
    	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas2 );

        glVertex3f( 1, 0, -1);
        glVertex3f( 0.25, 0, 2);
        glVertex3f( 2, 1, -1.5);

        glVertex3f( 1, 0, -1);
        glVertex3f( 2, 1, -1.5);
        glVertex3f( 0.25, 0, 2);

   glEnd();
  
glPopMatrix();

}

static void reshape( int width, int height )
{
   GLfloat     aspect;
   glViewport( 0, 0, width, height );
   aspect = (GLfloat) width / height;
   glMatrixMode( GL_PROJECTION );
   glLoadIdentity();
   gluPerspective( ANG_VIS, aspect,P_NEAR, P_FAR );
   glMatrixMode( GL_MODELVIEW );

}

/* ATRAPANDO LAS TECLAS */

static void key(unsigned char k, int x, int y)
{
   switch (k) {
      case 27:
	 exit(0);

      case 'o':
	if (Incx!=10)
		Rotandox=(Rotandox+1)%2;
	Incx=10;
	Rotandoy=0;
	break;
      case 'p':
	if (Incx!=-10)
		Rotandox=(Rotandox+1)%2;
	Incx=-10;
	Rotandoy=0;
	break;
      case 'q':
	if (Incy!=10)
		Rotandoy=(Rotandoy+1)%2;
	Incy=10;
	Rotandox=0;
	break;
      case 'a':
	if (Incy!=-10)
		Rotandoy=(Rotandoy+1)%2;
	Incy=-10;
	Rotandox=0;
	break;

      case ' ':
	crea_disparo(planox,Roty);
	break;

/* PROVISORIAS */

      case 'A':
	planox=(planox+180)%360;
	break;
      case 'v':
	vel=(vel+1)%2;
	break;
      case 'z':
	glScalef(1.1,1.1,1.1);
	break;
      case 'Z':
	glScalef(0.9,0.9,0.9);
	break;
			case 'x':
	break;
			case '1':
	etapa=0;
	break;
			case '2':
	etapa=1;
	break;
			case '3':
	etapa=2;
	break;
   }

   /* return GL_FALSE; */

	glutPostRedisplay();
}


/* INICIALIZACIONES */

static void inicia_etapa0()
{
Rotx=0,Roty=0,Incx=0,Incy=0,RotyOld,Rotandox=0,Rotandoy=0;
planox=180,planoy=0,altura=10;
posx=0.0,posy=120.0; 
explotando=0,cont=0,ang=0;
vel=1;

mon1=20;
gluLookAt( P_VIEW1, P_CENTER1, NORMAL );

}

static void inicia_etapa1()
{
GLfloat aspect;

Rotx=0,Roty=0,Incx=0,Incy=0,RotyOld,Rotandox=0,Rotandoy=0;
planox=180,planoy=0,altura=13;
posx=0.0,posy=100.0;
explotando=0,cont=0,ang=0;
vel=1;

glRotatef(25,1.0,0.0,0.0);

}

static void inicia_etapa2()
{
GLfloat aspect;

Rotx=0,Roty=0,Incx=0,Incy=0,RotyOld,Rotandox=0,Rotandoy=0;
planox=180,planoy=0,altura=12;
posx=0.0,posy=60.0;
explotando=0,cont=0,ang=0;
vel=1;

glRotatef(-25,1.0,0.0,0.0);
ang=0;
mon1=50;
}



/* ETAPAS */

static void etapa0()
{
int aux;

   glMaterialfv(GL_FRONT, GL_DIFFUSE, color_motor );
   glMaterialfv(GL_FRONT, GL_AMBIENT, floorcolor );

   // auxSolidBox(150.0,300.0,2.0); // DENIS 2003
   
   if ((posx>75)||(posx<-75)||(posy>150)||(posy<-150))
	explotando=1;
	
	glPushMatrix();
	
	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_edifi);
	glMaterialfv(GL_FRONT, GL_AMBIENT, color_edifi);
	caja(-40,15,15,5,80,30);
	caja(40,15,15,5,80,30);
	caja(0,40,15,80,5,30);
	caja(0,-44,15,82,5,30);
	mon1-=20*caja(0,-44.01,15,5,5,5);

	glPopMatrix();
 
	glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2);
	glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1);

	glPushMatrix();
	
	
	if (mon1>0)
		{
		mon1-=culebra(0,-3+6*seno[ang],2,9,3,1,1,ang);
		mon1-=culebra(0,0+6*seno[ang],2,9,3,1,1,(ang+90)%360);
		mon1-=culebra(0,3+6*seno[ang],2,9,3,1,1,(ang+180)%360);
		mon1-=culebra(0,6+6*seno[ang],2,9,3,1,1,(ang+270)%360);
		}
	else if (mon1>-10)
		{ 
		culebra_explota(0,-3+6*seno[ang],2,9,3,1,1,ang);
		culebra_explota(0,0+6*seno[ang],2,9,3,1,1,(ang+90)%360);
		culebra_explota(0,3+6*seno[ang],2,9,3,1,1,(ang+180)%360);
		culebra_explota(0,6+6*seno[ang],2,9,3,1,1,(ang+270)%360);
		mon1--;
		}

	glPopMatrix();

	glPushMatrix();
	asteroide(55,50,15,10);
	glPopMatrix();

	glPushMatrix();
	caja(42,-33,5+3*cose[((ang)%360)+1],3,10,16);
	caja(42,-33,26-3*cose[((ang)%360)+1],3,10,10);
	caja(-42,-33,5+3*cose[((ang)%360)+1],3,10,16);
	caja(-42,-33,26-3*cose[((ang)%360)+1],3,10,10);
	glPopMatrix();

	ang=(ang+3)%360;


glPopMatrix();

	if (mon1<-9)
		{
		inicia_etapa1();
		etapa=1;
		}


}



static void etapa1()
{
	glPushMatrix();
        glMaterialfv(GL_FRONT, GL_DIFFUSE, color_bolas );
        glMaterialfv(GL_FRONT, GL_AMBIENT, color_motor );                        
	// auxSolidSphere(0.001); // DENIS 2003
	glutSolidSphere(0.001,6,6);
	glPopMatrix();

	glPushMatrix();
	glTranslatef(0.0,-90.0,15.0);
	cilindro(0.0,90.0,15.0);
	glPopMatrix();

        glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2);
        glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1);
	posy-=500*caja(0.0,140,16,5,5,5);

glPopMatrix();

	if (posy<-110)
		{
		inicia_etapa2();
		etapa=2;
		}


}

static void etapa2()
{

glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas2);
glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas1);

caja(0.0,20.0,5.0,5,5,5);
caja(0.0,-80.0,5.0,5,5,5);
caja(50.0,-30.0,5.0,5,5,5);
caja(-50.0,-30.0,5.0,5,5,5);

if (mon1>0)
	{
	glPushMatrix();
	mon1-=Mario(2*cose[(ang*4)%360],5*cose[ang],absf(10*cose[ang]));
	glPopMatrix();
		
if (ang==0)
	{
	var[0]=2*cose[(ang*4)%360];
	var[1]=5*cose[ang]-25.5;
	var[2]=absf(10*cose[ang])+16.0;
	
	var[3]=posx-var[0];
	var[4]=posy-var[1];
	var[5]=(altura+5)-var[2];
	
	var[6]=sqrt(var[3]*var[3]+var[4]*var[4]+var[5]*var[5])/2;
	
	var[3]/=var[6];
	var[4]/=var[6];
	var[5]/=var[6];
	}

glMaterialfv(GL_FRONT, GL_DIFFUSE, color_alas1);
glMaterialfv(GL_FRONT, GL_AMBIENT, color_alas2);
	

asteroide(var[0],var[1],var[2],2);
var[0]+=var[3];
var[1]+=var[4];
var[2]+=var[5];

	}
else


	if (mon1>-50)
		{
	glPushMatrix();
	glScalef(absf(cose[ang])*2,absf(seno[ang])*2,absf(seno[ang])*2);
	Mario(2*cose[(ang*4)%360],5*cose[ang],absf(10*cose[ang]));
	glPopMatrix();
		mon1--;
		}
ang=(ang+10)%360;

glPopMatrix();

}












/* FUNCION DRAW */

static void draw( void )
{
   glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

if (!explotando)
{
   glPushMatrix();
   glShadeModel( GL_SMOOTH );

if ((altura==20 && Incy<0) || (altura==-2 && Incy>0))
	Rotandoy=0;

if (Rotandox>0)
	Rotx=(Rotx+Incx+360)%360;
	
else
	{
	Incx=0;
	if(Rotx!=0)
		if (Rotx>0 && Rotx<180)
			Rotx-=10;
		else
			Rotx=(Rotx+10)%360;
	}

RotyOld=Roty;
if (Rotandoy>0)
	Roty=(Roty+Incy+360)%360;
else
	{
	Incy=0;
	if(Roty!=0)
		if (Roty>0 && Roty<180)
			Roty-=10;
		else
			Roty=(Roty+10)%360;
	}

if (Rotx==70)
	Rotx=60;

if (Rotx==290)
	Rotx=300;

if (Roty==70)
	Roty=60;

if (Roty==290)
	Roty=300;

   glScalef(0.4,0.4,0.4);

   glTranslatef( 0.0, 0.0, 0.0 );
   glRotatef( Roty, 1.0, 0.0, 0.0 );
   glRotatef( Rotx, 0.0, 1.0, 0.0 );
   dibuja_nave();
   glPopMatrix();

if (Rotx>0 && Rotx<180)
	planox-=5;
if (Rotx>180)
 	planox+=5;
if (Roty>0 && Roty<180 && altura>-2)
	altura-=1;
if (Roty>180 && altura<20)
	altura+=1;

planox=(planox+360)%360;

if (vel==1)
	{
	posy=posy+cose[(-planox+360)%360]*0.75;
	posx=posx-seno[(-planox+360)%360]*0.75;
	}

if (Roty==0)
	planoy=0;
if (Roty>RotyOld)
	planoy-=2;
if (Roty<RotyOld)
	planoy+=2;

}

   glPushMatrix();
   glShadeModel( GL_SMOOTH );


   glRotatef(planoy,1.0,0.0,0.0);
  if (!explotando)
	sombra();
	
   glRotatef(planox,0.0,0.0,1.0);
   glTranslatef(posx,posy,-5.0-altura);
   dibuja_disparos();



/* ESCENARIOS */

   if (etapa==0)
   	etapa0();

   if (etapa==1)
   	etapa1();

   if (etapa==2)
   	etapa2();



/* AL PERDER EXPLOTA */



   if (explotando)
	{
	if (cont==0)
		cont=50;
	if (cont==2)
		exit(0);
	if (cont>20)
		{
		glShadeModel( GL_SMOOTH );
		glPushMatrix();
		glColor3fv(color_bolas);
		glMaterialfv(GL_FRONT, GL_DIFFUSE, color_bolas);
		glMaterialfv(GL_FRONT, GL_AMBIENT, color_motor);	
		
		glPushMatrix();
	glTranslatef(sin(cont*cont)/2,sin(cont*cont*cont)/2,cos(cont)/2);
		// auxSolidSphere(abs(sin(cont*3.1415926/3))+0.8); // DENIS 2003
		glutSolidSphere(abs(sin(cont*3.1415926/3))+0.8, 6,6);
		glPopMatrix();


	glTranslatef(cos(cont*cont)/2,cos(cont*cont*cont)/2,sin(cont)/2);
		// auxSolidSphere(2-(abs(sin(cont*3.1415926/3))+0.8));
		glutSolidSphere(2-(abs(sin(cont*3.1415926/3))+0.8),6,6);
		
		glPopMatrix();
		}
	cont-=1;
	}

   glFlush();
   glutSwapBuffers(); // auxSwapBuffers // DENIS 2003

   int i = 0;
   int c = 0;
   for (c = 0; c < 100000000; c++) {
     i = i +c;
   }

}

static void idle( void )
{
   draw();
}

/* PRINCIPAL */

int main( int argc, char *argv[] )
{
   GLfloat lightZeroColor[]    ={ 0.5,0.5,0.5,1.0 };   /* Light0 color */
   GLfloat lightOneColor[]     ={ 1.0,1.0,1.0,1.0 };   /* Light1 color */
   GLfloat aspect;

   iniciar_senos();

   glutInit(&argc, argv);
   glutInitDisplayMode( GLUT_RGBA | GLUT_DEPTH | GLUT_DOUBLE );
   glutInitWindowSize(600, 600);
   glutInitWindowPosition(100, 100);
   glutCreateWindow( "WARFOX" );


   inicia_etapa0();
   etapa=0;

   glClearColor( 0.0, 0.0, 0.0, 1.0 );
   glClearDepth( 1.0 );

   glEnable(GL_CULL_FACE);     /* ~50% better perfomance than no back-face
                                * culling on Entry Indigo */
   glEnable(GL_DEPTH_TEST);    /* Habilitar depth buffering */
   glEnable(GL_LIGHTING);      /* Habilitar lighting */
   glMatrixMode(GL_MODELVIEW); /* De nuevo a Modelview */


   glMatrixMode( GL_PROJECTION );
   aspect = (GLfloat)1;
   gluPerspective( ANG_VIS, aspect,P_NEAR, P_FAR );
   glMatrixMode( GL_MODELVIEW );

   glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, 0);
   glLightfv(GL_LIGHT1, GL_POSITION, lightOnePosition);
   glLightfv(GL_LIGHT1, GL_DIFFUSE, lightOneColor);
   glLightfv(GL_LIGHT1, GL_AMBIENT, lightZeroColor);
   glLightf(GL_LIGHT1, GL_CONSTANT_ATTENUATION, 0.5);
   glLightf(GL_LIGHT1, GL_LINEAR_ATTENUATION, 0.05);
   glEnable(GL_LIGHT1);

   /*
   tkExposeFunc( reshape );
   tkReshapeFunc( reshape );
   tkKeyDownFunc( key );
   tkIdleFunc( idle );
   tkDisplayFunc( draw );
   tkExec();
   */

   glutReshapeFunc( reshape );
   glutDisplayFunc(draw);
   glutIdleFunc(draw);
   glutKeyboardFunc(key);

   glutMainLoop();

   return 0;
}

