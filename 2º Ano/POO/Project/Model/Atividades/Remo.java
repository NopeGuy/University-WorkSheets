package Model.Atividades;

import java.time.LocalDateTime;

import Model.Hard;
import Model.Utilizadores.*;

public class Remo extends AtividadeDist implements Hard{

  // Constructors
  public Remo() {
    super();
  }

  public Remo(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Remo(Remo other) {
    super(other);
  }

  public Remo(LocalDateTime datainicio) {
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
      return 2.1f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado *= 0.000285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.2f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado *= 0.00027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.4f * (this.getDistancia() / tempo);
    }
    return 0;
  }

  @Override
  public String toString() {
    return "AtividadeDist - Remo {" + super.toString() + '}';
  }

  @Override
  public Remo clone() {
    return new Remo(this);
  }
}
