#ifndef XML_PARSER_HPP
#define XML_PARSER_HPP

#include <iostream>
#include <vector>
#include <string>
#include "../tinyXML/tinyxml2.h"

struct Window {
    int width = 0;
    int height = 0;
};

struct Position {
    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
};

struct LookAt {
    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
};

struct Up {
    float x = 0.0f;
    float y = 1.0f;
    float z = 0.0f;
};

struct Projection {
    float fov = 60.0f;
    float near = 1.0f;
    float far = 1000.0f;
};

struct Camera {
    Position position;
    LookAt lookAt;
    Up up;
    Projection projection;
};


struct Transform {
    char type;
    float x;
    float y;
    float z;
    float angle;
    float time;
    bool align;
    std::vector<Position> points;
};

struct Color {
    std::string type;
    float r = 0.0f;
    float g = 0.0f;
    float b = 0.0f;
    float value = 0.f;
};

struct ModelFile {
    std::string fileName;
    std::string textureName;
    std::vector<Color> colors;
};

struct Group {
    std::vector<Transform> transforms;
    std::vector<ModelFile> modelFiles;
    std::vector<Group> children;
};

struct Light {
    std::string type;
    std::vector<float> position;
    std::vector<float> direction;
    float cutoff; // Only applicable for spotlight
};

struct Parser {
    Window window;
    Camera camera;
    Group rootNode;
    std::vector<Light> lights;
};

void parseWindowSettings(tinyxml2::XMLElement* windowElement, Window& window);
void parseCameraSettings(tinyxml2::XMLElement* cameraElement, Camera& camera);
void parseTransform(tinyxml2::XMLElement* transformElement, std::vector<Transform>& transforms);
void parseModel(tinyxml2::XMLElement* modelsElement, std::vector<ModelFile>& modelFiles);
void parseGroupNode(tinyxml2::XMLElement* groupElement, Group& groupNode);
bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc);
std::unique_ptr<Parser> ParserSettingsConstructor(const std::string& filePath);
void print(const Parser& parser);

#endif // XML_PARSER_HPP
