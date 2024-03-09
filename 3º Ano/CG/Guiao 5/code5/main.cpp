#include <stdio.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

float step = 0.0f;

float alfa = 0.0f, beta = 0.5f, radius = 100.0f;
float camX, camY, camZ;

int r = 50;
int ri = 35;
int rc = 15;

// Fixed tree positions for initialization
float treePositions[10][2];

void initializeTreePositions(int nTrees)
{
	srand(420);
	for (int i = 0; i < nTrees; i++)
	{
		float angle = (rand() % 360) * (M_PI / 180.0);
		float distance = rand() % 70 + 50;
		treePositions[i][0] = distance * cos(angle);
		treePositions[i][1] = distance * sin(angle);
	}
}

void drawTree(int nTrees)
{
	for (int i = 0; i < nTrees; i++)
	{
		if (fabs(treePositions[i][0]) <= 100.0f && fabs(treePositions[i][1]) <= 100.0f)
		{
			glPushMatrix();
			glTranslatef(treePositions[i][0], 0, treePositions[i][1]);

			// Draw the trunk
			glColor3f(0.5f, 0.35f, 0.05f);
			glRotatef(-90, 1, 0, 0);
			glutSolidCone(1, 5, 10, 10);

			// Draw the leaves
			glColor3f(0.0f, 1.0f, 0.0f);
			glTranslatef(0, 0, 3);
			glutSolidCone(3, 7, 10, 10);

			glPopMatrix();
		}
	}
}

void spherical2Cartesian()
{
	camX = radius * cos(beta) * sin(alfa);
	camY = radius * sin(beta);
	camZ = radius * cos(beta) * cos(alfa);
}

void changeSize(int w, int h)
{
	if (h == 0)
		h = 1;

	float ratio = w * 1.0 / h;

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, w, h);
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);
	glMatrixMode(GL_MODELVIEW);
}

void renderScene(void)
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	gluLookAt(camX, camY, camZ,
			  0.0, 0.0, 0.0,
			  0.0f, 1.0f, 0.0f);

	glColor3f(0.2f, 0.8f, 0.2f);
	glBegin(GL_TRIANGLES);
	glVertex3f(100.0f, 0, -100.0f);
	glVertex3f(-100.0f, 0, -100.0f);
	glVertex3f(-100.0f, 0, 100.0f);

	glVertex3f(100.0f, 0, -100.0f);
	glVertex3f(-100.0f, 0, 100.0f);
	glVertex3f(100.0f, 0, 100.0f);
	glEnd();

	glColor3f(1.0f, 0.0f, 1.0f);
	glutSolidTorus(2, 4, 20, 20);

	glPushMatrix();
	drawTree(300);
	glPopMatrix();

	glColor3ub(255, 0, 0);
	for (int i = 0; i < 16; i++)
	{
		glPushMatrix();
		glRotated(22.5 * i + step, 0, 1, 0);
		glTranslated(ri, 2, 0);
		glutSolidTeapot(2);
		glPopMatrix();
	}

	glColor3ub(0, 0, 255);
	for (int i = 0; i < 8; i++)
	{
		glPushMatrix();
		glRotated(45.0 * i - step, 0, 1, 0);
		glTranslated(rc, 2, 0);
		glRotated(180, 0, 1, 0);
		glutSolidTeapot(2);
		glPopMatrix();
	}

	step += 0.5f;

	glutSwapBuffers();
}

void processKeys(unsigned char c, int xx, int yy)
{
	switch (c)
	{
	case 'w':
		radius -= 1.0f;
		if (radius < 1.0f)
			radius = 1.0f;
		break;

	case 's':
		radius += 1.0f;
		break;
	}
	spherical2Cartesian();
	glutPostRedisplay();
}

void processSpecialKeys(int key, int xx, int yy)
{
	switch (key)
	{
	case GLUT_KEY_RIGHT:
		alfa -= 0.1;
		break;

	case GLUT_KEY_LEFT:
		alfa += 0.1;
		break;

	case GLUT_KEY_UP:
		beta += 0.1f;
		if (beta > 1.5f)
			beta = 1.5f;
		break;

	case GLUT_KEY_DOWN:
		beta -= 0.1f;
		if (beta < -1.5f)
			beta = -1.5f;
		break;

	case GLUT_KEY_PAGE_DOWN:
		radius -= 1.0f;
		if (radius < 1.0f)
			radius = 1.0f;
		break;

	case GLUT_KEY_PAGE_UP:
		radius += 1.0f;
		break;
	}
	spherical2Cartesian();
	glutPostRedisplay();
}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("CG@DI-UM");

	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	spherical2Cartesian();
	initializeTreePositions(300);

	glutIdleFunc(renderScene);
	glutMainLoop();

	return 1;
}
