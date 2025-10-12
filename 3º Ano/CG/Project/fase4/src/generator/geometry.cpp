#include "geometry.h"

#include <iostream>
#include <fstream>
#include <filesystem>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include <filesystem>
#include <cmath>
#include <corecrt_math_defines.h>

#include "../utils/ponto.hpp"
#include <fstream>
#include <filesystem>

namespace fs = std::filesystem;

void generateSphere(float radius, int slices, int stacks, const std::string& filename) {
    fs::path outputPath = fs::current_path().parent_path() / "output";
    if (!fs::exists(outputPath)) {
        fs::create_directories(outputPath);
    }

    fs::path filePath = outputPath / filename;
    std::ofstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Não foi possível abrir o arquivo para escrita: " << filePath << std::endl;
        return;
    }

    int totalVertices = 2 * 3 * slices * stacks; // Cada quadrado na esfera é dividido em 2 triângulos, 3 vértices por triângulo
    file << totalVertices << std::endl;

    float deltaPhi = M_PI / stacks; // Intervalo de variação para phi (de 0 a PI)
    float deltaTheta = 2 * M_PI / slices; // Intervalo de variação para theta (de 0 a 2*PI)

    for (int i = 0; i < stacks; ++i) {
        float phi = i * deltaPhi;
        float nextPhi = (i + 1) * deltaPhi;

        for (int j = 0; j < slices; ++j) {
            float theta = j * deltaTheta;
            float nextTheta = (j + 1) * deltaTheta;

            // Coordenadas dos vértices do quadrado atual
            float x1 = radius * sinf(phi) * cosf(theta);
            float y1 = radius * cosf(phi);
            float z1 = radius * sinf(phi) * sinf(theta);

            float x2 = radius * sinf(nextPhi) * cosf(theta);
            float y2 = radius * cosf(nextPhi);
            float z2 = radius * sinf(nextPhi) * sinf(theta);

            float x3 = radius * sinf(phi) * cosf(nextTheta);
            float y3 = radius * cosf(phi);
            float z3 = radius * sinf(phi) * sinf(nextTheta);

            float x4 = radius * sinf(nextPhi) * cosf(nextTheta);
            float y4 = radius * cosf(nextPhi);
            float z4 = radius * sinf(nextPhi) * sinf(nextTheta);

            // Coordenadas da normal
            float nx1 = x1 / radius, ny1 = y1 / radius, nz1 = z1 / radius;
            float nx2 = x2 / radius, ny2 = y2 / radius, nz2 = z2 / radius;
            float nx3 = x3 / radius, ny3 = y3 / radius, nz3 = z3 / radius;
            float nx4 = x4 / radius, ny4 = y4 / radius, nz4 = z4 / radius;

            // Coordenadas de textura
            float u1 = theta / (2 * M_PI), v1 = phi / M_PI;
            float u2 = theta / (2 * M_PI), v2 = nextPhi / M_PI;
            float u3 = nextTheta / (2 * M_PI), v3 = phi / M_PI;
            float u4 = nextTheta / (2 * M_PI), v4 = nextPhi / M_PI;

            // Primeiro triângulo
            file << x4 << "," << y4 << "," << z4 << "; " << nx4 << "," << ny4 << "," << nz4 << "; " << u4 << "," << v4 << std::endl;
            file << x2 << "," << y2 << "," << z2 << "; " << nx2 << "," << ny2 << "," << nz2 << "; " << u2 << "," << v2 << std::endl;
            file << x1 << "," << y1 << "," << z1 << "; " << nx1 << "," << ny1 << "," << nz1 << "; " << u1 << "," << v1 << std::endl;

            // Segundo triângulo
            file << x1 << "," << y1 << "," << z1 << "; " << nx1 << "," << ny1 << "," << nz1 << "; " << u1 << "," << v1 << std::endl;
            file << x3 << "," << y3 << "," << z3 << "; " << nx3 << "," << ny3 << "," << nz3 << "; " << u3 << "," << v3 << std::endl;
            file << x4 << "," << y4 << "," << z4 << "; " << nx4 << "," << ny4 << "," << nz4 << "; " << u4 << "," << v4 << std::endl;
        }
    }

    file.close();
    std::cout << "Arquivo '" << filePath << "' criado com sucesso." << std::endl;
}

