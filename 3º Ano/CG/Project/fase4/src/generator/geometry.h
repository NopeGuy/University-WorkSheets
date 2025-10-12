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

//Declaração da função generateBezierSurface
Ponto bezier(float u, float v, Ponto* controlPoints, int* indices);
Ponto readPonto(std::istream& stream);
Ponto formulae(float t, Ponto point1, Ponto point2, Ponto point3, Ponto point4);
void generateBezierSurface(const std::string& patchFilePath, const std::string& filename, int tessellation);
Ponto derivative_u(float u, float v, std::vector<Ponto>& controlPoints, std::vector<int>& indices);
Ponto derivative_v(float u, float v, std::vector<Ponto>& controlPoints, std::vector<int>& indices);
Ponto normal_vector(Ponto tan_u, Ponto tan_v);

#endif // GEOMETRY_H
