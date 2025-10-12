#include <IL/il.h>
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
#include <memory.h>

using namespace std;

#define NOW ((1.0f * glutGet(GLUT_ELAPSED_TIME)) / 1000.0f)
float init_time = 0.0f;

GLuint verticeCount;
GLuint vertices = 0;
static int startpos = 0;
static int endpos = 0;
// Structure to save vertices and normals of each figure
struct TupleVector
{
	std::vector<float> v1;
	std::vector<float> v2;
};
// list of TupleVector
std::vector<TupleVector> tupleVectorList;

// VBO's
GLuint* buffers = NULL;	   // temos um buffer para cada figura
int info[100];			   // aqui guardamos o tamanho de cada buffer de cada figura
unsigned int figCount = 0; // total de figuras existentes no ficheiro de configuração.
GLuint bufferId[100];	   // buffer id
GLuint* buffersN = NULL;
GLuint buuferNId[100];
GLuint* buffersT = NULL;
GLuint buuferTId[100];
GLuint buuferTFile[100] = { 0 };

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

// Lighting Settings
int lightingOn = 0;

// File Settings
std::string fileName = "";

int mode = GL_LINE;

std::unique_ptr<Parser> settings;
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

void fillList(const Group* group)
{
	if (group != nullptr)
	{
		int modelLength = group->modelFiles.size();

		for (int i = 0; i < modelLength; i++)
		{
			const char* fileChar = group->modelFiles.at(i).fileName.c_str();
			const char* textChar = group->modelFiles.at(i).textureName.c_str();
			addValueList(figuras, fileToFigura(fileChar, textChar));
		}
		for (const auto& child : group->children)
			fillList(&child);
	}
}

void loadTexture(int i, const char* location)
{
	string locatione = "../configs/" + string(location);
	/* Variáveis auxiliares à definição da imagem em memória */
	unsigned int image, width, height;
	unsigned char* data;

	/* Leitura da imagem */
	ilGenImages(1, &image);
	ilBindImage(image);

	/* Verifica se a textura existe */
	if (!ilLoadImage((ILstring)locatione.c_str()))
		throw std::invalid_argument("invalid texture path given");

	/* Associação de propriedades da imagem */
	width = ilGetInteger(IL_IMAGE_WIDTH);
	height = ilGetInteger(IL_IMAGE_HEIGHT);

	/* Verifica se a textura é uma imagem */
	if (!ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE))
		throw std::invalid_argument("invalid texture path given");

	/* Informação da imagem */
	data = ilGetData();

	/* Associação da imagem à textura */
	glGenTextures(1, &(buuferTFile[i]));
	glBindTexture(GL_TEXTURE_2D, buuferTFile[i]);

	/* Associação dos parâmetros */
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

	/* Associação da imagem */
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
	glGenerateMipmap(GL_TEXTURE_2D);
}

void importFiguras(List figs) // TODO: Modificar
{
	figCount = getListLength(figs);

	// Iterate through the list of figures and create buffers
	for (unsigned long i = 0; i < getListLength(figs); i++)
	{
		glGenBuffers(i + 1, &bufferId[i]);
		glGenBuffers(i + 1, &buuferNId[i]);
		glGenBuffers(i + 1, &buuferTId[i]);
		Figura fig = (Figura)getListElemAt(figs, i);
		vector<float> vVertices = getPontos(fig);
		vector<float> vNormais = getNormais(fig);
		vector<float> vTextura = getTexturas(fig);
		// save to global variables
		tupleVectorList.push_back({ vVertices, vNormais });
		// Calculate the number of vertices
		info[i] = (vVertices.size() / 3);

		// Bind the buffer and fill it with data
		glBindBuffer(GL_ARRAY_BUFFER, bufferId[i]);
		glBufferData(GL_ARRAY_BUFFER, vVertices.size() * sizeof(float), vVertices.data(), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, buuferNId[i]);
		glBufferData(GL_ARRAY_BUFFER, vNormais.size() * sizeof(float), vNormais.data(), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, buuferTId[i]);
		glBufferData(GL_ARRAY_BUFFER, vTextura.size() * sizeof(float), vTextura.data(), GL_STATIC_DRAW);

		string location = getTexturaLocation(fig);
		if (location != "") {
			// cast to const char*
			const char* locatione = location.c_str();
			loadTexture(i, locatione);
		}
	}
}

