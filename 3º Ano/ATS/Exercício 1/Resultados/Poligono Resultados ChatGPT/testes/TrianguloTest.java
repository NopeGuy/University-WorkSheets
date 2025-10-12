package poligono;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrianguloTest {

    @Test
    void testAreaTriangulo() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(4, 0);
        Ponto p3 = new Ponto(0, 3);
        Triangulo triangulo = new Triangulo(p1, p2, p3);
        assertEquals(6.0, triangulo.areaTriangulo(), 0.001, "Area of triangle should be 6.0");
    }

    @Test
    void testClone() {
        Ponto p1 = new Ponto(0, 0);
        Ponto p2 = new Ponto(4, 0);
        Ponto p3 = new Ponto(0, 3);
        Triangulo original = new Triangulo(p1, p2, p3);
        Triangulo clone = original.clone();
        assertNotSame(original, clone, "Cloned object should not be the same instance");
    }
}

