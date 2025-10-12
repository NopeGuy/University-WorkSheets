package Model.Atividades;

import java.time.LocalDateTime;

public abstract class AtividadeRepPesos extends Atividade {
    private int repeticoes;
    private int peso;

    // Constructors
    public AtividadeRepPesos() {
        super();
        this.repeticoes = 0;
        this.peso = 0;
    }

    public AtividadeRepPesos(int tempoDispendido, int repeticoes, int peso) {
        super(tempoDispendido);
        this.repeticoes = repeticoes;
        this.peso = peso;
    }

    public AtividadeRepPesos(AtividadeRepPesos other) {
        super(other);
        this.repeticoes = other.repeticoes;
        this.peso = other.peso;
    }

    public AtividadeRepPesos(LocalDateTime datainicio) {
    super(datainicio);
    this.repeticoes = 0;
    this.peso = 0;
  }

    // Getters and Setters
    public int getRepeticoes() {
        return this.repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    // Override methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AtividadeRepPesos)) {
            return false;
        }
        AtividadeRepPesos that = (AtividadeRepPesos) obj;
        return this.repeticoes == that.repeticoes && this.peso == that.peso && super.equals(that);
    }

    @Override
    public String toString() {
        return "Peso= " + this.peso +
                ", Repeticoes= " + this.repeticoes +
                "," + super.toString();
    }
}
