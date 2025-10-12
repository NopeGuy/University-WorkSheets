package TP2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class TshirtTest {

    @Test
    public void testConstrutorVazio() {
        Tshirt tshirt = new Tshirt();
        assertNull(tshirt.getTamanho());
        assertNull(tshirt.getPadrao());
    }

    @Test
    public void testConstrutorParametrizado() {
        Tamanho tamanho = Tamanho.M;
        Padrao padrao = Padrao.liso;
        Tshirt tshirt = new Tshirt("Descrição", "Marca", 50.0, false, 0, 0, 1L, 1L, tamanho, padrao);

        assertEquals("Descrição", tshirt.getDescricao());
        assertEquals("Marca", tshirt.getMarca());
        assertEquals(50.0, tshirt.getPreco_base());
        assertFalse(tshirt.getNovo());
        assertEquals(0, tshirt.getEstado_uso());
        assertEquals(0, tshirt.getN_utilizadores());
        assertEquals(1L, tshirt.getIdTransportadora());
        assertEquals(1L, tshirt.getIdVendedor());
        assertEquals(tamanho, tshirt.getTamanho());
        assertEquals(padrao, tshirt.getPadrao());
    }

    @Test
    public void testGetSetTamanho() {
        Tshirt tshirt = new Tshirt();
        Tamanho tamanho = Tamanho.L;
        tshirt.setTamanho(tamanho);
        assertEquals(tamanho, tshirt.getTamanho());
    }

    @Test
    public void testGetSetPadrao() {
        Tshirt tshirt = new Tshirt();
        Padrao padrao = Padrao.liso;
        tshirt.setPadrao(padrao);
        assertEquals(padrao, tshirt.getPadrao());
    }

    @Test
    public void testClone() {
        Tamanho tamanho = Tamanho.S;
        Padrao padrao = Padrao.riscas;
        Tshirt tshirt = new Tshirt("Descrição", "Marca", 50.0, true, 1, 2, 3L, 4L, tamanho, padrao);
        Tshirt clone = tshirt.clone();
        assertEquals(tshirt, clone);
        assertNotSame(tshirt, clone);
    }

    @Test
    public void testEquals() {
        Tamanho tamanho = Tamanho.M;
        Padrao padrao = Padrao.liso;
        Tshirt tshirt1 = new Tshirt("Descrição", "Marca", 50.0, false, 0, 0, 1L, 1L, tamanho, padrao);
        Tshirt tshirt2 = new Tshirt("Descrição", "Marca", 50.0, false, 0, 0, 1L, 1L, tamanho, padrao);
        assertEquals(tshirt1, tshirt2);
    }

    @Test
    public void testPrecoAtual() {
        Tamanho tamanho = Tamanho.M;
        Padrao padrao = Padrao.riscas;
        Tshirt tshirt = new Tshirt("Descrição", "Marca", 100.0, false, 0, 0, 1L, 1L, tamanho, padrao);
        assertEquals(50.0, tshirt.preco_atual(LocalDate.now()));

        tshirt.setPadrao(Padrao.liso);
        assertEquals(100.0, tshirt.preco_atual(LocalDate.now()));

        tshirt.setNovo(true);
        assertEquals(100.0, tshirt.preco_atual(LocalDate.now()));
    }

    @Test
    public void testToString() {
        Tamanho tamanho = Tamanho.M;
        Padrao padrao = Padrao.riscas;
        Tshirt tshirt = new Tshirt("Descrição", "Marca", 50.0, false, 0, 0, 1L, 1L, tamanho, padrao);
        String expected = "T-shirt:: {Artigo:: {Descrição: Descrição Marca: Marca Preço Base: 50.0 Novo: false Estado de Uso: 0 Número de Utilizadores: 0 Transportadora: 1 Vendedor: 1} Tamanho: M Padrão: listrado}";
        assertEquals(expected, tshirt.toString());
    }
}
