package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PoligonoConvexoTest {

    @Test
    void testEConvexoTriangulo() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(1, 1));
        p.addPonto(new Ponto(2, 2));
        p.addPonto(new Ponto(3, 3));
        p.addPonto(new Ponto(1, 1));
        assertTrue(p.eConvexo());
    }
        @Test
    void testEConvexoPentagonoConvexo() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(2, 1);
        p.addPonto(3, 2);
        p.addPonto(2, 3);
        p.addPonto(0, 2);
        p.addPonto(0, 0);
        assertTrue(p.eConvexo());
    }

    @Test
    void testEConvexoNaoConvexo() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(2, 1);
        p.addPonto(1, 2);
        p.addPonto(0, 1);
        assertFalse(p.eConvexo());
    }

    @Test
    void testTriangula() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(2, 1);
        p.addPonto(1, 2);
        p.addPonto(0, 1);
        p.addPonto(0, 0);

        List<Triangulo> triangulos = p.triangula();
        assertEquals(4, triangulos.size());
    }

    @Test
    void testArea() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(2, 1);
        p.addPonto(1, 2);
        p.addPonto(0, 1);
        p.addPonto(0, 0);

        double area = p.area();
        assertEquals(2.5, area);
    }

    @Test
    void testClone() {
        PoligonoConvexo p1 = new PoligonoConvexo();
        p1.addPonto(new Ponto(1, 1));
        p1.addPonto(new Ponto(2, 2));
        PoligonoConvexo p2 = p1.clone();
        assertNotSame(p1, p2);
        assertEquals(p1, p2);
    }
}

