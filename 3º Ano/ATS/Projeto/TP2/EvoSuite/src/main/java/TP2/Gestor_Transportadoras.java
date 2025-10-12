package TP2;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Gestor_Transportadoras implements Serializable {
    private Map<Long, Transportadora> transportadoras; // contém as transportadoras todas

    // Construtor vazio
    public Gestor_Transportadoras() {
        this.transportadoras = new HashMap<>();
    }

    // Construtor parametrizado
    public Gestor_Transportadoras(Map<Long, Transportadora> transportadoras2) {
        this.transportadoras = new HashMap<>();

        for (Long l : transportadoras2.keySet())
            this.transportadoras.put(l, transportadoras2.get(l).clone());
    }

    // Construtor cópia
    public Gestor_Transportadoras(Gestor_Transportadoras umaGestora) {
        this.transportadoras = umaGestora.getTransportadoras();
    }

    // Métodos getters e setters
    public Map<Long, Transportadora> getTransportadoras() {
        Map<Long, Transportadora> copia = new HashMap<>();

        for (Long l : this.transportadoras.keySet())
            copia.put(l, this.transportadoras.get(l).clone());

        return copia;
    }

    public void setTransportadoras(Map<Long, Transportadora> novas) {
        this.transportadoras = new HashMap<>();

        for (Long l : novas.keySet())
            this.transportadoras.put(l, novas.get(l).clone());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gestor de Transportadoras:: {");
        sb.append(" Informações das transportadoras: ").append(this.transportadoras.toString()).append("}");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        Gestor_Transportadoras g = (Gestor_Transportadoras) o;

        return (this.transportadoras.equals(g.getTransportadoras()));
    }

    @Override
    public Gestor_Transportadoras clone() {
        Gestor_Transportadoras copia = new Gestor_Transportadoras(this);
        return copia;
    }

    // Método que cria uma nova Transportadora e adicona ao map
    public void criaTransportadora(String nome, double valor_pequenas, double valor_medio, double valor_grande,
            double impostos,
            double margemlucro, boolean premium, int formula) {
        Transportadora t = new Transportadora(nome, valor_pequenas, valor_medio, valor_grande, impostos, margemlucro,
                premium, formula);
        this.transportadoras.put(t.getId(), t);
    }

    // Método que elimina uma Transportadora
    public long removeTransportadora(long idTransportadora) throws TransportadoraNaoEncontradaException {
        if (this.transportadoras.containsKey(idTransportadora) == false)
            throw new TransportadoraNaoEncontradaException(
                    "A transportadora com ID " + idTransportadora + " não existe");

        else {
            this.transportadoras.remove(idTransportadora);
            return idTransportadora;
        }
    }

    // Método que determina qual a transportadora que fatorou mais
    public String fatorouMais() throws TransportadoraNaoEncontradaException {
        Transportadora maximo = this.transportadoras.get(this.transportadoras.keySet().iterator().next());
        if (maximo == null)
            throw new TransportadoraNaoEncontradaException("Não existe nehuma transportadora na Vintage");

        for (Long l : this.transportadoras.keySet()) {
            if (this.transportadoras.get(l).getTotalLucro() > maximo.getTotalLucro())
                maximo = this.transportadoras.get(l); // não sei se tenho de fazer clone
        }
        return (maximo.toString());
    }

    // Método que calcula o preço do transporte de uma encomenda
    // Recebe um List que contém os ids das transportadoras
    public double precoTransporte(List<Long> idsTransportadora) {
        double total = 0;
        List<Long> idsCopy = new ArrayList<>(idsTransportadora);
        for (Long l : idsCopy) {
            int quanti = Collections.frequency(idsTransportadora, l);
            // É preciso verificar se a transportadora existe
            if (this.transportadoras.get(l).getFormula() == 1)
                total += this.transportadoras.get(l).preco_transportadora1(quanti);
            else if (this.transportadoras.get(l).getFormula() == 2)
                total += this.transportadoras.get(l).preco_transportadora2(quanti);
            else if (this.transportadoras.get(l).getFormula() == 3)
                total += this.transportadoras.get(l).preco_transportadora3(quanti);

            idsTransportadora.removeIf(id -> id == l);
        }

        return total;
    }

    // Método que verifica se uma dada Transportadora existe
    public boolean existe_transpotadora(long idTransportadora) {
        return (this.transportadoras.containsKey(idTransportadora));
    }

    public boolean existe_transpotadoraNormal(long idTransportadora) {
        return (this.transportadoras.containsKey(idTransportadora) == true
                && this.transportadoras.get(idTransportadora).isPremium() == false);
    }

    public boolean existe_transpotadoraPremium(long idTransportadora) {
        return (this.transportadoras.containsKey(idTransportadora) == true
                && this.transportadoras.get(idTransportadora).isPremium() == true);
    }

    // Método que apresenta as transportadoras não premium
    public String transportadorasNormais() {
        StringBuilder sb = new StringBuilder();

        for (Long l : this.transportadoras.keySet()) {
            Transportadora t = this.transportadoras.get(l);
            if (t.isPremium() == false) {
                sb.append(t.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    // Método que apresenta apenas as transportadoras premium ----> Nova
    public String transportadorasPremium() {
        StringBuilder sb = new StringBuilder();

        for (Long l : this.transportadoras.keySet()) {
            Transportadora t = this.transportadoras.get(l);
            if (t.isPremium() == true) {
                sb.append(t.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    // Método que dado um id de transportadora, altera a formula de custos
    public void alteraFormula(long idTransportadora, int formula) {
        this.transportadoras.get(idTransportadora).setFormula(formula);
    }

    public String formulasNormais() {
        StringBuilder sb = new StringBuilder();
        sb.append("1)").append(" (ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 0,9").append("\n");
        sb.append("2)").append(" (ValorBase * (1 + MargemLucroTransportadora + Imposto)) * 0,7");

        return sb.toString();
    }

    public String formulasDisponiveis() {
        StringBuilder sb = new StringBuilder();
        sb.append("1)").append(" (ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 0,9").append("\n");
        sb.append("2)").append(" (ValorBase * (1 + MargemLucroTransportadora + Imposto)) * 0,7").append("\n");
        sb.append("3)").append(" (ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 1,5");

        return sb.toString();
    }
}
