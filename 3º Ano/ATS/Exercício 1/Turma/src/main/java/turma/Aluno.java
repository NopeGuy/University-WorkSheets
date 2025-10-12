package turma;
import java.util.HashMap;
import java.util.Map;

public class Aluno {
    private String nome;
    private String numero;
    private Map<String, Double> notas;

    public Aluno(String nome, String numero, Map<String, Double> notas) {
        this.nome = nome;
        this.numero = numero;
        this.notas = notas;
    }

    public Aluno() {
        this.nome = "";
        this.numero = "0";
        this.notas = new HashMap<>();
    }

    public Aluno(Aluno aluno) {
        this.nome = aluno.getNome();
        this.numero = aluno.getNumero();
        this.notas = new HashMap<>(aluno.notas);
    }

    public Aluno(String nome, String numero) {
        this.nome = nome;
        this.numero = numero;
        this.notas = new HashMap<>();
    }

    @Override
    public Aluno clone() {
        return new Aluno(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Aluno otherAluno = (Aluno) obj;

        return nome.equals(otherAluno.nome)
                && numero.equals(otherAluno.numero)
                && notas.equals(otherAluno.notas);
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public double getNota(String disciplina) {
        return notas.getOrDefault(disciplina, 0.0);
    }

    public String toString() {
        return "Nome: " + nome + ", Número: " + numero + ", Notas: " + notas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNota(String disciplina, double nota) throws NotaInvalidaException {
        if (nota < 0 || nota > 20) {
            throw new NotaInvalidaException("Nota inválida");
        }
        else{
            notas.put(disciplina, nota);
        }
    }

    public void incrementaNota(String disciplina, double nota) {
        notas.put(disciplina, notas.getOrDefault(disciplina, 0.0) + nota);
    }

    public double media() {
        if (notas.isEmpty()) {
            return 0.0;
        }

        double somaNotas = 0.0;
        for (double nota : notas.values()) {
            somaNotas += nota;
        }
        return somaNotas / notas.size();
    }
}
