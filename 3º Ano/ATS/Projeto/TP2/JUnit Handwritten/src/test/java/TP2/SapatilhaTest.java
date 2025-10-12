package TP2;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class SapatilhaTest {

    @Test
    public void testConstrutorVazio() {
        Sapatilha sapatilha = new Sapatilha();
        assertEquals(0, sapatilha.getTamanho());
        assertFalse(sapatilha.isAtacadores());
        assertEquals("", sapatilha.getCor());
        assertEquals(0, sapatilha.getAnoColecao());
        assertFalse(sapatilha.isPremium());
        assertEquals(0.0, sapatilha.getDesconto(), 0.01);
    }

    @Test
    public void testConstrutorParametrizado() {
        Sapatilha sapatilha = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        assertEquals("Descrição", sapatilha.getDescricao());
        assertEquals("Marca", sapatilha.getMarca());
        assertEquals(100.0, sapatilha.getPreco_base(), 0.01);
        assertFalse(sapatilha.getNovo());
        assertEquals(1, sapatilha.getEstado_uso());
        assertEquals(1, sapatilha.getN_utilizadores());
        assertEquals(1L, sapatilha.getIdTransportadora());
        assertEquals(1L, sapatilha.getIdVendedor());
        assertEquals(42, sapatilha.getTamanho());
        assertTrue(sapatilha.isAtacadores());
        assertEquals("Azul", sapatilha.getCor());
        assertEquals(2021, sapatilha.getAnoColecao());
        assertTrue(sapatilha.isPremium());
        assertEquals(10.0, sapatilha.getDesconto(), 0.01);
    }

    @Test
    public void testClone() {
        Sapatilha sapatilha = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        Sapatilha clone = sapatilha.clone();
        assertEquals(sapatilha, clone);
    }

    @Test
    public void testEquals() {
        Sapatilha sapatilha1 = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        Sapatilha sapatilha2 = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        assertEquals(sapatilha1, sapatilha2);
    }

    @Test
    public void testPrecoAtual() {
        Sapatilha sapatilha = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        LocalDate data = LocalDate.of(2023, 1, 1);
        double preco = sapatilha.preco_atual(data);
        double expected = 100.0 + (100.0 * (2023 - 2021) / 10);
        assertEquals(expected, preco, 0.01);

        sapatilha.setPremium(false);
        preco = sapatilha.preco_atual(data);
        expected = 100.0 - (100.0 * 1 / (10 * 1)) * (100 - 10.0) / 100;
        assertEquals(expected, preco, 0.01);

        sapatilha.setNovo(true);
        preco = sapatilha.preco_atual(data);
        expected = 100.0 * (100 - 10.0) / 100;
        assertEquals(expected, preco, 0.01);
    }

    @Test
    public void testToString() {
        Sapatilha sapatilha = new Sapatilha("Descrição", "Marca", 100.0, false, 1, 1, 1L, 1L, 42, true, "Azul", 2021,
                true, 10.0);
        String expected = "Sapatilhas:: {Artigo:: {Descrição: Descrição Marca: Marca Preço Base: 100.0 Novo: false Estado de Uso: 1 Número de Utilizadores: 1 Transportadora: 1 Vendedor: 1} Tamanho: 42 Atacadores: true Cor: Azul Data da Coleção: 2021 Premium: true Desconto: 10.0}";
        assertEquals(expected, sapatilha.toString());
    }
}
