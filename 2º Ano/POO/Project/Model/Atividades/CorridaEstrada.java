package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class CorridaEstrada extends AtividadeDistAlt {

  // Constructors
  public CorridaEstrada() {
    super();
  }

  public CorridaEstrada(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public CorridaEstrada(CorridaEstrada other) {
    super(other);
  }

  public CorridaEstrada(LocalDateTime datainicio) {
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
      return 4.3f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado += this.getDistancia() * 0.00285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 4.4f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado += this.getDistancia()* 0.0027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 4.6f * this.getAltura() + this.getDistancia() / tempo;
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CorridaEstrada)) {
      return false;
    }
    CorridaEstrada that = (CorridaEstrada) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDistAlt - Corrida na Estrada {" + super.toString() + '}';
  }

  @Override
  public CorridaEstrada clone() {
    return new CorridaEstrada(this);
  }
}
