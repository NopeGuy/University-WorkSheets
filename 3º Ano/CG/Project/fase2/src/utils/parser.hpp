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
};

struct ModelFile {
    std::string fileName;
};

struct Group {
    std::vector<Transform> transforms;
    std::vector<ModelFile> modelFiles;
    std::vector<Group> children;
};

struct Parser {
    Window window;
    Camera camera;
    Group rootNode;
};

void parseWindowSettings(tinyxml2::XMLElement* windowElement, Window& window);
void parseCameraSettings(tinyxml2::XMLElement* cameraElement, Camera& camera);
void parseTransform(tinyxml2::XMLElement* transformElement, std::vector<Transform>& transforms);
void parseModelFiles(tinyxml2::XMLElement* modelsElement, std::vector<ModelFile>& modelFiles);
void parseGroupNode(tinyxml2::XMLElement* groupElement, Group& groupNode);
bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc);
Parser* ParserSettingsConstructor(const std::string& filePath);
std::ostream& operator<<(std::ostream& os, const Transform& transform);
std::ostream& operator<<(std::ostream& os, const ModelFile& modelFile);
void printGroup(const Group& group, int level = 0);


#endif // XML_PARSER_HPP
