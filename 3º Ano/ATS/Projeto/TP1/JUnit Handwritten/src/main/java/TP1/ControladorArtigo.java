package TP1;

import java.time.LocalDate;
import java.io.*;

public class ControladorArtigo implements Serializable{

    private final Input in;

    public ControladorArtigo(){
        in = new Input();
    }



    public TShirt registarTShirt(Apresentacao a, Vintage v){
        int numDonos;
        Artigo.Avaliação avaliacao;
        Transportadoras transportadora = null; // MUDAR MAIS TARDE CASO SEJA NECESSARIO
        String transp = "";

        a.printMessage("===== Registo T-Shirt ====");
        String tamanhoString = in.lerString(a, ">> Tamanho T-Shirt (S,M,L,XL): ");
        TShirt.Tamanho tamanho = TShirt.Tamanho.valueOf(tamanhoString);
        String padraoString = in.lerString(a, ">> Padrão T-Shirt (LISO,RISCAS,PALMEIRAS): ");
        TShirt.Padrao padrao = TShirt.Padrao.valueOf(padraoString);
        String estadoString = in.lerString(a, ">> Estado T-Shirt (NOVO,USADO): ");
        Artigo.Estado estado = Artigo.Estado.valueOf(estadoString);
        if ((estadoString.equals("USADO"))){
            numDonos = in.lerInt(a, ">> Nº Donos: ", 1, 1000);
            String avaliacaoString = in.lerString(a, ">> Avaliação T-Shirt (IMPECÁVEL,BOM,SATISFATÓRIO): ");
            avaliacao = Artigo.Avaliação.valueOf(avaliacaoString);
        }else {
            numDonos = 0;
            avaliacao = null;
        }
        String descricao = in.lerString(a,">> Descrição T-Shirt: ");
        String marca = in.lerString(a,">> Marca T-Shirt: ");
        String cod = in.lerString(a,">> Código T-Shirt: ");
        Double precoB = in.lerDouble(a,">> Preço Base T-Shirt: ",0,100000);
        Double correcaoP = in.lerDouble(a,">> Correção Preço T-Shirt: ",0,10000);
        a.printTransportadoras(v.getTransportadoras());
        while (transportadora == null){
            transp = in.lerString(a, ">> Transportadora: ");
            transportadora = v.getTransportadora(transp);
        }
        return new TShirt(tamanho, padrao,"TShirt",estado,numDonos,avaliacao,descricao,marca,cod,precoB,correcaoP,transp);
    }




    public Malas registarMala(Apresentacao a, Vintage v){
        int numDonos;
        Artigo.Avaliação avaliacao;
        Transportadoras transportadora = null;
        String transp = "";
        a.printMessage("===== Registo Mala =====");
        String tipoString = in.lerString(a, ">> Tipo Mala (NORMAL,PREMIUM)");
        Malas.Tipos_Malas tipo = Malas.Tipos_Malas.valueOf(tipoString);
        float dimensao = in.lerFloat(a, ">> Dimensão Mala: ", 0, 100000);
        String material = in.lerString(a, ">> Material Mala: ");
        int ano = in.lerInt(a, ">> Ano Coleção: ", 0, 10000);
        String estadoString = in.lerString(a, ">> Estado da Mala (NOVO,USADO): ");
        Artigo.Estado estado = Artigo.Estado.valueOf(estadoString);
        if (estadoString.equals("USADO")){
            numDonos = in.lerInt(a, ">> Nº Donos: ", 1, 1000);
            String avaliacaoString = in.lerString(a, ">> Avaliação Mala (IMPECÁVEL,BOM,SATISFATÓRIO): ");
            avaliacao = Artigo.Avaliação.valueOf(avaliacaoString);
        } else {
            numDonos = 0;
            avaliacao = null;
        }
        String descricao = in.lerString(a,">> Descrição Mala: ");
        String marca = in.lerString(a,">> Marca Mala: ");
        String cod = in.lerString(a,">> Código Mala: ");
        Double precoB = in.lerDouble(a,">> Preço Base Mala: ",0,10000000);
        Double correcaoP = in.lerDouble(a,">> Correção Preço Mala: ",0,1000000);
        if (tipoString.equals("PREMIUM")){
            a.printTransportadoras(v.getTransportadorasPremium());
        }
        else {a.printTransportadoras(v.getTransportadoras());}
        
        while (transportadora == null){
            transp = in.lerString(a, ">> Transportadora: ");
            transportadora = v.getTransportadora(transp);
        }
        return new Malas(tipo,dimensao,material,ano,"Mala",estado,numDonos,avaliacao,descricao,marca,cod,precoB,correcaoP,transp);
    }





