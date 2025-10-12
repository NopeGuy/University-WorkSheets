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

void parseWindowSettings(tinyxml2::XMLElement* windowElement, WindowSettings& window) {
    if (windowElement) {
        windowElement->QueryIntAttribute("width", &window.width);
        windowElement->QueryIntAttribute("height", &window.height);
    } else {
        std::cerr << "Window settings not found." << std::endl;
        exit(1);
    }
}

void parseCameraSettings(tinyxml2::XMLElement* cameraElement, CameraSettings& camera) {
    if (cameraElement) {
        auto positionElement = cameraElement->FirstChildElement("position");
        auto lookAtElement = cameraElement->FirstChildElement("lookAt");
        auto upElement = cameraElement->FirstChildElement("up");
        auto projectionElement = cameraElement->FirstChildElement("projection");

        positionElement->QueryFloatAttribute("x", &camera.positionX);
        positionElement->QueryFloatAttribute("y", &camera.positionY);
        positionElement->QueryFloatAttribute("z", &camera.positionZ);

        lookAtElement->QueryFloatAttribute("x", &camera.lookAtX);
        lookAtElement->QueryFloatAttribute("y", &camera.lookAtY);
        lookAtElement->QueryFloatAttribute("z", &camera.lookAtZ);

        upElement->QueryFloatAttribute("x", &camera.upX);
        upElement->QueryFloatAttribute("y", &camera.upY);
        upElement->QueryFloatAttribute("z", &camera.upZ);

        projectionElement->QueryFloatAttribute("fov", &camera.fov);
        projectionElement->QueryFloatAttribute("near", &camera.near);
        projectionElement->QueryFloatAttribute("far", &camera.far);
    }
    else {
        std::cerr << "Camera settings not found." << std::endl;
        exit(1);
    }
}


void parseModelFiles(tinyxml2::XMLElement* modelsElement, std::vector<ModelFile>& modelFiles) {
    if (modelsElement) {
        tinyxml2::XMLElement* modelElement = modelsElement->FirstChildElement("model");
        while (modelElement) {
            ModelFile model;
            const char* fileName = modelElement->Attribute("file");
            if (fileName)
                model.fileName = fileName;
            modelFiles.push_back(model);
            modelElement = modelElement->NextSiblingElement("model");
        }
    } else {
        std::cerr << "Model files not found." << std::endl;
        exit(1);
    }
}

bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc) {
    if (doc.LoadFile(filePath.c_str()) != tinyxml2::XML_SUCCESS) {
        std::cerr << "Failed to load XML file." << std::endl;
        return false;
    }
    return true;
}

ParserSettings* ParserSettingsConstructor(const std::string& filePath) {
    ParserSettings* settings = new ParserSettings;

    tinyxml2::XMLDocument doc;
    if (!loadXML(filePath, doc)) {
        return nullptr;
    }

    tinyxml2::XMLElement* worldElement = doc.FirstChildElement("world");
    if (!worldElement) {
        std::cerr << "World element not found." << std::endl;
        exit(1);
    }

    tinyxml2::XMLElement* windowElement = worldElement->FirstChildElement("window");
    parseWindowSettings(windowElement, settings->window);

    tinyxml2::XMLElement* cameraElement = worldElement->FirstChildElement("camera");
    parseCameraSettings(cameraElement, settings->camera);

    tinyxml2::XMLElement* groupElement = worldElement->FirstChildElement("group");
    if (!groupElement) {
        std::cerr << "Group element not found." << std::endl;
        exit(1);
    }

    tinyxml2::XMLElement* modelsElement = groupElement->FirstChildElement("models");
    parseModelFiles(modelsElement, settings->modelFiles);

    return settings;
}