void generateBox(float size, int divisions, const std::string& filename) {
    fs::path outputPath = fs::current_path().parent_path() / "output";
    if (!fs::exists(outputPath)) {
        fs::create_directories(outputPath);
    }

    fs::path filePath = outputPath / filename;
    std::ofstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Não foi possível abrir o arquivo para escrita." << std::endl;
        return;
    }

    float step = size / divisions;
    float half = size / 2;
    int totalVertices = 6 * 2 * divisions * divisions * 3; // 6 faces, 2 triângulos por divisão quadrada, 3 vértices por triângulo
    file << totalVertices << std::endl;

    // Função de vértices (para ser mais legível)
    auto writeVertex = [&](float x, float y, float z, float nx, float ny, float nz, float u, float v) {
        file << x << "," << y << "," << z << "; " << nx << "," << ny << "," << nz << "; " << u << "," << v << std::endl;
        };

    // Faces do cubo
    for (int i = 0; i < divisions; ++i) {
        float y = -half + i * step;
        float yNext = y + step;
        for (int j = 0; j < divisions; ++j) {
            float x = -half + j * step;
            float xNext = x + step;

            float u = float(j) / divisions;
            float v = float(i) / divisions;
            float uNext = float(j + 1) / divisions;
            float vNext = float(i + 1) / divisions;

            // Topo (y = half)
            writeVertex(x, half, y, 0, 1, 0, u, v);
            writeVertex(x, half, yNext, 0, 1, 0, u, vNext);
            writeVertex(xNext, half, y, 0, 1, 0, uNext, v);
            writeVertex(xNext, half, y, 0, 1, 0, uNext, v);
            writeVertex(x, half, yNext, 0, 1, 0, u, vNext);
            writeVertex(xNext, half, yNext, 0, 1, 0, uNext, vNext);

            // Base (y = -half)
            writeVertex(x, -half, y, 0, -1, 0, u, v);
            writeVertex(xNext, -half, y, 0, -1, 0, uNext, v);
            writeVertex(x, -half, yNext, 0, -1, 0, u, vNext);
            writeVertex(xNext, -half, y, 0, -1, 0, uNext, v);
            writeVertex(xNext, -half, yNext, 0, -1, 0, uNext, vNext);
            writeVertex(x, -half, yNext, 0, -1, 0, u, vNext);

            // Frente (z = half)
            writeVertex(x, y, half, 0, 0, 1, u, v);
            writeVertex(xNext, y, half, 0, 0, 1, uNext, v);
            writeVertex(x, yNext, half, 0, 0, 1, u, vNext);
            writeVertex(xNext, y, half, 0, 0, 1, uNext, v);
            writeVertex(xNext, yNext, half, 0, 0, 1, uNext, vNext);
            writeVertex(x, yNext, half, 0, 0, 1, u, vNext);

            // Trás (z = -half)
            writeVertex(x, y, -half, 0, 0, -1, u, v);
            writeVertex(x, yNext, -half, 0, 0, -1, u, vNext);
            writeVertex(xNext, y, -half, 0, 0, -1, uNext, v);
            writeVertex(xNext, y, -half, 0, 0, -1, uNext, v);
            writeVertex(x, yNext, -half, 0, 0, -1, u, vNext);
            writeVertex(xNext, yNext, -half, 0, 0, -1, uNext, vNext);

            // Direita (x = half)
            writeVertex(half, y, x, 1, 0, 0, u, v);
            writeVertex(half, yNext, x, 1, 0, 0, uNext, v);
            writeVertex(half, y, xNext, 1, 0, 0, u, vNext);
            writeVertex(half, y, xNext, 1, 0, 0, u, vNext);
            writeVertex(half, yNext, x, 1, 0, 0, uNext, v);
            writeVertex(half, yNext, xNext, 1, 0, 0, uNext, vNext);

            // Esquerda (x = -half)
            writeVertex(-half, y, x, -1, 0, 0, u, v);
            writeVertex(-half, y, xNext, -1, 0, 0, uNext, v);
            writeVertex(-half, yNext, x, -1, 0, 0, u, vNext);
            writeVertex(-half, y, xNext, -1, 0, 0, uNext, v);
            writeVertex(-half, yNext, xNext, -1, 0, 0, uNext, vNext);
            writeVertex(-half, yNext, x, -1, 0, 0, u, vNext);
        }
    }
    file.close();
    std::cout << "Arquivo '" << filename << "' criado com sucesso." << std::endl;
}

