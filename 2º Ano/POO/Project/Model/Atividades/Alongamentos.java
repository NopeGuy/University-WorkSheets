package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Alongamentos extends AtividadeRep{

  // Constructors
  public Alongamentos() {
    super();
  }

  public Alongamentos(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Alongamentos(Alongamentos other) {
    super(other);
  }

  public Alongamentos(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.20f * this.getRepeticoes() * this.getTempoDispendido();
    }
    else if (utilizador instanceof UtilizadorOcasional) {
      return 0.20f * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;
    }
    else if (utilizador instanceof UtilizadorProfissional) {
      return 0.20f * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }

  @Override
  public String toString() {
    return "AtividadeRep - Alongamentos {" + super.toString() + '}';
  }

  @Override
  public Alongamentos clone() {
    return new Alongamentos(this);
  }
}
