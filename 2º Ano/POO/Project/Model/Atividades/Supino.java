package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class Supino extends AtividadeRepPesos{

  // Constructors
  public Supino() {
    super();
  }

  public Supino(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public Supino(Supino other) {
    super(other);
  }

  public Supino(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.35f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() ;
    } else if (utilizador instanceof UtilizadorOcasional) {
      return 0.35f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;
    } else if (utilizador instanceof UtilizadorProfissional) {
      return 0.35f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Supino)) {
      return false;
    }
    Supino that = (Supino) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeRepPesos - Supino {" + super.toString() + '}';
  }

  @Override
  public Supino clone() {
    return new Supino(this);
  }
}
