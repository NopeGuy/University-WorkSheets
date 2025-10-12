package Model.Utilizadores;


public class UtilizadorOcasional extends Utilizador{
    
    // Construtores
    public UtilizadorOcasional(String nome, String morada, String email, int freqCard) {
        super(nome, morada, email, freqCard);
    }

    public UtilizadorOcasional(int uNum, String nome, String morada, String email, int freqCard) {
        super(uNum, nome, morada, email, freqCard);
    }

    public UtilizadorOcasional(UtilizadorOcasional u) {
        super(u);
    }

    public UtilizadorOcasional() {
        super();
    }

    @Override
    public String toString() {
        return "UtilizadorOcasional{ " +
                "Nº Utilizador = " + getNUtilizador() +
                ",Nome = " + getNome() +
                ", Email = " + getEmail() +
                ", Morada = " + getMorada() +
                ", Frequência Cardíaca Média = " + getFreqCard() +
                ", Tipo = Ocasional" +
                ", Atividades = " + getAtividades() +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == null || o.getClass() != this.getClass())
            return false;
        if (this == o)
            return true;
        UtilizadorOcasional u = (UtilizadorOcasional) o;
        return (this.getNUtilizador() == u.getNUtilizador()) &&
                this.getNome().equals(u.getNome()) &&
                this.getMorada().equals(u.getMorada()) &&
                this.getEmail().equals(u.getEmail()) &&
                this.getFreqCard() == u.getFreqCard();
    }

    @Override
    public Utilizador clone() {
        return new UtilizadorOcasional(this);
    }

}
