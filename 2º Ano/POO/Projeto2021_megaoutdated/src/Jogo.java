package proj;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.*;

public class Jogo {
    private String equipaCasa;
    private String equipaFora;
    private int golosCasa;
    private int golosFora;
    private LocalDate date;
    private List<Integer> jogadoresCasa;
    private List<Integer> jogadoresFora;
    Map<Integer, Integer> substituicoesCasa = new HashMap<>(); //<23, 42>
    Map<Integer, Integer> substitucoesFora = new HashMap<>();


    public Jogo(String ec, String ef, int gc, int gf, LocalDate d, List<Integer> jc, List<Integer> jf, Map<Integer, Integer> sc, Map<Integer, Integer> sf) {
        equipaCasa = ec;
        equipaFora = ef;
        golosCasa = gc;
        golosFora = gf;
        date = d;
        jogadoresCasa = new ArrayList<>(jc);
        jogadoresFora = new ArrayList<>(jf);
        substituicoesCasa = new HashMap<>(sc);
        substitucoesFora = new HashMap<>(sf);
    }

    public static Jogo parse(String input) {
        String[] campos = input.split(",");
        String[] data = campos[4].split("-");
        List<Integer> jc = new ArrayList<>();
        List<Integer> jf = new ArrayList<>();
        Map<Integer, Integer> subsC = new HashMap<>(); //substituições <23,45>
        Map<Integer, Integer> subsF = new HashMap<>();
        for (int i = 5; i < 16; i++) {
            jc.add(Integer.parseInt(campos[i]));
        }
        for (int i = 16; i < 19; i++) {
            String[] sub = campos[i].split("->");
            subsC.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }
        for (int i = 19; i < 30; i++) {
            jf.add(Integer.parseInt(campos[i]));
        }
        for (int i = 30; i < 33; i++) {
            String[] sub = campos[i].split("->");
            subsF.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }
        return new Jogo(campos[0], campos[1], Integer.parseInt(campos[2]), Integer.parseInt(campos[3]),
                LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])),
                jc, jf, subsC, subsF);
    }

    public String toString() {
        return "Jogo:" + equipaCasa + " - " + equipaFora
                + " -> " + substituicoesCasa.toString()
                + " -> " + substitucoesFora.toString();
    }


    public static Jogo Play(Equipa casa, Equipa fora, int stratCasa, int stratFora) {
        Scanner in = new Scanner(System.in);
        LocalDate date = LocalDate.now();
        List<Integer> numerosCasa = new ArrayList<Integer>(), numerosFora = numerosCasa;
        Map<Integer, Integer> substituicoesCasa = new HashMap<>(), substitucoesFora = new HashMap<>();
        int golosCasa = 0, golosFora = 0;

        //obter numeros de casa
        for (Jogador j : casa.jogadores) {
            numerosCasa.add(j.getNumeroJogador());
        }
        //obter numeros de fora
        for (Jogador j : fora.jogadores) {
            numerosFora.add(j.getNumeroJogador());
        }


        //obter numeros de casa
        for (Jogador j : casa.jogadores) {
            numerosCasa.add(j.getNumeroJogador());
        }
        //obter numeros de fora
        for (Jogador j : fora.jogadores) {
            numerosFora.add(j.getNumeroJogador());
        }

        Jogo j = new Jogo(casa.getNome(), fora.getNome(), golosCasa, golosFora, date, numerosCasa, numerosFora, substituicoesCasa, substitucoesFora);


        //STATS Casa
        int velocidadeCasa = Equipa.getFullVelocidade(casa);
        int resistenciaCasa = Equipa.getFullResistencia(casa);
        int destrezaCasa = Equipa.getFullDestreza(casa);
        int impulsaoCasa = Equipa.getFullImpulsao(casa);
        int cabecaCasa = Equipa.getFullCabeca(casa);
        int remateCasa = Equipa.getFullRemate(casa);
        int passeCasa = Equipa.getFullPasse(casa);


        //Stats Fora
        int velocidadeFora = Equipa.getFullVelocidade(fora);
        int resistenciaFora = Equipa.getFullResistencia(fora);
        int destrezaFora = Equipa.getFullDestreza(fora);
        int impulsaoFora = Equipa.getFullImpulsao(fora);
        int cabecaFora = Equipa.getFullCabeca(fora);
        int remateFora = Equipa.getFullRemate(fora);
        int passeFora = Equipa.getFullPasse(fora);


        //Stats especificos
        double powerLevelCasa = Equipa.powerLevel(stratCasa, velocidadeCasa, resistenciaCasa, destrezaCasa, impulsaoCasa, cabecaCasa, remateCasa, passeCasa);
        double powerLevelFora = Equipa.powerLevel(stratFora, velocidadeFora, resistenciaFora, destrezaFora, impulsaoFora, cabecaFora, remateFora, passeFora);
        double powerLevelTotal = powerLevelCasa + powerLevelFora;
        //calc probabilidades
        double probCasa = powerLevelCasa / powerLevelTotal;
        double probFora = powerLevelFora / powerLevelTotal;


        int probabilityCasa = (int) (probCasa * 100), probabilityFora = (int) (probFora * 100); //e.g. 43 56

        //Gerara array lists



        Random random = new Random();
        Jogador randCasa = Jogador.randomPlayer(casa);
        Jogador randFora = Jogador.randomPlayer(fora);
        //1a Parte
        for (int i = 0; i < 45; i++) {
            //Jogador casa
            randCasa = Jogador.randomPlayer(casa);
            randFora = Jogador.randomPlayer(fora);
            //Jogador fora
            if (passe(probabilityCasa, probabilityFora)) {
                System.out.println("Passe de " + randCasa.toString() + "efetuado com sucesso");
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
            }
            else{
                System.out.println("Passe de " + randCasa.toString() + "falhado ! Corte de "+randFora.toString());
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
            }
            if (remate(probabilityCasa,probabilityFora)){
                System.out.println("Remate de " + randCasa.toString() + "efetuado com sucesso. Golooooo"+ "| " +golosCasa + " - "+golosFora);
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
                golosCasa++;
            }
            else{
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
                System.out.println("Remate de " + randCasa.toString() + "efetuado. Defendido por " + randFora.toString());
            }
        }

        //2a parte
        for (int i = 45; i < 91; i++) {
            //Jogador casa
            randCasa = Jogador.randomPlayer(casa);
            randFora = Jogador.randomPlayer(fora);
            //Jogador fora
            if (passe(probabilityCasa, probabilityFora)) {
                System.out.println("Passe de " + randCasa.toString() + "efetuado com sucesso");
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
            }
            else{
                System.out.println("Passe de " + randCasa.toString() + "falhado ! Corte de "+randFora.toString());
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
            }
            if (remate(probabilityCasa,probabilityFora)){
                System.out.println("Remate de " + randCasa.toString() + "efetuado com sucesso. Golooooo"+ "| " +golosCasa + " - "+golosFora);
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
                golosFora++;
            }
            else{
                randCasa = Jogador.randomPlayer(casa);
                randFora = Jogador.randomPlayer(fora);
                System.out.println("Remate de " + randCasa.toString() + "efetuado. Defendido por " + randFora.toString());
            }
        }

        return j;
    }




    public static boolean remate(int probCasa, int probFora){
        return resultadoInteracao(probCasa, probFora);
    }

    public static boolean passe(int probCasa, int probFora){
        return resultadoInteracao(probCasa, probFora);
    }

    public static boolean resultadoInteracao(int probCasa, int probFora) {
        Random r = new Random();
        boolean result = true;
        int resultadoInteracao = r.nextInt(100) + 1; //1-100

        if (resultadoInteracao >= probCasa) result = false;
        else result = true;


        return result;
    }
}
