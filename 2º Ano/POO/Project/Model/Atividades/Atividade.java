package Model.Atividades;

import java.io.Serializable;
import java.time.LocalDateTime;

import Model.SystemDate;
import Model.Utilizadores.Utilizador;

public abstract class Atividade implements Serializable{
    private LocalDateTime datainicio;
    private LocalDateTime datafim;
    private float tempoDispendido;
    private int efetuado;

    // Default constructor
    public Atividade() {
        this.datainicio = null;
        this.datafim = null;
        this.tempoDispendido = 0f;
        this.efetuado = 0;
    }

    // Constructor with all parameters
    public Atividade(float tempoDispendido) {
        this.datainicio = SystemDate.getDate();
        this.datafim = null;
        this.tempoDispendido = tempoDispendido;
        this.efetuado = 0;
    }

    // Copy constructor
    public Atividade(Atividade other) {
        this.datainicio = other.datainicio;
        this.datafim = other.datafim;
        this.tempoDispendido = other.tempoDispendido;
        this.efetuado = 0;
    }

    // Constructor with datainicio
    public Atividade(LocalDateTime datainicio) {
        this.datainicio = datainicio;
        this.datafim = null;
        this.tempoDispendido = 0f;
        this.efetuado = 0;
    }

    public float getTempoDispendido() {
        return tempoDispendido;
    }

    public LocalDateTime getDatainicio() {
        return datainicio;
    }

    public LocalDateTime getDatafim() {
        return datafim;
    }

    public int getEfetuado() {
        return efetuado;
    }

    public void setTempoDispendido(float tempoDispendido) {
        this.tempoDispendido = tempoDispendido;
    }

    public void setDataInicio(LocalDateTime datainicio) {
        this.datainicio = datainicio;
    }

    public void setDataFim(LocalDateTime datafim) {
        this.datafim = datafim;
    }

    public void setEfetuado(int efetuado) {
        this.efetuado = efetuado;
    }

    public void calculaTempoDispendido() {
        if (datafim != null) {
            long diffMinutes = java.time.Duration.between(datainicio, datafim).toMinutes();
            this.tempoDispendido = diffMinutes;
        }
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atividade)) return false;
        Atividade atividade = (Atividade) o;
        return Float.compare(atividade.tempoDispendido, tempoDispendido) == 0 &&
                efetuado == atividade.efetuado &&
                datainicio.equals(atividade.datainicio) &&
                datafim.equals(atividade.datafim);
    }

    // toString method
    public String toString() {
        return  "tempoDispendido=" + tempoDispendido + " minutos" +
                ", datainicio=" + datainicio +
                ", datafim=" + datafim +
                ", efetuado=" + efetuado;
    }

    // clone method
    public abstract Atividade clone();
    
    public abstract float calculaCaloriasGastas(Utilizador utilizador);
}