import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import turma.Aluno;

public class AlunoTest {

    @Test
    public void testGetNome() {
        Aluno aluno = new Aluno("João", "123456789");
        assertEquals("João", aluno.getNome());
    }

    @Test
    public void testGetNumero() {
        Aluno aluno = new Aluno("João", "123456789");
        assertEquals("123456789", aluno.getNumero());
    }

    @Test
    public void testSetNotaValida() {
        Aluno aluno = new Aluno("João", "123456789");
        aluno.setNota("Matemática", 10.0);

        assertEquals(10.0, aluno.getNota("Matemática"));
    }

    @Test
    public void testSetNotaInvalidaMenorQueZero() {
        Aluno aluno = new Aluno("João", "123456789");

        assertThrows(NotaInvalidaException.class, () -> {
            aluno.setNota("Matemática", -1.0);
        });
    }

    @Test
    public void testSetNotaInvalidaMaiorQueVinte() {
        Aluno aluno = new Aluno("João", "123456789");

        assertThrows(NotaInvalidaException.class, () -> {
            aluno.setNota("Matemática", 21.0);
        });
    }
    
    @Test
    public void testConstrutorValido() {
        Map<String, Double> notas = new HashMap<>();
        notas.put("Matemática", 10.0);

        Aluno aluno = new Aluno("João", "123456789", notas);

        assertEquals("João", aluno.getNome());
        assertEquals("123456789", aluno.getNumero());
        assertEquals(notas, aluno.getNotas());
    }

    @Test
    public void testConstrutorNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Aluno("", "123456789", new HashMap<>());
        });
    }

    @Test
    public void testConstrutorNumeroVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Aluno("João", "", new HashMap<>());
        });
    }

    @Test
    public void testConstrutorNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Aluno(null, "123456789", new HashMap<>());
        });
    }

    @Test
    public void testConstrutorNumeroNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Aluno("João", null, new HashMap<>());
        });
    }

    @Test
    public void testConstrutorMapaNotasNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Aluno("João", "123456789", null);
        });
    }
}

