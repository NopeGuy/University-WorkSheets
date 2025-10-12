package TP1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.*;
import java.time.LocalDate;

public class Utilizador implements Serializable{
    private static int nextID = 1;
    private int id;
    private String email;
    private String password;
    private String nome;
    private String morada;
    private int nif;
    private List<Artigo> compras;
    private List<Artigo> porVender;
    private List<Artigo> vendas;
    private Map<LocalDate,Double> faturacao;    // em cada dia o valor das vendas


    public Utilizador() {
        this.id = nextID++;
        this.email = "";
        this.password = "";
        this.nome = "";
        this.morada = "";
        this.nif = 0;
        this.porVender = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.faturacao = new HashMap<>();
    }

    public Utilizador(String email, String password, String nome, String morada, int nif, List<Artigo> compras, List<Artigo> porVender, List<Artigo> vendas, Map<LocalDate,Double> faturacao) {
        this.id = nextID++;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
        setCompras(compras);
        setPorVender(porVender);
        setVendas(vendas);
        setFaturacao(faturacao);
        
    }

    public Utilizador(Utilizador u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.nome = u.getNome();
        this.morada = u.getMorada();
        this.nif = u.getNif();
        this.porVender = u.getPorVender();
        this.compras = u.getCompras();
        this.vendas = u.getVendas();
        this.faturacao = u.getFaturacao();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }


    public List<Artigo> getCompras(){
        List<Artigo> aux = new ArrayList<>();
        for (Artigo a : this.compras){
            aux.add(a.clone());
        }
        return aux;
    }

    public void setCompras(List<Artigo> compras){
        this.compras = new ArrayList<>();
        for (Artigo a : compras){
            this.compras.add(a.clone());
        }
    }



    public List<Artigo> getPorVender() {
        List<Artigo> aux = new ArrayList<>();
        for (Artigo a : this.porVender){
            aux.add(a.clone());
        }
        return aux;
    }


    public void setPorVender(List<Artigo> porVender) {
        this.porVender = new ArrayList<>(porVender.size());
        for(Artigo a: porVender){
            this.porVender.add(a.clone());
        }
    }

    public List<Artigo> getVendas(){
        List<Artigo> aux = new ArrayList<>();
        for (Artigo a : this.vendas){
            aux.add(a.clone());
        }
        return aux;
    }

    public void setVendas(List<Artigo> vendas){
        this.vendas = new ArrayList<>();
        for (Artigo a : vendas){
            this.vendas.add(a.clone());
        }
    }


    public Map<LocalDate,Double> getFaturacao(){
        return new HashMap<>(this.faturacao);
    }

    public void setFaturacao(Map<LocalDate,Double> faturacao){
        this.faturacao = new HashMap<>(faturacao);
    }

    
    public void adicionarPorVender(Artigo a){
        this.porVender.add(a.clone());
    }

    public Artigo removePorVender(int id){
        Artigo art = null;
        for(Artigo a : this.porVender){
            if (a.getId() == id){
                art  = a;
                break;
            }
        }
        if (art != null){
        this.porVender.remove(art);
        }
        return art;
    }

    public void adicionaVendas(Artigo a){
        this.vendas.add(a.clone());
    }

    public void removeVenda(int id){
        Artigo art = null;
        for (Artigo a : this.vendas){
            if (a.getId() == id){
                art = a;
                break;
            }
        }
        if (art != null){ this.vendas.remove(art);}
    }



    // Para jogar pelo seguro
    public void adicionaCompra(Artigo a){
        this.compras.add(a.clone());
    }

    public boolean equals(Object o) {
        if(this == o) 
            return true;
        if((o == null) || (this.getClass() != o.getClass())) 
            return false;
        if (!super.equals(o)) 
            return false;
        Utilizador u = (Utilizador) o;
        return (this.id == u.getId() &&
                this.email.equals(u.getEmail()) &&
                this.password.equals(u.getPassword()) &&
                this.nome.equals(u.getNome()) &&
                this.morada.equals(u.getMorada()) &&
                this.nif == u.getNif() &&
                this.compras.equals(u.getCompras()) &&
                this.porVender.equals(u.getPorVender()) &&
                this.faturacao.equals(u.getFaturacao()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------\n");
        sb.append("               UTILIZADOR               \n");
        sb.append("----------------------------------------\n");
        sb.append("Utilizador: ").append(this.id).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Password: ").append(this.password).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Morada: ").append(this.morada).append("\n");
        sb.append("NIF: ").append(this.nif).append("\n");
        sb.append("Por Vender: ").append(this.porVender).append("\n");
        return sb.toString();
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }


    public void removeCompras(List<Artigo> artigos){
        this.compras.removeAll(artigos);        
    }

    public void removeFaturacao(LocalDate data, Double valor){
        Double valorExistente = this.faturacao.get(data);
        if (valorExistente != null){
            faturacao.put(data, valorExistente - valor);
        }

    }


    public void adicionaFaturacao(LocalDate data, double valor){
        if (this.faturacao.containsKey(data)){
            double novoValor = faturacao.get(data) + valor;
            faturacao.put(data, novoValor);
        } else {
            faturacao.put(data, valor);
        }
    }

    public double calculaFaturacaoSempre(){
        return this.faturacao.values().stream().reduce(0.0, (subtotal,valor) -> subtotal + valor);
    }

    public double calculaFaturacaoIntervalo(LocalDate before, LocalDate after){
        return this.faturacao.entrySet().stream().filter(entry -> entry.getKey().isAfter(before) && entry.getKey().isBefore(after)).mapToDouble(Map.Entry::getValue).sum();
    }

}
