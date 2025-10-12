package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Abdominais extends AtividadeRep {

  // Constructors
  public Abdominais() {
    super();
  }

  public Abdominais(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Abdominais(Abdominais other) {
    super(other);
  }

  public Abdominais(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
        return 0.25f * this.getRepeticoes() * this.getTempoDispendido();
      }
    else if (utilizador instanceof UtilizadorOcasional) {
        return 0.25f * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;

      }
     else if (utilizador instanceof UtilizadorProfissional) {
        return 0.25f * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
      }
    return 0;
  }

  @Override
  public String toString() {
    return "AtividadeRep - Abdominais {" + super.toString() + '}';
  }

  @Override
  public Abdominais clone() {
    return new Abdominais(this);
  }
}
