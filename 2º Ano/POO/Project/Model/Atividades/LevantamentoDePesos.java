package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class LevantamentoDePesos extends AtividadeRepPesos{

  // Constructors
  public LevantamentoDePesos() {
    super();
  }

  public LevantamentoDePesos(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public LevantamentoDePesos(LevantamentoDePesos other) {
    super(other);
  }

  public LevantamentoDePesos(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.325f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() ;
    } else if (utilizador instanceof UtilizadorOcasional) {
      return 0.325f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;
    } else if (utilizador instanceof UtilizadorProfissional) {
      return 0.325f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof LevantamentoDePesos)) {
      return false;
    }
    LevantamentoDePesos that = (LevantamentoDePesos) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeRepPesos - Levantamento de Pesos {" + super.toString() + '}';
  }

  @Override
  public LevantamentoDePesos clone() {
    return new LevantamentoDePesos(this);
  }
}
