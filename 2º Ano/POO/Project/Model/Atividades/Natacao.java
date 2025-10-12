package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Natacao extends AtividadeDist {
  
  // Constructors
  public Natacao() {
    super();
  }

  public Natacao(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Natacao(Natacao other) {
    super(other);
  }

  public Natacao(LocalDateTime datainicio) {
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
      return 2.5f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado *= 0.000285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.6f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado *= 0.00027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.8f * (this.getDistancia() / tempo);
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Natacao)) {
      return false;
    }
    Natacao that = (Natacao) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDist - Natacao {" + super.toString() + '}';
  }

  @Override
  public Natacao clone() {
    return new Natacao(this);
  }
}
