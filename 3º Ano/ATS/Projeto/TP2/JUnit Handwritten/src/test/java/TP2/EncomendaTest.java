package TP2;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class EncomendaTest {

    @Test
    public void testConstrutorVazio() {
        Encomenda encomenda = new Encomenda();
        assertEquals(Encomenda.getCodigo() - 1, encomenda.getId());
        assertEquals(-1, encomenda.getIdComprador());
        assertEquals(Dimensao.Pequeno, encomenda.getDimensao());
        assertEquals(Estado.Pendente, encomenda.getEstado());
        assertEquals(LocalDate.now(), encomenda.getData());
        assertNull(encomenda.getDataEntrega());
        assertTrue(encomenda.getEncomenda().isEmpty());
    }

    @Test
    public void testConstrutorParametrizado() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        LocalDate data = LocalDate.of(2023, 1, 1);
        Encomenda encomenda = new Encomenda(artigos, 123456789L, Dimensao.Medio, data);
        assertEquals(Encomenda.getCodigo() - 1, encomenda.getId());
        assertEquals(123456789L, encomenda.getIdComprador());
        assertEquals(Dimensao.Medio, encomenda.getDimensao());
        assertEquals(Estado.Pendente, encomenda.getEstado());
        assertEquals(data, encomenda.getData());
        assertNull(encomenda.getDataEntrega());
        assertEquals(artigos, encomenda.getEncomenda());
    }

    @Test
    public void testClone() {
        Set<String> artigos = new HashSet<>();
        artigos.add("Artigo1");
        Encomenda encomenda = new Encomenda(artigos, 123456789L, Dimensao.Medio, LocalDate.of(2023, 1, 1));
        Encomenda clone = encomenda.clone();
        assertNotSame(encomenda, clone);
    }

    @Test
    public void testEquals() {
        Set<String> artigos1 = new HashSet<>();
        artigos1.add("Artigo1");
        Encomenda encomenda1 = new Encomenda(artigos1, 123456789L, Dimensao.Medio, LocalDate.of(2023, 1, 1));

        Set<String> artigos2 = new HashSet<>();
        artigos2.add("Artigo1");
        Encomenda encomenda2 = new Encomenda(artigos2, 123456789L, Dimensao.Medio, LocalDate.of(2023, 1, 1));

        assertEquals(encomenda1, encomenda2);
    }

    @Test
    public void testAdicionaArtigo() throws Exception {
        Encomenda encomenda = new Encomenda();
        encomenda.adicionaArtigo("Artigo1");
        assertTrue(encomenda.getEncomenda().contains("Artigo1"));
    }

    @Test
    public void testRemoveArtigo() throws Exception {
        Encomenda encomenda = new Encomenda();
        encomenda.adicionaArtigo("Artigo1");
        encomenda.removeArtigo("Artigo1");
        assertFalse(encomenda.getEncomenda().contains("Artigo1"));
    }

    @Test
    public void testRemoveTudo() throws ArtigoExistenteException {
        Encomenda encomenda = new Encomenda();
        encomenda.adicionaArtigo("Artigo1");
        encomenda.adicionaArtigo("Artigo2");
        Set<String> removed = encomenda.removeTudo();
        assertTrue(encomenda.getEncomenda().isEmpty());
        assertTrue(removed.contains("Artigo1"));
        assertTrue(removed.contains("Artigo2"));
    }

    @Test
    public void testPodeDevolver() {
        Encomenda encomenda = new Encomenda();
        encomenda.setEstado(Estado.Expedida);
        encomenda.setDataEntrega(LocalDate.of(2023, 1, 1));
        assertTrue(encomenda.podeDevolver(LocalDate.of(2023, 1, 2)));
        assertFalse(encomenda.podeDevolver(LocalDate.of(2023, 1, 4)));
    }

    @Test
    public void testDefineTamanho() throws ArtigoExistenteException {
        Encomenda encomenda = new Encomenda();
        encomenda.adicionaArtigo("Artigo1");
        encomenda.defineTamanho();
        assertEquals(Dimensao.Pequeno, encomenda.getDimensao());

        encomenda.adicionaArtigo("Artigo2");
        encomenda.defineTamanho();
        assertEquals(Dimensao.Medio, encomenda.getDimensao());

        for (int i = 3; i <= 6; i++) {
            encomenda.adicionaArtigo("Artigo" + i);
        }
        encomenda.defineTamanho();
        assertEquals(Dimensao.Grande, encomenda.getDimensao());
    }

    @Test
    public void testNumeroArtigos() throws ArtigoExistenteException {
        Encomenda encomenda = new Encomenda();
        assertEquals(0, encomenda.numeroArtigos());

        encomenda.adicionaArtigo("Artigo1");
        assertEquals(1, encomenda.numeroArtigos());

        encomenda.adicionaArtigo("Artigo2");
        assertEquals(2, encomenda.numeroArtigos());
    }
}
