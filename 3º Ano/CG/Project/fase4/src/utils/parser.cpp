#include <iostream>
#include <vector>
#include <string>
#include <memory>
#include "../tinyXML/tinyxml2.h"

struct Window {
    int width = 0;
    int height = 0;
};

struct Point {
    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
};

struct Camera {
    Point position;
    Point lookAt;
    Point up;
    Point projection;
};

struct Transform {
    char type;
    Point point;
    float angle = 0.0f;
    float time = 0.0f;
    bool align;
    std::vector<Point> points;
};

struct Color {
    std::string type;
    float r = 0.0f; 
    float g = 0.0f;
    float b = 0.0f;
    float value = 0.f;
};

struct Model {
    std::string fileName;
    std::string textureName;
    std::vector<Color> colors;
};

struct Group {
    std::vector<Transform> transforms;
    std::vector<Model> modelFiles;
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

        if (positionElement && lookAtElement) {
            positionElement->QueryFloatAttribute("x", &camera.position.x);
            positionElement->QueryFloatAttribute("y", &camera.position.y);
            positionElement->QueryFloatAttribute("z", &camera.position.z);

            lookAtElement->QueryFloatAttribute("x", &camera.lookAt.x);
            lookAtElement->QueryFloatAttribute("y", &camera.lookAt.y);
            lookAtElement->QueryFloatAttribute("z", &camera.lookAt.z);
        }

        if (upElement) {
            upElement->QueryFloatAttribute("x", &camera.up.x);
            upElement->QueryFloatAttribute("y", &camera.up.y);
            upElement->QueryFloatAttribute("z", &camera.up.z);
        }

        if (projectionElement) {
            projectionElement->QueryFloatAttribute("fov", &camera.projection.x);
            projectionElement->QueryFloatAttribute("near", &camera.projection.y);
            projectionElement->QueryFloatAttribute("far", &camera.projection.z);
        }
    }
    else {
        std::cerr << "Camera settings not found." << std::endl;
        exit(1);
    }
}

void parseLights(tinyxml2::XMLElement* lightElement, std::vector<Light>& lights) {
    if (!lightElement)
        return;

    for (tinyxml2::XMLElement* light = lightElement->FirstChildElement("light"); light != NULL; light = light->NextSiblingElement("light")) {
        Light l;
        const char* type = light->Attribute("type");
        switch (type[0]) {
        case 'p': { // point
            l.type = type;
            l.position.push_back(atof(light->Attribute("posx")));
            l.position.push_back(atof(light->Attribute("posy")));
            l.position.push_back(atof(light->Attribute("posz")));
            break;
        }

        case 'd': { // directional
            l.type = type;
            l.direction.push_back(atof(light->Attribute("dirx")));
            l.direction.push_back(atof(light->Attribute("diry")));
            l.direction.push_back(atof(light->Attribute("dirz")));
            break;
        }

        case 's': { // spotlight
            l.type = type;
            l.position.push_back(atof(light->Attribute("posx")));
            l.position.push_back(atof(light->Attribute("posy")));
            l.position.push_back(atof(light->Attribute("posz")));
            l.direction.push_back(atof(light->Attribute("dirx")));
            l.direction.push_back(atof(light->Attribute("diry")));
            l.direction.push_back(atof(light->Attribute("dirz")));
            l.cutoff = atof(light->Attribute("cutoff"));
            break;
        }
        }
        lights.push_back(std::move(l));
    }
}