void drawNormais(int i)
{
	TupleVector tv = tupleVectorList[i];
	vector<float> vVerticesG = tv.v1;
	vector<float> vNormaisG = tv.v2;
	glDisable(GL_LIGHTING); // Disable lighting
	glColor3f(1.0f, 0.0f, 0.0f); // Set color to red
	glBegin(GL_LINES);
	for (unsigned long i = 0; i < vVerticesG.size(); i += 3)
	{
		glVertex3f(vVerticesG[i], vVerticesG[i + 1], vVerticesG[i + 2]);
		glVertex3f(vVerticesG[i] + vNormaisG[i], vVerticesG[i + 1] + vNormaisG[i + 1], vVerticesG[i + 2] + vNormaisG[i + 2]);
	}
	glEnd();
	glEnable(GL_LIGHTING); // Enable lighting again
}

void drawFigures(int startpos, int endpos)
{
	for (unsigned long i = startpos; i < endpos; i++)
	{
		glBindTexture(GL_TEXTURE_2D, buuferTFile[i]);
		glBindBuffer(GL_ARRAY_BUFFER, bufferId[i]);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, buuferNId[i]);
		glNormalPointer(GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, buuferTId[i]);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);

		// Draw the figure
		glDrawArrays(GL_TRIANGLES, 0, info[i]);
		//drawNormais(i);
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
void executeTransformations(const Transform& transform)
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
			for (const auto& point : t_points)
			{
				px.push_back(point.x);
				py.push_back(point.y);
				pz.push_back(point.z);
			}
			float t = fmod(NOW - init_time, t_time) / t_time;
			float pos[3];
			float deriv[3];
			if (lightingOn == 1)
				glDisable(GL_LIGHTING);
			GlobalCatRomPoint(t, px, py, pz, pos, deriv);
			displayCatmullRom(px, py, pz);
			if (lightingOn == 1)
				glEnable(GL_LIGHTING);
			glTranslatef(pos[0], pos[1], pos[2]);

			if (transform.align == true)
			{
				float rot[16];
				float* X = deriv;
				float Y[3] = { 0, 1, 0 }; // Variável que define a normal do objeto
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
	if (lightingOn == 1)
		glDisable(GL_LIGHTING);
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
	if (lightingOn == 1)
		glEnable(GL_LIGHTING);
}

void drawTeapot()
{
	glPushMatrix();
	glColor3f(WHITE);
	glTranslatef(-5, 0, 0);
	glutWireTeapot(1);
	glPopMatrix();
}

void drawGroups(const Group* group)
{
	if (group != nullptr)
	{
		glPushMatrix(); // Push the current matrix onto the stack
		for (const auto& transform : group->transforms)
		{
			executeTransformations(transform);
		}

		GLfloat vColors[4];
		endpos = startpos + group->modelFiles.size();
		// printf(group->modelFiles.size() > 0 ? "Drawing %d model(s)\n" : "No models to draw\n", group->modelFiles.size());
		if (lightingOn == 1)
		{
			for (int i = 0; i < group->modelFiles.size(); i++)
			{
				ModelFile m = group->modelFiles[i];
				if(m.colors.size() == 0)
					continue;
				auto adjustVColors = [&m, &vColors](int i)
					{
						vColors[0] = m.colors[i].r / 255;
						vColors[1] = m.colors[i].g / 255;
						vColors[2] = m.colors[i].b / 255;
						vColors[3] = 1.0f;
					};
				adjustVColors(0);
				glMaterialfv(GL_FRONT, GL_DIFFUSE, vColors);
				adjustVColors(1);
				glMaterialfv(GL_FRONT, GL_AMBIENT, vColors);
				adjustVColors(2);
				glMaterialfv(GL_FRONT, GL_SPECULAR, vColors);
				adjustVColors(3);
				glMaterialfv(GL_FRONT, GL_EMISSION, vColors);
				glMaterialf(GL_FRONT, GL_SHININESS, m.colors[4].value);
			}
		}
		drawFigures(startpos, endpos); // Draw the figure(s)
		// reset colors
		

		// Render child groups
		for (const auto& child : group->children)
		{
			startpos = endpos;
			drawGroups(&child);
		}
		if (group->children.size() == 0)
			startpos = 0;
		glPopMatrix(); // Restore the previous matrix
	}
}

