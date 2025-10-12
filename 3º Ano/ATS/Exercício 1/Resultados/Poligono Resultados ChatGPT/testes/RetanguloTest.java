package poligono;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RetanguloTest {

    @Test
    void testAreaQuadrado() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(0, 2);
        Ponto p3 = new Ponto(2, 2);
        Ponto p4 = new Ponto(2, 0);
        Retangulo retangulo = new Retangulo(p1, p2, p3, p4);
        assertEquals(4.0, retangulo.areaQuadrado(), "Area of rectangle should be 4.0");
    }

    @Test
    void testClone() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(0, 2);
        Ponto p3 = new Ponto(2, 2);
        Ponto p4 = new Ponto(2, 0);
        Retangulo original = new Retangulo(p1, p2, p3, p4);
        Retangulo clone = original.clone();
        assertNotSame(original, clone, "Cloned object should not be the same instance");
    }
}

