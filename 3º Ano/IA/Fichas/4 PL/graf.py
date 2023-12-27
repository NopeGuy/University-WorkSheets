import networkx as nx
import matplotlib.pyplot as plt
from collections import deque
from queue import Queue  

class Graph:
    def __init__(self):
        self.nodes = set()
        self.graph = {}

    def add_edge(self, u, v, weight):
        self.nodes.add(u)
        self.nodes.add(v)

        if u not in self.graph:
            self.graph[u] = []
        if v not in self.graph:
            self.graph[v] = []

        self.graph[u].append((v, weight))
        self.graph[v].append((u, weight))

    def remove_edge(self, u, v):
        if u in self.graph and v in self.graph:
            self.graph[u] = [(neighbor, weight) for neighbor, weight in self.graph[u] if neighbor != v]
            self.graph[v] = [(neighbor, weight) for neighbor, weight in self.graph[v] if neighbor != u]

    def remove_node(self, node):
        if node in self.graph:
            for neighbor in self.graph[node]:
                self.graph[neighbor[0]] = [(n, w) for n, w in self.graph[neighbor[0]] if n != node]
            del self.graph[node]
            self.nodes.remove(node)

    def get_nodes(self):
        return self.nodes

    def get_edges(self):
        return self.graph
    
    def procura_DFS(self, start, end, path=[], visited=set()):
        path.append(start)
        visited.add(start)
        
        if start == end:
            cost = self.calcula_custo(path)
            return path, cost
        
        for neighbor, _ in self.graph[start]:
            if neighbor not in visited:
                result = self.procura_DFS(neighbor, end, path, visited)
                if result is not None:
                    return result

        path.pop()
        return None
    
    def procura_BFS(self, start, end):
        visited = set()
        queue = Queue()  # Create a queue instance
        queue.put(start)
        visited.add(start)
        parent = dict()
        parent[start] = None
        path_found = False
        custo = 0  # Initialize the cost variable
        while not queue.empty() and not path_found:
            current_node = queue.get()
            if current_node == end:
                path_found = True
            else:
                for adjacent, weight in self.graph[current_node]:
                    if adjacent not in visited:
                        queue.put(adjacent)
                        parent[adjacent] = current_node
                        visited.add(adjacent)

        path = []
        if path_found:
            path.append(end)
            while parent[end] is not None:
                path.append(parent[end])
                end = parent[end]
            path.reverse()
            custo = self.calcula_custo(path)

        return path, custo

    def calcula_custo(self, path):
        cost = 0
        for i in range(len(path) - 1):
            node1 = path[i]
            node2 = path[i + 1]
            for neighbor, weight in self.graph[node1]:
                if neighbor == node2:
                    cost += weight
                    break

        return cost

def create_graph():
    G = Graph()
    # Adding premade edges
    G.add_edge('A', 'B', 2.0)
    G.add_edge('B', 'C', 2.0)
    G.add_edge('C', 'D', 3.0)
    G.add_edge('D', 'T', 3.0)
    G.add_edge('Z', 'W', 4.0)
    G.add_edge('A', 'S', 2.0)
    G.add_edge('S', 'E', 2.0)
    G.add_edge('E', 'F', 5.0)
    G.add_edge('F', 'G', 2.0)
    G.add_edge('G', 'T', 2.0)

    while True:
        print("\nMenu:")
        print("1. Add edge")
        print("2. Remove edge")
        print("3. Remove node")
        print("4. Display graph")
        print("5. Search shortest path DFS")
        print("6. Search shortest path BFS")
        print("7. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            src = input("Enter source vertex: ")
            dest = input("Enter destination vertex: ")
            weight = float(input("Enter the weight of the edge: "))
            G.add_edge(src, dest, weight)

        elif choice == 2:
            src = input("Enter source vertex of the edge to remove: ")
            dest = input("Enter destination vertex of the edge to remove: ")
            G.remove_edge(src, dest)
            print(f"Edge ({src}, {dest}) removed.")

        elif choice == 3:
            node = input("Enter the node to remove: ")
            G.remove_node(node)
            print(f"Node {node} and its associated edges removed.")

        elif choice == 4:
            print("Nodes: ", G.get_nodes())
            print("Edges: ", G.get_edges())
            
        elif choice == 5:
            start_node = input("Enter the start node: ")
            end_node = input("Enter the end node: ")
            shortest_path, cost = G.procura_DFS(start_node, end_node)
            if shortest_path:
                print("Shortest path:", shortest_path)
                print("Cost:", cost)
                
        elif choice == 6:
            start_node = input("Enter the start node: ")
            end_node = input("Enter the end node: ")
            shortest_path, cost = G.procura_BFS(start_node, end_node)
            if shortest_path:
                print("Shortest path:", shortest_path)
                print("Cost:", cost)

        elif choice == 7:
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    create_graph()
