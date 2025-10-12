package poligono;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PontoTest {

    @Test
    void testConstructorAndGetters() {
        Ponto p = new Ponto(3.0, 4.0);
        assertEquals(3.0, p.getX(), "X coordinate should be 3.0");
        assertEquals(4.0, p.getY(), "Y coordinate should be 4.0");
    }

    @Test
    void testDistancia() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(3, 4);
        assertEquals(5.0, p1.distancia(p2), "Distance should be 5.0");
    }

    @Test
    void testClone() {
        Ponto p1 = new Ponto(1, 2);
        Ponto clone = p1.clone();
        assertEquals(p1.getX(), clone.getX(), "Cloned point should have the same X");
        assertEquals(p1.getY(), clone.getY(), "Cloned point should have the same Y");
        assertNotSame(p1, clone, "Cloned object should not be the same instance");
    }
}

