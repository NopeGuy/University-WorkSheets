package Model.Atividades;

import java.time.LocalDateTime;

import Model.Hard;
import Model.Utilizadores.*;

public class Deadlift extends AtividadeRepPesos implements Hard{

  // Constructors
  public Deadlift() {
    super();
  }

  public Deadlift(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public Deadlift(Deadlift other) {
    super(other);
  }

  public Deadlift(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.5f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() ;
    } else if (utilizador instanceof UtilizadorOcasional) {
      return 0.5f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;
    } else if (utilizador instanceof UtilizadorProfissional) {
      return 0.5f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Deadlift)) {
      return false;
    }
    Deadlift that = (Deadlift) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeRepPesos - Deadlift {" + super.toString() + '}';
  }

  @Override
  public Deadlift clone() {
    return new Deadlift(this);
  }
}
