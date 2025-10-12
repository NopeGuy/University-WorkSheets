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

void parseWindowSettings(tinyxml2::XMLElement* windowElement, Window& window) {
    if (windowElement) {
        windowElement->QueryIntAttribute("width", &window.width);
        windowElement->QueryIntAttribute("height", &window.height);
    }
    else {
        std::cerr << "Window settings not found." << std::endl;
        exit(1);
    }
}

void parseCameraSettings(tinyxml2::XMLElement* cameraElement, Camera& camera) {
    if (cameraElement) {
        auto positionElement = cameraElement->FirstChildElement("position");
        auto lookAtElement = cameraElement->FirstChildElement("lookAt");
        auto upElement = cameraElement->FirstChildElement("up");
        auto projectionElement = cameraElement->FirstChildElement("projection");

        positionElement->QueryFloatAttribute("x", &camera.position.x);
        positionElement->QueryFloatAttribute("y", &camera.position.y);
        positionElement->QueryFloatAttribute("z", &camera.position.z);

        lookAtElement->QueryFloatAttribute("x", &camera.lookAt.x);
        lookAtElement->QueryFloatAttribute("y", &camera.lookAt.y);
        lookAtElement->QueryFloatAttribute("z", &camera.lookAt.z);

        if (upElement) {
            upElement->QueryFloatAttribute("x", &camera.up.x);
            upElement->QueryFloatAttribute("y", &camera.up.y);
            upElement->QueryFloatAttribute("z", &camera.up.z);
        }

        if (projectionElement) {
            projectionElement->QueryFloatAttribute("fov", &camera.projection.fov);
            projectionElement->QueryFloatAttribute("near", &camera.projection.near);
            projectionElement->QueryFloatAttribute("far", &camera.projection.far);
        }
    }
    else {
        std::cerr << "Camera settings not found." << std::endl;
        exit(1);
    }
}

void parseTransform(tinyxml2::XMLElement* transformElement, std::vector<Transform>& transforms) {
    if (transformElement) {
        for (tinyxml2::XMLElement* t = transformElement->FirstChildElement(); t; t = t->NextSiblingElement()) {
            Transform transform;
            transform.type = t->Value()[0];
            transform.x = atof(t->Attribute("x"));
            transform.y = atof(t->Attribute("y"));
            transform.z = atof(t->Attribute("z"));
            if (transform.type == 'r') {
                transform.angle = atof(t->Attribute("angle"));
            } else {
                transform.angle = 0.0f; // Set angle to 0 for transforms of type "t"
            }
            transforms.push_back(transform);
        }
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
    }
}

void parseGroupNode(tinyxml2::XMLElement* groupElement, Group& groupNode) {
    if (groupElement) {
        auto transformElement = groupElement->FirstChildElement("transform");
        if (transformElement) {
            parseTransform(transformElement, groupNode.transforms);
        }

        auto modelsElement = groupElement->FirstChildElement("models");
        if (modelsElement) {
            parseModelFiles(modelsElement, groupNode.modelFiles);
        }

        auto childElement = groupElement->FirstChildElement("group");
        while (childElement) {
            Group childNode;
            parseGroupNode(childElement, childNode);
            groupNode.children.push_back(childNode);
            childElement = childElement->NextSiblingElement("group");
        }
    }
}



bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc) {
    if (doc.LoadFile(filePath.c_str()) != tinyxml2::XML_SUCCESS) {
        std::cerr << "Failed to load XML file." << std::endl;
        return false;
    }
    return true;
}

Parser* ParserSettingsConstructor(const std::string& filePath) {
    Parser* settings = new Parser;

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

    parseGroupNode(groupElement, settings->rootNode);

    return settings;
}


#include <iostream>

std::ostream& operator<<(std::ostream& os, const Transform& transform) {
    os << "Transform: type=" << transform.type << ", x=" << transform.x << ", y=" << transform.y << ", z=" << transform.z << ", angle=" << transform.angle;
    return os;
}

std::ostream& operator<<(std::ostream& os, const ModelFile& modelFile) {
    os << "ModelFile: fileName=" << modelFile.fileName;
    return os;
}

void printGroup(const Group& group, int level = 0) {
    std::string indent(level * 2, ' ');
    std::cout << indent << "Group:\n";
    for (const auto& transform : group.transforms) {
        std::cout << indent << "  " << transform << "\n";
    }
    for (const auto& modelFile : group.modelFiles) {
        std::cout << indent << "  " << modelFile << "\n";
    }
    for (const auto& child : group.children) {
        printGroup(child, level + 1);
    }
}

