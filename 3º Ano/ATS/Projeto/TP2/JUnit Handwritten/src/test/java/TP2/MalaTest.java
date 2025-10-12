package TP2;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class MalaTest {

    @Test
    public void testConstrutorVazio() {
        Mala mala = new Mala();
        assertEquals(0, mala.getDimensao());
        assertEquals("", mala.getMaterial());
        assertEquals(0, mala.getAno_colecao());
        assertFalse(mala.isPremium());
        assertEquals(0, mala.getValorizacao(), 0.01);
    }

    @Test
    public void testConstrutorParametrizado() {
        Mala mala = new Mala("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 30, "Couro", 2021, true, 5.0f);
        assertEquals("Descrição", mala.getDescricao());
        assertEquals("Marca", mala.getMarca());
        assertEquals(100.0, mala.getPreco_base(), 0.01);
        assertFalse(mala.getNovo());
        assertEquals(1, mala.getEstado_uso());
        assertEquals(1, mala.getN_utilizadores());
        assertEquals(1L, mala.getIdTransportadora());
        assertEquals(1L, mala.getIdVendedor());
        assertEquals(30, mala.getDimensao());
        assertEquals("Couro", mala.getMaterial());
        assertEquals(2021, mala.getAno_colecao());
        assertTrue(mala.isPremium());
        assertEquals(5.0f, mala.getValorizacao(), 0.01);
    }

    @Test
    public void testClone() {
        Mala mala = new Mala("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 30, "Couro", 2021, true, 5.0f);
        Mala clone = mala.clone();
        assertEquals(mala, clone);
    }

    @Test
    public void testEquals() {
        Mala mala1 = new Mala("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 30, "Couro", 2021, true, 5.0f);
        Mala mala2 = new Mala("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 30, "Couro", 2021, true, 5.0f);
        assertEquals(mala1, mala2);
    }

    @Test
    public void testPrecoAtual() {
        Mala mala = new Mala("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 30, "Couro", 2021, true, 5.0f);
        LocalDate data = LocalDate.of(2023, 1, 1);
        double preco = mala.preco_atual(data);
        double expected = 100.0;
        for (int i = 0; i < 2; i++) {
            expected *= 1 + (5.0 / 100);
        }
        assertEquals(expected, preco, 0.01);

        mala.setPremium(false);
        preco = mala.preco_atual(data);
        expected = 100.0 - (100.0 * 1 / (30 * 1));
        assertEquals(expected, preco, 0.01);

        mala.setNovo(true);
        preco = mala.preco_atual(data);
        expected = 100.0;
        assertEquals(expected, preco, 0.01);
    }

}
