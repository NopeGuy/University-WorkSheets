#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>
#include <stdio.h>

void changeSize(int w, int h)
{
	if (h == 0)
		h = 1;

	float ratio = w * 1.0f / h;

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);
	glMatrixMode(GL_MODELVIEW);

	glViewport(0, 0, w, h);
}

void renderScene(void)
{
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set camera
	glLoadIdentity();
	gluLookAt(0.0f, 0.0f, 5.0f,
			  0.0f, 0.0f, -1.0f,
			  0.0f, 1.0f, 0.0f);

	// Draw a wireframe teapot
	glutWireTeapot(1.0);

	// End of frame
	glutSwapBuffers();
}

void printInfo()
{
	printf("Vendor: %s\n", glGetString(GL_VENDOR));
	printf("Renderer: %s\n", glGetString(GL_RENDERER));
	printf("Version: %s\n", glGetString(GL_VERSION));
}

int main(int argc, char **argv)
{
	// Initialize GLUT
	glutInit(&argc, argv);

	// Set display mode
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);

	// Create a window
	glutCreateWindow("OpenGL Window");

	// Register callbacks
	glutReshapeFunc(changeSize);
	glutDisplayFunc(renderScene);

	// some OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	// enter GLUT’s main cycle
	glutMainLoop();

	return 0;
}
