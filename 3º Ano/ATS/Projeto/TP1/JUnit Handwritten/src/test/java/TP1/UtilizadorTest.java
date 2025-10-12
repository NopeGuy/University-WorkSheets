package TP1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilizadorTest {

    @Test
    public void testConstrutorPadrao() {
        Utilizador utilizador = new Utilizador();
        assertEquals(6, utilizador.getId());
        assertEquals("", utilizador.getEmail());
        assertEquals("", utilizador.getPassword());
        assertEquals("", utilizador.getNome());
        assertEquals("", utilizador.getMorada());
        assertEquals(0, utilizador.getNif());
        assertTrue(utilizador.getCompras().isEmpty());
        assertTrue(utilizador.getPorVender().isEmpty());
        assertTrue(utilizador.getFaturacao().isEmpty());
    }

    @Test
    public void testConstrutorComParametros() {
        List<Artigo> compras = new ArrayList<>();
        List<Artigo> porVender = new ArrayList<>();
        List<Artigo> vendas = new ArrayList<>();
        Map<LocalDate, Double> faturacao = new HashMap<>();
        faturacao.put(LocalDate.now(), 100.0);

        Utilizador utilizador = new Utilizador("email@example.com", "password", "Nome", "Morada", 123456789, compras,
                porVender, vendas, faturacao);

        assertEquals(1, utilizador.getId()); // Pressupondo que é o segundo utilizador a ser criado
        assertEquals("email@example.com", utilizador.getEmail());
        assertEquals("password", utilizador.getPassword());
        assertEquals("Nome", utilizador.getNome());
        assertEquals("Morada", utilizador.getMorada());
        assertEquals(123456789, utilizador.getNif());
        assertEquals(compras, utilizador.getCompras());
        assertEquals(porVender, utilizador.getPorVender());
        assertEquals(vendas, utilizador.getVendas());
        assertEquals(faturacao, utilizador.getFaturacao());
    }

    @Test
    public void testSetters() {
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail("novoemail@example.com");
        assertEquals("novoemail@example.com", utilizador.getEmail());

        utilizador.setPassword("novapassword");
        assertEquals("novapassword", utilizador.getPassword());

        utilizador.setNome("NovoNome");
        assertEquals("NovoNome", utilizador.getNome());

        utilizador.setMorada("NovaMorada");
        assertEquals("NovaMorada", utilizador.getMorada());

        utilizador.setNif(987654321);
        assertEquals(987654321, utilizador.getNif());

        List<Artigo> novasCompras = new ArrayList<>();
        utilizador.setCompras(novasCompras);
        assertEquals(novasCompras, utilizador.getCompras());

        List<Artigo> novosPorVender = new ArrayList<>();
        utilizador.setPorVender(novosPorVender);
        assertEquals(novosPorVender, utilizador.getPorVender());

        List<Artigo> novasVendas = new ArrayList<>();
        utilizador.setVendas(novasVendas);
        assertEquals(novasVendas, utilizador.getVendas());

        Map<LocalDate, Double> novaFaturacao = new HashMap<>();
        utilizador.setFaturacao(novaFaturacao);
        assertEquals(novaFaturacao, utilizador.getFaturacao());
    }

    @Test
    public void testEquals() {
        List<Artigo> compras = new ArrayList<>();
        List<Artigo> porVender = new ArrayList<>();
        List<Artigo> vendas = new ArrayList<>();
        Map<LocalDate, Double> faturacao = new HashMap<>();

        Utilizador utilizador1 = new Utilizador("email@example.com", "password", "Nome", "Morada", 123456789, compras,
                porVender, vendas, faturacao);
        Utilizador utilizador2 = new Utilizador("email@example.com", "password", "Nome", "Morada", 123456789, compras,
                porVender, vendas, faturacao);
        Utilizador utilizador3 = new Utilizador("diferente@example.com", "password", "Nome", "Morada", 123456789,
                compras, porVender, vendas, faturacao);

        assertTrue(utilizador1.equals(utilizador2));
        assertFalse(utilizador1.equals(utilizador3));
    }

    @Test
    public void testToString() {
        List<Artigo> compras = new ArrayList<>();
        List<Artigo> porVender = new ArrayList<>();
        List<Artigo> vendas = new ArrayList<>();
        Map<LocalDate, Double> faturacao = new HashMap<>();
        Utilizador utilizador = new Utilizador("email@example.com", "password", "Nome", "Morada", 123456789, compras,
                porVender, vendas, faturacao);
        String esperado = "----------------------------------------\n" +
                "               UTILIZADOR               \n" +
                "----------------------------------------\n" +
                "Utilizador: " + utilizador.getId() + "\n" +
                "Email: email@example.com\n" +
                "Password: password\n" +
                "Nome: Nome\n" +
                "Morada: Morada\n" +
                "NIF: 123456789\n" +
                "Por Vender: []\n";

        assertEquals(esperado, utilizador.toString());
    }

    @Test
    public void testClone() {
        List<Artigo> compras = new ArrayList<>();
        List<Artigo> porVender = new ArrayList<>();
        List<Artigo> vendas = new ArrayList<>();
        Map<LocalDate, Double> faturacao = new HashMap<>();
        Utilizador utilizador1 = new Utilizador("email@example.com", "password", "Nome", "Morada", 123456789, compras,
                porVender, vendas, faturacao);
        Utilizador utilizador2 = utilizador1.clone();

        assertEquals(utilizador1, utilizador2);
        assertNotSame(utilizador1, utilizador2);
    }

    @Test
    public void testAdicionaRemoveFaturacao() {
        Utilizador utilizador = new Utilizador();
        LocalDate data = LocalDate.now();

        utilizador.adicionaFaturacao(data, 100.0);
        assertEquals(100.0, utilizador.getFaturacao().get(data), 0.0);

        utilizador.removeFaturacao(data, 50.0);
        assertEquals(50.0, utilizador.getFaturacao().get(data), 0.0);

        utilizador.removeFaturacao(data, 50.0);
        assertEquals(0.0, utilizador.getFaturacao().get(data), 0.0);
    }

    @Test
    public void testCalculaFaturacaoSempre() {
        Utilizador utilizador = new Utilizador();
        LocalDate data1 = LocalDate.now();
        LocalDate data2 = LocalDate.now().minusDays(1);

        utilizador.adicionaFaturacao(data1, 100.0);
        utilizador.adicionaFaturacao(data2, 200.0);

        assertEquals(300.0, utilizador.calculaFaturacaoSempre(), 0.0);
    }

    @Test
    public void testCalculaFaturacaoIntervalo() {
        Utilizador utilizador = new Utilizador();
        LocalDate data1 = LocalDate.now();
        LocalDate data2 = LocalDate.now().minusDays(5);
        LocalDate data3 = LocalDate.now().minusDays(10);

        utilizador.adicionaFaturacao(data1, 100.0);
        utilizador.adicionaFaturacao(data2, 200.0);
        utilizador.adicionaFaturacao(data3, 300.0);

        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fim = LocalDate.now().plusDays(1);

        assertEquals(300.0, utilizador.calculaFaturacaoIntervalo(inicio, fim), 0.0);
    }
}
