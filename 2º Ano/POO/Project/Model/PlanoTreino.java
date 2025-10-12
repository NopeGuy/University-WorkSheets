package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Atividades.Atividade;

public class PlanoTreino implements Serializable {
  private HashMap<Integer, List<Atividade>> atividades;
  private int atividadeMaxDia;
  private float consumoCalorico;
  private int recorrenciaSemanal;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFim;
  private List<Integer> diasRealizados;

  public PlanoTreino(PlanoTreino pt) {
    this.atividades = pt.getAtividades();
    this.atividadeMaxDia = pt.getAtividadeMaxDia();
    this.recorrenciaSemanal = pt.getRecorrenciaSemanal();
    this.consumoCalorico = pt.getConsumoCalorico();
    this.dataInicio = pt.getDataInicio();
    this.dataFim = pt.getDataFim();
    this.diasRealizados = pt.getDiasRealizados();
  }

  public PlanoTreino(HashMap<Integer, List<Atividade>> atividades, int atividadeMaxDia, int recorrenciaSemanal,
      float consumoCalorico) {
    this.atividades = atividades;
    this.atividadeMaxDia = atividadeMaxDia;
    this.recorrenciaSemanal = recorrenciaSemanal;
    this.consumoCalorico = consumoCalorico;
    this.dataInicio = SystemDate.getDate();
    this.dataFim = null;
    this.diasRealizados = new ArrayList<>();
  }

  public PlanoTreino() {
    this.atividades = new HashMap<>();
    this.atividadeMaxDia = 0;
    this.recorrenciaSemanal = 0;
    this.consumoCalorico = 0;
    this.dataInicio = SystemDate.getDate();
    this.dataFim = null;
    this.diasRealizados = new ArrayList<>();
  }

  public HashMap<Integer, List<Atividade>> getAtividades() {
    return this.atividades;
  }

  public int getAtividadeMaxDia() {
    return this.atividadeMaxDia;
  }

  public int getRecorrenciaSemanal() {
    return this.recorrenciaSemanal;
  }

  public float getConsumoCalorico() {
    return this.consumoCalorico;
  }

  public LocalDateTime getDataInicio() {
    return this.dataInicio;
  }

  public LocalDateTime getDataFim() {
    return this.dataFim;
  }

  public List<Integer> getDiasRealizados() {
    return this.diasRealizados;
  }

  public void setAtividades(HashMap<Integer, List<Atividade>> atividades) {
    this.atividades = atividades;
  }

  public void setAtividadeMaxDia(int atividadeMaxDia) {
    this.atividadeMaxDia = atividadeMaxDia;
  }

  public void setRecorrenciaSemanal(int recorrenciaSemanal) {
    this.recorrenciaSemanal = recorrenciaSemanal;
  }

  public void setConsumoCalorico(float consumoCalorico) {
    this.consumoCalorico = consumoCalorico;
  }

  public void setDataInicio(LocalDateTime dataInicio) {
    this.dataInicio = dataInicio;
  }

  public void setDataFim(LocalDateTime dataFim) {
    this.dataFim = dataFim;
  }

  public void setDiasRealizados(List<Integer> diasRealizados) {
    this.diasRealizados = diasRealizados;
  }

  // reset recorrencia semanal
  public void resetRecorrenciaSemanal() {
    this.recorrenciaSemanal = atividades.size();
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    PlanoTreino pt = (PlanoTreino) o;
    return this.atividades.equals(pt.getAtividades()) && this.atividadeMaxDia == pt.getAtividadeMaxDia()
        && this.recorrenciaSemanal == pt.getRecorrenciaSemanal() && this.consumoCalorico == pt.getConsumoCalorico()
        && this.dataInicio.equals(pt.getDataInicio()) && this.dataFim.equals(pt.getDataFim());
  }

  public PlanoTreino clone() {
    return new PlanoTreino(this);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.atividades.size(); i++) {
      sb.append("Dia da Semana: ").append(i+1).append("\n");
      for (int j = 0; j < this.atividades.get(i).size(); j++) {
        sb.append("Atividade ").append(j+1).append(": ").append(this.atividades.get(i).get(j)).append("\n");
      }
    }
    sb.append("Atividade Máxima por Dia: ").append(this.atividadeMaxDia).append("\n");
    sb.append("Recorrência Semanal: ").append(this.recorrenciaSemanal).append("\n");
    sb.append("Consumo Calórico: ").append(this.consumoCalorico).append("\n");
    sb.append("Data de Início: ").append(this.dataInicio).append("\n");
    sb.append("Data de Fim: ").append(this.dataFim).append("\n");
    sb.append("Dias Realizados: ").append(this.diasRealizados).append("\n");
    return sb.toString();
  }

}