void parseTransform(tinyxml2::XMLElement* transformElement, std::vector<Transform>& transforms) {
    if (transformElement) {
        for (tinyxml2::XMLElement* t = transformElement->FirstChildElement(); t; t = t->NextSiblingElement()) {
            Transform transform;
            transform.type = t->Value()[0];
            if (transform.type == 't') {
                const char* timeAttr = t->Attribute("time");
                if (timeAttr) {
                    transform.time = atof(timeAttr);
                    transform.align = t->BoolAttribute("align", false);
                    tinyxml2::XMLElement* pointElement = t->FirstChildElement("point");
                    while (pointElement) {
                        Point point;
                        pointElement->QueryFloatAttribute("x", &point.x);
                        pointElement->QueryFloatAttribute("y", &point.y);
                        pointElement->QueryFloatAttribute("z", &point.z);
                        transform.points.push_back(point);
                        pointElement = pointElement->NextSiblingElement("point");
                    }
                }
                else {
                    transform.time = 0.0f;
                    transform.point.x = atof(t->Attribute("x"));
                    transform.point.y = atof(t->Attribute("y"));
                    transform.point.z = atof(t->Attribute("z"));
                }
            }
            else if (transform.type == 'r') {
                const char* timeAttr = t->Attribute("time");
                if (timeAttr) {
                    transform.time = atof(timeAttr);
                    transform.point.x = atof(t->Attribute("x"));
                    transform.point.y = atof(t->Attribute("y"));
                    transform.point.z = atof(t->Attribute("z"));
                }
                else {
                    transform.time = 0.0f;
                    transform.angle = atof(t->Attribute("angle"));
                    transform.point.x = atof(t->Attribute("x"));
                    transform.point.y = atof(t->Attribute("y"));
                    transform.point.z = atof(t->Attribute("z"));
                }
            }
            else if (transform.type == 's') {
                transform.time = 0.0f;
                transform.point.x = atof(t->Attribute("x"));
                transform.point.y = atof(t->Attribute("y"));
                transform.point.z = atof(t->Attribute("z"));
            }
            else {
                transform.angle = 0.0f;
            }
            transforms.push_back(transform);
        }
    }
}

Color parseColor(tinyxml2::XMLElement* colorElement, const std::string& colorType) {
    Color c;
    c.type = colorType;
    if (colorType != "shininess") {
        c.r = atof(colorElement->Attribute("R"));
        c.g = atof(colorElement->Attribute("G"));
        c.b = atof(colorElement->Attribute("B"));
    }
    else {
        c.value = atof(colorElement->Attribute("value"));
    }
    return c;
}

void parseModel(tinyxml2::XMLElement* modelsElement, std::vector<Model>& models) {
    if (!modelsElement)
        return;

    // Assuming there's only one <model> element
    tinyxml2::XMLElement* modelElement = modelsElement->FirstChildElement("model");
    if (!modelElement)
        return;

    Model model;

    const char* modelFileName = modelElement->Attribute("file");
    if (modelFileName)
        model.fileName = "../output/" + std::string(modelFileName);

    tinyxml2::XMLElement* textureElement = modelElement->FirstChildElement("texture");
    if (textureElement) {
        const char* textureFileName = textureElement->Attribute("file");
        if (textureFileName)
            model.textureName = textureFileName;
    }

    tinyxml2::XMLElement* colorElement = modelElement->FirstChildElement("color");
    if (colorElement) {
        std::vector<std::string> colorTypes = { "diffuse", "ambient", "specular", "emissive", "shininess" };
        for (const auto& colorType : colorTypes) {
            tinyxml2::XMLElement* colorTypeElement = colorElement->FirstChildElement(colorType.c_str());
            if (colorTypeElement) {
                model.colors.push_back(parseColor(colorTypeElement, colorType));
            }
        }
    }
    models.push_back(std::move(model));
}


void parseGroupNode(tinyxml2::XMLElement* groupElement, Group& groupNode) {
    if (!groupElement)
        return;

    auto transformElement = groupElement->FirstChildElement("transform");
    if (transformElement)
        parseTransform(transformElement, groupNode.transforms);

    auto modelsElement = groupElement->FirstChildElement("models");
    if (modelsElement)
        parseModel(modelsElement, groupNode.modelFiles);

    for (tinyxml2::XMLElement* childElement = groupElement->FirstChildElement("group"); childElement; childElement = childElement->NextSiblingElement("group")) {
        Group childNode;
        parseGroupNode(childElement, childNode);
        groupNode.children.push_back(std::move(childNode)); // Move semantics for efficient copying
    }
}



bool loadXML(const std::string& filePath, tinyxml2::XMLDocument& doc) {
    if (doc.LoadFile(filePath.c_str()) != tinyxml2::XML_SUCCESS) {
        std::cerr << "Failed to load XML file." << std::endl;
        return false;
    }
    return true;
}

std::unique_ptr<Parser> ParserSettingsConstructor(const std::string& filePath) {
    std::unique_ptr<Parser> settings(new Parser);

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

    tinyxml2::XMLElement* lightElement = worldElement->FirstChildElement("lights");
    parseLights(lightElement, settings->lights);

    tinyxml2::XMLElement* groupElement = worldElement->FirstChildElement("group");
    parseGroupNode(groupElement, settings->rootNode);

    return settings;
}


