package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NaoConvexoExceptionTest {

    @Test
    void testNaoConvexoException() {
        NaoConvexoException exception = new NaoConvexoException();
        assertNotNull(exception);
        assertEquals("Polígono não convexo!", exception.getMessage());
    }
}

