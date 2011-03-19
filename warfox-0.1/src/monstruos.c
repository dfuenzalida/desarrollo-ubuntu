#include "globales.h"
#include "monstruos.h"

/* CULEBRA */

int culebra(float x,float y,float z,int cant,float rad,
float separa,int hv,int ang)
{
int i,aux=0;

glPushMatrix();
caja(x,y,z,rad,rad,rad);

for (i=1;i<=(cant-1);i++)
{
	if (hv)
		aux+=caja(x+(rad+separa)*i*seno[(ang-10*i+360)%360],y,
		z+(rad+separa)*i*absf(cose[(ang-10*i+360)%360]),rad,rad,rad);
	else
		aux+=caja(x,y+(rad+separa)*i*seno[(ang-10*i+360)%360],
		z+(rad+separa)*i*absf(cose[(ang-10*i+360)%360]),rad,rad,rad);
}

glPopMatrix();
return(aux);

}

/* CULEBRA EXPLOTA */

void culebra_explota(float x,float y,float z,int cant,float rad,
float separa,int hv,int ang)
{
int i;

glPushMatrix();
impacto(x,y,z);

for (i=1;i<=(cant-1);i++)
{
	if (hv)
		impacto(x+(rad+separa)*i*seno[(ang-10*i+360)%360],y,
		z+(rad+separa)*i*absf(cose[(ang-10*i+360)%360]));
	else
		impacto(x,y+(rad+separa)*i*seno[(ang-10*i+360)%360],
		z+(rad+separa)*i*absf(cose[(ang-10*i+360)%360]));
}

glPopMatrix();

}
