package TP1;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransportadorasTest {

    @Test
    public void testConstrutorPadrao() {
        Transportadoras transportadora = new Transportadoras();
        assertEquals("", transportadora.getNome());
        assertEquals(0.3, transportadora.getImposto(), 0.0);
        assertEquals(0.10, transportadora.getLucro(), 0.0);
        assertFalse(transportadora.isPremium());
        assertEquals(0.0, transportadora.getVolFaturacao(), 0.0);
        assertEquals(1, transportadora.getFormula());
    }

    @Test
    public void testConstrutorComParametros() {
        Transportadoras transportadora = new Transportadoras("Nome1", 0.2, 0.15, true, 500.0);
        assertEquals("Nome1", transportadora.getNome());
        assertEquals(0.2, transportadora.getImposto(), 0.0);
        assertEquals(0.15, transportadora.getLucro(), 0.0);
        assertTrue(transportadora.isPremium());
        assertEquals(500.0, transportadora.getVolFaturacao(), 0.0);
        assertEquals(1, transportadora.getFormula()); // Presume-se que a fórmula padrão é 1
    }

    @Test
    public void testSetters() {
        Transportadoras transportadora = new Transportadoras();
        transportadora.setNome("NovoNome");
        assertEquals("NovoNome", transportadora.getNome());

        transportadora.setImposto(0.25);
        assertEquals(0.25, transportadora.getImposto(), 0.0);

        transportadora.setLucro(0.20);
        assertEquals(0.20, transportadora.getLucro(), 0.0);

        transportadora.setPremium(true);
        assertTrue(transportadora.isPremium());

        transportadora.setVolFaturacao(1000.0);
        assertEquals(1000.0, transportadora.getVolFaturacao(), 0.0);
    }

    @Test
    public void testAddVolFaturacao() {
        Transportadoras transportadora = new Transportadoras();
        transportadora.addVolFaturacao(250.0);
        assertEquals(250.0, transportadora.getVolFaturacao(), 0.0);

        transportadora.addVolFaturacao(100.0);
        assertEquals(350.0, transportadora.getVolFaturacao(), 0.0);
    }

    @Test
    public void testEquals() {
        Transportadoras transportadora1 = new Transportadoras("Nome1", 0.2, 0.15, true, 500.0);
        Transportadoras transportadora2 = new Transportadoras("Nome1", 0.2, 0.15, true, 500.0);
        Transportadoras transportadora3 = new Transportadoras("Nome2", 0.3, 0.10, false, 300.0);

        assertTrue(transportadora1.equals(transportadora2));
        assertFalse(transportadora1.equals(transportadora3));
    }

    @Test
    public void testToString() {
        Transportadoras transportadora = new Transportadoras("Nome1", 0.2, 0.15, true, 500.0);
        String esperado = "Nome: Nome1\n" +
                "Imposto: 0.2\n" +
                "Lucro: 0.15\n" +
                "Premium: true\n" +
                "# Formula: 1\n";

        assertEquals(esperado, transportadora.toString());
    }

    @Test
    public void testClone() {
        Transportadoras transportadora1 = new Transportadoras("Nome1", 0.2, 0.15, true, 500.0);
        Transportadoras transportadora2 = transportadora1.clone();

        assertEquals(transportadora1, transportadora2);
        assertNotSame(transportadora1, transportadora2);
    }

    @Test
    public void testCalculaPrecoExpedicao() {
        Transportadoras transportadora = new Transportadoras("Nome1", 0.2, 0.15, true, 0.0);

        double preco1 = transportadora.calculaPrecoExpedicao(5L);
        assertEquals(6.075, preco1, 0.001);

        double preco2 = transportadora.calculaPrecoExpedicao(2L);
        assertEquals(3.645, preco2, 0.001);

        double preco3 = transportadora.calculaPrecoExpedicao(1L);
        assertEquals(1.215, preco3, 0.001);
    }

    @Test
    public void testAltera() {
        Transportadoras transportadora = new Transportadoras();
        transportadora.altera(0.25, 0.3, 2);

        assertEquals(0.25, transportadora.getLucro(), 0.0);
        assertEquals(0.3, transportadora.getImposto(), 0.0);
        assertEquals(2, transportadora.getFormula());
    }
}
