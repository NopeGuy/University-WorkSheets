package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PontoTest {

    @Test
    void testDistancia() {
        Ponto p1 = new Ponto(1, 1);
        Ponto p2 = new Ponto(4, 5);
        assertEquals(5.0, p1.distancia(p2));
    }

    @Test
    void testSimetrico() {
        Ponto p1 = new Ponto(1, 1);
        Ponto p2 = new Ponto(-1, -1);
        assertTrue(p1.simetrico());
        assertTrue(p2.simetrico());
    }

    @Test
    void testEquals() {
        Ponto p1 = new Ponto(1, 1);
        Ponto p2 = new Ponto(1, 1);
        assertEquals(p1, p2);
    }

    @Test
    void testClone() {
        Ponto p1 = new Ponto(1, 1);
        Ponto p2 = p1.clone();
        assertNotSame(p1, p2);
        assertEquals(p1, p2);
    }
}

