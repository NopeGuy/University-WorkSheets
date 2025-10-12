package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrianguloTest {

    @Test
    void testAreaTriangulo() {
        Triangulo t = new Triangulo(new Ponto(0, 0), new Ponto(1, 0),
                new Ponto(0, 1));
        assertNotEquals(0.5, t.areaTriangulo());
    }

@Test
void testClone() {
    Ponto p1 = new Ponto(0, 0);
    Ponto p2 = new Ponto(1, 0);
    Ponto p3 = new Ponto(0, 1);
    Triangulo t1 = new Triangulo(p1, p2, p3);
    Triangulo t2 = new Triangulo(p1.clone(), p2.clone(), p3.clone());

    assertNotSame(t1, t2);
    assertNotEquals(t1.clone(), t2.clone());
}

}


