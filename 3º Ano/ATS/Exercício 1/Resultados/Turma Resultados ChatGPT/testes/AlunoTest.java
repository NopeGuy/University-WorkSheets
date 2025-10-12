package turma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlunoTest {
    
    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno("João Silva", "12345");
    }

    @Test
    void testSetNotaValida() {
        assertDoesNotThrow(() -> aluno.setNota("Matemática", 15));
        assertEquals(15, aluno.getNota("Matemática"), 0.01, "Nota deveria ser 15");
    }

    @Test
    void testSetNotaInvalida() {
        assertThrows(NotaInvalidaException.class, () -> aluno.setNota("Matemática", 21), "Deveria lançar NotaInvalidaException para notas acima de 20");
    }

    @Test
    void testMediaNotas() {
        assertAll(
                () -> aluno.setNota("Matemática", 14),
                () -> aluno.setNota("Português", 16)
        );
        assertEquals(15, aluno.media(), 0.01, "A média deveria ser 15");
    }

    @Test
    void testClone() {
        Aluno clone = aluno.clone();
        assertNotSame(aluno, clone, "Clonado deve ser um novo objeto");
        assertEquals(aluno.getNome(), clone.getNome(), "Nomes devem ser iguais");
        assertEquals(aluno.getNumero(), clone.getNumero(), "Números devem ser iguais");
    }
}

