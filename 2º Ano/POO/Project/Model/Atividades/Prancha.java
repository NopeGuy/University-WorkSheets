package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Prancha extends AtividadeRep{

  // Constructors
  public Prancha() {
    super();
  }

  public Prancha(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Prancha(Prancha other) {
    super(other);
  }

  public Prancha(LocalDateTime datainicio) {
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
    return "AtividadeRep - Prancha {" + super.toString() + '}';
  }

  @Override
  public Prancha clone() {
    return new Prancha(this);
  }
}
