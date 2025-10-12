package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.Utilizador;

public abstract class AtividadeDist extends Atividade {

  private int distancia;

  // Constructors
  public AtividadeDist() {
    super();
    this.distancia = 0;
  }

  public AtividadeDist(int tempoDispendido, int distancia) {
    super(tempoDispendido);
    this.distancia = distancia;
  }

  public AtividadeDist(AtividadeDist other) {
    super(other);
    this.distancia = other.distancia;
  }

  public AtividadeDist(LocalDateTime datainicio) {
    super(datainicio);
    this.distancia = 0;
  }
  // Getters and Setters
  public int getDistancia() {
    return this.distancia;
  }

  public void setDistancia(int distancia) {
    this.distancia = distancia;
  }
  // Override methods
  @Override
  public abstract float calculaCaloriasGastas(Utilizador utilizador);

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AtividadeDist)) {
      return false;
    }
    AtividadeDist that = (AtividadeDist) obj;
    return this.distancia == that.distancia && super.equals(that);
  }

  @Override
  public String toString() {
    return "Distancia =" + this.distancia +
        "," + super.toString();
  }
}
