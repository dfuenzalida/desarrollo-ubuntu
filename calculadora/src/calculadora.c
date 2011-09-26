/**
	Submitted by:		Amarra, Christine
				Dueñas, Miko
				Lucena, Joshua
				Nieva, Jeanie
				Sevilla, Mae
				Vy, Sally
	Class:			CS 159.3 A
	Description:		Calculator Program 
**/



#include <Xm/Xm.h>
#include <Xm/PushB.h>
#include <Xm/Form.h>
#include <Xm/Text.h>
#include <string.h>
#include <stdlib.h>

#define DIVISIONS 6 										/* create an N x N grid for the layout */
#define BUFFER 100

double result = 0;
int checker = 0;
int operator = 0;
int point = 0;

main (int argc, char *argv[])
{
	Widget shell;								 			/* shell */
	Widget form; 											/* form manager */
	Widget decimal, zero, clear, equals, one, two, three, plus, four, five, six, minus;		/* number buttons */
	Widget seven, eight, nine, multiply, divide;
	Widget textfield;										/* textfield widgets */
	int top, left;											/* poitions in the layout grid */

	/* callback declarations */
	void decimal_cb (Widget, XtPointer, XtPointer);
	void zero_cb (Widget, XtPointer, XtPointer);
	void clear_cb (Widget, XtPointer, XtPointer);
	void equals_cb (Widget, XtPointer, XtPointer);
	void one_cb (Widget, XtPointer, XtPointer);
	void two_cb (Widget, XtPointer, XtPointer);
	void three_cb (Widget, XtPointer, XtPointer);
	void plus_cb (Widget, XtPointer, XtPointer);
	void four_cb (Widget, XtPointer, XtPointer);
	void five_cb (Widget, XtPointer, XtPointer);
	void six_cb (Widget, XtPointer, XtPointer);
	void minus_cb (Widget, XtPointer, XtPointer);
	void seven_cb (Widget, XtPointer, XtPointer);
	void eight_cb (Widget, XtPointer, XtPointer);
	void nine_cb (Widget, XtPointer, XtPointer);
	void multiply_cb (Widget, XtPointer, XtPointer);
	void divide_cb (Widget, XtPointer, XtPointer);



	XtAppContext app_context;

	Arg args[20];							/* resources */
	Cardinal n = 0;							/* number of resources applied */

	shell = XtVaOpenApplication (&app_context, "X/Motif Calculator (c) 2002", NULL, 0, &argc, argv, NULL, sessionShellWidgetClass, NULL);
	
	XtSetArg (args[n], XmNfractionBase, DIVISIONS); n++; 		 /* set the configuration and increment the resource number */

	form = XmCreateForm (shell, "form", args, n);

	/* TextField */ 
	n = 0;
	top = 1;
	left = 1;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;			/* locks the widget in the specified position */
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, 4); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;
	XtSetArg (args[n], XmNeditable, False); n++;
	XtSetArg (args[n], XmNcursorPositionVisible, False); n++;

	textfield =(Widget) XmCreateTextField(form, "textfield", args, n);
	XmTextSetString (textfield, "");
	XtManageChild (textfield);

	/* Zero */
	n = 0;
	top = 5;
	left = 1;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	zero = XmCreatePushButton (form, "0", args, n);
	XtManageChild (zero);
	XtAddCallback (zero, XmNactivateCallback, zero_cb, (XtPointer) textfield);	/* provide a callback routine */
											/* call_data that is provided is a reference to the
											 * textfield widget --> (...,(XtPointer) textfield)*/

	/* decimal */
	n = 0;
	top = 5;
	left = 2;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	decimal = XmCreatePushButton (form, ".", args, n);
	XtAddCallback (decimal, XmNactivateCallback, decimal_cb, (XtPointer) textfield);
	XtManageChild (decimal);
	
	/* equals */
	n = 0;
	top = 5;
	left = 3;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	equals = XmCreatePushButton (form, "=", args, n);
	XtManageChild (equals);
	XtAddCallback (equals, XmNactivateCallback, equals_cb, (XtPointer) textfield);
	
	/* Divide */
	n = 0;
	top = 5;
	left = 4;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	divide =  XmCreatePushButton (form, "/", args, n);
	XtManageChild (divide);
	XtAddCallback (divide, XmNactivateCallback, divide_cb, (XtPointer) textfield);

	/* One */
	n = 0;
	top = 4;
	left = 1;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	one =  XmCreatePushButton (form, "1", args, n);
	XtManageChild (one);
	XtAddCallback (one, XmNactivateCallback, one_cb, (XtPointer) textfield);

	/* Two */	
	n = 0;
	top = 4;
	left = 2;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;
	
	two =  XmCreatePushButton (form, "2", args, n);
	XtManageChild (two);
	XtAddCallback (two, XmNactivateCallback, two_cb, (XtPointer) textfield);
	

	/* Three */	
	n = 0;
	top = 4;
	left = 3;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;
	three =  XmCreatePushButton (form, "3", args, n);
	XtManageChild (three);
	XtAddCallback (three, XmNactivateCallback, three_cb, (XtPointer)textfield);

	/* Plus */	
	n = 0;
	top = 4;
	left = 4;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	plus =  XmCreatePushButton (form, "+", args, n);
	XtManageChild (plus);
	XtAddCallback (plus, XmNactivateCallback, plus_cb, (XtPointer) textfield);

	/* Four */	
	n = 0;
	top = 3;
	left = 1;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	four =  XmCreatePushButton (form, "4", args, n);
	XtManageChild (four);
	XtAddCallback (four, XmNactivateCallback, four_cb, (XtPointer) textfield);
	
	
	/* Five */
	n = 0;
	top = 3;
	left = 2;
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	five =  XmCreatePushButton (form, "5", args, n);
	XtManageChild (five);
	XtAddCallback (five, XmNactivateCallback, five_cb, (XtPointer) textfield);

	/* Six */	
	n = 0;
	top = 3;
	left = 3;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	six =  XmCreatePushButton (form, "6", args, n);
	XtManageChild (six);
	XtAddCallback (six, XmNactivateCallback, six_cb, (XtPointer) textfield);

	/* Minus */	
	n = 0;
	top = 3;
	left = 4;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	minus =  XmCreatePushButton (form, "-", args, n);
	XtManageChild (minus);
	XtAddCallback (minus, XmNactivateCallback, minus_cb, (XtPointer) textfield);

	/* Seven */	
	n = 0;
	top = 2;
	left = 1;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	seven =  XmCreatePushButton (form, "7", args, n);
	XtManageChild (seven);
	XtAddCallback (seven, XmNactivateCallback, seven_cb, (XtPointer) textfield);

	/* Eight */	
	n = 0;
	top = 2;
	left = 2;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;
	
	eight =  XmCreatePushButton (form, "8", args, n);
	XtManageChild (eight);
	XtAddCallback (eight, XmNactivateCallback, eight_cb, (XtPointer) textfield);

	/* Nine */	
	n = 0;
	top = 2;
	left = 3;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	nine =  XmCreatePushButton (form, "9", args, n);
	XtManageChild (nine);
	XtAddCallback (nine, XmNactivateCallback, nine_cb, (XtPointer) textfield);

	/* Multiply */	
	n = 0;
	top = 2;
	left = 4;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	multiply =  XmCreatePushButton (form, "*", args, n);
	XtManageChild (multiply);
	XtAddCallback (multiply, XmNactivateCallback, multiply_cb, (XtPointer) textfield);

	/* Clear */ 
	n = 0;
	top = 1;
	left = 4;	
	XtSetArg (args[n], XmNtopAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNtopPosition, top); n++;
	XtSetArg (args[n], XmNleftAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNleftPosition, left); n++;	
	XtSetArg (args[n], XmNrightAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNrightPosition, left+1); n++;
	XtSetArg (args[n], XmNbottomAttachment, XmATTACH_POSITION); n++;
	XtSetArg (args[n], XmNbottomPosition, top+1); n++;

	clear =  XmCreatePushButton (form, "C", args, n);
	XtManageChild (clear);
	XtAddCallback (clear, XmNactivateCallback, clear_cb, (XtPointer) textfield);

	XtManageChild (form);
	XtRealizeWidget (shell);
	XtAppMainLoop (app_context);
}


