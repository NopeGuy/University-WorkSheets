#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

struct Polar
{
	double radius, alpha, beta;
};
struct RGB
{
	double r, g, b;
};
struct HSV
{
	double h, s, v;
};

Polar camPos = {sqrt(75), M_PI_4, M_PI_4};

double polarX(Polar polar) { return polar.radius * cos(polar.beta) * sin(polar.alpha); }
double polarY(Polar polar) { return polar.radius * sin(polar.beta); }
double polarZ(Polar polar) { return polar.radius * cos(polar.beta) * cos(polar.alpha); }

void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0)
		h = 1;

	// compute window's aspect ratio 
	float ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();
	
	// Set the viewport to be the entire window
    glViewport(0, 0, w, h);

	// Set perspective
	gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}
void drawCylinder(float radius, float height, int slices)
{
	double hh = height / 2;
	double sliceStep = M_PI * 2 / slices;
	Polar baseP = {hh, 0, -M_PI / 2};
	Polar topP = {hh, 0, M_PI / 2};

	// Draw base
	glBegin(GL_LINE_LOOP);
	for (int slice = 0; slice <= slices; slice++)
	{
		Polar p = {sqrt(hh * hh + radius * radius), sliceStep * slice, -atan(hh / radius)};
		glVertex3d(polarX(p), polarY(p), polarZ(p));
	}
	glEnd();

	// Draw top
	glBegin(GL_LINE_LOOP);
	for (int slice = 0; slice <= slices; slice++)
	{
		Polar p = {sqrt(hh * hh + radius * radius), sliceStep * slice, atan(hh / radius)};
		glVertex3d(polarX(p), polarY(p), polarZ(p));
	}
	glEnd();

	// Draw sides
	glBegin(GL_LINES);
	for (int slice = 0; slice <= slices; slice++)
	{
		Polar pb = {sqrt(hh * hh + radius * radius), sliceStep * slice, -atan(hh / radius)};
		Polar pt = {sqrt(hh * hh + radius * radius), sliceStep * slice, atan(hh / radius)};
		glVertex3d(polarX(pt), polarY(pt), polarZ(pt));
		glVertex3d(polarX(pb), polarY(pb), polarZ(pb));
	}
	glEnd();
}

void renderScene(void) {

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(5.0,5.0,5.0, 
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);

	drawCylinder(1,2,100);

	// End of frame
	glutSwapBuffers();
}


void processKeys(unsigned char c, int xx, int yy) {

// put code to process regular keys in here

}


void processSpecialKeys(int key, int xx, int yy) {

// put code to process special keys in here

}


int main(int argc, char **argv) {

// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(800,800);
	glutCreateWindow("CG@DI-UM");
		
// Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	
// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	
// enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
