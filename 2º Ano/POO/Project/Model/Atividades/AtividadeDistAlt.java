package Model.Atividades;

import java.time.LocalDateTime;

public abstract class AtividadeDistAlt extends Atividade {
    private int distancia;
    private int altura;

    // Constructors
    public AtividadeDistAlt() {
        super();
        this.distancia = 0;
        this.altura = 0;
    }

    public AtividadeDistAlt(int tempoDispendido, int distancia, int altura) {
        super(tempoDispendido);
        this.distancia = distancia;
        this.altura = altura;
    }

    public AtividadeDistAlt(AtividadeDistAlt other) {
        super(other);
        this.distancia = other.distancia;
        this.altura = other.altura;
    }

    public AtividadeDistAlt(LocalDateTime datainicio) {
    super(datainicio);
    this.distancia = 0;
    this.altura = 0;
  }

    // Getters and Setters
    public int getDistancia() {
        return this.distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    // Override methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AtividadeDistAlt)) {
            return false;
        }
        AtividadeDistAlt that = (AtividadeDistAlt) obj;
        return this.distancia == that.distancia && this.altura == that.altura && super.equals(that);
    }

    @Override
    public String toString() {
        return "Altura= " + this.altura +
                ", Distancia= " + this.distancia +
                "," + super.toString();
    }
}