/* callback routines */  

void decimal_cb (Widget widget, XtPointer client_data, XtPointer call_data) 
{
	char *value;
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
	if (checker != 0) {
		XmTextSetString (text,"");
		checker = 0;
	}
	if (point == 0){
		XmTextSetString (text, strcat (value, "."));
		point = 1;
	}
	printf("Decimal clicked\n");
}

void zero_cb (Widget widget, XtPointer client_data, XtPointer call_data)
{	
	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
        value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "0"));
	printf("0\n");
}
void clear_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	Widget text = (Widget) client_data;
	XmTextSetString(text, "");
	result = 0.0;
	printf("clear clicked\n");
	
}
void equals_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	char temp[BUFFER];
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
	if (operator == 1)
		result = result + atof (value);
	else if (operator == 2)
		result = result - atof (value);
	else if (operator == 3)
		result = result * atof (value);
	else if (operator == 4)
		result = result / atof (value);
	sprintf (temp, "%f", result);
	XmTextSetString(text,temp);
	result = 0;
	operator = 0;
	checker = 1;
	point = 0;
	printf("equals clicked\n");

}
void one_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
        value = XmTextGetString (text);
	XmTextSetString(text, strcat(value,"1"));
	printf("one clicked\n");
}
void two_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "2"));
	printf ("two clicked\n");

}
void three_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "3"));
	printf("three clicked\n");

}
void plus_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	char temp[BUFFER];
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
	if (result != 0) {
		result = result + atof(value);
	}
	else
		result = atof (value);

	sprintf (temp,"%f",result);
	XmTextSetString(text,temp);
	printf("plus clicked");
	checker = 1;
	operator = 1;
	point = 0;
}
void four_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "4"));
	printf("four clicked\n");
}
void five_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "5"));
	printf("five clicked\n");

}
void six_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "6"));
	printf("six clicked\n");

}
void minus_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	char temp[BUFFER];
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
        if (result != 0)
                result = result - atof(value);
        else
                result = atof (value);

        sprintf (temp,"%f",result);
        XmTextSetString(text,temp);
	printf("minus clicked");
	checker = 1;
	operator = 2;
	point = 0;
}
void seven_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "7"));
	printf("seven clicked\n");

}
void eight_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "8"));
	printf("eight clicked\n");

}
void nine_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	Widget text = (Widget) client_data;
        if (checker != 0) {
                XmTextSetString (text,"");
                checker = 0;
        }
	value = XmTextGetString (text);
	XmTextSetString(text, strcat (value, "9"));
	printf("nine clicked\n");

}
void multiply_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	char temp[BUFFER];
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
        if (result != 0)
                result = result * atof(value);
        else
                result = atof (value);

        sprintf (temp,"%f",result);
        XmTextSetString(text,temp);
	printf("multiply clicked");
	checker = 1;
	operator = 3;
	point = 0;
}

void divide_cb (Widget w, XtPointer client_data, XtPointer call_data)
{	char *value;
	char temp[BUFFER];
	Widget text = (Widget) client_data;
	value = XmTextGetString (text);
        if (result != 0) {
		if (value != 0)
	        	result = result / atof(value);
		else
			XmTextSetString (text,"E");
	}
        else
                result = atof (value);

        sprintf (temp,"%f",result);
        XmTextSetString(text,temp);
	printf("divide clicked");
	checker = 1;
	operator = 4;
	point = 0;
}
