package Model.Utilizadores;


public class UtilizadorProfissional extends Utilizador{

    // Construtores
    public UtilizadorProfissional(String nome, String morada, String email, int freqCard) {
        super(nome, morada, email, freqCard);
    }

    public UtilizadorProfissional(int uNum, String nome, String morada, String email, int freqCard) {
        super(uNum, nome, morada, email, freqCard);
    }

    public UtilizadorProfissional(UtilizadorProfissional u) {
        super(u);
    }

    public UtilizadorProfissional() {
        super();
    }

    @Override
    public String toString() {
        return "UtilizadorProfissional{ " +
                "Nº Utilizador = " + getNUtilizador() +
                ",Nome = " + getNome() +
                ", Email = " + getEmail() +
                ", Morada = " + getMorada() +
                ", Frequência Cardíaca Média = " + getFreqCard() +
                ", Tipo = Profissional" +
                ", Atividades = " + getAtividades() +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == null || o.getClass() != this.getClass())
            return false;
        if (this == o)
            return true;
        UtilizadorProfissional u = (UtilizadorProfissional) o;
        return (this.getNUtilizador() == u.getNUtilizador()) &&
                this.getNome().equals(u.getNome()) &&
                this.getMorada().equals(u.getMorada()) &&
                this.getEmail().equals(u.getEmail()) &&
                this.getFreqCard() == u.getFreqCard();
    }

    @Override
    public Utilizador clone() {
        return new UtilizadorProfissional(this);
    }

}
