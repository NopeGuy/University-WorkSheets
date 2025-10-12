package TP1;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class SapatilhasTest {

    @Test
    public void testConstrutorPadrao() {
        Sapatilhas sapatilhas = new Sapatilhas();
        assertEquals(0, sapatilhas.getTamanhoNumerico());
        assertFalse(sapatilhas.getAtilhos());
        assertEquals("", sapatilhas.getCor());
        assertEquals(LocalDate.of(0, Month.JANUARY, 1), sapatilhas.getDataLancamento());
        assertNull(sapatilhas.getTiposSapatilhas());
    }

    @Test
    public void testConstrutorComParametros() {
        LocalDate dataLancamento = LocalDate.of(2020, Month.JANUARY, 1);
        Sapatilhas.Tipos_Sapatilhas tipo = Sapatilhas.Tipos_Sapatilhas.PREMIUM;
        Sapatilhas sapatilhas = new Sapatilhas(42, true, "Preto", dataLancamento, tipo, "Tipo1", Artigo.Estado.NOVO, 0,
                null, "Descrição1", "Marca1", "Código1", 100.0, 10.0, "Transportadora1");

        assertEquals(42, sapatilhas.getTamanhoNumerico());
        assertTrue(sapatilhas.getAtilhos());
        assertEquals("Preto", sapatilhas.getCor());
        assertEquals(dataLancamento, sapatilhas.getDataLancamento());
        assertEquals(tipo, sapatilhas.getTiposSapatilhas());
        assertEquals("Tipo1", sapatilhas.getTipo());
        assertEquals(Artigo.Estado.NOVO, sapatilhas.getEstado());
        assertEquals(0, sapatilhas.getNumeroDonos());
        assertNull(sapatilhas.getAvaliacao());
        assertEquals("Descrição1", sapatilhas.getDescricao());
        assertEquals("Marca1", sapatilhas.getMarca());
        assertEquals("Código1", sapatilhas.getCodigo());
        assertEquals(100.0, sapatilhas.getPrecoBase(), 0.0);
        assertEquals(10.0, sapatilhas.getCorrecaoPreco(), 0.0);
        assertEquals("Transportadora1", sapatilhas.getTransportadora());
    }

    @Test
    public void testSetters() {
        Sapatilhas sapatilhas = new Sapatilhas();
        sapatilhas.setTamanhoNumerico(44);
        assertEquals(44, sapatilhas.getTamanhoNumerico());

        sapatilhas.setAtilhos(true);
        assertTrue(sapatilhas.getAtilhos());

        sapatilhas.setCor("Azul");
        assertEquals("Azul", sapatilhas.getCor());

        LocalDate novaDataLancamento = LocalDate.of(2021, Month.FEBRUARY, 1);
        sapatilhas.setDataLancamento(novaDataLancamento);
        assertEquals(novaDataLancamento, sapatilhas.getDataLancamento());

        Sapatilhas.Tipos_Sapatilhas novoTipo = Sapatilhas.Tipos_Sapatilhas.NORMAL;
        sapatilhas.setTiposSapatilhas(novoTipo);
        assertEquals(novoTipo, sapatilhas.getTiposSapatilhas());
    }

    @Test
    public void testEquals() {
        LocalDate dataLancamento = LocalDate.of(2020, Month.JANUARY, 1);
        Sapatilhas sapatilhas1 = new Sapatilhas(42, true, "Preto", dataLancamento, Sapatilhas.Tipos_Sapatilhas.PREMIUM,
                "Tipo1", Artigo.Estado.NOVO, 0, null, "Descrição1", "Marca1", "Código1", 100.0, 10.0,
                "Transportadora1");
        Sapatilhas sapatilhas3 = new Sapatilhas(44, false, "Branco", dataLancamento, Sapatilhas.Tipos_Sapatilhas.NORMAL,
                "Tipo2", Artigo.Estado.USADO, 1, Artigo.Avaliação.BOM, "Descrição2", "Marca2", "Código2", 200.0, 20.0,
                "Transportadora2");

        assertFalse(sapatilhas1.equals(sapatilhas3));
    }

    @Test
    public void testClone() {
        LocalDate dataLancamento = LocalDate.of(2020, Month.JANUARY, 1);
        Sapatilhas sapatilhas1 = new Sapatilhas(42, true, "Preto", dataLancamento, Sapatilhas.Tipos_Sapatilhas.PREMIUM,
                "Tipo1", Artigo.Estado.NOVO, 0, null, "Descrição1", "Marca1", "Código1", 100.0, 10.0,
                "Transportadora1");
        Sapatilhas sapatilhas2 = sapatilhas1.clone();

        assertEquals(sapatilhas1, sapatilhas2);
        assertNotSame(sapatilhas1, sapatilhas2);
    }

    @Test
    public void testPrecoFinal() {
        LocalDate dataLancamento = LocalDate.of(2020, Month.JANUARY, 1);
        Sapatilhas sapatilhas = new Sapatilhas(46, true, "Preto", dataLancamento, Sapatilhas.Tipos_Sapatilhas.PREMIUM,
                "Tipo1", Artigo.Estado.USADO, 2, Artigo.Avaliação.BOM, "Descrição1", "Marca1", "Código1", 100.0, 10.0,
                "Transportadora1");
        LocalDate data = LocalDate.now();

        double precoEsperado = 100.0 - (10.0 * 0.5 * 2); // Preço base menos correção de preço para usados
        int diferencaAnos = Period.between(dataLancamento, data).getYears();
        precoEsperado *= (1 + diferencaAnos); // Ajuste para tipos_sapatilhas PREMIUM

        assertEquals(precoEsperado, sapatilhas.precoFinal(data), 0.001);
    }
}
