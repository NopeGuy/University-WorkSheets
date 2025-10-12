from graph.Graph import Grafo

class GuimaraesStreetGraphGenerator:
    def generate_graph(self):
        guimaraes_graph = Grafo()

        # Edges within Freguesia 1
        guimaraes_graph.add_edge("Alameda Dom Afonso Henriques", "Avenida Conde Margaride", 2)
        guimaraes_graph.add_edge("Alameda Dom Afonso Henriques", "Rua de Santo Antonio", 3)
        guimaraes_graph.add_edge("Avenida Conde Margaride", "Largo Republica do Brasil", 4)
        guimaraes_graph.add_edge("Rua de Santo Antonio", "Avenida D Joao IV", 1)
        guimaraes_graph.add_edge("Rua de Santo Antonio", "Rua da Rainha D Maria II", 5)
        guimaraes_graph.add_edge("Avenida D Joao IV", "Largo do Toural", 3)
        guimaraes_graph.add_edge("Largo do Toural", "Rua da Liberdade", 2)
        guimaraes_graph.add_edge("Alameda Dom Afonso Henriques", "Rua das Flores", 2)
        guimaraes_graph.add_edge("Rua de Santo Antonio", "Rua da Olaria", 4)
        guimaraes_graph.add_edge("Rua da Rainha D Maria II", "Rua dos Artistas", 3)
        guimaraes_graph.add_edge("Largo do Toural", "Rua do Comercio", 1)
        guimaraes_graph.add_edge("Largo do Toural", "Rua do Mercado", 2)

        # Edges within Freguesia 2
        guimaraes_graph.add_edge("Largo da Mumadona", "Rua de Santa Maria", 3)
        guimaraes_graph.add_edge("Largo do Trovador", "Avenida D Afonso Henriques", 10)
        guimaraes_graph.add_edge("Rua de Santa Maria", "Avenida D Afonso Henriques", 2)
        guimaraes_graph.add_edge("Rua de Santa Maria", "Largo do Trovador", 1)
        guimaraes_graph.add_edge("Rua da Oliveira", "Rua Gil Vicente", 3)
        guimaraes_graph.add_edge("Rua Gil Vicente", "Rua Dom Joao I", 2)
        guimaraes_graph.add_edge("Rua Dom Joao I", "Rua da Liberdade", 4)
        guimaraes_graph.add_edge("Largo do Trovador", "Rua da Torre", 2)
        guimaraes_graph.add_edge("Rua da Oliveira", "Rua da Ribeira", 3)
        guimaraes_graph.add_edge("Rua Gil Vicente", "Rua dos Cedros", 1)

        # Edges within Freguesia 3
        guimaraes_graph.add_edge("Rua da Penha", "Avenida Alberto Sampaio", 4)
        guimaraes_graph.add_edge("Avenida Alberto Sampaio", "Rua da Caldeiroa", 3)
        guimaraes_graph.add_edge("Rua da Caldeiroa", "Rua das Fontes", 2)
        guimaraes_graph.add_edge("Rua das Fontes", "Largo da Oliveira", 5)
        guimaraes_graph.add_edge("Rua dos Platanos", "Rua da Penha", 2)
        guimaraes_graph.add_edge("Rua dos Platanos", "Rua da Caldeiroa", 3)
        guimaraes_graph.add_edge("Rua do Carmo", "Rua dos Platanos", 1)
        guimaraes_graph.add_edge("Rua do Carmo", "Rua da Liberdade", 2)
        guimaraes_graph.add_edge("Rua da Liberdade", "Largo do Toural", 3)

        # Edges within Freguesia 4
        guimaraes_graph.add_edge("Avenida D X", "Rua da Abadia", 6)
        guimaraes_graph.add_edge("Rua da Abadia", "Rua Conde Dom Henrique", 2)
        guimaraes_graph.add_edge("Rua Conde Dom Henrique", "Rua Egas Moniz", 7)
        guimaraes_graph.add_edge("Rua Egas Moniz", "Rua de Camoes", 3)
        guimaraes_graph.add_edge("Rua da Saudade", "Rua Conde Dom Henrique", 4)
        guimaraes_graph.add_edge("Rua da Saudade", "Rua da Abadia", 3)
        guimaraes_graph.add_edge("Avenida D X", "Rua da Vila", 2)
        guimaraes_graph.add_edge("Rua Conde Dom Henrique", "Rua das Virtudes", 3)
        guimaraes_graph.add_edge("Rua Egas Moniz", "Rua das Oliveiras", 2)

        # Edges within Freguesia 5
        guimaraes_graph.add_edge("Largo da Feira", "Rua de Fernao Mendes Pinto", 3)
        guimaraes_graph.add_edge("Rua de Fernao Mendes Pinto", "Rua da Santa Cruz", 5)
        guimaraes_graph.add_edge("Rua da Santa Cruz", "Avenida D X2", 1)
        guimaraes_graph.add_edge("Avenida D X2", "Rua da Rainha", 4)
        guimaraes_graph.add_edge("Rua da Rainha", "Rua de Camoes", 2)
        guimaraes_graph.add_edge("Rua dos Cedros", "Largo da Feira", 2)
        guimaraes_graph.add_edge("Rua dos Cedros", "Rua de Fernao Mendes Pinto", 3)
        guimaraes_graph.add_edge("Largo da Feira", "Rua da Igreja", 2)
        guimaraes_graph.add_edge("Rua de Fernao Mendes Pinto", "Rua da Misericordia", 4)

        # Connections between freguesias
        guimaraes_graph.add_edge("Largo do Trovador", "Rua da Penha", 2)
        guimaraes_graph.add_edge("Rua da Liberdade", "Rua da Abadia", 1)
        guimaraes_graph.add_edge("Rua de Camoes", "Rua dos Platanos", 2)
        guimaraes_graph.add_edge("Rua de Camoes", "Rua da Saudade", 2)
        guimaraes_graph.add_edge("Rua de Camoes", "Rua dos Cedros", 1)
        guimaraes_graph.add_edge("Rua do Mercado", "Rua das Fontes", 3)
        guimaraes_graph.add_edge("Rua da Vila", "Rua da Igreja", 1)

        return guimaraes_graph

if __name__ == "__main__":
    generator = GuimaraesStreetGraphGenerator()
    guimaraes_graph = generator.generate_graph()
    print("Guimarães Street Graph:")
    print(guimaraes_graph)
    guimaraes_graph.desenha()
