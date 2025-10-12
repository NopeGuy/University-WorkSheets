#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#include <GLUT/glew.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include "math.h"
#include "../utils/figura.hpp"
#include "../utils/ponto.hpp"
#include "../utils/list.hpp"
#include "../tinyXML/tinyxml2.h"
#include "../utils/parser.hpp"
#include <vector>
#include "../utils/curve.hpp"

using namespace std;

#define NOW ((1.0f * glutGet(GLUT_ELAPSED_TIME)) / 1000.0f)
float init_time = 0.0f;

GLuint verticeCount;
GLuint vertices = 0;
static int startpos = 0;
static int endpos = 0;

/*
// Structure to save the color of the figure
float r, g, b;
struct Color
{
	float r, g, b;
};
vector<Color> colors;
*/

// VBO's
GLuint *buffers = NULL;	   // temos um buffer para cada figura
int info[100];			   // aqui guardamos o tamanho de cada buffer de cada figura
unsigned int figCount = 0; // total de figuras existentes no ficheiro de configuração.
GLuint bufferId[100];	   // buffer id

// Códigos de cores
#define RED 1.0f, 0.0f, 0.0f
#define GREEN 0.0f, 1.0f, 0.0f
#define BLUE 0.0f, 0.0f, 1.0f
#define YELLOW 1.0f, 1.0f, 0.0f
#define CYAN 0.0f, 1.0f, 1.0f
#define WHITE 1.0f, 1.0f, 1.0f

// Variáveis da câmara
float alpha = M_PI / 4;
float beta_ = M_PI / 4;
float radius = 5.0f;
float camx = 5.0f;
float camy = 5.0f;
float camz = 5.0f;
float lookAtx = 0.0f;
float lookAty = 0.0f;
float lookAtz = 0.0f;
float upx = 0.0f;
float upy = 0.0f;
float upz = 0.0f;
float fov = 0.0f;
float near = 0.0f;
float far = 0.0f;

// Window Settings
int width = 0;
int height = 0;

// File Settings
std::string fileName = "";

int mode = GL_LINE;

Parser *settings = new Parser();
List figuras = NULL;

