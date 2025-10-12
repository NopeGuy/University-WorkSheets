package TP2;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class FaturaTest {

    @Test
    public void testConstrutorVazio() {
        Fatura fatura = new Fatura();
        assertEquals(Fatura.getCodigo() - 1, fatura.getId());
        assertFalse(fatura.isCompra());
        assertEquals(0, fatura.getIdEncomenda());
        assertEquals(0, fatura.getNifComprador());
        assertTrue(fatura.getArtigos().isEmpty());
        assertEquals(0, fatura.getCustoProdutos(), 0.01);
        assertEquals(0, fatura.getAlteracaoPreco(), 0.01);
        assertEquals(0, fatura.getPrecoFinal(), 0.01);
    }

    @Test
    public void testConstrutorParametrizado() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        Fatura fatura = new Fatura(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        assertEquals(Fatura.getCodigo() - 1, fatura.getId());
        assertTrue(fatura.isCompra());
        assertEquals(1L, fatura.getIdEncomenda());
        assertEquals(123456789L, fatura.getNifComprador());
        assertEquals(artigos, fatura.getArtigos());
        assertEquals(100.0, fatura.getCustoProdutos(), 0.01);
        assertEquals(10.0, fatura.getAlteracaoPreco(), 0.01);
        assertEquals(110.0, fatura.getPrecoFinal(), 0.01);
    }

    @Test
    public void testClone() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        Fatura fatura = new Fatura(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        Fatura clone = fatura.clone();
        assertEquals(fatura, clone);
        assertNotSame(fatura, clone);
    }

    @Test
    public void testEquals() {
        Set<String> artigos1 = new HashSet<>();
        artigos1.add("Artigo1");
        Fatura fatura1 = new Fatura(true, 1L, 123456789L, artigos1, 100.0, 10.0, 110.0);

        Set<String> artigos2 = new HashSet<>();
        artigos2.add("Artigo1");
        Fatura fatura2 = new Fatura(true, 1L, 123456789L, artigos2, 100.0, 10.0, 110.0);

        assertEquals(fatura1, fatura2);
    }

    @Test
    public void testAtualizaFatura() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        Fatura fatura = new Fatura(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        fatura.atualizaFatura("Artigo2", 50.0, 5.0, 55.0);

        Set<String> expectedArtigos = new HashSet<>();
        expectedArtigos.add("Artigo1");
        expectedArtigos.add("Artigo2");

        assertEquals(expectedArtigos, fatura.getArtigos());
        assertEquals(150.0, fatura.getCustoProdutos(), 0.01);
        assertEquals(15.0, fatura.getAlteracaoPreco(), 0.01);
        assertEquals(165.0, fatura.getPrecoFinal(), 0.01);
    }

    @Test
    public void testToString() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        Fatura fatura = new Fatura(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        String expected = "Fatura " + fatura.getId() + " - Compra\n" +
                "NIF do comprador: 123456789\n" +
                "Artigos vendidos: [Artigo1]\n\n" +
                "Custo dos produtos: 100.00€\n" +
                "Custo do transporte: 10.00€\n" +
                "Preço final: 110.00€\n";
        assertEquals(expected, fatura.toString());
    }
}
