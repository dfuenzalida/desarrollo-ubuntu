#include <math.h>
#include <stdio.h>
#include <GL/glut.h>

#define COS(X)   cos( (X) * 3.1415926/180.0 )
#define SIN(X)   sin( (X) * 3.1415926/180.0 )


#define ANG_VIS     75.0            /* Angulo del campo de  Vision */
#define P_NEAR      0.1             /* Distancia mas cercana visible */
#define P_FAR       100.0           /* Distancia mas lejana visible*/
#define S_STEP      10.0            /* Variacion de la serpiente*/
#define MAX_ANG     60              /* Maxima variacion  */
#define ANG_ROT     10              /* Angulo de rotacion Mundial*/
#define ZOOM_IN     1.1             /* Factor de Zoom Mundial IN*/
#define ZOOM_OUT    0.9             /* 0.9 Factor de Zoom Mundial OUT*/
#define P_VIEW1      0.0,4.0,2.0    /* 0,4,2 Punto de vista*/
#define P_CENTER1    0.0,-2.0,0.0     /* 0,-2,0 Centro de la vision*/
#define P_VIEW2      0.0,0.1,10.0    /* 0,4,2 Punto de vista*/
#define P_CENTER2    0.0,0.0,0.0     /* 0,-2,0 Centro de la vision*/
#define P_VIEW3      10.0,0.0,0.0    /* 0,4,2 Punto de vista*/
#define P_CENTER3    0.0,0.0,0.0     /* 0,-2,0 Centro de la vision*/
#define P_VIEW4	     0.0,4.0,0.0     /* CAMARA ATRAS */
#define P_CENTER4    0.0,-2.0,0.0
#define NORMAL      0.0,0.0,1.0     /* Normal al centro de vision*/
#define L1_POS      0.0,6.0,6.0     /* Light1 posicion*/

typedef struct balas{
	GLfloat orix;
	GLfloat oriy;
	GLfloat oriz;
	GLfloat incy;
	GLfloat incx;
	GLfloat incz;
	GLint dist;
	GLint impacto;
	GLint activo;
	}balas;

GLfloat lightOnePosition[];

GLfloat color_motor[];
GLfloat color_tronco[];
GLfloat color_alas1[];
GLfloat color_alas2[];
GLfloat color_edifi[];

GLfloat floorcolor[];
GLfloat color_bolas[];
GLfloat color_negro[];

GLint Rotx,Roty,Incx,Incy,RotyOld,Rotandox,Rotandoy;
GLint planox,planoy,altura;
GLfloat posx,posy; 
GLint explotando,cont,ang;
GLint vel,etapa;

GLfloat seno[720],cose[720];

balas disparos[6];
GLint mon1;
GLfloat var[10];

void iniciar_senos();
float absf(float x);


