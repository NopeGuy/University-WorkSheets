package turma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurmaTest {

    private Turma turma;
    private Aluno aluno1;
    private Aluno aluno2;

    @BeforeEach
    void setUp() throws NotaInvalidaException {
        aluno1 = new Aluno("João Silva", "12345");
        aluno1.setNota("Matemática", 15);
        aluno2 = new Aluno("Maria Souza", "67890");
        aluno2.setNota("Matemática", 10);
        turma = new Turma(Arrays.asList(aluno1, aluno2));
    }

    @Test
    void testAdicionaAluno() {
        Aluno novoAluno = new Aluno("Carlos Pereira", "54321");
        turma.addAluno(novoAluno);
        assertTrue(turma.containsAluno("54321"), "A turma deve conter o aluno adicionado");
    }

    @Test
    void testRemoveAlunoExistente() {
        assertDoesNotThrow(() -> turma.removeAluno("12345"), "Remover um aluno existente não deve lançar exceção");
        assertFalse(turma.containsAluno("12345"), "O aluno não deve mais constar na turma após ser removido");
    }

    @Test
    void testRemoveAlunoInexistente() {
        assertThrows(AlunoInexistenteException.class, () -> turma.removeAluno("99999"), "Deveria lançar AlunoInexistenteException ao tentar remover aluno inexistente");
    }

    @Test
    void testMediaTurma() {
        assertEquals(12.5, turma.media(), 0.01, "A média da turma deveria ser 12.5");
    }

    @Test
    void testReprovados() {
        Aluno reprovado = new Aluno("Ana Ferreira", "112233");
        try {
            reprovado.setNota("Matemática", 8);
        } catch (NotaInvalidaException e) {
            fail("Não deveria lançar NotaInvalidaException");
        }
        turma.addAluno(reprovado);
        List<Aluno> alunosReprovados = turma.reprovados();
        assertEquals(1, alunosReprovados.size(), "Deveria ter um aluno reprovado");
        assertTrue(alunosReprovados.contains(reprovado), "A lista de reprovados deve conter o aluno reprovado");
    }

}

