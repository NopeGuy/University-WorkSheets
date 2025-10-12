package proj;

import java.util.ArrayList;
import java.util.Random;

public class Jogador {

    private String nomeJogador;
    private int numeroJogador;
    private int velocidade, resistencia, destreza, impulsao, cabeca, remate, passe;

    public Jogador(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p){
        nomeJogador = nomeJ;
        numeroJogador = numeroJ;
        velocidade = vel;
        resistencia = res;
        destreza = des;
        impulsao = imp;
        cabeca = cab;
        remate = rem;
        passe = p;
    }

    public Jogador(Jogador j) {
        nomeJogador = j.getNomeJogador();
        numeroJogador = j.getNumeroJogador();
        velocidade = j.getVelocidade();
        resistencia = j.getResistencia();
        destreza = j.getDestreza();
        impulsao = j.getImpulsao();
        cabeca = j.getCabeca();
        remate = j.getRemate();
        passe = j.getPasse();
    }


    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public int getNumeroJogador() {
        return numeroJogador;
    }

    public void setNumeroJogador(int numeroJogador) {
        this.numeroJogador = numeroJogador;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getImpulsao() {
        return impulsao;
    }

    public void setImpulsao(int impulsao) {
        this.impulsao = impulsao;
    }

    public int getCabeca() {
        return cabeca;
    }

    public void setCabeca(int cabeca) {
        this.cabeca = cabeca;
    }

    public int getRemate() {
        return remate;
    }

    public void setRemate(int remate) {
        this.remate = remate;
    }

    public int getPasse() {
        return passe;
    }

    public void setPasse(int passe) {
        this.passe = passe;
    }

    @Override
    protected Jogador clone(){
        return new Jogador(this);
    }

    public static Jogador parse(String input){
        String[] campos = input.split(",");
        return new Jogador(campos[0], Integer.parseInt(campos[1]), Integer.parseInt(campos[2]),
                                        Integer.parseInt(campos[3]),
                                        Integer.parseInt(campos[4]),
                                        Integer.parseInt(campos[5]),
                                        Integer.parseInt(campos[6]),
                                        Integer.parseInt(campos[7]),
                                        Integer.parseInt(campos[8]));
    }

    public String toString(){
        return nomeJogador +"\n";
    }

    public String toStringWithStats(){
        return ("Nº "+numeroJogador+"|"+nomeJogador+"|"+velocidade+"|"+resistencia+"|"+destreza+"|"+impulsao+"|"+cabeca+"|"+remate+"|"+passe+"\n");
    }

    public static Jogador randomPlayer(Equipa e){
        Random random = new Random();
        ArrayList<Jogador> jogadoresCasa = new ArrayList<Jogador>();

        for (Jogador jog : e.jogadores) {
            jogadoresCasa.add(jog);
        }

        Jogador j =  jogadoresCasa.get(random.nextInt(11));
        return j;
    }

}