    public Sapatilhas registarSapatilha(Apresentacao a, Vintage v){
        int numDonos;
        Artigo.Avaliação avaliacao;
        Transportadoras transportadora = null;
        String transp = "";

        a.printMessage("===== Registo Sapatilha =====");
        String tipoString = in.lerString(a, ">> Tipo Sapatilha (NORMAL,PREMIUM): ");
        Sapatilhas.Tipos_Sapatilhas tipo = Sapatilhas.Tipos_Sapatilhas.valueOf(tipoString);
        int tamanho = in.lerInt(a, ">> Tamanho Sapatilha: ", 0, 100);
        boolean temAtilhos = in.lerSN(a, "Tem atilhos (S,N): ");
        String cor = in.lerString(a, ">> Cor Sapatilha: ");
        LocalDate data = in.lerData(a, ">> Data Lançamento Sapatilha (dd-mm-yyyy): ");
        String estadoString = in.lerString(a, ">> Estado Sapatilha (NOVO,USADO): ");
        Artigo.Estado estado = Artigo.Estado.valueOf(estadoString);
        if (estadoString.equals("USADO")){
            numDonos = in.lerInt(a, ">> Nº Donos: ", 1, 1000);
            String avaliacaoString = in.lerString(a, ">> Avaliação Sapatilha (IMPECÁVEL,BOM,SATISFATÓRIO): ");
            avaliacao = TShirt.Avaliação.valueOf(avaliacaoString);
        } else {
            numDonos = 0;
            avaliacao = null;
        }
        String desc = in.lerString(a,">> Descrição Sapatilha: ");
        String marca = in.lerString(a,">> Marca Sapatilha: ");
        String cod = in.lerString(a,">>> Código Sapatilha: ");
        Double precoB = in.lerDouble(a,">>> Preço Base Sapatilha: ",0,10000000);
        Double correcaoP = in.lerDouble(a,">>> Correção Preço Sapatilha: ",0,1000000);
        if (tipoString.equals("PREMIUM")){
            a.printTransportadoras(v.getTransportadorasPremium());
        }
        else {a.printTransportadoras(v.getTransportadoras());}

        while (transportadora == null){
            transp = in.lerString(a, ">> Transportadora: ");
            transportadora = v.getTransportadora(transp);
        }

        return new Sapatilhas(tamanho,temAtilhos,cor,data,tipo,"Sapatilha",estado,numDonos,avaliacao,desc,marca,cod,precoB,correcaoP,transp);
    }







    public void interpretador(Apresentacao a,Vintage v){
        boolean b = true;
        int comando;
        String userAtual = v.getSessaoAtual();
        
        while(b){
            a.printMenuArtigo();
            comando = in.lerInt(a,"Escolha a sua opção:",0,3);

            switch(comando){
                case 1:
                    TShirt tShirt = registarTShirt(a, v);
                    a.printMessage("T-Shirt registada com Sucesso!");
                    v.adicionarPorVender(tShirt,userAtual);
                    break;
                    
                case 2:
                    Malas mala = registarMala(a,v);
                    a.printMessage("Mala registada com Sucesso!");
                    v.adicionarPorVender(mala,userAtual);
                    break;

                case 3:
                    Sapatilhas sap = registarSapatilha(a,v);
                    a.printMessage("Sapatilha registada com Sucesso!");
                    v.adicionarPorVender(sap,userAtual);
                    break;
                case 0:
                    b = false;
                    break;
                default:
                    a.printMessage("Erro! Comando Inválido.");
                    break;
            }
        }
    }

    
}