void lighting(vector<Light>* lights)
{
	std::vector<Light> lightVec = *lights;
	Light luz;
	int numLights = lights->size();
	GLfloat white[4] = { 1.0f, 1.0f, 1.0f, 1.0f };
	for (int i = 0; i < numLights; i++)
	{
		luz = lightVec[i];
		GLenum label = GL_LIGHT0 + i; // TODO: Verificar se funciona assim

		luz.position.push_back(1.0f);
		luz.direction.push_back(0.0f);
		if (luz.type.compare("point") == 0)
		{
			glLightfv(label, GL_POSITION, luz.position.data());
		}
		else if (luz.type.compare("directional") == 0)
		{
			glLightfv(label, GL_POSITION, luz.direction.data());
		}
		else if (luz.type.compare("spot") == 0)
		{
			glLightfv(label, GL_POSITION, luz.position.data());
			glLightfv(label, GL_SPOT_DIRECTION, luz.direction.data());
			glLightf(label, GL_SPOT_CUTOFF, luz.cutoff);
			glLightf(label, GL_SPOT_EXPONENT, 0.0);
		}
		else
		{
			std::cerr << "Light type not correctly defined! Light: " << i;
		}
		glEnable(label);
		glLightfv(label, GL_DIFFUSE, white);
		glLightfv(label, GL_SPECULAR, white);
	}
}

void renderScene(void)
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glLoadIdentity();
	gluLookAt(camx, camy, camz, lookAtx, lookAty, lookAtz, upx, upy, upz);

	glColor3f(1.0f, 1.0f, 1.0f);
	if (lightingOn == 1)
		lighting(&settings->lights);
	glPolygonMode(GL_FRONT_AND_BACK, mode);

	glPushMatrix();
	glScalef(2.0f, 2.0f, 2.0f);
	drawEixos();
	glPopMatrix();
	drawGroups(&settings->rootNode);

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
	case ('m'):
	{
		// Move camera to the right
		lookAtx += 2.0f;
		break;
	}
	case ('n'):
	{
		// Move camera to the left
		lookAtx -= 2.0f;
		break;
	}
	case ('j'):
	{
		// Move camera up
		lookAty += 2.0f;
		break;
	}
	case ('k'):
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

void enableLights()
{
	int numLights = settings->lights.size();
	if (numLights == 0)
		return; // Verifica se n há luzes
	glEnable(GL_LIGHTING);
	glEnable(GL_RESCALE_NORMAL);
	if (numLights > 8)
	{ // É o número máximo suportado
		std::cerr << "XML exceeds maximum number of lights (8)! Number received: " + numLights;
		exit(1);
	}
	float amb[4] = { 1.0f, 1.0f, 1.0f, 1.0f };
	glLightModelfv(GL_LIGHT_MODEL_AMBIENT, amb);
}

int main(int argc, char* argv[])
{
	ilInit();
	if (argc != 2)
	{
		printf("Usage: %s <path to xml file>\n", argv[0]);
		return 1;
	}
	std::string filePath = argv[1];
	printf("File path: %s\n", filePath.c_str());
	figuras = newEmptyList();
	settings = ParserSettingsConstructor(filePath);
	print(*settings);
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

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(800, 800);
	glutCreateWindow("Engine");
	glewInit();

	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);

	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(renderScene);
	glutKeyboardFunc(keyProc);
	glutSpecialFunc(specKeyProc);

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glEnable(GL_TEXTURE_2D);
	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	if (settings->lights.size() > 0)
		lightingOn = 1;
	enableLights();

	importFiguras(figuras);

	// OpenGL settings

	// enter GLUT's main cycle
	glutMainLoop();

	deepDeleteList(figuras, deleteFigura);

	return 1;
}