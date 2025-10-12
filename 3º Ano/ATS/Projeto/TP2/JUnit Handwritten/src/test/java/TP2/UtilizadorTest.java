package TP2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class UtilizadorTest {

    @Test
    public void testCriaCodigo() {
        long codigoInicial = Utilizador.getCodigo();
        long novoCodigo = Utilizador.criaCodigo();
        assertEquals(codigoInicial + 1, novoCodigo);
    }

    @Test
    public void testConstrutorVazio() {
        Utilizador utilizador = new Utilizador();
        assertEquals("n/d", utilizador.getEmail());
        assertEquals("n/d", utilizador.getNome());
        assertEquals("n/d", utilizador.getMorada());
        assertEquals(-1, utilizador.getNif());
        assertEquals("n/d", utilizador.getPassword());
        assertEquals(0, utilizador.getN_vendidos());
        assertEquals(0, utilizador.getN_comprados());
        assertNotNull(utilizador.getProdutosVendidos());
        assertNotNull(utilizador.getProdutosVender());
        assertNotNull(utilizador.getPendentes());
        assertNotNull(utilizador.getFaturasVendas());
        assertNotNull(utilizador.getFaturasCompras());
        assertNotNull(utilizador.getComprei());
        assertNotNull(utilizador.getDevolvidos());
    }

    @Test
    public void testAdicionaProdutoVendido() throws ArtigoExistenteException {
        Utilizador utilizador = new Utilizador();
        utilizador.adicionaArtigo("prod1");
        assertTrue(utilizador.getProdutosVendidos().contains("prod1"));
    }

    @Test
    public void testAdicionaFaturaVendedor() {
        Utilizador utilizador = new Utilizador();
        utilizador.adicionaFaturaVendedor(true, 1L, 123456789L, "artigo1", 100.0, true, 10.0, 5.0);
        assertTrue(utilizador.getFaturasVendas().containsKey(1L));
    }

    @Test
    public void testAdicionaFaturaComprador() {
        Utilizador utilizador = new Utilizador();
        Set<String> artigos = new HashSet<>();
        artigos.add("artigo1");
        utilizador.adicionaFaturaComprador(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        assertTrue(utilizador.getFaturasCompras().containsKey(1L));
    }

    @Test
    public void testImprimeFaturasCompras() {
        Utilizador utilizador = new Utilizador();
        Set<String> artigos = new HashSet<>();
        artigos.add("artigo1");
        utilizador.adicionaFaturaComprador(true, 1L, 123456789L, artigos, 100.0, 10.0, 110.0);
        String faturas = utilizador.imprimeFaturasCompras();
        assertTrue(faturas.contains("artigo1"));
    }
}
