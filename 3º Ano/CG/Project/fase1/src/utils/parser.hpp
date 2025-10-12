#ifndef PARSER_SETTINGS_HPP
#define PARSER_SETTINGS_HPP

#include <iostream>
#include <vector>
#include <string>
#include "../tinyXML/tinyxml2.h"

struct WindowSettings {
    int width = 0;
    int height = 0;
};

struct CameraSettings {
    float positionX = 0.0f;
    float positionY = 0.0f;
    float positionZ = 0.0f;
    float lookAtX = 0.0f;
    float lookAtY = 0.0f;
    float lookAtZ = 0.0f;
    float upX = 0.0f;
    float upY = 0.0f;
    float upZ = 0.0f;
    float fov = 0.0f;
    float near = 0.0f;
    float far = 0.0f;
};

struct ModelFile {
    std::string fileName;
};

struct ParserSettings {
    WindowSettings window;
    CameraSettings camera;
    std::vector<ModelFile> modelFiles;
};

void parseWindowSettings(tinyxml2::XMLElement* windowElement, WindowSettings& window);
void parseCameraSettings(tinyxml2::XMLElement* cameraElement, CameraSettings& camera);
void parseModelFiles(tinyxml2::XMLElement* modelsElement, std::vector<ModelFile>& modelFiles);
bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc);
ParserSettings* ParserSettingsConstructor(const std::string& filePath);

#endif // PARSER_SETTINGS_HPP
