package TP1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import TP1.Encomenda.Estado_Encomenda;

import java.io.*;
import java.time.LocalDate;

public class Vintage implements Serializable{
    private String sessaoAtual;
    private Map<String, Utilizador> utilizadores;
    private List<Encomenda> encomendas;
    private Map<String, Transportadoras> transportadoras;
    private LocalDate dataPrograma;

    public Vintage() {
        this.sessaoAtual = null;
        this.utilizadores = new HashMap<>();
        this.encomendas = new ArrayList<>();
        this.transportadoras = new HashMap<>();
        this.dataPrograma = LocalDate.now();
    }

    public Vintage(String sessao, Map<String,Utilizador> users, List<Encomenda> enco, Map<String,Transportadoras> transp, LocalDate data){
        this.sessaoAtual = sessao;
        setUtizadores(users);
        setEncomendas(enco);
        setTransportadoras(transp);
        this.dataPrograma = data;
    }

    public Vintage(Vintage v){
        this.sessaoAtual = v.getSessaoAtual();
        setUtizadores(v.getUtilizadores());
        setEncomendas(v.getEncomendas());
        setTransportadoras(v.getTransportadoras());
        this.dataPrograma = v.getDataPrograma();
    }




    public String getSessaoAtual(){
        return this.sessaoAtual;
    }

    public void SetSessaoAtual(String s){
        this.sessaoAtual = s;
    }



    public Map<String,Utilizador> getUtilizadores(){
        Map<String,Utilizador> aux = new HashMap<>();
        for (Map.Entry<String,Utilizador> a : this.utilizadores.entrySet()){
            aux.put(a.getKey(), a.getValue().clone());
        }
        return aux;
    }

    public void setUtizadores(Map<String,Utilizador> u){
        Map<String,Utilizador> aux = new HashMap<>();
        for(Map.Entry<String,Utilizador> a : u.entrySet()){
            aux.put(a.getKey(),a.getValue().clone());
        }
        this.utilizadores = aux;
    }



    public List<Encomenda> getEncomendas(){
        List<Encomenda> enc = new ArrayList<>();
        for (Encomenda e : this.encomendas){
            enc.add(e.clone());
        }
        return enc;
    }

    

    public void setEncomendas(List<Encomenda> enco){
        List<Encomenda> enc = new ArrayList<>();
        for (Encomenda e : enco){
            enc.add(e.clone());
        }
        this.encomendas = enc;
    }

    public Map<String, Transportadoras> getTransportadoras() {
        Map<String,Transportadoras> aux = new HashMap<>();
        for (Map.Entry<String,Transportadoras> e : this.transportadoras.entrySet()){
            aux.put(e.getKey(), e.getValue().clone());
        }
        return aux;
    }


    public void setTransportadoras(Map<String,Transportadoras> transp){
        Map<String,Transportadoras> aux = new HashMap<>();
        for (Map.Entry<String,Transportadoras> e : transp.entrySet()){
            aux.put(e.getKey(), e.getValue().clone());
        }
        this.transportadoras = aux;
    }






    public LocalDate getDataPrograma(){
        return this.dataPrograma;
    }



    public void setDataPrograma(LocalDate data){
        this.dataPrograma = data;
    }





