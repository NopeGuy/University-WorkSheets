package poligono;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NaoConvexoExceptionTest {

    @Test
    void testNaoConvexoException() {
        Exception exception = assertThrows(NaoConvexoException.class, () -> {
            throw new NaoConvexoException();
        });
        assertEquals(null ,exception.getMessage());
    }
}