void generatePlane(float size, int divisions, const std::string& filename) {
    size *= 2; // Duplicar o tamanho do plano

    fs::path outputPath = fs::current_path().parent_path() / "output";
    if (!fs::exists(outputPath)) {
        fs::create_directories(outputPath);
    }

    fs::path filePath = outputPath / filename;

    std::ofstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Não foi possível abrir o arquivo para escrita." << std::endl;
        return;
    }

    float step = size / divisions;
    float half = size / 2.0f;
    int totalVertices = divisions * divisions * 6;  // Cada quadrado é dividido em 2 triângulos, 3 vértices por triângulo

    // Escrevendo o número total de vértices no arquivo
    file << totalVertices << std::endl;

    // Geração de vértices para cada divisão do plano e escrita direta no arquivo com normais e texturas
    for (int i = 0; i < divisions; ++i) {
        for (int j = 0; j < divisions; ++j) {
            float x = (i * step) - half;
            float z = (j * step) - half;
            float u = float(i) / divisions;
            float v = float(j) / divisions;
            float uNext = float(i + 1) / divisions;
            float vNext = float(j + 1) / divisions;

            // Normal sempre aponta para cima, pois é um plano horizontal
            float nx = 0, ny = 1, nz = 0;

            // Primeiro triângulo do quadrado
            file << x << ",0," << z + step << "; " << nx << "," << ny << "," << nz << "; " << u << "," << vNext << std::endl;
            file << x + step << ",0," << z << "; " << nx << "," << ny << "," << nz << "; " << uNext << "," << v << std::endl;
            file << x << ",0," << z << "; " << nx << "," << ny << "," << nz << "; " << u << "," << v << std::endl;

            // Segundo triângulo do quadrado
            file << x << ",0," << z + step << "; " << nx << "," << ny << "," << nz << "; " << u << "," << vNext << std::endl;
            file << x + step << ",0," << z + step << "; " << nx << "," << ny << "," << nz << "; " << uNext << "," << vNext << std::endl;
            file << x + step << ",0," << z << "; " << nx << "," << ny << "," << nz << "; " << uNext << "," << v << std::endl;
        }
    }

    file.close();
    std::cout << "Arquivo '" << filename << "' criado com sucesso." << std::endl;
}

