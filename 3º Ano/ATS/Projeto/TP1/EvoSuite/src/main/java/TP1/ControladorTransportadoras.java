package TP1;
import java.io.*;
import java.util.Map;

public class ControladorTransportadoras implements Serializable{
    private final Input in;
    
    public ControladorTransportadoras(){
        in = new Input();
    }

    // Método para registar uma Transportadora
    public Transportadoras registarTransportadoras (Apresentacao a){

        String nome = in.lerString(a, ">> Nome da Empresa: ");
        Double lucro = in.lerDouble(a, ">> Margem de lucro da Transportadora: ",0,100000);
        boolean premium = in.lerSN(a,">> A Transportadora é Premium(S/N): ");
        return new Transportadoras(nome, 0.23, lucro, premium, 0.0);    // imposto e valor base de expedição já vem definido no arranque
    }





    public void interpretador(Vintage v, Apresentacao a){
        Transportadoras t = registarTransportadoras(a);
        a.printMessage("Transportadora registada com sucesso!");
        v.adicionarTransportadora(t);
    }

    public void alterarTransportadora(Vintage v, Apresentacao a){
        Map<String,Transportadoras> transportadoras = v.getTransportadoras();
        a.printTransportadoras(transportadoras);
        String transportadora = in.lerString(a, "Selecione o nome da transportadora: ");
        if (transportadoras.keySet().contains(transportadora)){
            Double lucro = in.lerDouble(a, "Valor do Lucro: ", 0, 100);
            Double imposto = in.lerDouble(a, "Valor do Imposto: ", 0, 100);
            a.formulasTransportadora();
            int formula = in.lerInt(a,"Selecione a fórmula que pretende aplicar: ", 0,4);
            v.aterarTransportadora(transportadora,lucro,imposto,formula);
            a.printMessage("Transportadora Alterada com Sucesso!");
        }
        else {
            a.printMessage("Transportadora não disponível.");
        }
    }

}



