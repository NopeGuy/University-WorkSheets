package Model.Atividades;

import java.time.LocalDateTime;

import Model.Hard;
import Model.Utilizadores.*;

public class TrailNoMonte extends AtividadeDistAlt implements Hard {

  // Constructors
  public TrailNoMonte() {
    super();
  }

  public TrailNoMonte(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public TrailNoMonte(TrailNoMonte other) {
    super(other);
  }

  public TrailNoMonte(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    float tempoEsperado = 1.25f * this.getAltura();
    float tempo;
    if (utilizador instanceof UtilizadorAmador) {
      tempoEsperado += this.getDistancia() * 0.003f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.5f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado += this.getDistancia() * 0.00285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.6f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado += this.getDistancia()* 0.0027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.7f * this.getAltura() + this.getDistancia() / tempo;
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof TrailNoMonte)) {
      return false;
    }
    TrailNoMonte that = (TrailNoMonte) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDistAlt - Trail no Monte {" + super.toString() + '}';
  }

  @Override
  public TrailNoMonte clone() {
    return new TrailNoMonte(this);
  }
}
