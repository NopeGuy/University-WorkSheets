package turma;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Turma {

    private List<Aluno> alunos;

    public Turma(){
        alunos = new ArrayList<>();
    }

    public Turma(List<Aluno> novosAlunos){
        this.setAlunos(novosAlunos);
    }

    public Turma (Turma t){
        this(t.getAlunos());
    }

    public void setAlunos(List<Aluno> novosAlunos){
        alunos = novosAlunos.stream().map(Aluno::clone).collect(Collectors.toList());
    }

    public List<Aluno> getAlunos(){
        return alunos.stream().map(Aluno::clone).collect(Collectors.toList());
    }

    public void addAluno(Aluno a){
        if (!alunos.contains(a)) alunos.add(a.clone());
    }

    public boolean containsAluno(String numero) {
        return alunos.stream().filter(x -> x.getNumero().equals(numero)).count() > 0;
    }

    public void removeAluno(String numero) throws AlunoInexistenteException {
        Iterator<Aluno> it = alunos.iterator();
        boolean removido = false;
        while (it.hasNext() && !removido) {
            Aluno aluno = it.next();
            if (aluno.getNumero().equals(numero)) {
                it.remove();
                removido = true;
            }
        }
        if (!removido)
            throw new AlunoInexistenteException(numero);
    }

    public Aluno getAluno(String numero) throws AlunoInexistenteException{
         return alunos.stream().filter(x -> x.getNumero().equals(numero)).findFirst().orElseThrow(() -> new AlunoInexistenteException(numero)).clone();
    }

    public double media(){
        int i = 0;
        double media = 0.0;
        for (Aluno a : alunos){
            media += a.media();
            i++;
        }
        return media/i;
    }

    public List<Aluno> reprovados(){
        List<Aluno> r = new ArrayList<>();
        for (Aluno a : alunos) {
            if (a.media() < 10) alunos.add(a.clone());
        }
        return r;
    }

}