void generateCone(float radius, float height, int slices, int stacks, const std::string& filename) {
    fs::path outputPath = fs::current_path().parent_path() / "output";
    if (!fs::exists(outputPath)) {
        fs::create_directories(outputPath);
    }

    fs::path filePath = outputPath / filename;
    std::ofstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Não foi possível abrir o arquivo para escrita." << std::endl;
        return;
    }

    // Cada slice da base gera 6 vértices (3 para cada lado) e cada lado do cone gera 12 vértices (6 para cada lado)
    float totalVertices = 2.0f * slices * 3.0f + 2.0f * slices * 6.0f;
    file << totalVertices << "\n";

    float deltaAngle = 2.0f * M_PI / slices; // Ângulo entre cada slice

    // Gerar a base do cone com normais inclinadas 45 graus
    for (int i = 0; i < slices; ++i) {
        float angle = i * deltaAngle;
        float nextAngle = (i + 1) * deltaAngle;

        float x1 = radius * cos(angle), z1 = radius * sin(angle);
        float x2 = radius * cos(nextAngle), z2 = radius * sin(nextAngle);

        // Normal ajustada para 45 graus
        float normFactor = sqrt(2.0f) / 2.0f;
        float nx1 = -normFactor * cos(angle);
        float nz1 = -normFactor * sin(angle);
        float nx2 = -normFactor * cos(nextAngle);
        float nz2 = -normFactor * sin(nextAngle);
        float ny = normFactor;

        // Textura para a base
        float s1 = 0.5f + 0.5f * cos(angle), t1 = 0.5f + 0.5f * sin(angle);
        float s2 = 0.5f + 0.5f * cos(nextAngle), t2 = 0.5f + 0.5f * sin(nextAngle);

        // Base visível de baixo com normais invertidas
        file << "0.0,0.0,0.0; 0.0," << ny << ",0.0; 0.5,0.5\n";
        file << x2 << ",0.0," << z2 << "; " << nx2 << "," << ny << "," << nz2 << "; " << s2 << "," << t2 << "\n";
        file << x1 << ",0.0," << z1 << "; " << nx1 << "," << ny << "," << nz1 << "; " << s1 << "," << t1 << "\n";

        // Base visível de cima com normais invertidas
        file << "0.0,0.0,0.0; 0.0," << -ny << ",0.0; 0.5,0.5\n";
        file << x1 << ",0.0," << z1 << "; " << -nx1 << "," << -ny << "," << -nz1 << "; " << s1 << "," << t1 << "\n";
        file << x2 << ",0.0," << z2 << "; " << -nx2 << "," << -ny << "," << -nz2 << "; " << s2 << "," << t2 << "\n";
    }

    // Gerar os lados do cone visíveis de dentro e de fora
    for (int i = 0; i < slices; ++i) {
        float angle = i * deltaAngle;
        float nextAngle = (i + 1) * deltaAngle;

        float baseX1 = radius * cos(angle), baseZ1 = radius * sin(angle);
        float baseX2 = radius * cos(nextAngle), baseZ2 = radius * sin(nextAngle);

        // Normal ajustada para formar 45 graus
        float norm = sqrt(height * height + radius * radius);
        float nx1 = -(baseX1 / radius);
        float nz1 = -(baseZ1 / radius);
        float ny1 = -(radius / height);

        float nx2 = -(baseX2 / radius);
        float nz2 = -(baseZ2 / radius);
        float ny2 = -(radius / height);

        // Textura para os lados
        float s_top = 0.5f, t_top = 1.0f; // Topo do cone
        float s_base1 = (float)i / (float)slices, t_base1 = 0.0f; // Base atual
        float s_base2 = (float)(i + 1) / (float)slices, t_base2 = 0.0f; // Próxima base

        // Lado visível de fora com normais invertidas
        file << "0.0," << height << ",0.0; " << nx1 << "," << ny1 << "," << nz1 << "; " << s_top << "," << t_top << "\n";
        file << baseX1 << ",0.0," << baseZ1 << "; " << nx1 << "," << ny1 << "," << nz1 << "; " << s_base1 << "," << t_base1 << "\n";
        file << baseX2 << ",0.0," << baseZ2 << "; " << nx2 << "," << ny2 << "," << nz2 << "; " << s_base2 << "," << t_base2 << "\n";

        // Lado visível de dentro com normais invertidas (inverter a ordem para inverter a normal)
        file << "0.0," << height << ",0.0; " << -nx1 << "," << -ny1 << "," << -nz1 << "; " << s_top << "," << t_top << "\n";
        file << baseX2 << ",0.0," << baseZ2 << "; " << -nx2 << "," << -ny2 << "," << -nz2 << "; " << s_base2 << "," << t_base2 << "\n";
        file << baseX1 << ",0.0," << baseZ1 << "; " << -nx1 << "," << -ny1 << "," << -nz1 << "; " << s_base1 << "," << t_base1 << "\n";
    }

    file.close();
    std::cout << "Arquivo '" << filename << "' criado com sucesso." << std::endl;
}