void printGroup(const Group& group, int depth = 0) {
    for (const auto& transform : group.transforms) {
        for (int i = 0; i < depth; ++i)
            std::cout << "  ";
        std::cout << "Type: " << transform.type << std::endl;
        if (transform.type == 't') {
            if (transform.time != 0.0f) {
                for (int i = 0; i < depth; ++i)
                    std::cout << "  ";
                std::cout << "Time: " << transform.time << std::endl;
                for (int i = 0; i < depth; ++i)
                    std::cout << "  ";
                std::cout << "Align: " << (transform.align ? "true" : "false") << std::endl;
                for (const auto& point : transform.points) {
                    for (int i = 0; i < depth; ++i)
                        std::cout << "  ";
                    std::cout << "Translation Point: (" << point.x << ", " << point.y << ", " << point.z << ")" << std::endl;
                }
            }
            else {
                for (int i = 0; i < depth; ++i)
                    std::cout << "  ";
                std::cout << "Translation: (" << transform.point.x<< ", " << transform.point.y << ", " << transform.point.z << ")" << std::endl;
            }
        }
        else if (transform.type == 'r') {
            if (transform.time != 0.0f) {
                for (int i = 0; i < depth; ++i)
                    std::cout << "  ";
                std::cout << "Time: " << transform.time << std::endl;
            }
            else {
                for (int i = 0; i < depth; ++i)
                    std::cout << "  ";
                std::cout << "Angle: " << transform.angle << std::endl;
            }
            for (int i = 0; i < depth; ++i)
                std::cout << "  ";
            std::cout << "Rotation: (" << transform.point.x << ", " << transform.point.y << ", " << transform.point.z << ")" << std::endl;
        }
        else if (transform.type == 's') {
            for (int i = 0; i < depth; ++i)
                std::cout << "  ";
            std::cout << "Scale: (" << transform.point.x << ", " << transform.point.y << ", " << transform.point.z << ")" << std::endl;
        }
        else {
            for (int i = 0; i < depth; ++i)
                std::cout << "  ";
            std::cout << "Unknown transform type: " << transform.type << std::endl;
        }
    }

    for (const auto& model : group.modelFiles) {
        for (int i = 0; i < depth; ++i)
            std::cout << "  ";
        std::cout << "File Name: " << model.fileName << std::endl;
        std::cout << "Texture File: " << model.textureName << std::endl;
        for (const auto& color : model.colors) {
            std::cout << "Color: (" << color.type << "," << color.r << ", " << color.g << ", " << color.b << "), Value: " << color.value << std::endl;
        }
    }

    for (const auto& child : group.children) {
        for (int i = 0; i < depth; ++i)
            std::cout << "  ";
        std::cout << "Child Group:" << std::endl;
        printGroup(child, depth + 1);
    }
}

void print(const Parser& parser) {
    if (!&parser) {
		std::cerr << "Parser is empty." << std::endl;
		return;
	}
    std::cout << "Window settings:" << std::endl;
    std::cout << "Width: " << parser.window.width << std::endl;
    std::cout << "Height: " << parser.window.height << std::endl;

    std::cout << "Lights:\n";
    for (const auto& light : parser.lights) {
        std::cout << "Type: " << light.type << std::endl;
        if(light.position.size()>0)
        std::cout << "Position: (" << light.position.at(0) << ", " << light.position.at(1) << ", " << light.position.at(2) << ")" << std::endl;
        if(light.direction.size()>0)
        std::cout << "Direction: (" << light.direction.at(0) << ", " << light.direction.at(1) << ", " << light.direction.at(2) << ")" << std::endl;
        if (light.type == "spotlight") {
            std::cout << "Cutoff: " << light.cutoff << std::endl;
        }
        std::cout << std::endl;
    }

    std::cout << "\nCamera settings:" << std::endl;
    std::cout << "Position: (" << parser.camera.position.x << ", " << parser.camera.position.y << ", " << parser.camera.position.z << ")" << std::endl;
    std::cout << "LookAt: (" << parser.camera.lookAt.x << ", " << parser.camera.lookAt.y << ", " << parser.camera.lookAt.z << ")" << std::endl;
    std::cout << "Up: (" << parser.camera.up.x << ", " << parser.camera.up.y << ", " << parser.camera.up.z << ")" << std::endl;
    std::cout << "Projection:" << std::endl;
    std::cout << "FOV: " << parser.camera.projection.x << std::endl;
    std::cout << "Near: " << parser.camera.projection.y << std::endl;
    std::cout << "Far: " << parser.camera.projection.z << std::endl;

    std::cout << "\nTransforms:" << std::endl;
    printGroup(parser.rootNode);

    std::cout << std::endl;
}