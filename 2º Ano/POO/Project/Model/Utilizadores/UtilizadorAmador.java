package Model.Utilizadores;

public class UtilizadorAmador extends Utilizador{


    // Construtores
    public UtilizadorAmador(String nome, String morada, String email, int freqCard) {
        super(nome, morada, email, freqCard);
    }
    
    public UtilizadorAmador(int uNum, String nome, String morada, String email, int freqCard) {
        super(uNum, nome, morada, email, freqCard);
    }

    public UtilizadorAmador(UtilizadorAmador u) {
        super(u);
    }

    public UtilizadorAmador() {
        super();
    }


    @Override
    public String toString() {
        return "UtilizadorAmador{ " +
                "Nº Utilizador = " + getNUtilizador() +
                ",Nome = " + getNome() +
                ", Email = " + getEmail() +
                ", Morada = " + getMorada() +
                ", Frequência Cardíaca Média = " + getFreqCard() +
                ", Tipo = Amador" +
                ", Atividades = " + getAtividades() +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if(this == null || o.getClass() != this.getClass()) return false;
        if(this == o) return true;
        UtilizadorAmador u = (UtilizadorAmador) o;
        return (this.getNUtilizador() == u.getNUtilizador()) &&
                this.getNome().equals(u.getNome()) &&
                this.getMorada().equals(u.getMorada()) &&
                this.getEmail().equals(u.getEmail()) &&
                this.getFreqCard() == u.getFreqCard();
    }

    @Override
    public Utilizador clone() {
        return new UtilizadorAmador(this);
    }  
}
