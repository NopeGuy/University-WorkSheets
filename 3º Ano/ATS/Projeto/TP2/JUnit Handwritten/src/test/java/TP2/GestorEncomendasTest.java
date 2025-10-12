package TP2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestorEncomendasTest {

    @Test
    public void testConstrutorVazio() {
        GestorEncomendas gestor = new GestorEncomendas();
        assertTrue(gestor.getEncomendas().isEmpty());
    }

    @Test
    public void testConstrutorParametrizado() {
        // Criar um conjunto de artigos
        Set<String> artigos = new HashSet<>();
        artigos.add("artigo1");
        artigos.add("artigo2");

        // Criar uma data para a encomenda
        LocalDate data = LocalDate.of(2024, 5, 28);

        // Criar uma encomenda utilizando o construtor parametrizado
        Encomenda encomenda = new Encomenda(artigos, 123456789L, Dimensao.Grande, data);

        // Verificar se os valores foram atribuídos corretamente
        assertEquals(2, encomenda.getEncomenda().size());
        assertEquals(123456789L, encomenda.getIdComprador());
        assertEquals(Dimensao.Grande, encomenda.getDimensao());
        assertEquals(Estado.Pendente, encomenda.getEstado());
        assertEquals(data, encomenda.getData());
        assertEquals(null, encomenda.getDataEntrega()); // Verifica se dataEntrega é inicializado como null
    }

    @Test
    public void testConstrutorCopia() throws EncomendaExistenteException {
        GestorEncomendas gestor1 = new GestorEncomendas();
        Encomenda encomenda = new Encomenda();
        gestor1.adicionaEncomenda(encomenda);

        GestorEncomendas gestor2 = new GestorEncomendas(gestor1);
        assertEquals(gestor1, gestor2);
        assertNotSame(gestor1, gestor2);
    }

    @Test
    public void testRemoveEncomenda() throws EncomendaNaoEncontradaException, EncomendaExistenteException {
        GestorEncomendas gestor = new GestorEncomendas();
        Encomenda encomenda = new Encomenda();
        gestor.adicionaEncomenda(encomenda);
        gestor.removeEncomenda(encomenda.getId());
        assertFalse(gestor.getEncomendas().containsKey(encomenda.getId()));
    }

    @Test
    public void testEncomendaToString() throws EncomendaNaoEncontradaException, EncomendaExistenteException {
        GestorEncomendas gestor = new GestorEncomendas();
        Encomenda encomenda = new Encomenda();
        gestor.adicionaEncomenda(encomenda);
        String expected = encomenda.toString();
        assertEquals(expected, gestor.encomendaToString(encomenda.getId()));
    }

    @Test
    public void testEncomendasToString() throws EncomendaExistenteException {
        GestorEncomendas gestor = new GestorEncomendas();
        Encomenda encomenda1 = new Encomenda();
        Encomenda encomenda2 = new Encomenda();
        gestor.adicionaEncomenda(encomenda1);
        gestor.adicionaEncomenda(encomenda2);
        Set<Long> ids = new HashSet<>();
        ids.add(encomenda1.getId());
        ids.add(encomenda2.getId());
        String expected = encomenda1.toString() + "\n" + encomenda2.toString() + "\n";
        assertEquals(expected, gestor.encomendasToString(ids));
    }

    @Test
    public void testFinalizadaToExpedida() throws EncomendaNaoEncontradaException, EncomendaExistenteException {
        GestorEncomendas gestor = new GestorEncomendas();
        Encomenda encomenda = new Encomenda();
        gestor.adicionaEncomenda(encomenda);
        LocalDate data = LocalDate.of(2023, 1, 1);
        gestor.FinalizadaToExpedida(encomenda.getId(), data);
        assertEquals(Estado.Expedida, encomenda.getEstado());
        assertEquals(data, encomenda.getDataEntrega());
    }

    @Test
    public void testTamanhoEncomenda() throws ArtigoExistenteException, EncomendaExistenteException {
        GestorEncomendas gestor = new GestorEncomendas();
        Encomenda encomenda = new Encomenda();
        encomenda.adicionaArtigo("Artigo1");
        encomenda.adicionaArtigo("Artigo2");
        gestor.adicionaEncomenda(encomenda);
        assertEquals(2, gestor.tamanhoEncomenda(encomenda.getId()));
    }
}
