#ifndef MATH_FUNCTIONS_HPP
#define MATH_FUNCTIONS_HPP

// Function to multiply a matrix by a vector
void multMatrixVector(float* m, float* v, float* res);

void CatRomPoint(float t, float* p0, float* p1, float* p2, float* p3, float* pos, float* deriv);

void GlobalCatRomPoint(float gt, vector<float> px, vector<float> py, vector<float> pz, float* pos, float* deriv);

void displayCatmullRom(vector<float> px, vector<float> py, vector<float> pz);

void vetxvet(float* a, float* b, float* res);

void normalize(float* a);

void buildRotMatrix(float* x, float* y, float* z, float* m);
#endif /* MATH_FUNCTIONS_HPP */
