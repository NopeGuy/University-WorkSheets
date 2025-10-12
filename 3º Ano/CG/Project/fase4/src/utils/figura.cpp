#include "figura.hpp"

using namespace std;

struct figura{
    vector<Ponto>* pontos;
    vector<Ponto>* normais; 
    vector<Ponto>* pontosText;
    std::string *textura;
};

Figura newEmptyFigura(){
    Figura r = (Figura)malloc(sizeof(struct figura));
    if(r != NULL){
        r->pontos = new vector<Ponto>();
        r->normais = new vector<Ponto>();
        r->pontosText = new vector<Ponto>();
        r->textura = new string();
    }
    return r;
}

Figura newFigura(vector<Ponto> pontos){
    Figura r = newEmptyFigura();
    if(r != NULL){
        for(unsigned long i = 0; i < pontos.size(); i++){
            addPonto(r, pontos[i]);
        }
    }
    return r;
}

void addPonto(Figura f, Ponto p){
    if(f){
        f->pontos->push_back(p);
    }
}

void addNormais(Figura f, Ponto normais) {
    if (f) {
        f->normais->push_back(normais);
    }
}

void addPontosText(Figura f, Ponto texturas) {
    if (f) {
        f->pontosText->push_back(texturas);
    }
}

void addPontos(Figura f, Figura toAdd){
    if(f){
        vector<Ponto>* pontos = toAdd->pontos;
        for(Ponto p: *pontos){
            addPonto(f, p);
        }
    }
}

void addPontoNormais(Figura f, Ponto ponto = NULL, Ponto normais = NULL) {
    if (ponto) addPonto(f, ponto);
    if (normais) {
        normalizePonto(normais);
        addNormais(f, normais);
    }
}

void addPontoNormaisTextura(Figura f, Ponto ponto = NULL, Ponto normais = NULL,Ponto texturas = NULL) {
    if (ponto) addPonto(f, ponto);
    if (normais) {
        normalizePonto(normais);
        addNormais(f, normais);
    }
    if (texturas) addPontosText(f, texturas);
}

void addTextura(Figura f, const char* textura) {
    if (f) {
        f->textura = new string (textura);
	}
}

Figura fileToFigura(const char* path, const char* text) {
    Figura f = newEmptyFigura();
    FILE* file = fopen(path, "r");
    if (f && file) {
        char buffer[1024];
        fgets(buffer, 1023, file);
        int vertices = atoi(buffer);
        float x, y, z, vx, vy, vz, u, v;
        for (int i = 0; i < vertices; i++) {
            fgets(buffer, 1023, file);
            sscanf(buffer, "%f,%f,%f; %f,%f,%f; %f,%f", &x, &y, &z, &vx, &vy, &vz, &u, &v);
            addPontoNormaisTextura(f, newPonto(x, y, z), newPonto(vx, vy, vz), newPonto(u, v, 0.0f));
        }
        addTextura(f, text);
        fclose(file);
    }
    return f;
}

vector<float> getPontos(Figura f) {
    vector<float> pontos;
    if (f) {
        vector<Ponto>* figuraPontos = f->pontos;
        for (Ponto p : *figuraPontos) {
            pontos.push_back(getX(p));
            pontos.push_back(getY(p));
            pontos.push_back(getZ(p));
        }
    }
    return pontos;
}

vector<float> getNormais(Figura f) {
    vector<float> pontos;
    if (f) {
        vector<Ponto>* figuraPontos = f->normais;
        for (Ponto p : *figuraPontos) {
            pontos.push_back(getX(p));
            pontos.push_back(getY(p));
            pontos.push_back(getZ(p));
        }
    }
    return pontos;
}

vector<float> getTexturas(Figura f) {
	vector<float> pontos;
    if (f) {
		vector<Ponto>* figuraPontos = f->pontosText;
        for (Ponto p : *figuraPontos) {
			pontos.push_back(getX(p));
			pontos.push_back(getY(p));
		}
	}
	return pontos;
}   

string getTexturaLocation(Figura f) {
    if (f) {
		return *f->textura;
	}
	return "";
}

void deleteFigura(void* figura){
    if(figura){
        for(Ponto p : *((Figura)figura)->pontos){
            deletePonto(p);
        }
        delete ((Figura)figura)->pontos;
        free(figura);
    }
}
