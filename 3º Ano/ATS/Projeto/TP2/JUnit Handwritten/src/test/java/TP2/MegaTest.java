package TP2;    

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import TP2.*;

public class MegaTest {

    private Vintage vintage;
    private Utilizador user1;
    private Utilizador user2;
    private Transportadora transportadora;

    @Before
    public void setUp() {
        vintage = new Vintage();

        vintage.adicionaUtilizador("user1@example.com", "User One", "Morada 1", 1234, "password1");
        vintage.adicionaUtilizador("user2@example.com", "User Two", "Morada 2", 9876, "password2");

        long idUser1 = vintage.getGestor_Utilizadores().get1IdUtilizador("user1@example.com");
        long idUser2 = vintage.getGestor_Utilizadores().get1IdUtilizador("user2@example.com");

        String idArtigo = vintage.getGestor_Artigos().criar_artigo_mala("des1", "marca1", 10, true, 2, 0, 1, 1, 1,
                "camurça", 2020, false, 0);
        String id2Artigo = vintage.getGestor_Artigos().criar_artigo_mala("des2", "marca1", 10, true, 2, 0, 1, 1, 1,
                "camurça", 2020, false, 0);

        //vintage.comprarArtigo(idUser1, idArtigo, new LocalDate.now());

    }

    @Test
    public void complexTest1() {
        // User1 adiciona itens para venda
        TShirt tshirt = new TShirt(TShirt.Tamanho.M, TShirt.Padrao.LISO, "T-Shirt",
                Artigo.Estado.NOVO, 0,
                Artigo.Avaliação.IMPECÁVEL, "T-Shirt", "Brand", "TS123", 20.0, 5.0,
                transportadora.getNome());
        user1.adicionarPorVender(tshirt);

        Sapatilhas sapatilhas = new Sapatilhas(42, true, "Red", LocalDate.of(2023, 1,
                1),
                Sapatilhas.Tipos_Sapatilhas.NORMAL, "Sapatilhas", Artigo.Estado.NOVO, 0,
                Artigo.Avaliação.BOM,
                "Comfortable Shoes", "BrandB", "SP123", 50.0, 10.0,
                transportadora.getNome());
        user1.adicionarPorVender(sapatilhas);

        // Verificar que os itens estão à venda
        assertEquals(2, user1.getPorVender().size());

        // User2 cria uma encomenda
        List<Artigo> carrinho = Arrays.asList(tshirt, sapatilhas);
        Map<Integer, String> vendedores = new HashMap<>();
        vendedores.put(tshirt.getId(), user1.getEmail());
        vendedores.put(sapatilhas.getId(), user1.getEmail());

        Encomenda encomenda = new Encomenda(user2.getEmail(), carrinho,
                vintage.calculaCustoExpedicao(carrinho),
                LocalDate.now(), vendedores);
        vintage.addEncomenda(encomenda);

        // Verifica que a ordem foi adicionada
        assertEquals(1, vintage.getEncomendas().size());

        // Pôr sessão atual como user2
        vintage.SetSessaoAtual(user2.getEmail());

        // Processar a encomenda
        vintage.trataEncomenda(Arrays.asList(tshirt.getId(), sapatilhas.getId()), new ArrayList<>(), new HashMap<>());

        // Verificar que os itens foram removidos da lista de venda de User1
        assertTrue(user1.getPorVender().isEmpty());

        // Verificar que os itens foram adicionados à lista de compras de User2
        assertEquals(2, user2.getCompras().size());

        // Finalizar a encomenda
        encomenda.setEstado(Encomenda.Estado_Encomenda.FINALIZADA);
        vintage.avancarTempo();
        assertEquals(Encomenda.Estado_Encomenda.FINALIZADA, encomenda.getEstado());

        // Verificar que o custo total da encomenda está correto
        double totalCost = encomenda.getPrecoFinal();
        double expectedCost = tshirt.precoFinal(LocalDate.now()) +
                sapatilhas.precoFinal(LocalDate.now())
                + vintage.calculaCustoExpedicao(carrinho);
        assertEquals(expectedCost, totalCost, 0.01);
    }
}
