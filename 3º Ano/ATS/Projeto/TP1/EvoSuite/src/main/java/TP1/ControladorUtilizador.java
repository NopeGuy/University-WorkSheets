package TP1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.time.LocalDate;

public class ControladorUtilizador implements Serializable{
    
    private final Input in;


    public ControladorUtilizador(){
        in = new Input();
    }



    
    private String login(Vintage v, Apresentacao a){

        String email = in.lerString(a, "Introduza o email: ");
        String password = in.lerString(a, "Introduza a password: ");

        if(v.existeUser(email, password)) {return email;}
        return null;
    }



    private Utilizador registo(Vintage v, Apresentacao a){

        List<Artigo> compras = new ArrayList<>();
        List<Artigo> porVender = new ArrayList<>();
        String nome = in.lerString(a, "Introduza o nome: ");
        String email = in.lerString(a, "Introduza o email: ");
        while (v.existeEmail(email)){ a.printMessage("Email já existente."); email = in.lerString(a, "Introduza o email: ");}
        String password = in.lerString(a, "Introduza a password: ");
        String morada = in.lerString(a, "Introduza a morada: ");
        int nif = in.lerInt(a, "Introduza o NIF: ", 10000000, 999999999);
        List<Artigo> vendas = new ArrayList<>();
        Map<LocalDate,Double> faturacao = new HashMap<>();
        return new Utilizador(email,password,nome,morada,nif,compras,porVender,vendas,faturacao);
    }





    public String interpretador(Vintage v, Apresentacao a){
        
        boolean b = true;
        int comando;
        String email = null;

        while(b){
            comando = in.lerInt(a, "Escolha uma das opções: ", 0, 10000);

            switch(comando){
                case 1: 
                    if ((email = login(v,a)) != null){
                        a.printMessage("Login com Sucesso!");
                        b = false;
                    } else {
                        a.printMessage("Dados inválidos.");
                    } break;
                case 2:
                    Utilizador u = registo(v, a);
                    v.adicionaUtilizador(u);
                    email = u.getEmail();
                    a.printMessage("Registo com Sucesso!");
                    b = false;
                    a.printMessage("Email: " + email + "  |  Password: " + u.getPassword());
                case 0:
                    b = false;
                    break;
            }
        }
        return email;
    }

}
