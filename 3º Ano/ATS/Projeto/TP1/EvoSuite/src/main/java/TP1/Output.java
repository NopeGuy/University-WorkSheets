package TP1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;



public class Output implements Serializable {




    private void printLine(int size) {
        for(int i=0; i<size; i++)
            System.out.print("-");

        System.out.println("");
    }

    public void printMenus(String []menu, String message, int type){

        int size, length=message.length();

        for(String linha: menu)
            if(linha.length() + 4 > length)
                length = linha.length() + 4;

        if(length < 20)
            length = 20;

        printLine(length);
        System.out.println(message);
        printLine(length);

        size = menu.length;
        for(int i = 0;i < size;i++)
            System.out.println(i+1+" | "+menu[i]);
        if(type == 0)
            System.out.println("0 | Sair");
        else
            System.out.println("0 | Voltar atrás");
        printLine(length);
    }


    public void printMessage(String message) {
        System.out.println(message);
    }


    public void printTransportadoras(Map<String, Transportadoras> transportadoras){
        System.out.println("+------------------------------+\n|        Transportadoras       |\n+------------------------------+");
        for (Transportadoras a : transportadoras.values()){
            System.out.print(a);
            System.out.println("+------------------------------+");
        }
    }



    public void printArtigos(List<Artigo> artigos,LocalDate data){
        System.out.println("+--------------------------+\n|          Artigos         |\n+--------------------------+");
        for (Artigo a : artigos){
            System.out.print(a.toString(data));
            System.out.println("+--------------------------+");
        }
    }

    public void printEncomendas(List<Encomenda> encomendas){
        System.out.println("+-----------------------------+\n|          Encomendas         |\n+-----------------------------+");
        for (Encomenda e : encomendas){
            System.out.print(e.toString());
            System.out.println("+-----------------------------+");
        }
    }

    public void printCarrinho(List<Integer> carrinho){
        System.out.print("[CARRINHO] : ");
        for (int i : carrinho){
            System.out.print("[" + i + "] ");
        }
        System.out.println();
    }

    public void printTop10(Map<String,Double> top){
        int i = 1;
        System.out.println("+-------------------------+\n|          TOP-10         |\n+-------------------------+");
        for(Map.Entry<String, Double> map : top.entrySet()){
            System.out.println(i + " -> " + map.getKey() + " | " + map.getValue()+"\n");
            i++;
            if (i > 10) {break;}
        }
        System.out.println("+-------------------------+");
    }

    public void formulasTransportadora(){
        System.out.println("+---------------------------------------------+");
        System.out.println("|                    FÓRMULAS                 |");
        System.out.println("+---------------------------------------------+");
        System.out.println("1 - ValorBase * Lucro * (1 + Imposto) * 0.9");
        System.out.println("2 - Valor(Base + Lucro * Imposto) * 0.5");
        System.out.println("3 - (1 + Lucro) * Imposto * ValorBase ");
        System.out.println("4 - Imposto * 5 + ValorBase - Lucro * 5");
        System.out.println("+---------------------------------------------+");
    }
}