void changeSize(int w, int h)
{

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if (h == 0)
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
	gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

// fill lists

void fillList(const Group *group)
{
	if (group != nullptr)
	{
		int modelLength = group->modelFiles.size();

		for (int i = 0; i < modelLength; i++)
		{
			const char *fileChar = group->modelFiles.at(i).fileName.c_str();
			addValueList(figuras, fileToFigura(fileChar));
		}
		for (const auto &child : group->children)
			fillList(&child);
	}
}

void importFiguras(List figs)
{
	figCount = getListLength(figs);

	// Iterate through the list of figures and create buffers
	for (unsigned long i = 0; i < getListLength(figs); i++)
	{
		glGenBuffers(i + 1, &bufferId[i]);
		Figura fig = (Figura)getListElemAt(figs, i);
		List figPontos = getPontos(fig);
		vector<float> vVertices;

		// Iterate through the list of points in the figure and populate vVertices
		for (unsigned long j = 0; j < getListLength(figPontos); j++)
		{
			Ponto point = (Ponto)getListElemAt(figPontos, j);
			vVertices.push_back(getX(point));
			vVertices.push_back(getY(point));
			vVertices.push_back(getZ(point));
		}
		// Calculate the number of vertices
		info[i] = (vVertices.size() / 3);

		// Bind the buffer and fill it with data
		glBindBuffer(GL_ARRAY_BUFFER, bufferId[i]);
		glBufferData(GL_ARRAY_BUFFER, vVertices.size() * sizeof(float), vVertices.data(), GL_STATIC_DRAW);
	}
}

void drawFigures(int startpos, int endpos)
{ 
	for (unsigned long i = startpos; i < endpos; i++)
	{
		glBindBuffer(GL_ARRAY_BUFFER, bufferId[i]);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		// Set the color
		//glColor3f(colors[i].r, colors[i].g, colors[i].b);
		// Draw the figure
		glDrawArrays(GL_TRIANGLES, 0, info[i]);
	}
	
	if (endpos == figCount)
	{
		startpos = 0;
	}
	else
	{
		startpos = endpos;
	}
}

// Function to execute transformations
void executeTransformations(const Transform &transform)
{
	float x = transform.x;
	float y = transform.y;
	float z = transform.z;
	char tr_type = transform.type;
	if (tr_type == 'r')
	{ // Rotation
		float r_angle = transform.angle;
		float r_time = transform.time;
		if (r_time > 0.0f)
		{
			r_angle = ((NOW - init_time) * 360.0f) / r_time;
		}
		glRotatef(r_angle, x, y, z);
	}
	else if (tr_type == 't')
	{ // Translation
		float t_time = transform.time;
		if (t_time > 0.0f)
		{
			vector<Position> t_points = transform.points;
			vector<float> px;
			vector<float> py;
			vector<float> pz;
			for (const auto &point : t_points)
			{
				px.push_back(point.x);
				py.push_back(point.y);
				pz.push_back(point.z);
			}
			float t = fmod(NOW - init_time, t_time) / t_time;
			float pos[3];
			float deriv[3];
			GlobalCatRomPoint(t, px, py, pz, pos, deriv);
			displayCatmullRom(px, py, pz);
			glTranslatef(pos[0], pos[1], pos[2]);

			if (transform.align == true)
			{
				float rot[16];
				float *X = deriv;
				float Y[3] = {0, 1, 0}; // Variável que define a normal do objeto
				float Z[3];

				// Z = X x Y
				vetxvet(X, Y, Z);
				// Y = Z x X
				vetxvet(Z, X, Y);

				// Normaliza os vetores
				normalize(X);
				normalize(Y);
				normalize(Z);

				buildRotMatrix(X, Y, Z, rot);

				glMultMatrixf(rot);
			}
		}
		else
		{
			glTranslatef(x, y, z);
		}
	}
	else if (tr_type == 's')
	{					   // Scale
		glScalef(x, y, z); // Fix: Pass x, y, z as arguments
	}
}

// Desenha os eixos, caso a flag esteja ativa.
void drawEixos()
{
	glBegin(GL_LINES);
	// X axis in red
	glColor3f(RED);
	glVertex3f(-100.0f, 0.0f, 0.0f);
	glVertex3f(100.0f, 0.0f, 0.0f);
	// Y Axis in Green
	glColor3f(GREEN);
	glVertex3f(0.0f, -100.0f, 0.0f);
	glVertex3f(0.0f, 100.0f, 0.0f);
	// Z Axis in Blue
	glColor3f(BLUE);
	glVertex3f(0.0f, 0.0f, -100.0f);
	glVertex3f(0.0f, 0.0f, 100.0f);
	glColor3f(WHITE);
	glEnd();
}

void drawTeapot()
{
	glPushMatrix();
	glColor3f(WHITE);
	glTranslatef(-5, 0, 0);
	glutWireTeapot(1);
	glPopMatrix();
}

void drawGroups(const Group *group)
{
	if (group != nullptr)
	{
		glPushMatrix(); // Push the current matrix onto the stack
		for (const auto &transform : group->transforms)
		{
			executeTransformations(transform);
		}

		endpos = startpos + group->modelFiles.size();
		// printf(group->modelFiles.size() > 0 ? "Drawing %d model(s)\n" : "No models to draw\n", group->modelFiles.size());
		drawFigures(startpos, endpos); // Draw the figure(s)

		// Render child groups
		for (const auto &child : group->children)
		{
			startpos = endpos;
			drawGroups(&child);
		}
		if (group->children.size() == 0) startpos = 0;
		glPopMatrix(); // Restore the previous matrix
	}
}

void renderScene(void)
{
	// Clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Set the camera
	glLoadIdentity();
	gluLookAt(camx, camy, camz, lookAtx, lookAty, lookAtz, upx, upy, upz);

	glColor3f(1.0f, 1.0f, 1.0f);
	glPolygonMode(GL_FRONT_AND_BACK, mode);

	// DEBUG: Draw eixos
	glPushMatrix();
	glScalef(2.0f, 2.0f, 2.0f);
	drawEixos();
	glPopMatrix();
	drawGroups(&settings->rootNode);

	// End of frame
	glutSwapBuffers();
}

void specKeyProc(int key_code, int x, int y)
{
	switch (key_code)
	{
	case GLUT_KEY_UP:
	{
		radius -= 0.5f;
		break;
	}

	case GLUT_KEY_DOWN:
	{
		radius += 0.5f;
		break;
	}

	default:
		break;
	}
	glutPostRedisplay();
}

void keyProc(unsigned char key, int x, int y)
{
	switch (key)
	{
	case 'a':
	{ // left
		alpha -= 0.1f;
		break;
	}

	case 'd':
	{ // right
		alpha += 0.1f;
		break;
	}

	case 'w':
	{ // up
		beta_ += beta_ <= 1.48f ? 0.1f : 0.0f;
		break;
	}

	case 's':
	{ // down
		beta_ -= beta_ >= -1.48f ? 0.1f : 0.0f;
		break;
	}

	case ('f'):
		mode = GL_FILL;
		break;

	case ('l'):
		mode = GL_LINE;
		break;

	case ('p'):
		mode = GL_POINT;
		break;

	case ('t'):
	{
		radius -= 1.0f;
		break;
	}

	case ('y'):
	{
		radius += 1.0f;
		break;
	}
	case('m'):
	{
		// Move camera to the right
		lookAtx += 2.0f;
		break;
	}
	case('n'):
	{
		// Move camera to the left
		lookAtx -= 2.0f;
		break;
	}
	case('j'):
	{
		// Move camera up
		lookAty += 2.0f;
		break;
	}
	case('k'):
	{
		// Move camera down
		lookAty -= 2.0f;
		break;
	}
	default:
		break;
	}
	camx = radius * sin(alpha) * cos(beta_);
	camy = radius * sin(beta_);
	camz = radius * cos(alpha) * cos(beta_);
	glutPostRedisplay();
}

int main(int argc, char *argv[])
{
	if (argc != 2)
	{
		printf("Usage: %s <path to xml file>\n", argv[0]);
		return 1;
	}
	std::string filePath = argv[1];
	printf("File path: %s\n", filePath.c_str());
	figuras = newEmptyList();
	settings = ParserSettingsConstructor(filePath);
	camx = settings->camera.position.x;
	camy = settings->camera.position.y;
	camz = settings->camera.position.z;
	lookAtx = settings->camera.lookAt.x;
	lookAty = settings->camera.lookAt.y;
	lookAtz = settings->camera.lookAt.z;
	upx = settings->camera.up.x;
	upz = settings->camera.up.z;
	upy = settings->camera.up.y;
	fov = settings->camera.projection.fov;
	near = settings->camera.projection.near;
	far = settings->camera.projection.far;
	width = settings->window.width;
	height = settings->window.height;
	radius = sqrt(camx * camx + camy * camy + camz * camz);
	alpha = acos(camz / sqrt(camx * camx + camz * camz));
	beta_ = asin(camy / radius);

	fillList(&settings->rootNode);

	// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("Engine");
	glewInit();
	// Required callback registry
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(renderScene);
	// put here the registration of the keyboard callbacks (por enquanto só mexem na camara como forma de debug)
	glutKeyboardFunc(keyProc);
	glutSpecialFunc(specKeyProc);

	glEnableClientState(GL_VERTEX_ARRAY);

	importFiguras(figuras);
	/* Colors
	colors.push_back({1.0f, 1.0f, 0.0f}); // Yellow for Sun
	colors.push_back({0.9f, 0.4f, 0.0f}); // Orange for Mercury
	colors.push_back({0.7f, 0.7f, 0.7f}); // Gray for Venus
	colors.push_back({0.0f, 0.0f, 1.0f}); // Blue for Earth
	colors.push_back({0.7f, 0.7f, 0.7f}); // Gray for Moon
	colors.push_back({0.8f, 0.8f, 0.8f}); // Light Gray for Mars
	colors.push_back({0.8f, 0.4f, 0.0f}); // Brown for Jupiter
	colors.push_back({0.6f, 0.6f, 0.3f}); // Dark Gray for Saturn
	colors.push_back({0.8f, 0.6f, 0.4f}); // Light Brown for Saturn Ring
	colors.push_back({0.0f, 0.8f, 1.0f}); // Light Blue for Uranus
	colors.push_back({0.0f, 0.0f, 0.8f}); // Dark Blue for Neptune
	colors.push_back({0.7f, 0.7f, 0.7f}); // Gray for Comet
	*/
	//	drawGroups(&settings->rootNode);

	// OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	// enter GLUT's main cycle
	glutMainLoop();

	deepDeleteList(figuras, deleteFigura);

	return 1;
}

/* Debugging function to print the parsed values

int main(int argc, char* argv[]) {
	if (argc != 2) {
		std::cerr << "Usage: " << argv[0] << " <xml_file_path>" << std::endl;
		return 1;
	}

	const std::string filePath = argv[1];

	// Create a Parser object and parse the XML file
	Parser* settings = ParserSettingsConstructor(filePath);
	if (!settings) {
		std::cerr << "Failed to parse XML file." << std::endl;
		return 1;
	}

	// Print the parsed values
	print(*settings);

	// Clean up memory
	delete settings;

	return 0;
}
*/