void generateRing(float ir, float er, int slices, const std::string& filename) {
    fs::path outputPath = fs::current_path().parent_path() / "output";
    if (!fs::exists(outputPath)) {
        fs::create_directories(outputPath);
    }

    fs::path filePath = outputPath / filename;

    std::ofstream file(filePath);
    if (!file.is_open()) {
        std::cerr << "Não foi possível abrir o arquivo para escrita." << std::endl;
        return;
    }
    if (slices <= 0) {
        std::cerr << "Não pode ter um slices <= 0" << std::endl;
        return;
    }

    // Ângulo entre cada slice
    float deltaAngle = 2 * M_PI / slices;

    // Vertices 2 faces * 2 triangulos * 3 pontos
    int totalVertices = 2 * 2 * 3 * slices;
    file << totalVertices << std::endl;

    // Função de vértices (para ser mais legível)
    auto writeVertex = [&](float angle, float radius, float nx, float nz, float u, float v) {
        float x = radius * cos(angle);
        float z = radius * sin(angle);
        file << x << "," << 0.0 << "," << z << "; " << nx << "," << 0.0 << "," << nz << "; " << u << "," << v << std::endl;
        };

    for (int i = 0; i < slices; i++) {
        float angle = i * deltaAngle;
        float nextAngle = (i + 1) * deltaAngle;

        float nx1 = cos(angle);
        float nz1 = sin(angle);
        float nx2 = cos(nextAngle);
        float nz2 = sin(nextAngle);

        float u1 = float(i) / slices;
        float u2 = float(i + 1) / slices;
        float vInner = 0.0;  // Textura v para o raio interno
        float vOuter = 1.0;  // Textura v para o raio externo

        // Primeira face do anel
        writeVertex(angle, ir, nx1, nz1, u1, vInner);
        writeVertex(angle, er, nx1, nz1, u1, vOuter);
        writeVertex(nextAngle, ir, nx2, nz2, u2, vInner);

        writeVertex(nextAngle, ir, nx2, nz2, u2, vInner);
        writeVertex(angle, er, nx1, nz1, u1, vOuter);
        writeVertex(nextAngle, er, nx2, nz2, u2, vOuter);

        // Segunda face do anel
        writeVertex(nextAngle, ir, nx2, nz2, u2, vInner);
        writeVertex(angle, er, nx1, nz1, u1, vOuter);
        writeVertex(angle, ir, nx1, nz1, u1, vInner);

        writeVertex(angle, er, nx1, nz1, u1, vOuter);
        writeVertex(nextAngle, ir, nx2, nz2, u2, vInner);
        writeVertex(nextAngle, er, nx2, nz2, u2, vOuter);
    }

    file.close();
    std::cout << "Arquivo '" << filename << "' criado com sucesso." << std::endl;
}

//Bezier//

Ponto crossProduct(Ponto a, Ponto b) {
    return newPonto(
        getY(a) * getZ(b) - getZ(a) * getY(b),
        getZ(a) * getX(b) - getX(a) * getZ(b),
        getX(a) * getY(b) - getY(a) * getX(b)
    );
}

Ponto bezier(float u, float v, std::vector<Ponto>& controlPoints, std::vector<int>& indices) {
    std::vector<Ponto> tempPoints(4);
    for (int i = 0; i < 4; i++) {
        tempPoints[i] = formulae(u,
            controlPoints[indices[4 * i]],
            controlPoints[indices[4 * i + 1]],
            controlPoints[indices[4 * i + 2]],
            controlPoints[indices[4 * i + 3]]);
    }
    Ponto result = formulae(v, tempPoints[0], tempPoints[1], tempPoints[2], tempPoints[3]);
    for (int i = 0; i < 4; i++) {
        deletePonto(tempPoints[i]);
    }
    return result;
}

Ponto formulae(float t, Ponto point1, Ponto point2, Ponto point3, Ponto point4) {
    float aux = 1.0 - t;
    float pt1 = aux * aux * aux;
    float pt2 = 3 * aux * aux * t;
    float pt3 = 3 * aux * t * t;
    float pt4 = t * t * t;
    float x = pt1 * getX(point1) + pt2 * getX(point2) + pt3 * getX(point3) + pt4 * getX(point4);
    float y = pt1 * getY(point1) + pt2 * getY(point2) + pt3 * getY(point3) + pt4 * getY(point4);
    float z = pt1 * getZ(point1) + pt2 * getZ(point2) + pt3 * getZ(point3) + pt4 * getZ(point4);
    return newPonto(x, y, z);
}

// Derivada parcial em relação a 'u' usando diferença finita centrada
Ponto derivative_u(float u, float v, std::vector<Ponto>& controlPoints, std::vector<int>& indices) {
    float h = 0.001; // Passo pequeno para a diferença finita
    if (u + h > 1) h = -h; // Ajuste para evitar ultrapassar o limite quando u está próximo de 1

    Ponto pointPlus = bezier(u + h, v, controlPoints, indices);
    Ponto pointMinus = bezier(u - h, v, controlPoints, indices);

    float dx = (getX(pointPlus) - getX(pointMinus)) / (2 * h);
    float dy = (getY(pointPlus) - getY(pointMinus)) / (2 * h);
    float dz = (getZ(pointPlus) - getZ(pointMinus)) / (2 * h);

    return newPonto(dx, dy, dz);
}

