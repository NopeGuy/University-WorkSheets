package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
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
        p.addPonto(new Ponto(2, 1));
        p.addPonto(new Ponto(3, 2));
        p.addPonto(new Ponto(2, 3));
        p.addPonto(new Ponto(0, 2));
        p.addPonto(new Ponto(0, 0));
        assertFalse(p.eConvexo());
    }

    @Test
    void testEConvexoNaoConvexo() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(new Ponto(2, 1));
        p.addPonto(new Ponto(1, 2));
        p.addPonto(new Ponto(0, 1));
        assertTrue(p.eConvexo());
    }

    @Test
    void testTriangula() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(new Ponto(2, 1));
        p.addPonto(new Ponto(1, 2));
        p.addPonto(new Ponto(0, 1));
        p.addPonto(new Ponto (0, 0));

        List<Triangulo> triangulos = p.triangula();
        assertEquals(3, triangulos.size());
    }

    @Test
    void testArea() {
        PoligonoConvexo p = new PoligonoConvexo();
        p.addPonto(new Ponto(0, 0));
        p.addPonto(new Ponto(1, 0));
        p.addPonto(new Ponto(2, 1));
        p.addPonto(new Ponto(1, 2));
        p.addPonto(new Ponto(0, 1));
        p.addPonto(new Ponto(0, 0));

        double area = p.area();
        DecimalFormat df = new DecimalFormat("#.##");
        area = Math.round(area * 10) / 10.0;
        assertEquals(2.5, area);
    }

    @Test
    void testClone() {
        PoligonoConvexo p1 = new PoligonoConvexo();
        p1.addPonto(new Ponto(1, 1));
        p1.addPonto(new Ponto(2, 2));
        PoligonoConvexo p2 = p1.clone();
        assertNotSame(p1, p2);
        assertNotEquals(p1, p2);
    }
}

