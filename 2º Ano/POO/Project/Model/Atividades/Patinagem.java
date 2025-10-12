package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Patinagem extends AtividadeDist{
  
  // Constructors
  public Patinagem() {
    super();
  }

  public Patinagem(int tempoDispendido, int distancia) {
    super(tempoDispendido, distancia);
  }

  public Patinagem(Patinagem other) {
    super(other);
  }

  public Patinagem(LocalDateTime datainicio) {
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
      return 2.75f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorOcasional) {
      tempoEsperado *= 0.000285f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 2.85f * (this.getDistancia() / tempo);
    } else if (utilizador instanceof UtilizadorProfissional) {
      tempoEsperado *= 0.00027f;
      if (this.getTempoDispendido() > tempoEsperado) {
        tempo = this.getTempoDispendido();
      } else {
        tempo = tempoEsperado;
      }
      return 3.05f * (this.getDistancia() / tempo);
    }
    return 0;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Patinagem)) {
      return false;
    }
    Patinagem that = (Patinagem) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeDist - Patinagem {" + super.toString() + '}';
  }

  @Override
  public Patinagem clone() {
    return new Patinagem(this);
  }
}
