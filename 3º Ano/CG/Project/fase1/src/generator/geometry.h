// geometry.h

#ifndef GEOMETRY_H
#define GEOMETRY_H

#include <string>
#include "../utils/figura.hpp"
#include "../utils/ponto.hpp"
#include "../utils/list.hpp"

// Declaração da função generateSphere
void generateSphere(float radius, int slices, int stacks, const std::string& filename);
// Declaração da função generateBox
void generateBox(float size, int divisions, const std::string& filename);

// Declaração da função generatePlane
void generatePlane(float size, int divisions, const std::string& filename);

// Declaração da função generateCone
void generateCone(float radius, float height, int slices, int stacks, const std::string& filename);

// Declaração da função generateRing
void generateRing(float ir, float er, int slices, const std::string& filename);

#endif // GEOMETRY_H
