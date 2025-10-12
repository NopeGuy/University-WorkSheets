package TP1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncomendaTest {

    @Test
    public void testConstrutorPadrao() {
        Encomenda encomenda = new Encomenda();
        assertEquals(1, encomenda.getId());
        assertEquals("", encomenda.getDono());
        assertTrue(encomenda.getArtigos().isEmpty());
        assertNull(encomenda.getEmbalagem());
        assertEquals(0.0, encomenda.getPrecoFinal(), 0.0);
        assertEquals(0.0, encomenda.getCustosExpedicao(), 0.0);
        assertEquals(Encomenda.Estado_Encomenda.PENDENTE, encomenda.getEstado());
        assertEquals(LocalDate.now(), encomenda.getDataCriacao());
        assertEquals(0, encomenda.getTamanho());
        assertEquals(LocalDate.now().plusDays(2), encomenda.getPrazoLimite());
        assertTrue(encomenda.getVendedores().isEmpty());
    }

    @Test
    public void testConstrutorComParametros() {
        List<Artigo> artigos = new ArrayList<>();
        Map<Integer, String> vendedores = new HashMap<>();
        LocalDate dataCriacao = LocalDate.of(2023, 5, 1);
        Encomenda encomenda = new Encomenda("Dono1", artigos, 15.0, dataCriacao, vendedores);

        assertEquals(1, encomenda.getId()); // Supondo que é a segunda encomenda a ser criada
        assertEquals("Dono1", encomenda.getDono());
        assertEquals(artigos, encomenda.getArtigos());
        assertEquals(15.0, encomenda.getPrecoFinal(), 0.0);
        assertEquals(15.0, encomenda.getCustosExpedicao(), 0.0);
        assertEquals(Encomenda.Estado_Encomenda.EXPEDIDA, encomenda.getEstado());
        assertEquals(dataCriacao, encomenda.getDataCriacao());
        assertEquals(0, encomenda.getTamanho());
        assertEquals(dataCriacao.plusDays(2), encomenda.getPrazoLimite());
        assertEquals(vendedores, encomenda.getVendedores());
    }

    @Test
    public void testSetters() {
        Encomenda encomenda = new Encomenda();
        encomenda.setDono("NovoDono");
        assertEquals("NovoDono", encomenda.getDono());

        List<Artigo> novosArtigos = new ArrayList<>();
        encomenda.setArtigos(novosArtigos);
        assertEquals(novosArtigos, encomenda.getArtigos());

        encomenda.setEmbalagem(Encomenda.Dimensao_Embalagem.GRANDE);
        assertEquals(Encomenda.Dimensao_Embalagem.GRANDE, encomenda.getEmbalagem());

        encomenda.setPrecoFinal(200.0);
        assertEquals(200.0, encomenda.getPrecoFinal(), 0.0);

        encomenda.setCustosExpedicao(20.0);
        assertEquals(20.0, encomenda.getCustosExpedicao(), 0.0);

        encomenda.setEstado(Encomenda.Estado_Encomenda.FINALIZADA);
        assertEquals(Encomenda.Estado_Encomenda.FINALIZADA, encomenda.getEstado());

        LocalDate novaDataCriacao = LocalDate.of(2023, 6, 1);
        encomenda.setDataCriacao(novaDataCriacao);
        assertEquals(novaDataCriacao, encomenda.getDataCriacao());

        encomenda.setTamanho(3);
        assertEquals(3, encomenda.getTamanho());

        LocalDate novoPrazoLimite = LocalDate.of(2023, 6, 3);
        encomenda.setPrazoLimite(novoPrazoLimite);
        assertEquals(novoPrazoLimite, encomenda.getPrazoLimite());

        Map<Integer, String> novosVendedores = new HashMap<>();
        encomenda.setVendedores(novosVendedores);
        assertEquals(novosVendedores, encomenda.getVendedores());
    }

    @Test
    public void testEquals() {
        List<Artigo> artigos = new ArrayList<>();
        Map<Integer, String> vendedores = new HashMap<>();
        LocalDate dataCriacao = LocalDate.of(2023, 5, 1);
        Encomenda encomenda1 = new Encomenda("Dono1", artigos, 15.0, dataCriacao, vendedores);
        Encomenda encomenda2 = new Encomenda("Dono1", artigos, 15.0, dataCriacao, vendedores);
        Encomenda encomenda3 = new Encomenda("Dono2", artigos, 20.0, dataCriacao, vendedores);

        assertTrue(encomenda1.equals(encomenda2));
        assertFalse(encomenda1.equals(encomenda3));
    }

    @Test
    public void testClone() {
        List<Artigo> artigos = new ArrayList<>();
        Map<Integer, String> vendedores = new HashMap<>();
        LocalDate dataCriacao = LocalDate.of(2023, 5, 1);
        Encomenda encomenda1 = new Encomenda("Dono1", artigos, 15.0, dataCriacao, vendedores);
        Encomenda encomenda2 = encomenda1.clone();

        assertEquals(encomenda1, encomenda2);
        assertNotSame(encomenda1, encomenda2);
    }
}
