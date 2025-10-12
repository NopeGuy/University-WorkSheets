package Model.Atividades;

import java.time.LocalDateTime;

import Model.Utilizadores.*;

public class ExtensaoDePernas extends AtividadeRepPesos{

  // Constructors
  public ExtensaoDePernas() {
    super();
  }

  public ExtensaoDePernas(int tempoDispendido, int distancia, int altura) {
    super(tempoDispendido, distancia, altura);
  }

  public ExtensaoDePernas(ExtensaoDePernas other) {
    super(other);
  }

  public ExtensaoDePernas(LocalDateTime datainicio) {
    super(datainicio);
  }

  // Override methods
  @Override
  public float calculaCaloriasGastas(Utilizador utilizador) {
    if (utilizador instanceof UtilizadorAmador) {
      return 0.375f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() ;
    } else if (utilizador instanceof UtilizadorOcasional) {
      return 0.375f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.1f;
    } else if (utilizador instanceof UtilizadorProfissional) {
      return 0.375f * this.getPeso() * this.getRepeticoes() * this.getTempoDispendido() * 1.3f;
    }
    return 0;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ExtensaoDePernas)) {
      return false;
    }
    ExtensaoDePernas that = (ExtensaoDePernas) obj;
    return super.equals(that);
  }

  @Override
  public String toString() {
    return "AtividadeRepPesos - Extensão de Pernas {" + super.toString() + '}';
  }

  @Override
  public ExtensaoDePernas clone() {
    return new ExtensaoDePernas(this);
  }
}