    public boolean equals(Object o){
        if (this == o) 
            return true;
        if ((o == null) || (this.getClass() != o.getClass())) 
            return false;
        Vintage v = (Vintage) o;
        return (this.sessaoAtual.equals(v.getSessaoAtual()) &&
                this.utilizadores.equals(v.getUtilizadores()) &&
                this.encomendas.equals(v.getEncomendas()) &&
                this.transportadoras.equals(v.getTransportadoras()) &&
                this.dataPrograma.equals(v.getDataPrograma()));
    } 

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------\n");
        sb.append("               VINTAGE                  \n");
        sb.append("----------------------------------------\n");
        sb.append("Sessão Atual: ").append(this.sessaoAtual).append("\n");
        sb.append("Utilizadores: ").append(this.utilizadores).append("\n");
        sb.append("Encomendas: ").append(this.encomendas).append("\n");
        sb.append("Transportadoras: ").append(this.transportadoras).append("\n");
        sb.append("Data do Programa: ").append(this.dataPrograma).append("\n");
        return sb.toString();
    }










    //  Métodos auxiliares




    public List<Artigo> getArtigosVenda(){
        List<Artigo> artigos = new ArrayList<>();
        for (Map.Entry<String,Utilizador> entry: utilizadores.entrySet()){
            if (!entry.getKey().equals(this.sessaoAtual)){
                entry.getValue().getPorVender().stream().forEach(artigos::add);
            }
        }
        return artigos;
    }


    public Map<String, Transportadoras> getTransportadorasPremium(){
        Map<String,Transportadoras> aux = new HashMap<>();
        for (Map.Entry<String,Transportadoras> e : this.transportadoras.entrySet()){
            if(e.getValue().isPremium()){
            aux.put(e.getKey(), e.getValue().clone());
            }
        }
        return aux;
    }



    public void adicionaUtilizador(Utilizador user){
        utilizadores.put(user.getEmail(),user);
    }


    public void adicionarTransportadora(Transportadoras t){
        transportadoras.put(t.getNome(), t);
    }

    public Transportadoras getTransportadora(String t){
        return this.transportadoras.get(t).clone();
    }




    public List<Encomenda> getEncomendasSessaoAtual(){
        List<Encomenda> encomendas = new ArrayList<>();
        for (Encomenda e : this.encomendas){
            if (e.getDono().equals(this.sessaoAtual)){
                encomendas.add(e.clone());
            }
        }
        return encomendas;
    }


    public boolean existeEmail(String email){
        return this.utilizadores.containsKey(email);
    }


    public boolean existeUser(String email, String password){
        Utilizador user = this.utilizadores.get(email);
        if (user != null){
        return this.utilizadores.get(email).getPassword().equals(password);
        }
        return false;
    }

    public void addEncomenda(Encomenda e){
        this.encomendas.add(e);
    }

    public void avancarTempo(){
        for (Encomenda e : this.encomendas){
            int comparar = this.dataPrograma.compareTo(e.getPrazoLimite());
            if (comparar > 0 && e.getEstado() != Estado_Encomenda.DEVOLVIDA){
                e.setEstado(Estado_Encomenda.FINALIZADA);
            }

        }
    }


    public List<Encomenda> encomendasParaDevolver(){
        return this.encomendas.stream().filter(e -> e.getPrazoLimite().isAfter(dataPrograma) && e.getDono().equals(this.sessaoAtual)).map(e -> e.clone()).collect(Collectors.toList());
    }


    public void devolverEncomenda(int id){
        for (Encomenda e : this.encomendas){
            if (id == e.getId()){
                for (Map.Entry<Integer,String> dono : e.getVendedores().entrySet()){
                    adicionaArtigoVenda(dono.getKey(), dono.getValue(), e);
                }
                this.utilizadores.get(e.getDono()).removeCompras(e.getArtigos());
                e.setEstado(Estado_Encomenda.DEVOLVIDA);
                break;
            }
        }
    }

    public void adiarPrazoLimite(int id, int dias){
        for (Encomenda e : this.encomendas){
            if (id == e.getId()){
                LocalDate dataLimite = e.getPrazoLimite().plusDays(dias);
                e.setPrazoLimite(dataLimite);
            }
        }
    }

    private void adicionaArtigoVenda(int id, String email, Encomenda e){
        for(Artigo a : e.getArtigos()){
            if (a.getId() == id){
                for(String u : this.utilizadores.keySet()){
                    if (email.equals(u)){ 
                        adicionarPorVender(a, u);
                        this.utilizadores.get(u).removeVenda(id);
                        this.utilizadores.get(u).removeFaturacao(e.getDataCriacao(),a.precoFinal(e.getDataCriacao()));
                     }
                }
            }
        }
    }


    public void trataEncomenda(List<Integer> carrinho, List<Artigo> artigos, Map<Integer,String> vendedores){
        for (int id: carrinho){
            adicionaArtigo(id, artigos, vendedores);
        }
    }

    private void adicionaArtigo(int id,List<Artigo> artigos,Map<Integer,String> vendedores){

        for (Map.Entry<String,Utilizador> user : this.utilizadores.entrySet()){
            if (user.getValue().getPorVender().stream().anyMatch(artigo -> artigo.getId() == id)){
                Artigo a = user.getValue().removePorVender(id);
                this.utilizadores.get(this.sessaoAtual).adicionaCompra(a.clone());
                user.getValue().adicionaVendas(a);
                user.getValue().adicionaFaturacao(this.dataPrograma,a.precoFinal(dataPrograma));    // adiciona a faturação
                artigos.add(a.clone());
                vendedores.put(id, user.getKey());
            }
        }
    }

    public double calculaCustoExpedicao(List<Artigo> artigosEncomenda){
        double custosExpedicao = 0.0;

        for (String t : this.transportadoras.keySet()){
            Long aux = artigosEncomenda.stream().filter(a -> a.getTransportadora().equals(t)).count();
            if (aux != 0){
                double expedicaoT = this.transportadoras.get(t).calculaPrecoExpedicao(aux);
                custosExpedicao +=expedicaoT;
            }
        }
        return custosExpedicao;
    }

    public void adicionarPorVender(Artigo a,String user){
        this.utilizadores.get(user).adicionarPorVender(a);
    }



    public void aterarTransportadora(String transportadora, Double lucro, Double imposto, int formula){
        this.transportadoras.get(transportadora).altera(lucro,imposto,formula);
    }




    // ESTATISTICAS

    public String calculaMaiorFaturacaoSempre(){
        double max = 0.0;
        String utilizador = "";
        for(String user : this.utilizadores.keySet()){
            double valor = this.utilizadores.get(user).calculaFaturacaoSempre();
            if (valor > max) {
                max = valor;
                utilizador = user;
            }
        }
        return utilizador;
    }

    public double getMaiorFaturacaoSempre(String user){
        return this.utilizadores.get(user).calculaFaturacaoSempre();
    }

    public String calculaMaiorFauracaoIntevalo(LocalDate before, LocalDate after){
        double max = 0.0;
        String utilizador = "";
        for(String user : this.utilizadores.keySet()){
            double valor = this.utilizadores.get(user).calculaFaturacaoIntervalo(before, after);
            if (valor > max) {
                max = valor;
                utilizador = user;
            }
        }
        return utilizador;
    }

    public double getMaiorFaturacaoIntervalo(LocalDate before, LocalDate after, String user){
        return this.utilizadores.get(user).calculaFaturacaoIntervalo(before, after);
    }


    public String calculaMaiorVolFaturacao(){
        double max = 0.0;
        String transportadora = "";
        for(String transp : this.transportadoras.keySet()){
            double valor = this.transportadoras.get(transp).getVolFaturacao();
            if (valor > max){
                max = valor;
                transportadora = transp;
            }
        }
        return transportadora;
    }

    public double getMaiorVolFaturacao(String transp){
        return this.transportadoras.get(transp).getVolFaturacao();
    }

    public List<Artigo> listarVendas(String email){
        if (this.utilizadores.containsKey(email)){
            return this.utilizadores.get(email).getVendas();
        } else {
            return null;
        }
    }

    public Map<String,Double> top10Vendedores(LocalDate data){
        Map<String,Double> vendasUser = new LinkedHashMap<>();
        for(String u : this.utilizadores.keySet()){
            double venda = verificaCompras(data, this.utilizadores.get(u).getVendas());
            vendasUser.put(u, venda);
        }
        return vendasUser.entrySet().stream()
                                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                            .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    Map.Entry::getValue,
                                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }



    public Map<String,Double> top10Compradores(LocalDate data){

        Map<String,Double> comprasUser = new LinkedHashMap<>();
        for(String u : this.utilizadores.keySet()){
            double compra = verificaCompras(data,this.utilizadores.get(u).getCompras());
            comprasUser.put(u, compra);
        }

        return comprasUser.entrySet().stream()
                                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                            .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    Map.Entry::getValue,
                                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));


    }


    private double verificaCompras(LocalDate data, List<Artigo> compras){
        double total = 0.0;
        for (Artigo a : compras){
            for (Encomenda e : this.encomendas){
                if (e.getArtigos().contains(a) && e.getDataCriacao().isAfter(data)){
                        total+= a.precoFinal(e.getDataCriacao());
                }
            }
        }
        return total;
    }



    public double totalDinheiroVintage(){
        double total = 0.0;
        for (Encomenda e : this.encomendas){
            if (e.getEstado() == Estado_Encomenda.FINALIZADA) 
            total += e.getPrecoFinal();
        }
        return total;
    } 




}
