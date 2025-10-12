package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.Utilizador;

public abstract class AtividadeRep extends Atividade {

  private int repeticoes;

  // Constructors
  public AtividadeRep() {
    super();
    this.repeticoes = 0;
  }

  public AtividadeRep(int tempoDispendido, int repeticoes) {
    super(tempoDispendido);
    this.repeticoes = repeticoes;
  }

  public AtividadeRep(AtividadeRep other) {
    super(other);
    this.repeticoes = other.repeticoes;
  }

  public AtividadeRep(LocalDateTime datainicio) {
    super(datainicio);
    this.repeticoes = 0;
  }
  // Getters and Setters
  public int getRepeticoes() {
    return this.repeticoes;
  }

  public void setRepeticoes(int repeticoes) {
    this.repeticoes = repeticoes;
  }
  // Override methods
  @Override
  public abstract float calculaCaloriasGastas(Utilizador utilizador);

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AtividadeRep)) {
      return false;
    }
    AtividadeRep that = (AtividadeRep) obj;
    return this.repeticoes == that.repeticoes && super.equals(that);
  }

  @Override
  public String toString() {
    return "Repeticoes= " + this.repeticoes +
        "," + super.toString();
  }
}
