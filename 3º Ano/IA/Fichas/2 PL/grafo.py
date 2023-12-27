import networkx as nx
import matplotlib.pyplot as plt

def create_graph():
    G = nx.Graph()
    vertices = int(input("Enter the number of vertices: "))
    G.add_nodes_from(range(vertices))

    while True:
        print("\nMenu:")
        print("1. Add edge")
        print("2. Remove edge")
        print("3. Display graph")
        print("4. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            src = int(input("Enter source vertex: "))
            dest = int(input("Enter destination vertex: "))
            if 0 <= src < vertices and 0 <= dest < vertices:
                G.add_edge(src, dest)
            else:
                print("Invalid vertex. Please try again.")

        elif choice == 2:
            src = int(input("Enter source vertex to remove edge: "))
            dest = int(input("Enter destination vertex to remove edge: "))
            if 0 <= src < vertices and 0 <= dest < vertices:
                if G.has_edge(src, dest):
                    G.remove_edge(src, dest)
                    print("Edge removed.")
                else:
                    print("The specified edge does not exist.")
            else:
                print("Invalid vertex. Please try again.")

        elif choice == 3:
            nx.draw(G, with_labels=True, font_weight='bold', node_color='lightblue', node_size=800, edge_color='gray')
            plt.show()

        elif choice == 4:
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    create_graph()
