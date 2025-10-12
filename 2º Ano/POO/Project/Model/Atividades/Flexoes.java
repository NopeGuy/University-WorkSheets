package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Flexoes extends AtividadeRep{

  // Constructors
  public Flexoes() {
    super();
  }

  public Flexoes(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Flexoes(Flexoes other) {
    super(other);
  }

  public Flexoes(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.3f * this.getRepeticoes() * this.getTempoDispendido();
    }
    else if (utilizador instanceof UtilizadorOcasional) {
      return 0.3f * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;

    }
    else if (utilizador instanceof UtilizadorProfissional) {
      return 0.3f * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }

  @Override
  public String toString() {
    return "AtividadeRep - Flexões {" + super.toString() + '}';
  }

  @Override
  public Flexoes clone() {
    return new Flexoes(this);
  }
}
