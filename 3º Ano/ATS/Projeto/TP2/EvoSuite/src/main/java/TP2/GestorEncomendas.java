package TP2;
import java.util.Map;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GestorEncomendas implements Serializable  {
    private Map<Long, Encomenda> encomendas;

    // Construtor vazio
    public GestorEncomendas() {
        this.encomendas = new HashMap<>();
    }

    // Construtor parametrizado
    public GestorEncomendas(Map<Long, Encomenda> encomendas) {
        this.encomendas = new HashMap<>();

        for (Long l : encomendas.keySet())
            this.encomendas.put(l, encomendas.get(l).clone());
    }

    // Construtor cópia
    public GestorEncomendas(GestorEncomendas umGestor) {
        this.encomendas = umGestor.getEncomendas();
    }

    // Método getters e setters
    public Map<Long, Encomenda> getEncomendas() {
        Map<Long, Encomenda> copia = new HashMap<>();

        for (Long l : this.encomendas.keySet())
            copia.put(l, this.encomendas.get(l).clone());

        return copia;
    }

    public void setEncomendas(Map<Long, Encomenda> encomendas) {
        this.encomendas = new HashMap<>();

        for (Long l : encomendas.keySet())
            this.encomendas.put(l, encomendas.get(l).clone());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gestor de Encomendas:: {");
        sb.append(" Informações das encomendas: ").append(this.encomendas.toString()).append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        GestorEncomendas g = (GestorEncomendas) o;

        return (this.encomendas.equals(g.getEncomendas()));
    }

    @Override
    public GestorEncomendas clone() {
        GestorEncomendas copia = new GestorEncomendas(this);
        return copia;
    }

    // Método que adiciona uma Encomenda no map
    // Retorna o id da encomenda
    public long adicionaEncomenda(Encomenda e) throws EncomendaExistenteException{
        if(this.encomendas.containsKey(e.getId()) == true)
            throw new EncomendaExistenteException("Já existe uma encomenda com o ID " + e.getId());

        else{
            this.encomendas.put(e.getId(), e.clone());
            return e.getId();
        }
    }

    // Método que remove uma Encomenda do map
    public long removeEncomenda(long idEncomenda) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não exite nenhuma encomenda com o ID " + idEncomenda);

        else{
            this.encomendas.remove(idEncomenda);
            return idEncomenda;
        }
    }

    // Método que verifica se uma encomenda pode ser devolvida e retorna um set de ids de artigos devolvidos
    public Set<String> podeDevolvida(long idEncomenda, LocalDate data) throws EncomendaNaoEncontradaException, EncomendaDevolucaoImpossivelException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não exite nenhuma encomenda com o ID " + idEncomenda);

        else{
            if(this.encomendas.get(idEncomenda).podeDevolver(data))
                return(this.encomendas.get(idEncomenda).getEncomenda());
            else
                throw new EncomendaDevolucaoImpossivelException("A encomenda com o ID " + idEncomenda + " não pode ser devolvida");
        }
    }

    // Método que dado um set de ids de Encomendas
    // Devolve um set com os artigos todos
    public Set<String> encomendasToArtigos(Set<Long> idsEncomenda){
        Set<String> artigosTodos = new HashSet<>();

        for(Long l : idsEncomenda){
            artigosTodos.addAll(this.encomendas.get(l).getEncomenda());
        }
        return artigosTodos;
    }

    public Set<String> EncomendaToArtigos(long idEncomenda) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não exite nenhuma encomenda com o ID " + idEncomenda);

        else{
            return(this.encomendas.get(idEncomenda).getEncomenda());
        }
    }

    public int EncomendaToQuantidade(long idEncomenda) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não exite nenhuma encomenda com o ID " + idEncomenda);

        else{
            return(this.encomendas.get(idEncomenda).numeroArtigos());
        }
    }


    // Método que transforma uma dada encomenda numa String
    public String encomendaToString(long idEncomenda) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não exite nenhuma encomenda com o ID " + idEncomenda);

        else{
            return(this.encomendas.get(idEncomenda).toString());
        }
    }

    // Método que transforma as encomendas numa string
    public String encomendasToString(Set<Long> idsEncomendas){
        StringBuilder sb = new StringBuilder();
        for(Long l : idsEncomendas){ 
                sb.append(this.encomendas.get(l).toString()).append("\n");
        }
        return sb.toString();
        
    }

    // Método que altera o estado da encomenda para expedida e a data de entrega
    public void FinalizadaToExpedida(long idEncomenda, LocalDate data) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("A encomenda com o ID " + idEncomenda + "não foi expedida");

        else{
            Encomenda encomenda = this.encomendas.get(idEncomenda);
            encomenda.setEstado(Estado.Expedida);
            encomenda.setDataEntrega(data);
        }
    }

    public Encomenda getEncomenda(long idEncomenda) throws EncomendaNaoEncontradaException{
        if(this.encomendas.containsKey(idEncomenda) == false)
            throw new EncomendaNaoEncontradaException("Não existe nehuma encomenda com o ID "+ idEncomenda);

        else{
            return this.encomendas.get(idEncomenda).clone();
        }
    }

    // Método que dado um id de uma Encomenda retorna o tamanho dela
    public int tamanhoEncomenda(long idEncomenda){
        return(this.encomendas.get(idEncomenda).numeroArtigos());
    }

}
