package turma;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import turma.Aluno;
import turma.AlunoInexistenteException;
import turma.Turma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurmaTest {

    @Test
    public void testConstrutorListaVazia() {
        Turma turma = new Turma();

        assertEquals(0, turma.getAlunos().size());
    }

    @Test
    public void testConstrutorListaValida() throws NotaInvalidaException {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João", "123456789", new HashMap<>()));
        alunos.add(new Aluno("Maria", "987654321", new HashMap<>()));

        Turma turma = new Turma(alunos);

        assertEquals(alunos, turma.getAlunos());
    }

    @Test
    public void testConstrutorListaNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Turma((List<Aluno>) null);
        });
    }

    @Test
    public void testSetAlunosValida() throws NotaInvalidaException {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João", "123456789", new HashMap<>()));
        alunos.add(new Aluno("Maria", "987654321", new HashMap<>()));

        Turma turma = new Turma();
        turma.setAlunos(alunos);

        assertEquals(alunos, turma.getAlunos());
    }

    @Test
    public void testSetAlunosNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            Turma turma = new Turma();
            turma.setAlunos(null);
        });
    }

    @Test
    public void testGetAlunos() throws NotaInvalidaException {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João", "123456789", new HashMap<>()));
        alunos.add(new Aluno("Maria", "987654321", new HashMap<>()));

        Turma turma = new Turma(alunos);

        List<Aluno> alunosRetornados = turma.getAlunos();

        assertEquals(alunos.size(), alunosRetornados.size());
        for (int i = 0; i < alunos.size(); i++) {
            assertEquals(alunos.get(i), alunosRetornados.get(i));
        }
    }

    @Test
    public void testAddAlunoValido() throws NotaInvalidaException {
        Turma turma = new Turma();
        Aluno aluno = new Aluno("João", "123456789", new HashMap<>());

        turma.addAluno(aluno);

        assertTrue(turma.containsAluno("123456789"));
    }

    @Test
    public void testAddAlunoNulo() {
        Turma turma = new Turma();

        assertThrows(IllegalArgumentException.class, () -> {
            turma.addAluno(null);
        });
    }

    @Test
    public void testAddAlunoExistente() throws NotaInvalidaException {
        Turma turma = new Turma();
        Aluno aluno = new Aluno("João", "123456789", new HashMap<>());
        turma.addAluno(aluno);

        assertThrows(IllegalArgumentException.class, () -> {
            turma.addAluno(aluno);
        });
    }

    @Test
    public void testContainsAlunoExistente() throws NotaInvalidaException {
        Turma turma = new Turma();
        Aluno aluno = new Aluno("João", "123456789", new HashMap<>());
        turma.addAluno(aluno);

        assertTrue(turma.containsAluno("123456789"));
    }

    @Test
    public void testContainsAlunoInexistente() {
        Turma turma = new Turma();

        assertFalse(turma.containsAluno("123456789"));
    }

    @Test
    public void testGetAlunoExistente() throws AlunoInexistenteException, NotaInvalidaException {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João", "123456789", new HashMap<>()));

        Turma turma = new Turma(alunos);

        Aluno aluno = turma.getAluno("123456789");

        assertNotNull(aluno);
    }

    @Test
    public void testRemoveAlunoExistente() throws AlunoInexistenteException, NotaInvalidaException {
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João", "123456789", new HashMap<>()));

        Turma turma = new Turma(alunos);

        turma.removeAluno("123456789");

        assertFalse(turma.containsAluno("123456789"));
    }
}
