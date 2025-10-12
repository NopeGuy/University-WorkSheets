package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class CorridaAtletismo extends AtividadeDist{

  // Constructors
  public CorridaAtletismo() {
    super();
  }

  public CorridaAtletismo(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public CorridaAtletismo(CorridaAtletismo other) {
    super(other);
  }

  public CorridaAtletismo(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    float tempoEsperado = this.getDistancia(), tempo;
    if (utilizador instanceof UtilizadorAmador) {
      tempoEsperado *= 0.0003f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado *= 0.000285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.1f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado *= 0.00027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.3f * (this.getDistancia() / tempo);
    }
    return 0;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CorridaAtletismo)) {
      return false;
    }
    CorridaAtletismo that = (CorridaAtletismo) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDist - Corrida de Atletismo {" + super.toString() + '}';
  }

  @Override
  public CorridaAtletismo clone() {
    return new CorridaAtletismo(this);
  }
}
