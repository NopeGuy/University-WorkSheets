package proj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Equipa {

    private String nome;
    public List<Jogador> jogadores;

    public Equipa(String nomeE) {
        nome=nomeE;
        jogadores = new ArrayList<Jogador>();
    }

    public static Equipa parse(String input){
        String[] campos = input.split(",");
        return new Equipa(campos[0]);
    }

    public void insereJogador(Jogador j) {
        jogadores.add(j.clone());
    }

    public String getNome(){
        return nome;
    }

    public String toString(){
        String r =  "Equipa:" + nome + "\n";
        for (Jogador j : jogadores){
            r += j.toString();
        }
        return r;
    }

    public String toStringWithStats(){
        String r =  "Equipa:" + nome + "\n";
        for (Jogador j : jogadores){
            r += j.toStringWithStats();
        }
        return r;
    }

    public static Equipa defineTeam(Equipa e){
        Scanner in = new Scanner(System.in);
        Equipa r = new Equipa(e.nome); int n, counter = 0;


        for (Jogador j : e.jogadores){
            System.out.println(j.toStringWithStats());
        }
        while (counter != 11){
            System.out.println("\nPróximo jogador:  (Faltam " +(11-counter)+" jogadores)");
            n = in.nextInt();
            for (Jogador j: e.jogadores){
                if (j.getNumeroJogador() == n){ r.insereJogador(j); counter++;}
            }
            System.out.println("A tua Equipa: \n"+r.toStringWithStats());
        }

        return r;
    }

    public static Equipa getSuplentes(Equipa Inicial, Equipa Final){
        Final = new Equipa(Final.nome);
        for (Jogador j: Inicial.jogadores){
            if (!Inicial.jogadores.contains(j)){Final.insereJogador(j);}
        }
        return Final;
    }
    //Estatísticas de jogo
    public static int getFullRemate(Equipa e){
        int remate = 0;
        for (Jogador j: e.jogadores){
            remate += j.getRemate();
        }
        return  remate;
    }

    public static int getFullVelocidade(Equipa e){
        int velocidade = 0;
        for (Jogador j: e.jogadores){
            velocidade += j.getVelocidade();
        }
        return  velocidade;
    }
    public static int getFullResistencia(Equipa e){
        int resistencia = 0;
        for (Jogador j: e.jogadores){
            resistencia += j.getResistencia();
        }
        return  resistencia;
    }
    public static int getFullImpulsao(Equipa e){
        int impulsao = 0;
        for (Jogador j: e.jogadores){
            impulsao += j.getImpulsao();
        }
        return  impulsao;
    }
    public static int getFullPasse(Equipa e){
        int passe = 0;
        for (Jogador j: e.jogadores){
            passe += j.getPasse();
        }
        return  passe;
    }

    public static int getFullCabeca(Equipa e){
        int cabeca = 0;
        for (Jogador j: e.jogadores){
            cabeca += j.getCabeca();
        }
        return cabeca;
    }


    public static int getFullDestreza(Equipa e){
        int destreza = 0;
        for (Jogador j: e.jogadores){
            destreza += j.getDestreza();
        }
        return destreza;
    }



    public static double powerLevel(int strat, int velocidade, int resistencia, int destreza, int impulsao, int cabeca, int remate, int passe/*, int recuperacao, int cruzamento, int elasticidade*/){
        double powerLevel = 0;
        if (!legalStrat(strat)){return powerLevel;}

        //atk defesa control
        //Calculo do power em funcao da strat
        if (strat == 433){ powerLevel = 0.14*velocidade + 0.13*resistencia + 0.06*destreza +0.1*impulsao+ 0.07*cabeca+ 0.21*remate+0.08*passe/*+0.03*recuperacao+0.07*cruzamento+0.02*elasticidade*/;}
        if (strat == 442){ powerLevel = 0.15*velocidade + 0.05*resistencia + 0.01*destreza +0.1*impulsao+ 0.1*cabeca+ 0.1*remate+0.19*passe/*+0.0*recuperacao+0.2*cruzamento+0.1*elasticidade*/;}
        if (strat == 541){ powerLevel = 0.05*velocidade + 0.15*resistencia + 0.1*destreza +0.02*impulsao+ 0.1*cabeca+ 0.01*remate+0.1*passe/*+0.18*recuperacao+0.1*cruzamento+0.19*elasticidade*/;}
        if (strat == 532){ powerLevel = 0.07*velocidade + 0.13*resistencia + 0.15*destreza +0.05*impulsao+ 0.08*cabeca+ 0.16*remate+0.12*passe/*+0.04*recuperacao+0.12*cruzamento+0.08*elasticidade*/;}

        return  powerLevel;
    }

    public static boolean legalStrat(int strat){
        if (strat == 433 || strat == 442 || strat == 541 || strat == 532){return true;}
        return false;
    }

}