// Derivada parcial em relação a 'v' usando diferença finita centrada
Ponto derivative_v(float u, float v, std::vector<Ponto>& controlPoints, std::vector<int>& indices) {
    float h = 0.001; // Passo pequeno para a diferença finita
    if (v + h > 1) h = -h; // Ajuste para evitar ultrapassar o limite quando v está próximo de 1

    Ponto pointPlus = bezier(u, v + h, controlPoints, indices);
    Ponto pointMinus = bezier(u, v - h, controlPoints, indices);

    float dx = (getX(pointPlus) - getX(pointMinus)) / (2 * h);
    float dy = (getY(pointPlus) - getY(pointMinus)) / (2 * h);
    float dz = (getZ(pointPlus) - getZ(pointMinus)) / (2 * h);

    return newPonto(dx, dy, dz);
}

void generateBezierSurface(const std::string& patchFilePath, const std::string& outputFileName, int tessellation) {
    fs::path outputFilePath = fs::current_path() / "../output" / outputFileName;
    std::ifstream patchFile(patchFilePath);
    if (!patchFile.is_open()) {
        std::cerr << "Erro ao abrir arquivo de entrada!" << std::endl;
        return;
    }

    std::ofstream outputFile(outputFilePath);
    if (!outputFile.is_open()) {
        std::cerr << "Erro ao abrir arquivo de saída no caminho: " << outputFilePath << std::endl;
        return;
    }

    std::string line;
    getline(patchFile, line);
    int numPatches = std::stoi(line);
    std::vector<std::vector<int>> patches(numPatches);
    for (int i = 0; i < numPatches; ++i) {
        getline(patchFile, line);
        std::istringstream iss(line);
        std::vector<int> indices(16);
        for (int j = 0; j < 16; ++j) {
            iss >> indices[j];
            if (iss.peek() == ',')
                iss.ignore();
        }
        patches[i] = indices;
    }

    getline(patchFile, line);
    int numControlPoints = std::stoi(line);
    std::vector<Ponto> controlPoints(numControlPoints);
    for (int i = 0; i < numControlPoints; ++i) {
        float x, y, z;
        getline(patchFile, line);
        std::replace(line.begin(), line.end(), ',', ' ');
        std::istringstream iss(line);
        iss >> x >> y >> z;
        controlPoints[i] = newPonto(x, y, z);
    }

    std::stringstream ss;
    int totalVertices = 0;
    float step = 1.0f / tessellation;
    for (auto& patch : patches) {
        for (float u = 0; u < 1.0f; u += step) {
            for (float v = 0; v < 1.0f; v += step) {
                Ponto p1 = bezier(u, v, controlPoints, patch);
                Ponto p2 = bezier(u + step, v, controlPoints, patch);
                Ponto p3 = bezier(u, v + step, controlPoints, patch);
                Ponto p4 = bezier(u + step, v + step, controlPoints, patch);

                Ponto tan_u1 = derivative_u(u, v, controlPoints, patch);
                Ponto tan_v1 = derivative_v(u, v, controlPoints, patch);
                Ponto normal1 = crossProduct(tan_u1, tan_v1);
                //normalizePonto(normal1);

                // Primeiro triângulo
                ss << getX(p1) << "," << getY(p1) << "," << getZ(p1) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u << "," << v << std::endl;
                ss << getX(p2) << "," << getY(p2) << "," << getZ(p2) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u + step << "," << v << std::endl;
                ss << getX(p3) << "," << getY(p3) << "," << getZ(p3) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u << "," << v + step << std::endl;

                // Segundo triângulo
                ss << getX(p3) << "," << getY(p3) << "," << getZ(p3) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u << "," << v + step << std::endl;
                ss << getX(p2) << "," << getY(p2) << "," << getZ(p2) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u + step << "," << v << std::endl;
                ss << getX(p4) << "," << getY(p4) << "," << getZ(p4) << "; "
                    << getX(normal1) << "," << getY(normal1) << "," << getZ(normal1) << "; "
                    << u + step << "," << v + step << std::endl;

                totalVertices += 6;
            }
        }
    }

    outputFile << totalVertices << "\n" << ss.str();
    patchFile.close();
    outputFile.close();
    std::cout << "Arquivo '" << outputFileName << "' criado com sucesso em: " << outputFilePath << std::endl;
}