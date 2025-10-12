package TP2;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransportadoraTest {

    @Test
    public void testConstrutorVazio() {
        Transportadora transportadora = new Transportadora();
        assertEquals("Transportadora Invalida", transportadora.getNome());
        assertEquals(0, transportadora.getValor_pequenas(), 0.0);
        assertEquals(0, transportadora.getValor_medio(), 0.0);
        assertEquals(0, transportadora.getValor_grande(), 0.0);
        assertEquals(0, transportadora.getImpostos(), 0.0);
        assertEquals(0, transportadora.getMargemlucro(), 0.0);
        assertEquals(0, transportadora.getTotalLucro(), 0.0);
        assertFalse(transportadora.isPremium());
        assertEquals(0, transportadora.getFormula());
    }

    @Test
    public void testConstrutorParametrizado() {
        Transportadora transportadora = new Transportadora("Transportadora X", 10.0, 20.0, 30.0, 0.2, 0.1, true, 1);
        assertEquals("Transportadora X", transportadora.getNome());
        assertEquals(10.0, transportadora.getValor_pequenas(), 0.0);
        assertEquals(20.0, transportadora.getValor_medio(), 0.0);
        assertEquals(30.0, transportadora.getValor_grande(), 0.0);
        assertEquals(0.2, transportadora.getImpostos(), 0.0);
        assertEquals(0.1, transportadora.getMargemlucro(), 0.0);
        assertEquals(0, transportadora.getTotalLucro(), 0.0);
        assertTrue(transportadora.isPremium());
        assertEquals(1, transportadora.getFormula());
    }

    @Test
    public void testClone() {
        Transportadora transportadora = new Transportadora("Transportadora X", 10.0, 20.0, 30.0, 0.2, 0.1, true, 1);
        Transportadora clone = new Transportadora(transportadora);
        assertEquals(transportadora, clone);
        assertNotSame(transportadora, clone);
    }

    @Test
    public void testPrecoTransportadora1() {
        Transportadora transportadora = new Transportadora("Transportadora X", 10.0, 20.0, 30.0, 0.2, 0.1, true, 1);
        double preco = transportadora.preco_transportadora1(3);
        double expected = (20.0 * 0.1 * (1 + 0.2)) * 0.9;
        assertEquals(expected, preco, 0.01);
        assertEquals(expected, transportadora.getTotalLucro(), 0.01);
    }

    @Test
    public void testPrecoTransportadora2() {
        Transportadora transportadora = new Transportadora("Transportadora X", 10.0, 20.0, 30.0, 0.2, 0.1, true, 2);
        double preco = transportadora.preco_transportadora2(3);
        double expected = (20.0 * (1 + 0.1 + 0.2)) * 0.7;
        assertEquals(expected, preco, 0.01);
        assertEquals(expected, transportadora.getTotalLucro(), 0.01);
    }

    @Test
    public void testPrecoTransportadora3() {
        Transportadora transportadora = new Transportadora("Transportadora X", 10.0, 20.0, 30.0, 0.2, 0.1, true, 3);
        double preco = transportadora.preco_transportadora3(3);
        double expected = (20.0 * 0.1 * (1 + 0.2)) * 1.5;
        assertEquals(expected, preco, 0.01);
        assertEquals(expected, transportadora.getTotalLucro(), 0.01);
    }

    @Test
    public void testFormulas() {
        Transportadora transportadora = new Transportadora();
        assertEquals("(ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 0,9", transportadora.formula1());
        assertEquals("ValorBase * (1 + MargemLucroTransportadora + Imposto) * 0,7", transportadora.formula2());
        assertEquals("(ValorBase * MargemLucroTransportadora * (1 + Imposto)) * 1,5", transportadora.formula3());
    }
}
