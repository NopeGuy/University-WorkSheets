package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class BicicletaDeEstrada extends AtividadeDistAlt{

  // Constructors
  public BicicletaDeEstrada() {
    super();
  }

  public BicicletaDeEstrada(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public BicicletaDeEstrada(BicicletaDeEstrada other) {
    super(other);
  }

  public BicicletaDeEstrada(LocalDateTime datainicio) {
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
      return 2f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado += this.getDistancia() * 0.00285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.1f * this.getAltura() + this.getDistancia() / tempo;
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado += this.getDistancia()* 0.0027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.3f * this.getAltura() + this.getDistancia() / tempo;
    }
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof BicicletaDeEstrada)) {
      return false;
    }
    BicicletaDeEstrada that = (BicicletaDeEstrada) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDist - Bicicleta de Estrada {" + super.toString() + '}';
  }

  @Override
  public BicicletaDeEstrada clone() {
    return new BicicletaDeEstrada(this);
  }
}
