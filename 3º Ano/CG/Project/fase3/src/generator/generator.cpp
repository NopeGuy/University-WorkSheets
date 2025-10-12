#include "geometry.h"

#include <iostream>
#include <fstream>
#include <vector>
#include <string>

struct Vertex {
    float x, y, z;
};

// Função principal
int main(int argc, char* argv[]) {
    if (argc < 4) {
        std::cerr << "Usage: " << argv[0] << " <object> <param1> <param2> ... <output_file>" << std::endl;
        return 1;
    }

    std::string object = argv[1];
    std::string outputFile = argv[argc - 1];
    
    if (object == "sphere") {
        if (argc != 6) {
            std::cerr << "Usage for sphere: " << argv[0] << " sphere <radius> <slices> <stacks> <output_file>" << std::endl;
            return 1;
        }
        float radius = std::stof(argv[2]);
        int slices = std::stoi(argv[3]);
        int stacks = std::stoi(argv[4]);
        std::string outputFile = argv[5];
        generateSphere(radius, slices, stacks, outputFile);
        return 0;    
    } else if(object == "box"){
        if (argc != 5) {
            std::cerr << "Usage for sphere: " << argv[0] << " box <size> <divisions per edge> <output_file>" << std::endl;
            return 1;
        }
        float size = std::stof(argv[2]);
        int divisions = std::stoi(argv[3]);
        std::string outputFile = argv[4];
        generateBox(size, divisions, outputFile);
        return 0;    
    } else if(object == "plane"){
        if (argc != 5) {
            std::cerr << "Usage for sphere: " << argv[0] << " plane <size> <divisions per edge> <output_file>" << std::endl;
            return 1;
        }
        float size = std::stof(argv[2]);
        int divisions = std::stoi(argv[3]);
        std::string outputFile = argv[4];
        generatePlane(size, divisions, outputFile);
        return 0;    
    } else if(object == "cone"){
        if (argc != 7) {
            std::cerr << "Usage for sphere: " << argv[0] << " cone <radius> <height> <slices> <stacks> <output_file>" << std::endl;
            return 1;
        }
        float radius = std::stof(argv[2]);
        float height = std::stof(argv[3]);
        int slices = std::stoi(argv[4]);
        int stacks = std::stoi(argv[5]);
        std::string outputFile = argv[6];
        generateCone(radius, height, slices, stacks, outputFile);
        return 0;    
    }else if (object == "ring") {
        if (argc != 6) {
            std::cerr << "Usage for ring: " << argv[0] << " ring <internalRadius> <externalRadius> <slices> <output_file>" << std::endl;
            return 1;
        }
        float ir = std::stof(argv[2]);
        int er = std::stof(argv[3]);
        int slices = std::stoi(argv[4]);
        std::string outputFile = argv[5];
        generateRing(ir, er, slices, outputFile);
        return 0;
    }
    else if (object == "bezier") {
        if (argc != 5) { // Corrigindo o número de argumentos esperados
            std::cerr << "Usage for bezier: " << argv[0] << " bezier <patch_file_path> <output_file> <tessellation_level>" << std::endl;
            return 1;
        }
        std::string patchFilePath = argv[2]; // lê o caminho do arquivo patch
        std::string outputFile = argv[3]; // Mantem a definição correta de outputFile
        std::string tessellation = argv[4];
        generateBezierSurface(patchFilePath, outputFile, stoi(tessellation));
        return 0;
    }
    else {
        std::cerr << "Unknown object type." << std::endl;
        return 1;
    }
    return 0;
}
